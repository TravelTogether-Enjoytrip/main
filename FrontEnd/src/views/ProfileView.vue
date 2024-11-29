<script setup>
import GroupCard from "@/components/Profile/GroupCard.vue";
import PlanCard from "@/components/Profile/PlanCard.vue";
import SearchBar from "@/components/Profile/SearchBar.vue";
// 컴포넌트
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";
import { storeToRefs } from "pinia";
// 기타 기능
import { useUserStore } from "@/stores/user";
import { getMyGroupList } from "@/api/user";
// 직접 만든 함수

let token = sessionStorage.getItem("accessToken");
// 로그인 토큰
const userStore = useUserStore();
const { userInfo } = storeToRefs(userStore);
const router = useRouter();
const route = useRoute();

onMounted(() => {
  userStore.getUserInfo(token);
  getGroupList();
  getTour();
  console.log(userInfo);
});

const groupList = ref([]);
const getGroupList = async () => {
  await getMyGroupList(
    userInfo.value.userId,
    (response) => {
      if (response.status === httpStatusCode.OK) {
        console.log("성공", response.data);
        groupList.value = response.data;
      } else {
        console.log("그룹 리스트 없음!");
      }
    },
    async (error) => {
      console.error(
        "그룹 리스트 없음!",
        error.response.status,
        error.response.statusText
      );
    }
  );
};

import { findById } from "@/api/user";
import { httpStatusCode } from "@/util/http-status";

const addGroup = ref(false);
const addPlanModal = ref(false);
const plan = ref({
  startdate: Date.now(),
  enddate: Date.now(),
});
const addTour = () => {
  router.push({
    name: "addtour",
    params: { userid: route.params.userid },
    query: { startdate: plan.value.startdate, enddate: plan.value.enddate },
  });
};

const text_ = ref("");
const userName = ref("");
const textChange = async (text) => {
  // 나중에 kmp 같은 거 써서 입력값을 prefix로 포함하는 모든 유저 아이디 검색하기
  console.log(text);
  await findById(
    text,
    (response) => {
      if (
        response.status === httpStatusCode.OK &&
        response.data.userInfo !== null
      ) {
        text_.value = text;
        console.log("성공", response.data.userInfo);
      } else {
        text_.value = "";
        console.log("유저 정보 없음!!!!");
      }
    },
    async (error) => {
      text_.value = "";
      console.error(
        "유저 정보 없음!",
        error.response.status,
        error.response.statusText
      );
    }
  );
};

const group = ref({});
const selectedUserList = ref([userInfo.value.userId]);
// const prefixUserList = ref([]);
const addGroupMember = (text) => {
  if (text !== "" && !selectedUserList.value.includes(text)) {
    selectedUserList.value.push(text);
  }
};
const makeGroup = () => {
  group.value.groupMembers = selectedUserList.value;
  axios
    .post(`http://localhost:8080/groupapi/makegroup`, group.value)
    .then((res) => {
      console.log(res);
      addGroup.value = false;
      selectedUserList.value = [userInfo.value.userId];
      getGroupList();
    })
    .catch((err) => {
      console.log(err);
    });
};

const tourList = ref({});

const getTour = () => {
  axios
    .get(`http://localhost:8080/userapi/getTour/${userInfo.value.userId}`)
    .then((res) => {
      console.log("tourId : " + res.data);
      tourList.value = res.data;
      console.log(tourList.value);
    })
    .catch((err) => {
      console.log(err);
    });
};

const deleteSelectedUser = (userName) => {
  selectedUserList.value = selectedUserList.value.filter(
    (ele) => ele !== userName
  );
};

const goGroupPage = (key) => {
  router.push({ name: "group", params: { groupid: key } });
};

const goModify = () => {
  router.push({ name: "user-modify" });
};
</script>

