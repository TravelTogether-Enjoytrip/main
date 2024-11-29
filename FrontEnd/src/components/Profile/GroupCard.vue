<script setup>
defineProps(["groupInfo"]);
</script>

<template>
  <div id="group-card">{{ groupInfo.groupName }}</div>
</template>

<style scoped>
/* #group-card {
  --card-height: 70px;
  border-radius: calc(var(--card-height) * 0.15);
  width: var(--card-height);
  height: var(--card-height);
  background-color: blanchedalmond;
  margin: 10px;
  font-size: calc(var(--card-height) * 0.8);
  text-align: center;
  line-height: var(--card-height);
  font-weight: lighter;
  font-family: "Courier New", Courier, monospace;
} */
#group-card {
}
#group-card:hover {
  color: #aa210f;
}
</style>
