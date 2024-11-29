<script setup>
import GroupMemberCard from "@/components/Group/GroupMemberCard.vue";
import SearchBar from "@/components/Profile/SearchBar.vue";
// 컴포넌트
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import axios from "axios";
import { storeToRefs } from "pinia";
// 기타 기능
import { useUserStore } from "@/stores/user";
import { getMyGroupMemberList } from "@/api/user";
import { httpStatusCode } from "@/util/http-status";
// 직접 만든 함수
import { findById } from "@/api/user";

onMounted(() => {
  userStore.getUserInfo(token);
  getGroupMemberList();
});

// const addMember = () => {};
const addMember = ref(false);

let token = sessionStorage.getItem("accessToken");
const userStore = useUserStore();
const { userInfo } = storeToRefs(userStore);
const router = useRouter();
const route = useRoute();

const text_ = ref("");

const groupMemberList = ref([]);
const getGroupMemberList = async () => {
  await getMyGroupMemberList(
    route.params.groupid,
    (response) => {
      if (response.status === httpStatusCode.OK) {
        console.log("멤버 가져오기 성공", response.data);
        groupMemberList.value = response.data;
        // 현재 로그인 된 유저를 배열의 맨 앞으로 보낸다 start //
        const isUserIndex = groupMemberList.value.findIndex((ele) => ele.userId === route.params.userid);
        let element = groupMemberList.value.splice(isUserIndex, 1)[0];
        groupMemberList.value.unshift(element);
        // 현재 로그인 된 유저를 배열의 맨 앞으로 보낸다 end //
      } else {
        console.log("멤버 가져오기 실패!");
      }
    },
    async (error) => {
      console.error("그룹 리스트 없음!", error.response.status, error.response.statusText);
    }
  );
};

const textChange = async (text) => {
  // 나중에 kmp 같은 거 써서 입력값을 prefix로 포함하는 모든 유저 아이디 검색하기
  await findById(
    text,
    (response) => {
      if (response.status === httpStatusCode.OK && response.data.userInfo !== null) {
        text_.value = text;
        console.log("성공", response.data.userInfo);
      } else {
        text_.value = "";
        console.log("유저 정보 없음!!!!");
      }
    },
    async (error) => {
      text_.value = "";
      console.error("유저 정보 없음!", error.response.status, error.response.statusText);
    }
  );
};
const userName = ref("");
const group = ref({});
const selectedUserList = ref([]);

const isGroupHasMember = (text) => {
  groupMemberList.value.forEach((ele) => {
    console.log(ele.userid);
    if (ele.userId === text) return true;
  });
  return false;
};

const addGroupMember = (text) => {
  if (
    !selectedUserList.value.includes(text) &&
    !groupMemberList.value.includes(text)
  ) {
    selectedUserList.value.push(text);
  }
};

const modifyGroup = () => {
  group.value.groupMembers = selectedUserList.value;
  console.log(group.value.groupMembers);
  axios
    .post(`http://localhost:8080/groupapi/modifygroup/${route.params.groupid}`, group.value)
    .then((res) => {
      console.log(res);
      addMember.value = false;
      selectedUserList.value = [userInfo.value.userId];
      getGroupMemberList();
    })
    .catch((err) => {
      console.log(err);
    });
};
</script>

<template>
  <div id="group-view">
    <div id="group-members">
      <div id="group-members-head" class="list-head">
        <p class="list-head-title">그룹 멤버</p>
        <button class="plus-button" @click="addMember = true">+</button>
      </div>
      <hr />
      <div id="group-members-main" class="list-body">
        <GroupMemberCard v-for="gm in groupMemberList" :memberInfo="gm" :isUser="gm.userId === $route.params.userid" :key="gm.userId" />
      </div>
    </div>
  </div>
  <Teleport to="body">
    <div v-if="addMember" class="modal-bg" @click="addMember = false">
      <div class="modal" @click.stop="">
        <div id="button-close" @click="addMember = false">x</div>
        <div class="modal-main">
          <p class="modal-title">그룹 추가하기</p>
          <form
            class="modal-form"
            @submit.prevent="modifyGroup"
            @keypress.enter.prevent
          >
            <input
              type="text"
              @keyup="textChange(userName)"
              placeholder="유저명을 입력하세요."
              class="modal-input"
              v-model="userName"
              @keypress.enter="addGroupMember(text_)"
            />
            <div class="searched-user" @click="addGroupMember(text_)">
              {{ text_ }}
            </div>
            <div class="selected-users">
              <div class="selected-user" v-for="su in selectedUserList">
                {{ su }}
                <div
                  v-if="sul !== $route.params.userid"
                  class="delete-user"
                  @click="deleteSelectedUser(sul)"
                >
                  x
                </div>
              </div>
            </div>
            <button class="submit-button" type="submit">addMember</button>
          </form>
        </div>
      </div>
    </div>
  </Teleport>
