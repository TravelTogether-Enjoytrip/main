<script setup>
import { onMounted, ref, watch } from "vue";
import { KakaoMap, KakaoMapMarker, KakaoMapCustomOverlay } from "vue3-kakao-maps";
import draggable from "vuedraggable";
import axios from "axios";
import { useRoute, useRouter } from "vue-router";
import { httpStatusCode } from "@/util/http-status";

const route = useRoute();
const router = useRouter();

const tourplan = ref([]);
const markerList = ref([]);

const lat = ref(0);
const lng = ref(0);
const map = ref();
const keyword = ref("");
const init = ref(false);

const gugunList = ref([]);
const selectedArea = ref(0);
const selectedGugun = ref(0);
const selectedContent = ref(0);

onMounted(() => {
  onMountedcalculateDay();
});
// onMounted 안에 들어가는 함수들 >>>>>>>>>
const onMountedcalculateDay = () => {
  const startdate = new Date(route.query.startdate);
  const enddate = new Date(route.query.enddate);
  const tourperiod = (enddate - startdate) / (1000 * 60 * 60 * 24) + 1;
  for (let index = 0; index < tourperiod; index++) {
    tourplan.value.push([]);
  }
};
// onMounted 안에 들어가는 함수들 <<<<<<<<<

// 카카오맵 관련 함수 & 변수 >>>>>>>>>>
const onLoadKakaoMap = async (mapRef) => {
  map.value = mapRef;
  if (init.value === false) {
    getLatLngInit();
    init.value = true;
  }
  console.log(init.value);
  searchPlaces();
  kakao.maps.event.addListener(map.value, "dragend", function () {
    if (map.value.getLevel() <= 6) {
      showMarker();
    }
    console.log("map level : " + map.value.getLevel());
  });
  kakao.maps.event.addListener(map.value, "zoom_changed", function () {
    if (map.value.getLevel() <= 6) {
      showMarker();
    }
    console.log("map level : " + map.value.getLevel());
  });
};

function getcurrentposition() {
  return new Promise((resolve, reject) => {
    navigator.geolocation.getCurrentPosition(resolve, reject);
  });
}

const searchPlaces = () => {
  if (!keyword.value) return;
  fetchSearchList(selectedArea.value, selectedGugun.value, selectedContent.value, keyword.value);
};

const fetchGugunList = async (areaCode) => {
  try {
    const response = await axios.get(`http://localhost:8080/tripapi/gugun`, {
      params: {
        areaCode,
      },
    });
    console.log(response.data);
    gugunList.value = response.data;
  } catch (error) {
    console.error(error);
  }
};

const fetchSearchList = async (areaCode, gugun, contentTypeId, keyword) => {
  if (!areaCode || !gugun || !contentTypeId || !keyword) {
    console.error("All parameters (areaCode, gugun, contentTypeId, keyword) are required.");
    return;
  }
  try {
    // kakao.maps.event.removeListener(map.value, "dragend");
    // kakao.maps.event.removeListener(map.value, "zoom_changed");

    const response = await axios.get(`http://localhost:8080/tripapi/search`, {
      params: {
        areaCode,
        gugun,
        contentTypeId,
        keyword,
      },
    });
    console.log(response.data);

    const bounds = new kakao.maps.LatLngBounds();
    markerList.value = [];

    for (let marker of response.data) {
      const markerItem = {
        lat: marker.mapY,
        lng: marker.mapX,
        address_name: marker.addr1,
        image: marker.firstImage,
        id: marker.contentId,
        place_name: marker.title,
        infoWindow: {
          visible: false,
        },
      };

      markerList.value.push(markerItem);
      console.log(markerList.value);
      bounds.extend(new kakao.maps.LatLng(Number(marker.mapY), Number(marker.mapX)));
    }

    map.value?.setBounds(bounds);

    // kakao.maps.event.addListener(map.value, "dragend", function () {
    //   if (map.value.getLevel() <= 6) {
    //     showMarker();
    //   }
    //   console.log("map level : " + map.value.getLevel());
    // });
    // kakao.maps.event.addListener(map.value, "zoom_changed", function () {
    //   if (map.value.getLevel() <= 6) {
    //     showMarker();
    //   }
    //   console.log("map level : " + map.value.getLevel());
    // });
  } catch (error) {
    console.error(error);
  }

  // watch(favorites, (newFavorites) => {
  //   latLngList.value = newFavorites.map((fav) => ({ id: fav.id, lat: fav.lat, lng: fav.lng }));
  // });
};

