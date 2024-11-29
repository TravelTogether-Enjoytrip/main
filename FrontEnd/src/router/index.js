import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/profile/:userid",
      name: "profile",
      component: () => import("../views/ProfileView.vue"),
    },
    {
      path: "/profile/:userid/group/:groupid",
      name: "group",
      component: () => import("../views/GroupView.vue"),
    },
    {
      path: "/profile/:userid/addtour",
      name: "addtour",
      component: () => import("../views/AddTourView.vue"),
    },
    {
      path: "/post",
      name: "post",
      component: () => import("../views/PostView.vue"),
      redirect: { name: "post-list" },
      children: [
        {
          path: "list",
          name: "post-list",
          component: () => import("@/components/post/PostList.vue"),
        },
        {
          path: "post/:postId",
          name: "post-detail",
          component: () => import("@/components/post/PostDetail.vue"),
        },
        {
          path: "post/:postId",
          name: "post-modify",
          component: () => import("@/components/post/PostModify.vue"),
        },
        {
          path: "write",
          name: "post-write",
          component: () => import("@/components/post/PostWrite.vue"),
        },
      ],
    },
    {
      path: "/user",
      name: "user",
      component: () => import("../views/UserView.vue"),
      redirect: { name: "user-login" },
      children: [
        {
          path: "login",
          name: "user-login",
          component: () => import("@/components/users/UserLogin.vue"),
        },
        {
          path: "join",
          name: "user-join",
          component: () => import("@/components/users/UserJoin.vue"),
        },
        {
          path: "modify",
          name: "user-modify",
          component: () => import("@/components/users/UserModify.vue"),
        },
      ],
    },
    {
      path: "/map",
      name: "map",
      component: () => import("../views/MapView.vue"),
      redirect: { name: "map-search" },
      children: [
        {
          path: "search",
          name: "map-search",
          component: () => import("@/components/map/MapSearch.vue"),
        },
      ],
    },
  ],
});

export default router;