</template>

<style scoped>
#group-view {
  width: 55%;
  font-family: Arial, Helvetica, sans-serif;
}
#group-members {
  border: 1px solid black;
}
.list-body {
  display: flex;
  flex-wrap: wrap;
}
.list-head {
  display: flex;
  flex-direction: row;
  align-items: center;
}
button {
  --button-size: 20px;
  width: var(--button-size);
  height: var(--button-size);
  text-align: center;
  padding: 0px;
  margin: 0 0 0 5px;
}

.list-head-title {
  line-height: 18px;
}

.plus-button,
.plus-button:active {
  color: #edefee;
  background-color: #41403c;
  --button-size: 20px;
  width: var(--button-size);
  height: var(--button-size);
  text-align: center;
  padding: 0px;
  margin: 0 0 0 5px;
}
.plus-button:hover {
  color: #edefee;
  background-color: #aa210f;
}

/* --------------------------------------------------------- Modal 관련 css */
#modal-group-name {
}

#button-make-group {
  --button-size: 20px;
  width: var(--button-size);
  height: var(--button-size);
  text-align: center;
  padding: 0px;
  margin: 0 0 0 5px;
}

#button-close {
  --button-size: 20px;
  width: var(--button-size);
  height: var(--button-size);
  text-align: center;
  padding: 0px;
  background-color: #41403c;
  color: #edefee;
  position: fixed;
}

#button-close:active {
  background-color: #aa210f;
  color: #edefee;
}

.modal {
  z-index: 999;
  position: fixed;
  left: 50%;
  top: 20%;
  transform: translate(-50%, 0);
  width: 300px;
  height: 400px;
  font-family: Arial, Helvetica, sans-serif;
  background-color: #edefee;
  display: flex;
  flex-direction: row-reverse;
}

.modal-main {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  color: #41403c;
  justify-content: center;
}

.modal-bg {
  position: fixed;
  z-index: 998;
  background-color: rgba(0, 0, 0, 0.5); /* 검은색 반투명 배경 */
  width: 3000px;
  height: 3000px;
  top: 0%;
  left: 0%;
}
.modal-title {
  margin: 0;
}

.modal-input {
  background-color: white;
  color: #41403c;
  margin-bottom: 10px;
}
.modal-input:focus {
  outline: none;

  caret-color: #41403c;
}
.modal-form {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
}
.searched-user {
}

.modal-label {
  font-size: 14px;
}
.selected-users {
  border-top: solid 1px;
  border-bottom: solid 1px;
  width: 100%;
  padding-top: 5px;
  padding-bottom: 5px;
  margin: 10px 0;
}

.selected-user {
  display: flex;
}

.delete-user {
  margin-left: 5px;
}

.submit-button {
  --height: 30px;
  border-radius: 0;
  background-color: #41403c;
  color: #edefee;
  font-size: 13px;
  width: 100%;
  height: var(--height);
  line-height: var(--height);
  margin-bottom: 10px;
  align-items: center;
  text-align: center;
}
.submit-button:active {
  background-color: #aa210f;
}

.list-head-title {
  line-height: 18px;
}

.plus-button,
.plus-button:active {
  border: 0;
  color: #edefee;
  background-color: #41403c;
  margin-left: 5px;
}
.plus-button:hover {
  color: #edefee;
  background-color: #aa210f;
}

.submit-button {
  --height: 30px;
  border-radius: 0;
  background-color: #41403c;
  color: #edefee;
  font-size: 13px;
  width: 100%;
  height: var(--height);
  line-height: var(--height);
  margin-bottom: 10px;
  align-items: center;
  text-align: center;
}
</style>
