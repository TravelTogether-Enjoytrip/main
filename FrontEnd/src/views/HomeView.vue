<script setup>
import HomeCarousel from "@/components/home/HomeCarousel.vue";
</script>

<template>
  <div id="home">
    <!-- <HomeCarousel /> -->
    <img src="@/assets/maple_image.jpg" alt="" />
    <div id="home-right">
      토함산 자연휴양림은 삼국통일의 위업을 달성한 신라천년의 찬란한 문화유적을 간직한 국제적 관광도시, 경주의 명산 토함산 기슭에 자리잡고 있으며, 불국사, 석굴암, 보문관광단지, 무열왕릉 등 신라
      고적관광과 함께 대자연 속에서 조용히 쉬면서 생각할 수 있는 최적의 산림내 휴식공간이다. * 구역면적 - 123 ㏊(37만평)
    </div>
  </div>
</template>

<style scoped>
#home {
  display: flex;
  justify-content: space-between;
  width: 55%;
}
#home-right {
  width: 30%;
  border-top: solid 1px;
  border-bottom: solid 1px;
  padding-top: 5px;
  font-family: Arial, Helvetica, sans-serif;
  align-items: center;
}
img {
  width: 67%;
  height: auto;
}
</style>