const getLatLngInit = () => {
  // 현재 접속 위치를 가져오는 함수
  if (navigator.geolocation) {
    getcurrentposition()
      .then((position) => {
        console.log("현재 위치 가져오기 성공");
        lat.value = position.coords.latitude; // 위도
        lng.value = position.coords.longitude; // 경도
      })
      .then(showMarker);
  } else {
    // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
    console.log("현재 위치를 불러오기에 실패해서 기본 위치를 세팅합니다.");
    lat.value = 37.501254352581256; // 위도
    lng.value = 127.03960057685254; // 경도
    showMarker();
  }
};

const showMarker = async () => {
  const bounds = map.value.getBounds();
  const startLat = bounds.getSouthWest().getLat();
  const startLng = bounds.getSouthWest().getLng();
  const endLat = bounds.getNorthEast().getLat();
  const endLng = bounds.getNorthEast().getLng();
  try {
    const response = await axios.get(`http://localhost:8080/tripapi/searchbyLatLng`, {
      params: {
        startLat,
        endLat,
        startLng,
        endLng,
      },
    });
    if (response.status === httpStatusCode.OK) {
      console.log("관광지 정보 띄우기 성공", response.data);
      // 받아온 정보로 마커 생성하기 >>>>>>>>
      markerList.value = [];
      for (let marker of response.data) {
        const markerItem = {
          lat: marker.mapY,
          lng: marker.mapX,
          address_name: marker.addr1,
          image: marker.firstImage,
          id: marker.contentId,
          place_name: marker.title,
          infoWindow: {
            visible: false,
          },
        };
        markerList.value.push(markerItem);
      }
      // 받아온 정보로 마커 생성하기 <<<<<<<<
    } else {
      console.error("Error : httpStatusCode error");
    }
  } catch (error) {
    console.error(error);
  }
};

const onClickMapMarker = (markerItem) => {
  if (markerItem.infoWindow.visible === true) {
    markerItem.infoWindow.visible = false;
  } else {
    markerList.value.forEach((marker) => {
      marker.infoWindow.visible = false;
    });
    markerItem.infoWindow.visible = true;
    map.value?.panTo(new kakao.maps.LatLng(Number(markerItem.lat + 0.001), Number(markerItem.lng)));
  }
};

const dragOverlayStart = (event, marker) => {
  console.log("DragOverlayStart");
  map.value.setDraggable(false);
  const dragevent = event.originalEvent || event;
  dragevent.dataTransfer.setData("draggedOverlay", JSON.stringify(marker));
};
const dragOverlayEnd = (event) => {
  event.target.classList.remove("dragging");
  map.value.setDraggable(true);
};

const onDrop = (event, index) => {
  const data = event.dataTransfer.getData("draggedOverlay");

  if (!data) {
    console.log("No data found in draggedOverlay");
    return;
  }
  const draggedOverlay = JSON.parse(event.dataTransfer.getData("draggedOverlay"));
  tourplan.value[index].push(draggedOverlay);
  console.log(tourplan.value);
};

