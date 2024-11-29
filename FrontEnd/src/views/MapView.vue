<script setup>
import { RouterView } from "vue-router";
</script>

<template>
  <div id="map-view">
    <router-view></router-view>
  </div>
</template>

<style scoped>
#map-view {
  width: 60%;
}
</style>
