<script setup>
import { useRouter } from "vue-router";
import axios from "axios";
import { ref } from "vue";

const router = useRouter();

const joinUser = () => {
  axios
    .post(`http://localhost:8080/userapi/join`, user.value)
    .then((res) => {
      console.log(res);
      router.push({ name: "home" });
    })
    .catch((err) => {
      console.log(err);
    });
};

const user = ref({});
</script>

<template>
  <div class="user-info-container">
    <p class="user-info-header">회 &nbsp &nbsp 원 &nbsp &nbsp 가 &nbsp &nbsp 입</p>
    <form class="user-info-main" @submit.prevent="joinUser">
      <label for="userId">
        <div class="user-info-element">
          <div class="user-info-element-title">아이디</div>
          <input type="text" class="user-info-input" id="userId" v-model="user.userId" required />
        </div>
      </label>
      <label for="name">
        <div class="user-info-element">
          <div class="user-info-element-title">이름</div>
          <input type="text" id="name" class="user-info-input" v-model="user.name" required />
        </div>
      </label>
      <label for="password">
        <div class="user-info-element">
          <div class="user-info-element-title">비밀번호</div>
          <input type="password" id="password" class="user-info-input" v-model="user.password" required />
        </div>
      </label>
      <label for="email">
        <div class="user-info-element">
          <div class="user-info-element-title">이메일</div>
          <input type="text" id="email" class="user-info-input" v-model="user.email" required />
        </div>
      </label>
      <label for="address">
        <div class="user-info-element">
          <div class="user-info-element-title">주소</div>
          <input type="text" id="address" class="user-info-input" v-model="user.address" required />
        </div>
      </label>
      <div class="user-info-buttons">
        <button id="user-info-button-join" type="submit">회원가입</button>
      </div>
    </form>
  </div>
</template>

<style scoped>
.user-info-container {
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
  font-weight: lighter;

  background-color: #f5f5f5;
}

.user-info-buttons {
  text-align: center;
  display: flex;
  justify-content: center;
}

#user-info-button-join {
  color: #aa210f;
  width: 100%;
  cursor: pointer;
  padding: 0.75em 1.5em;
  margin: 0.5em;
  font-size: 0.9em;
  background: none;
  border: none;
}
#user-info-button-join:hover {
  color: #0f98aa;
}
#user-info-button-join:active {
  color: #aa210f;
}
</style>