const addTourPlan = () => {
  console.log("tourplan : ");
  console.log(tourplan.value);
  const plan = [];
  tourplan.value.forEach((dayplan) => {
    plan.push([]);
    dayplan.forEach((attraction) => {
      plan[plan.length - 1].push(attraction.id);
    });
  });
  console.log("plan : ");
  console.log(plan);
  const planDto = {
    userId: route.params.userid,
    plan: plan,
  };
  console.log(planDto);
  axios
    .post(`http://localhost:8080/tripapi/maketrippersonal`, planDto)
    .then((res) => {
      console.log(res);
      tourplan.value = false;
    })
    .catch((err) => {
      console.log(err);
    });
  router.push({
    name: "profile",
    params: { userid: route.params.userid },
  });
};

const showTourplanMarkers = () => {
  // tourplan에 있는 마커들을 지도에 표시하는 로직 작성
  const bounds = new kakao.maps.LatLngBounds();
  markerList.value = [];

  // tourplan에 있는 모든 마커들을 순회하면서 마커 정보를 markerList에 추가합니다.
  tourplan.value.forEach((dayplan) => {
    dayplan.forEach((attraction) => {
      const markerItem = {
        lat: attraction.lat,
        lng: attraction.lng,
        address_name: attraction.address_name,
        image: attraction.image,
        id: attraction.id,
        place_name: attraction.place_name,
        infoWindow: {
          visible: false,
        },
      };
      markerList.value.push(markerItem);
      bounds.extend(new kakao.maps.LatLng(Number(attraction.lat), Number(attraction.lng)));
    });
  });

  // 지도 영역을 tourplan에 있는 마커들을 포함하는 영역으로 설정합니다.
  map.value?.setBounds(bounds);
};
</script>

<template>
  <div id="main">
    <div id="left-main">
      <div id="card-main" v-for="(dayplan, index) in tourplan" :key="index" :dayplan="dayplan" @drop.prevent="onDrop($event, index)" @dragover.prevent>
        Day {{ index + 1 }}
        <draggable v-model="tourplan[index]" group="plans" itemKey="id">
          <template #item="{ element, index }">
            <div class="favorite-item">
              <img v-if="element.image" :src="element.image" class="favorite-image" />
              {{ element.place_name }}
              <!-- <button @click="removeFavorite(index)" class="remove-button">Remove</button> -->
            </div>
          </template>
        </draggable>
      </div>
      <button @click="showTourplanMarkers">Tourplan 마커 보기</button>
    </div>

    <div id="right-main">
      <div id="right-main-header">
        <form @submit.prevent="searchPlaces" class="search-form">
          <select id="search-area" class="search-input" v-model="selectedArea" @change="fetchGugunList(selectedArea)">
            <option value="0" selected>검색 할 지역 선택</option>
            <option value="1">서울</option>
            <option value="2">인천</option>
            <option value="3">대전</option>
            <option value="4">대구</option>
            <option value="5">광주</option>
            <option value="6">부산</option>
            <option value="7">울산</option>
            <option value="8">세종특별자치시</option>
            <option value="31">경기도</option>
            <option value="32">강원도</option>
            <option value="33">충청북도</option>
            <option value="34">충청남도</option>
            <option value="35">경상북도</option>
            <option value="36">경상남도</option>
            <option value="37">전라북도</option>
            <option value="38">전라남도</option>
            <option value="39">제주도</option>
          </select>
          <select id="search-gugun-id" class="search-input" v-model="selectedGugun">
            <option value="0" selected>검색 할 구군 선택</option>
            <option v-for="gugun in gugunList" :key="gugun" :value="gugun.gugunCode">
              {{ gugun.gugunName }}
            </option>
          </select>
          <select id="search-content-id" class="search-input" v-model="selectedContent">
            <option value="0" selected>관광지 유형</option>
            <option value="12">관광지</option>
            <option value="14">문화시설</option>
            <option value="15">축제공연행사</option>
            <option value="25">여행코스</option>
            <option value="28">레포츠</option>
            <option value="32">숙박</option>
            <option value="38">쇼핑</option>
            <option value="39">음식점</option>
          </select>
          <input type="text" v-model="keyword" placeholder="검색어를 입력하세요" @keydown.enter.prevent="searchPlaces" class="search-input" />
        </form>
        <button id="submit-button" @click="addTourPlan">Next</button>
      </div>
      <KakaoMap :lat="lat" :lng="lng" @onLoadKakaoMap="onLoadKakaoMap" id="kakao-map">
        <KakaoMapMarker v-for="(marker, index) in markerList" :key="index" :lat="marker.lat" :lng="marker.lng" @onClickKakaoMapMarker="onClickMapMarker(marker)" :clickable="true" />
        <KakaoMapCustomOverlay v-for="(marker, index) in markerList" :key="index" :lat="marker.lat" :lng="marker.lng" :yAnchor="1.2" :visible="marker.infoWindow.visible">
          <div class="info-window" draggable="true" @dragstart="dragOverlayStart($event, marker)" @dragend="dragOverlayEnd" @drop.prevent>
            <div class="place-name">{{ marker.place_name }}</div>
            <div class="address">{{ marker.address_name }}</div>
            <img :src="marker.image" class="place-image" />
          </div>
        </KakaoMapCustomOverlay>
      </KakaoMap>
    </div>
  </div>
