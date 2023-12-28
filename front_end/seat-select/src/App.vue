<template>
  <div id="app" style="display: flex; flex-direction: column; height: 100vh;">
    <div style="flex: 1;">
      <router-view />
    </div>
    <Footer style="margin-top: auto;"/>
  </div>
</template>
<script>
import Footer from './components/Footer.vue'
export default {
  name: 'App',
  components: {
    Footer
  }
}
</script>
<style>
.row {
  display: flex;
  flex-direction: row;
}
.col {
  display: flex;
  flex-direction: column;
}
</style>
