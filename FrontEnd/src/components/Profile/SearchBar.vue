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
          placeholder="유저명을 입력하세요"
          @keyup="$emit('textChange', text)"
          v-model="text"
        />
      </div>
    </form>
  </div>
</template>

<style scoped></style>
