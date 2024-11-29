<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";

const router = useRouter();

const userStore = useUserStore();

const { isLogin, isLoginError } = storeToRefs(userStore);
const { userLogin, getUserInfo } = userStore;

const loginUser = ref({
  userId: "",
  userPwd: "",
});

const login = async () => {
  await userLogin(loginUser.value);
  let token = sessionStorage.getItem("accessToken");
  console.log(token);
  console.log("isLogin: " + isLogin.value);
  if (isLogin.value) {
    getUserInfo(token);
    router.replace({ name: "home" });
  }
};

const goJoin = () => {
  router.push({ name: "user-join" });
};
</script>

<template>
  <div class="container">
    <p class="user-info-header">로 &nbsp &nbsp 그 &nbsp &nbsp 인</p>
    <form @submit.prevent>
      <div class="user-info-element">
        <label for="userid">
          <input type="text" v-model="loginUser.userId" placeholder="아이디"
        /></label>
      </div>
      <div class="user-info-element">
        <label for="userpwd">
          <input
            type="password"
            v-model="loginUser.userPwd"
            @keyup.enter="login"
            placeholder="비밀번호"
        /></label>
      </div>

      <div class="user-info-element" v-if="isLoginError === true">
        <div class="error-message">아이디 또는 비밀번호를 확인해 주세요</div>
      </div>
      <div class="form-actions">
        <button @click="login">로그인</button>
        <button @click="goJoin">회원가입</button>
      </div>
    </form>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #f5f5f5;
  padding-left: 30px;
  padding-right: 30px;
  font-family: Arial, Helvetica, sans-serif;
}

.user-info-header {
  color: #aa210f;
}

.user-info-element {
  display: flex;
  margin-bottom: 1em;
}

input {
  border: none;
  background: transparent;
  width: 95%;
  border-bottom: solid 1px;
  caret-color: #aa210f;
  font-size: 20px;
  font-weight: lighter;
}
input:focus {
  outline: none;
}

.error-message {
  color: #aa210f;
  font-size: 0.75em;
  margin-bottom: 1em;
}

.form-actions {
  text-align: center;
}

button {
  padding: 0.75em 1.5em;
  margin: 0.5em;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button {
  background: transparent;
  color: #aa210f;
  font-size: 0.9em;
}
button:active {
  color: #aa210f;
}
button:hover {
  color: #0f98aa;
}
</style>
