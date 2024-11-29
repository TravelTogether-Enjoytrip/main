<script setup>
import { ref } from "vue";
defineEmits(["textChange"]);
const text = ref("");
</script>

<template>
  <div>
    <form action="">
      <div class="search">
        <input
          type="text"
          placeholder="관광지명을 입력하세요"
          @keyup="$emit('textChange', text)"
          v-model="text"
        />
      </div>
    </form>
  </div>
</template>

<style scoped>
.search input {
  font-size: 30px;
  font-weight: 100;
  width: 100%;
}
</style>
