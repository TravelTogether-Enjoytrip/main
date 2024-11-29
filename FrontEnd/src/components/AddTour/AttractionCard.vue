<script setup>
defineProps(["data"]);
</script>

<template>
  <div id="attraction-card">{{ data.place_name }}</div>
</template>

<style scoped>
#attraction-card {
  background-color: gray;
  width: 95%;
  border-radius: 5px;
  margin: 2px 0;
}
</style>
