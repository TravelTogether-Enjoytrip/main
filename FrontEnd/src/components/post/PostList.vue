<script setup>
import { storeToRefs } from "pinia";
import { ref, onMounted } from "vue";
import { useUserStore } from "@/stores/user";
import axios from "axios";
import InfiniteLoading from "v3-infinite-loading";
import "v3-infinite-loading/lib/style.css";

let token = sessionStorage.getItem("accessToken");
let page = 1;
const limit = 10;
const isEnd = ref(false);
const keyword = ref("");

const userStore = useUserStore();

const { userInfo } = storeToRefs(userStore);

const postList = ref([]);

const getPostList = () => {
  axios
    .get("http://localhost:8080/postapi/list")
    .then((res) => {
      console.log(res);
      postList.value = res.data;
    })
    .catch((err) => {
      console.log(err);
    });
};

const searchPost = () => {
  console.log(keyword.value);
  axios
    .get(`http://localhost:8080/postapi/search/${keyword.value}`)
    .then((res) => {
      console.log(res);
      postList.value = res.data;
    })
    .catch((err) => {
      console.log(err);
    });
};

const load = async ($state) => {
  console.log("loading...");
  console.log(page);

  try {
    const response = await axios.get(`http://localhost:8080/postapi/list/${page}`);
    const data = response.data;

    if (data.length < limit) {
      isEnd.value = true;
      postList.value.push(...data);
      $state.complete();
    } else {
      postList.value.push(...data);
      $state.loaded();
      page++;
    }
  } catch (error) {
    $state.error();
    console.error(error);
  }
};

onMounted(() => {
  getPostList();
  userStore.getUserInfo(token);
});
</script>

<template>
  <div class="board-container">
    <div class="page-title">
      <div class="container">
        <h3>게시판</h3>
      </div>
    </div>
    <!-- board search area -->
    <div id="board-search">
      <div class="container">
        <div class="search-window">
          <form action="">
            <div class="search-wrap">
              <label for="search" class="blind">공지사항 내용 검색</label>
              <input id="search" type="search" name="" placeholder="검색어를 입력해주세요." v-model="keyword" />
              <button type="submit" class="btn btn-dark" @click.prevent="searchPost">검색</button>
            </div>
            <router-link v-if="userInfo !== null" :to="{ name: 'post-write' }" class="btn-dark-2"> 글쓰기 </router-link>
          </form>
        </div>
      </div>
    </div>
    <!-- board list area -->
    <div id="board-list">
      <div class="container">
        <table class="board-table">
          <thead>
            <tr>
              <th scope="col" class="th-num">번호</th>
              <th scope="col" class="th-title">제목</th>
              <th scope="col" class="th-date">등록일</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="post in postList" :key="post.postId">
              <td>{{ post.postId }}</td>
              <td>
                <router-link :to="{ name: 'post-detail', params: { postId: post.postId } }">
                  {{ post.title }}
                </router-link>
              </td>
              <td>{{ post.registerTime }}</td>
            </tr>
            <InfiniteLoading v-if="keyword === ``" @infinite="load">
              <template #complete>
                <span></span>
              </template>
            </InfiniteLoading>
          </tbody>
        </table>
        <!-- <div v-if="isEnd === true">더 이상 게시물이 없습니다 !</div> -->
      </div>
    </div>
  </div>
</template>

<style scoped>
.board-container {
  width: 60%;
  margin: 2rem auto;
  font-family: Arial, sans-serif;
}

h1 {
  text-align: center;
  margin-bottom: 1rem;
  color: #333;
}

