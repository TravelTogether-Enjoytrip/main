import { createApp } from "vue";
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

import App from "./App.vue";
import router from "./router";
import { useKakao } from "vue3-kakao-maps";

useKakao("6332fa0142e8948d9e35436299f7b798", ["clusterer", "services", "drawing"]);

const app = createApp(App);
const pinia = createPinia();

pinia.use(piniaPluginPersistedstate);

app.use(pinia);
app.use(router);

app.mount("#app");
