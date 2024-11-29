import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function userConfirm(param, success, fail) {
  console.log(param);
  await local.post(`/userapi/login`, param).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  local.defaults.headers["Authorization"] =
    sessionStorage.getItem("accessToken");
  await local.get(`/userapi/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] =
    sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`/userapi/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await local.get(`/userapi/logout/${userid}`).then(success).catch(fail);
}

async function getMyGroupList(userid, success, fail) {
  await local.get(`/groupapi/list/${userid}`).then(success).catch(fail);
}

async function getMyGroupMemberList(groupid, success, fail) {
  await local.get(`/groupapi/memberlist/${groupid}`).then(success).catch(fail);
}

async function getAllAttraction(success, fail) {
  await local.get(`/attraction/memberlist/${groupid}`).then(success).catch(fail);
}

export {
  userConfirm,
  findById,
  tokenRegeneration,
  logout,
  getMyGroupList,
  getMyGroupMemberList,
};
