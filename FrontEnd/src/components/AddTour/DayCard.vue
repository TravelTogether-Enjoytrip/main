<script setup>
import AttractionCard from "@/components/AddTour/AttractionCard.vue";
const props = defineProps(["dayplan"]);
console.log("dp");
console.log(props.dayplan);
</script>

<template>
  <div id="card-main">
    Day
    <AttractionCard v-for="(_, i) in dayplan.length" :data="dayplan[i]" />
  </div>
</template>

<style scoped>
#card-main {
  width: 95%;
  background-color: #edefee;
  height: auto;
  min-height: 100px;
  border: 1px solid gray;
  border-radius: 5px;
  margin: 5px;
  font-family: Arial, Helvetica, sans-serif;
}
</style>
