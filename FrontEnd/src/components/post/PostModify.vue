<script setup>
import { useRoute, useRouter } from "vue-router";
import { ref, onMounted } from "vue";
import axios from "axios";

const post = ref({});
const route = useRoute();
const router = useRouter();

const { postId } = route.params;

const getPost = () => {
  axios
    .get(`http://localhost:8080/postapi/post/${postId}`)
    .then((res) => {
      console.log(res);
      post.value = res.data;
    })
    .catch((err) => {
      console.log(err);
    });
};

const updatePost = () => {
  axios
    .put(`http://localhost:8080/postapi/post/${postId}`, post.value)
    .then((res) => {
      console.log("수정 성공:", res);
      router.push({ name: "post-list" });
    })
    .catch((err) => {
      console.log("수정 실패:", err);
    });
};

const deletePost = () => {
  axios
    .delete(`http://localhost:8080/postapi/post/${postId}`)
    .then((res) => {
      console.log("삭제 성공:", res);
      router.push({ name: "post-list" });
    })
    .catch((err) => {
      console.log("삭제 실패:", err);
    });
};

onMounted(() => {
  getPost();
});
</script>

<template>
  <div class="post-container">
    <h2>글 수정</h2>
    <div class="modifyForm">
      <label>번호</label>
      <input type="text" v-model="post.postId" readonly />
      <br />
      <label>제목</label>
      <input type="text" v-model="post.title" />
      <br />
      <label>내용</label>
      <textarea v-model="post.content"></textarea>
      <br />
      <div class="post-modify-buttons">
        <button class="post-modify-button" type="button" @click="updatePost">
          수정
        </button>
        <button class="post-modify-button" type="button" @click="deletePost">
          삭제
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.post-container {
  width: 100%;
  margin: 2rem auto;
  font-family: Arial, Helvetica, sans-serif;
}

h2 {
  text-align: center;
  color: #333;
}

.modifyForm {
  display: flex;
  flex-direction: column;
}

label {
  margin-top: 1rem;
  margin-bottom: 0.5rem;
  font-weight: bold;
  color: #555;
}

input[type="text"],
textarea {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 100%;
  box-sizing: border-box;
}

textarea {
  resize: vertical;
  height: 100px;
}

button:hover {
  background-color: #0056b3;
}

.post-modify-buttons {
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.post-modify-button {
  cursor: pointer;
  color: #aa210f;
  background-color: transparent;
  border: none;
  font-size: 20px;
}

.post-modify-button:hover {
  color: #0f98aa;
  background-color: transparent;
}

.post-modify-button:active {
  color: #aa210f;
}
</style>
