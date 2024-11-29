<script setup>
import { ref } from "vue";

const images = ref(["src/assets/korea_001.jpg", "src/assets/korea_005.jpg", "src/assets/korea_017.jpg"]);
const currentImageIndex = ref(0);

const nextImage = () => {
  currentImageIndex.value = (currentImageIndex.value + 1) % images.value.length;
};

const prevImage = () => {
  currentImageIndex.value = (currentImageIndex.value - 1 + images.value.length) % images.value.length;
};
</script>

<template>
  <div class="carousel">
    <div v-for="(image, index) in images" :key="index" v-show="currentImageIndex === index" class="image-container">
      <img class="carousel-image" :src="image" :alt="image" />
    </div>
    <button class="carousel-control prev" @click="prevImage"><</button>
    <button class="carousel-control next" @click="nextImage">></button>
  </div>
</template>

<style scoped>
.carousel {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
  position: relative;
}

.image-container {
  width: 100%; /* 이미지 컨테이너 너비 */
  height: 100%; /* 이미지 컨테이너 높이 */
  display: flex;
  justify-content: center; /* 이미지 가로 중앙 정렬 */
  align-items: center; /* 이미지 세로 중앙 정렬 */
}

.carousel-image {
  max-width: 50%; /* 이미지 최대 너비 */
  max-height: 50%; /* 이미지 최대 높이 */
}

.carousel-control {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  padding: 1rem;
  border: none;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  cursor: pointer;
}

.carousel-control.prev {
  left: 20rem;
}

.carousel-control.next {
  right: 20rem;
}
</style>