</template>

<style scoped>
#card-main {
  width: 95%;
  background-color: white;
  background-color: #edefee;
  height: auto;
  min-height: 100px;
  border: 1px solid #41403c;
  border-radius: 5px;
  margin: 5px;
  font-family: Arial, Helvetica, sans-serif;
}

#main {
  display: flex;
  flex-direction: row;
  width: 100%;
}
#left-main {
  width: 22.5%;
  background-color: #41403c;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 5px;
  min-height: 100vh;
}

#kakao-map {
  min-height: 93vh;
  min-width: 100%;
  border-radius: 0;
}

.info-window {
  width: 270px;
  padding: 10px;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 5px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.favorite-image {
  width: 10rem;
}
.container {
  display: flex;
  flex-direction: row;
  padding: 20px;
}

.search-form {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  width: 100%;
  max-width: 1200px;
  margin-bottom: 20px;
}

.map {
  border: 1px solid #ccc;
  border-radius: 10px;
  overflow: hidden;
}

.place-name {
  font-weight: bold;
  margin-bottom: 5px;
}

.fav-button {
  background-color: #f4f4f4;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}

.fav-button:hover {
  background-color: #0056b3;
}

.address,
.phone,
.place-url {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.place-url {
  color: blue;
  text-decoration: none;
}

.place-image {
  width: 15rem;
}

.place-url:hover {
  text-decoration: underline;
}

.foot {
  display: flex;
  justify-content: space-between;
  width: 100%;
  margin-top: 10px;
}

.favorites-container {
  width: 400px;
  margin-left: 20px;
}

.favorites-title {
  font-weight: bold;
  margin-bottom: 10px;
  text-align: center;
}

.favorite-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  margin-bottom: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.remove-button {
  background-color: red;
  color: white;
  border: none;
  padding: 5px;
  border-radius: 5px;
  cursor: pointer;
}

.remove-button:hover {
  background-color: darkred;
}

/* ----------------------------------------- right-main-header */
#right-main {
  display: flex;
  flex-direction: column;
  height: 700px;
  width: 55%;
  background-color: #ccc;
  min-height: 100vh;
  min-width: 77.5%;
}

#right-main-header {
  display: flex;
}

.search-input {
  width: 20%;
  max-width: 200px;
  padding: 10px;
  margin-right: 10px;
  border: 1px solid #ccc;
  background-color: #41403c;
  color: #edefee;
  border: none;
}

#submit-button {
  margin-top: 10px;
  margin-bottom: 10px;
  margin-right: 10px;
  width: 100px;
  height: 50px;
  font-size: 20px;
  background-color: #41403c;
  color: white;
}
</style>