.table-container {
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

th,
td {
  padding: 1rem;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #f4f4f4;
  font-weight: bold;
}

tr:nth-child(even) {
  background-color: #f9f9f9;
}

tr:hover {
  background-color: #f1f1f1;
}

.write-link {
  display: inline-block;
  padding: 0.5rem 1rem;
  background-color: #007bff;
  color: white;
  text-align: center;
  text-decoration: none;
  border-radius: 4px;
  margin-top: 1rem;
  transition: background-color 0.3s;
  margin-bottom: 1rem;
}

.write-link:hover {
  background-color: #0056b3;
}

.router-link {
  color: #007bff;
  text-decoration: none;
}

.router-link:hover {
  text-decoration: underline;
}
table {
  border-collapse: collapse;
  border-spacing: 0;
}
section.notice {
  padding: 80px 0;
}

.page-title {
  margin-bottom: 60px;
}
.page-title h3 {
  font-size: 28px;
  color: #333333;
  font-weight: 400;
  text-align: center;
}

#board-search .search-window {
  padding: 15px 0;
  background-color: #f9f7f9;
}
#board-search .search-window .search-wrap {
  position: relative;
  /*   padding-right: 124px; */
  margin: 0 auto;
  width: 80%;
  max-width: 564px;
}
#board-search .search-window .search-wrap input {
  height: 40px;
  width: 100%;
  font-size: 14px;
  padding: 7px 14px;
  border: 1px solid #ccc;
}
#board-search .search-window .search-wrap input:focus {
  border-color: #333;
  outline: 0;
  border-width: 1px;
}
#board-search .search-window .search-wrap .btn {
  position: absolute;
  right: 0;
  top: 0;
  bottom: 0;
  width: 108px;
  padding: 0;
  font-size: 16px;
}

.board-table {
  font-size: 13px;
  width: 100%;
  border-top: 1px solid #ccc;
  border-bottom: 1px solid #ccc;
}

.board-table a {
  color: #333;
  display: inline-block;
  line-height: 1.4;
  word-break: break-all;
  vertical-align: middle;
}
.board-table a:hover {
  text-decoration: underline;
}
.board-table th {
  text-align: center;
}

.board-table .th-num {
  width: 100px;
  text-align: center;
}

.board-table .th-date {
  width: 200px;
}

.board-table th,
.board-table td {
  padding: 14px 0;
}

.board-table tbody td {
  border-top: 1px solid #e7e7e7;
  text-align: center;
}

.board-table tbody th {
  padding-left: 28px;
  padding-right: 14px;
  border-top: 1px solid #e7e7e7;
  text-align: left;
}

.board-table tbody th p {
  display: none;
}

.btn {
  display: inline-block;
  padding: 0 30px;
  font-size: 15px;
  font-weight: 400;
  background: transparent;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  -ms-touch-action: manipulation;
  touch-action: manipulation;
  cursor: pointer;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  border: 1px solid transparent;
  text-transform: uppercase;
  -webkit-border-radius: 0;
  -moz-border-radius: 0;
  border-radius: 0;
  -webkit-transition: all 0.3s;
  -moz-transition: all 0.3s;
  -ms-transition: all 0.3s;
  -o-transition: all 0.3s;
  transition: all 0.3s;
}

.btn-dark-2 {
  background: #555;
  color: #fff;
  height: 50px;
  padding: 10px 30px;
  font-size: 15px;
  font-weight: 400;
  text-align: center;
  vertical-align: middle;
  cursor: pointer;
  user-select: none;
  border: 1px solid transparent;
  text-transform: uppercase;
  border-radius: 0;
  transition: all 0.3s;
  margin-left: 1rem;
  margin-bottom: 2rem;
}

.btn-dark {
  background: #555;
  color: #fff;
}

.btn-dark:hover,
.btn-dark:focus {
  background: #373737;
  border-color: #373737;
  color: #fff;
}

/* reset */

* {
  list-style: none;
  text-decoration: none;
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}
.clearfix:after {
  content: "";
  display: block;
  clear: both;
}
.container {
  width: 1100px;
  margin: 0 auto;
}
.blind {
  position: absolute;
  overflow: hidden;
  clip: rect(0 0 0 0);
  margin: -1px;
  width: 1px;
  height: 1px;
}
</style>
