<script setup>
defineProps(["memberInfo", "isUser"]);
</script>

<template>
  <div id="group-member-card" :class="[isUser ? 'is-user' : 'is-others']">
    {{ memberInfo.userId }}
  </div>
</template>

<style scoped>
#group-member-card {
  --card-height: 70px;
  border-radius: calc(var(--card-height) * 0.15);
  width: calc(var(--card-height) * 1.8);
  height: var(--card-height);
  background-color: rgb(70, 70, 70);
  margin: 10px;
  font-size: calc(var(--card-height) * 0.8);
  text-align: center;
  line-height: var(--card-height);
  font-weight: lighter;
  font-family: "Courier New", Courier, monospace;
  font-size: calc(var(--card-height) * 0.3);
}

.is-user {
  color: greenyellow;
}
.is-others {
  color: white;
}
</style>
