<script setup>
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
import { ref, onMounted } from "vue";

let token = sessionStorage.getItem("accessToken");
const router = useRouter();

const userStore = useUserStore();

const { userInfo } = storeToRefs(userStore);

const { userLogout } = userStore;

const goProfile = () => {
  router.push({ name: "profile", params: { userid: userInfo.value.userId } });
  toggleDropdown();
};

const goHome = () => {
  router.push({ name: "home" });
};

const goList = () => {
  router.push({ name: "post" });
};

const goLogin = () => {
  router.push({ name: "user" });
};

const goJoin = () => {
  router.push({ name: "user-join" });
};

const goSearch = () => {
  router.push({ name: "map-search" });
};

const goModify = () => {
  router.push({ name: "user-modify" });
  toggleDropdown();
};

const logout = () => {
  userLogout();
  toggleDropdown();
};

const isDropdownOpen = ref(false);

const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value;
};

onMounted(() => {
  userStore.getUserInfo(token);
});
</script>

<template>
  <div id="header">
    <router-link id="logo">
      <div @click="goHome">TravelTogether</div>
    </router-link>

    <div id="nav_bar">
      <div class="header-nav-button" @click="goList">Board</div>
      <div class="header-nav-button" @click="goSearch">Search</div>
      <!-- <div v-if="userInfo !== null" class="dropdown">
        <div v-if="isDropdownOpen" class="dropdown-content">
          <a @click="goProfile">View Profile</a>
          <a @click="goModify">Modify Profile</a>
          <a @click.prevent="logout">Logout</a>
        </div>
      </div> -->
      <div v-if="userInfo !== null" class="header-nav-button" @click="goProfile">Profile</div>
      <div v-if="userInfo !== null" class="header-nav-button" @click="logout">Logout</div>
      <div v-if="userInfo === null" class="header-nav-button" @click="goLogin">Login</div>
      <div v-if="userInfo === null" class="header-nav-button" @click="goJoin">Join</div>
    </div>
  </div>
</template>

<style scoped>
#header {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  mix-blend-mode: multiply;
  position: relative;
  margin-bottom: 20px;
}
#logo,
#logo:active {
  font-size: 50px;
  font-family: Arial, Helvetica, sans-serif;
  font-weight: bold;
  text-decoration-line: none;
  color: #41403c;
  margin-top: 50px;
  margin-bottom: 20px;
}

#nav_bar {
  display: flex;
  flex-direction: row;
  align-items: center;
  border-top: solid 1px;
  border-bottom: solid 1px;
}
#profile {
  padding: 10px 20px;
  background-color: transparent;
  color: #41403c;
  border: 2px solid transparent;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
}
#profile:hover {
  background-color: rgba(255, 255, 255, 0.2);
  border-color: rgba(255, 255, 255, 0.5);
}

.dropdown {
  position: relative;
  display: inline-block;
}
.dropdown-content {
  display: block;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
  right: 0;
}
.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}
.dropdown-content a:hover {
  background-color: #f1f1f1;
}

#profile {
  padding: 10px 20px;
  background-color: transparent;
  color: #41403c;
  border: 2px solid transparent;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.header-nav-button {
  font-size: 16px;
  padding: 10px 20px;
  background-color: transparent;
  color: #41403c;
  font-family: Arial, Helvetica, sans-serif;
}

.header-nav-button:hover {
  /* background-color: #41403c; */
  /* color: #f9f9f9; */
  color: #aa210f;
}
</style>
