<script setup>
import { ref, watch } from "vue";
import { KakaoMap, KakaoMapMarker, KakaoMapCustomOverlay, KakaoMapPolyline } from "vue3-kakao-maps";
import draggable from "vuedraggable";
import axios from "axios";

const map = ref();
const markerList = ref([]);
const favorites = ref([]);
const keyword = ref("");

const gugunList = ref([]);
const latLngList = ref([]);
const selectedArea = ref(0);
const selectedGugun = ref(0);
const selectedContent = ref(0);

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
  searchPlaces();
};

const searchPlaces = () => {
  if (!keyword.value) return;
  fetchSearchList(selectedArea.value, selectedGugun.value, selectedContent.value, keyword.value);
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

const addToFavorites = (marker) => {
  if (!favorites.value.find((fav) => fav.id === marker.id)) {
    favorites.value.push({ id: marker.id, place_name: marker.place_name, image: marker.image, lat: marker.lat, lng: marker.lng, address_name: marker.address_name });
    latLngList.value.push({ id: marker.id, lat: marker.lat, lng: marker.lng });
  }
};

const removeFavorite = (index) => {
  const flag = favorites.value[index].id;
  console.log(flag);

  const indexToRemove = latLngList.value.findIndex((it) => it.id === flag);
  if (indexToRemove !== -1) {
    latLngList.value.splice(indexToRemove, 1);
  }

  console.log(latLngList.value);
  favorites.value.splice(index, 1);
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

const favoriteMarkers = () => {
  const bounds = new kakao.maps.LatLngBounds();
  markerList.value = [];

  for (let marker of favorites.value) {
    const markerItem = {
      lat: marker.lat,
      lng: marker.lng,
      address_name: marker.address_name,
      image: marker.image,
      place_name: marker.place_name,
      infoWindow: {
        visible: false,
      },
    };

    markerList.value.push(markerItem);
    bounds.extend(new kakao.maps.LatLng(Number(marker.lat), Number(marker.lng)));
  }

  map.value?.setBounds(bounds);
};

const fetchSearchList = async (areaCode, gugun, contentTypeId, keyword) => {
  if (!areaCode || !gugun || !contentTypeId || !keyword) {
    console.error("All parameters (areaCode, gugun, contentTypeId, keyword) are required.");
    return;
  }
  try {
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
      bounds.extend(new kakao.maps.LatLng(Number(marker.mapY), Number(marker.mapX)));
    }

    map.value?.setBounds(bounds);
  } catch (error) {
    console.error(error);
  }

  watch(favorites, (newFavorites) => {
    latLngList.value = newFavorites.map((fav) => ({ id: fav.id, lat: fav.lat, lng: fav.lng }));
  });
};
</script>

<template>
  <div class="container">
    <div class="search-container">
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
          <option v-for="gugun in gugunList" :key="gugun" :value="gugun.gugunCode">{{ gugun.gugunName }}</option>
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
      <KakaoMap :lat="37.566826" :lng="126.9786567" @onLoadKakaoMap="onLoadKakaoMap" width="100%" height="800px" class="map">
        <KakaoMapMarker v-for="(marker, index) in markerList" :key="index" :lat="marker.lat" :lng="marker.lng" @onClickKakaoMapMarker="onClickMapMarker(marker)" :clickable="true" />
        <KakaoMapCustomOverlay v-for="(marker, index) in markerList" :key="index" :lat="marker.lat" :lng="marker.lng" :yAnchor="1.4" :visible="marker.infoWindow.visible">
          <div class="info-window">
            <div class="place-name">{{ marker.place_name }}</div>
            <div class="address">{{ marker.address_name }}</div>
            <img :src="marker.image" class="place-image" />
            <!-- <div class="foot">
              <button @click="addToFavorites(marker)" class="fav-button">추가</button>
            </div> -->
          </div>
        </KakaoMapCustomOverlay>
        <KakaoMapPolyline :latLngList="latLngList" />
      </KakaoMap>
    </div>
    <!-- <div class="favorites-container">
      <div class="favorites-title">Favorites</div>
      <draggable v-model="favorites" group="favorites" itemKey="id">
        <template #item="{ element, index }">
          <div class="favorite-item">
            <img :src="element.image" class="favorite-image" />
            {{ element.place_name }}
            <button @click="removeFavorite(index)" class="remove-button">Remove</button>
          </div>
        </template>
      </draggable>
      <button v-if="favorites.length > 0" @click="favoriteMarkers" class="viewFav">즐겨찾기 보기</button>
    </div> -->
  </div>
</template>

<style scoped>
.favorite-image {
  width: 10rem;
}
.container {
  display: flex;
  flex-direction: row;
  padding-bottom: 20px;
  padding-top: 20px;
}

.search-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  flex: 1;
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

.search-input {
  width: 20%;
  max-width: 200px;
  padding: 10px;
  margin-right: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.map {
  border: 1px solid #ccc;
  border-radius: 10px;
  overflow: hidden;
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

.place-name {
  font-weight: bold;
  margin-bottom: 5px;
}

.fav-button {
  background-color: #007bff;
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

.viewFav {
  background-color: #555;
  border: none;
  color: white;
  padding: 5px 10px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 12px;
  margin-top: 20px;
  border-radius: 4px;
  cursor: pointer;
}

.viewFav:hover {
  background-color: #373737;
}
</style>
