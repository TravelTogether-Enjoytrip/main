<script setup></script>

<template>
  <div id="footer">
    <div class="copyright">
      <p>© CopyRight</p>
      <p>&nbspTravelTogether</p>
      <a href="https://github.com/bsyun0571">&nbsp윤병서</a>
      <a href="https://github.com/rlawnsdn">&nbsp김준우</a>
    </div>
  </div>
</template>

<style scoped>
#footer {
  color: #41403c;
  font-family: Arial, Helvetica, sans-serif;
  position: relative;
  margin-top: 20px;
}

.copyright {
  list-style: none;
  padding: 0;
  margin: 0;
  font-size: 14px;
  display: flex;
}

.copyright :first-child {
  text-decoration: underline;
}

.copyright p {
  margin: 0px;
}

.copyright a,
.copyright a:visited {
  text-decoration: none;
  color: #41403c;
}
</style>
