<script setup>
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import axios from "axios";
import { ref, onMounted } from "vue";
import { useUserStore } from "@/stores/user";

const router = useRouter();

let token = sessionStorage.getItem("accessToken");

const userStore = useUserStore();

const userId = ref("");

const { isLogin, userInfo } = storeToRefs(userStore);
const { userLogout } = userStore;

const modifyUser = () => {
  axios
    .post(`http://localhost:8080/userapi/modify`, user.value)
    .then((res) => {
      console.log(res);
      router.push({ name: "home" });
    })
    .catch((err) => {
      console.log(err);
    });
};

const deleteUser = async () => {
  try {
    userId.value = userInfo.value.userId;

    await axios.put(
      `http://localhost:8080/postapi/post/setnull/${userId.value}`
    );

    const res = await axios.delete(
      `http://localhost:8080/userapi/delete/${userId.value}`
    );

    console.log(res);
    router.push({ name: "home" }).then(() => {
      window.location.reload();
    });
  } catch (err) {
    console.log(err);
  }
};

const user = ref({
  userId: userInfo.value.userId,
  name: userInfo.value.name,
  email: userInfo.value.email,
  address: userInfo.value.address,
  password: "",
});

onMounted(() => {
  userStore.getUserInfo(token);
});
</script>

<template>
  <div id="user-info-modify">
    <img id="user-info-img" src="@\assets\profile_image.jpeg" alt="" />
    <div id="user-info-description">
      <p class="user-info-header">
        회 &nbsp; &nbsp; 원 &nbsp; &nbsp; 정 &nbsp; &nbsp; 보
      </p>
      <form class="user-info-main" @submit.prevent="joinUser">
        <label for="userId">
          <div class="user-info-element">
            <div class="user-info-element-title">아이디</div>
            <div class="user-info-input">{{ userInfo.userId }}</div>
          </div>
        </label>
        <label for="name">
          <div class="user-info-element">
            <div class="user-info-element-title">이름</div>
            <input
              type="text"
              id="name"
              class="user-info-input"
              v-model="user.name"
              :placeholder="userInfo.name"
              required
            />
          </div>
        </label>
        <label for="password">
          <div class="user-info-element">
            <div class="user-info-element-title">새 비밀번호</div>
            <input
              type="password"
              id="password"
              class="user-info-input"
              v-model="user.password"
              required
            />
          </div>
        </label>
        <label for="email">
          <div class="user-info-element">
            <div class="user-info-element-title">이메일</div>
            <input
              type="text"
              id="email"
              class="user-info-input"
              v-model="user.email"
              :placeholder="userInfo.email"
              required
            />
          </div>
        </label>
        <label for="address">
          <div class="user-info-element">
            <div class="user-info-element-title">주소</div>
            <input
              type="text"
              id="address"
              class="user-info-input"
              v-model="user.address"
              :placeholder="userInfo.address"
              required
            />
          </div>
        </label>
        <div class="user-info-buttons">
          <button
            type="submit"
            id="user-info-button-modify"
            class="user-info-button"
          >
            수정
          </button>
          <button
            type="button"
            id="user-info-button-resign"
            class="user-info-button"
            @click="deleteUser"
          >
            탈퇴
          </button>
        </div>
      </form>
    </div>
  </div>

  <!-- <div class="container">
    <p class="user-info-header">회원정보 수정</p>
    <form @submit.prevent="modifyUser">
      <div class="user-info-element">
        <label for="userId">아이디:</label>
        <input
          type="text"
          id="userId"
          class="user-info-input"
          v-model="user.userId"
          readonly
        />
      </div>
      <div class="user-info-element">
        <label for="name">이름:</label>
        <input type="text" id="name" v-model="user.name" readonly />
      </div>
      <div class="user-info-element">
        <label for="password">비밀번호:</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div class="user-info-element">
        <label for="address">주소:</label>
        <input type="text" id="address" v-model="user.address" required />
      </div>
      <div class="user-info-buttons">
        <div
          type="submit"
          id="user-info-button-modify"
          class="user-info-button"
        >
          수정
        </div>
        <div
          type="button"
          id="user-info-button-resign"
          class="user-info-button"
          @click="deleteUser"
        >
          탈퇴
        </div>
      </div>
    </form>
  </div> -->
</template>

<style scoped>
#user-info-modify {
  display: flex;
  align-items: center;
  width: 100%;
  background-color: #f5f5f5;
  font-family: Arial, Helvetica, sans-serif;
}

#user-info-img {
  --img-width: 200px;
  width: var(--img-width);
  margin-left: 30px;
}
#user-info-description {
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

.user-info-input {
  caret-color: #aa210f;
  border: none;
  outline: none;
  width: 70%;
  font-size: 20px;
  font-family: system-ui;
  font-weight: 100;
  color: #41403c;
  background-color: #f5f5f5;
}

.user-info-buttons {
  text-align: center;
  display: flex;
  justify-content: center;
}

#user-info-button-modify,
#user-info-button-resign {
  color: #aa210f;
  width: 100%;
  cursor: pointer;
  padding: 0.75em 1.5em;
  font-size: 1em;
  background-color: transparent;
  outline: none;
  border: none;
  margin-bottom: 1em;
}
#user-info-button-modify:hover,
#user-info-button-resign:hover {
  color: #0f98aa;
}
#user-info-button-modify:active,
#user-info-button-resign:active {
  color: #aa210f;
}
</style>
