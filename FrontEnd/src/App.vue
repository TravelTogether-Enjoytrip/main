<script setup>
import Footer from "@/layout/Footer.vue";
import Header from "@/layout/Header.vue";

import { RouterView, useRouter } from "vue-router";
const router = useRouter();
</script>

<template>
  <div class="layout">
    <Header
      v-if="router.currentRoute.value.name !== 'addtour'"
      class="header"
    />
    <RouterView />
    <Footer class="footer" />
  </div>
</template>

<style scoped>
.layout {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
  background-color: #edefee;
}

.header {
  position: fixed;
  width: 54.5%;
}

.content {
  margin-top: 188px;
  flex-grow: 1;
  min-height: 500px; /* 원하는 최소 높이 값 */
  width: 55%;
}
.footer {
  width: 55%;
  position: absolute;
  bottom: 0;
}
</style>
