<script setup>
import { useRoute, useRouter } from "vue-router";
import { ref, onMounted } from "vue";
import axios from "axios";
import { storeToRefs } from "pinia";
import { useUserStore } from "@/stores/user";

const post = ref({});
const comments = ref([]);
const route = useRoute();
const router = useRouter();
const commentDto = ref({
  comment: "",
  postId: "",
  userId:"",
})

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

const getComments = () => {
  axios
    .get(`http://localhost:8080/postapi/post/${postId}/comments`)
    .then((res) => {
      comments.value = res.data;
    })
    .catch((err) => {
      console.log(err);
    });
};

const writeComment = () => {
  commentDto.value.postId = postId;
  commentDto.value.userId = userInfo.value.userId;
  axios
    .post(
      `http://localhost:8080/postapi/post/comment`, commentDto.value)
    .then(() => {
      getComments();
      commentDto.value.comment = "";
    })
    .catch((err) => {
      console.log(err);
    });
};

const userStore = useUserStore();
const { isLogin, userInfo } = storeToRefs(userStore);

onMounted(() => {
  userStore.getUserInfo(sessionStorage.getItem("accessToken"));
  getPost();
  getComments();
});

function moveModify() {
  router.push({ name: "post-modify", params: { postId } });
}
</script>


<template>
  <div class="post-container">
    <div class="post-header">
      <p class="post-title">{{ post.title }}</p>
      <div class="post-info">
        <div class="post-info-element">게시물 번호: {{ post.postId }} / </div>
        <div class="post-info-element">유저 아이디: {{ post.userId }} / </div>
        <div class="post-info-element">조회수: {{ post.hit }}</div>
      </div>
    </div>
    <div class="post-body">
      <div class="post-body-content">{{ post.content }}</div>
      <div class="post-body-img" v-if="post.imgPath !== null">
        <img :src="post.imgPath" alt="post-img" class="post-img" />
      </div>
    </div>
    <div class="post-actions">
      <button type="button" v-if="userInfo !== null && userInfo.userId === post.userId" @click="moveModify" class="modify-button">Modify Post</button>
    </div>

    <!-- Comments Section -->
    <div class="comments-section">
      <h2>Comments</h2>
      <div v-for="comment in comments" :key="comment.id" class="comment">
        <p class="comment-content">{{ comment.comment }}</p>
        <p class="comment-meta">{{ comment.userId }} on {{ comment.registerTime }}</p>
      </div>

      <!-- Add Comment Form -->
    </div>
    <div v-if="userInfo !== null" class="comment-form">
      <input type="text" v-model="commentDto.comment" placeholder="Write a comment..." class="comment-input"></input>
      <button @click="writeComment" class="comment-button">Post Comment</button>
    </div>
  </div>
</template>


<style scoped>
.post-container {
  font-family: Arial, Helvetica, sans-serif;
  width: 100%;
  background-color: #edefee;
  display: flex;
  flex-direction: column;
}

.post-header {
  border-bottom: 1px solid #ddd;
  padding-bottom: 10px;
  margin-bottom: 20px;
}

.post-title {
  font-size: 28px;
  font-weight: bold;
  color: #333;
  margin: 0;
}

.post-info {
  display: flex;
  color: #41403C;
  font-size: 14px;
  margin-top: 10px;
}
.post-info-element {
  margin-right: 5px;
}



.post-body {
  display: flex;
  justify-content: space-between;
}

.post-body-content {
  font-size: 18px;
  line-height: 1.6;
  color: #333;
  margin-right: 20px; /* Add some space between content and image */
  width: 70%; /* Adjust as needed */
}

.post-body-img {
  width: 70%;
  display: flex;
  justify-content: flex-end;
}

.post-img img {
  max-width: 100%;
  height: auto;
  border-radius: 8px;
}

.post-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.modify-button {
  background-color: #4caf50;
  border: none;
  color: white;
  padding: 10px 20px;
  font-size: 16px;
  border-radius: 4px;
  cursor: pointer;
}

.modify-button:hover {
  background-color: #45a049;
}

.comments-section {
  margin-top: 40px;
}

.comments-section h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

.comment {
  border-top: 1px solid #edefee;
}

.comment-content {
  font-size: 16px;
  color: #333;
}

.comment-meta {
  font-size: 14px;
  color: #777;
}

.comment-form {
  margin-top: 20px;
}

.comment-input {
  width: 100%;
  height: 100px;
  font-size: 16px;
  margin-bottom: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.comment-button {
  background-color: #007bff;
  border: none;
  color: white;
  padding: 10px 20px;
  font-size: 16px;
  border-radius: 4px;
  cursor: pointer;
}

.comment-button:hover {
  background-color: #0069d9;
}
</style>