<template>
  <div id="profile">
    <div id="profile-left">
      <img id="profile_img" src="@\assets\profile_image.jpeg" alt="" />
      <div id="profile-description">
        <p class="user-info-header">
          회 &nbsp; &nbsp; 원 &nbsp; &nbsp; 정 &nbsp; &nbsp; 보
        </p>
        <div class="user-info-main" @submit.prevent="joinUser">
          <label for="userId">
            <div class="user-info-element">
              <div class="user-info-element-title">아이디</div>
              <div class="user-info-content">{{ userInfo.userId }}</div>
            </div>
          </label>
          <label for="name">
            <div class="user-info-element">
              <div class="user-info-element-title">이름</div>
              <div class="user-info-content">{{ userInfo.name }}</div>
            </div>
          </label>
          <!-- <label for="password">
            <div class="user-info-element">
              <div class="user-info-element-title">비밀번호</div>
              <div class="user-info-content">{{ userInfo.name }}</div>
            </div>
          </label> -->
          <label for="email">
            <div class="user-info-element">
              <div class="user-info-element-title">이메일</div>
              <div class="user-info-content">{{ userInfo.email }}</div>
            </div>
          </label>
          <label for="address">
            <div class="user-info-element">
              <div class="user-info-element-title">주소</div>
              <div class="user-info-content">{{ userInfo.address }}</div>
            </div>
          </label>
          <div class="user-info-buttons">
            <div id="user-info-button-modify" @click="goModify">
              회원정보 수정
            </div>
          </div>
        </div>
      </div>
    </div>
    <div id="profile-right">
      <div id="groups">
        <div id="groups-head" class="list-head">
          <p class="list-head-title">GROUP</p>
          <button class="plus-button" @click="addGroup = true">+</button>
        </div>
        <div id="groups-main" class="list">
          <GroupCard
            @click="goGroupPage(gl.groupId)"
            v-for="gl in groupList"
            :groupInfo="gl"
            :key="gl.groupId"
          />
        </div>
      </div>
      <div id="plans">
        <div id="plans-head" class="list-head">
          <p class="list-head-title">PLAN</p>
          <button class="plus-button" @click="addPlanModal = true">+</button>
        </div>
        <div id="plans" class="list">
          <div v-for="i in tourList">{{ i }} 번 계획</div>
          <!-- <PlanCard @click="goGroupPage(i.tourId)" v-for="i in tourList">{{ i.tourId }}<PlanCard/> -->
        </div>
      </div>
    </div>
  </div>
  <!-- Modal -->
  <Teleport to="body">
    <div v-if="addGroup" class="modal-bg" @click="addGroup = false">
      <div class="modal" @click.stop="">
        <div id="button-close" @click="addGroup = false">x</div>
        <div class="modal-main">
          <p class="modal-title">그룹 추가하기</p>
          <form
            class="modal-form"
            @submit.prevent="makeGroup"
            @keypress.enter.prevent
          >
            <input
              type="text"
              class="modal-input"
              v-model="group.groupName"
              placeholder="그룹 이름"
              required
            />
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
              <div class="selected-user" v-for="sul in selectedUserList">
                {{ sul }}
                <div
                  v-if="sul !== $route.params.userid"
                  class="delete-user"
                  @click="deleteSelectedUser(sul)"
                >
                  x
                </div>
              </div>
            </div>
            <div type="submit" class="submit-button">그룹 만들기</div>
          </form>
        </div>
      </div>
    </div>
  </Teleport>
  <Teleport to="body">
    <div v-if="addPlanModal" class="modal-bg" @click="addPlanModal = false">
      <div class="modal" @click.stop="">
        <div id="button-close" @click="addPlanModal = false">x</div>
        <div class="modal-main">
          <p class="modal-title">개인 계획 추가하기</p>
          <div class="modal-form">
            <label class="modal-label" for="start-date"
              >여행 시작 날짜:
              <input type="date" id="start-date" v-model="plan.startdate"
            /></label>
            <br />
            <label class="modal-label" for="end-date"
              >여행 복귀 날짜:
              <input
                type="date"
                id="end-date"
                v-model="plan.enddate"
                :min="plan.startdate"
            /></label>
            <br />
            <div class="submit-button" @click="addTour">Next</div>
            <br />
          </div>
        </div>
      </div>
    </div>
  </Teleport>
</template>

<style scoped>
#profile {
  display: flex;
  justify-content: space-between;
  /* height: 60rem; */
  font-family: Arial, Helvetica, sans-serif;
  width: 55%;
}
#profile-left {
  display: flex;
  align-items: center;
  width: 66%;
  background-color: #f5f5f5;
}

#profile_img {
  --img-width: 200px;
  width: var(--img-width);
  margin-left: 30px;
}

#profile-right {
  border-top: 1px solid;
  border-bottom: 1px solid;
  width: 30%;
  padding-left: 10px;
}

#groups {
}
.list {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
}
.list-head {
  display: flex;
  flex-direction: row;
  align-items: center;
}
#plans {
}

/* Modal 관련 css */

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

/* ----------------------------------------------- user info dexcription */
#profile-description {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #f5f5f5;
  padding-left: 30px;
  padding-right: 30px;
  width: 100%;
}
.user-info-header {
  color: #aa210f;
}

.user-info-main {
  width: 100%;
}

.user-info-element {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  border-bottom: 1px solid;
  margin-bottom: 1em;
  width: 100%;
  font-size: 20px;
  font-weight: lighter;
}
.user-info-element-title {
  color: darkgray;
  font-family: system-ui;
  font-weight: 100;
  border: none;
}

.user-info-content {
  caret-color: #aa210f;
  border: none;
  outline: none;
  width: 70%;
  font-size: 20px;
  font-weight: lighter;
  font-family: cursive;
  color: #41403c;
}

.user-info-buttons {
  text-align: center;
  display: flex;
  justify-content: center;
}

#user-info-button-modify {
  color: #aa210f;
  width: 100%;
  cursor: pointer;
  padding: 0.75em 1.5em;
  margin: 0.5em;
  font-size: 0.9em;
}
#user-info-button-modify:hover {
  color: #0f98aa;
}
#user-info-button-modify:active {
  color: #aa210f;
}
</style>
