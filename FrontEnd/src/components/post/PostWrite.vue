<script setup>
import { useRouter } from "vue-router";
import axios from "axios";
import { storeToRefs } from "pinia";
import { useUserStore } from "@/stores/user";
import { ref, onMounted } from "vue";
import { QuillEditor } from "@vueup/vue-quill";
import "quill/dist/quill.snow.css";

const router = useRouter();

const userStore = useUserStore();
const { userInfo } = storeToRefs(userStore);

const post = ref({
  title: "",
  userId: "",
  groupId: "",
  content: "",
});

const selectedFile = ref(null);

// 이미지 업로드 관련 함수
const uploadImage = (event) => {
  selectedFile.value = event.target.files[0];
};

const token = sessionStorage.getItem("accessToken");

onMounted(() => {
  userStore.getUserInfo(token);
  post.value.userId = userInfo.value.userId;
});

const writePost = () => {
  const formData = new FormData();
  formData.append("file", selectedFile.value);
  formData.append("title", post.value.title);
  formData.append("userId", post.value.userId);
  formData.append("groupId", post.value.groupId);
  formData.append("content", post.value.content);
  console.log(selectedFile.value);
  for (let [key, value] of formData.entries()) {
    console.log(`${key}: ${value}`);
  }
  axios
    .post(`http://localhost:8080/postapi/post`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then((res) => {
      console.log(res);
      router.push({ name: "post-list" });
    })
    .catch((err) => {
      console.log(err);
    });
};

// Quill Editor의 옵션 설정
const editorOptions = {
  modules: {
    toolbar: [[{ header: [1, 2, 3, false] }], ["bold", "italic", "underline", "strike"], [{ list: "ordered" }, { list: "bullet" }], [{ align: [] }], ["link", "image"], ["clean"]],
  },
};
</script>

<template>
  <div class="form-container">
    <h2>글 작성</h2>
    <form @submit.prevent="writePost">
      <label for="title">제목:</label>
      <input type="text" id="title" v-model="post.title" required />

      <label for="userId">사용자 아이디:</label>
      <input type="text" id="userId" v-model="post.userId" readonly />

      <label for="groupId">그룹:</label>
      <input type="text" id="groupId" v-model="post.groupId" />

      <label for="content">내용:</label>
      <!-- <quill-editor v-model="post.content" :options="editorOptions" /> -->
      <input type="text" id="content" v-model="post.content" required />

      <label for="imageUpload">이미지 업로드:</label>
      <input type="file" id="imageUpload" @change="uploadImage" />

      <button type="submit">작성</button>
    </form>
  </div>
</template>

<style scoped>
.form-container {
  font-family: "Arial", sans-serif;
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  background-color: #f9f9f9;
}

h2 {
  text-align: center;
  color: #333;
}

form {
  display: flex;
  flex-direction: column;
}

label {
  margin-top: 10px;
  font-size: 16px;
  color: #555;
}

input[type="text"],
textarea,
input[type="file"] {
  margin-top: 5px;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

textarea {
  resize: vertical;
}

button {
  background-color: #4caf50; /* Green */
  border: none;
  color: white;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin-top: 20px;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>
