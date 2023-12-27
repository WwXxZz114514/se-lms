<template>
  <div id="app">
    <el-container>
      <Aside v-if="$route.path == '/' || $route.path == '/order'" />
      <router-view/>
    </el-container>
  </div>
</template>
<script>
import { mapState, mapMutations } from 'vuex'
import router from './router'
import Aside from './pages/components/Aside'
export default {
  name: 'app',
  components: {
    Aside
  },
  router,
  computed: {
    ...mapState({
      fullscreen: state => state.config.fullscreen
    })
  },
  methods: {
    ...mapMutations([
      'makeFullscreen'
    ])
  },
  mounted () {
    let that = this
    window.onresize = () => {
      return (() => {
        console.log(document.fullscreenElement === null)
        if (document.fullscreenElement === null) {
          that.makeFullscreen(false)
        }
      })()
    }
  }
}

</script>
<style>
  *{margin: 0;padding: 0}
</style>
