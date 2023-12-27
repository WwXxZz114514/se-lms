<template>
    <el-aside>
        <div class="scroll" ref="contentScroll">
          <el-menu :default-active="defaultActive" :router="true">
            <el-submenu index="1">
              <template slot="title">
                <i class="el-icon-s-data"></i>
                <span>模版管理</span>
              </template>
              <el-menu-item-group title="查看">
                <el-menu-item index="/">模版列表</el-menu-item>
                <el-menu-item index="/order">订单列表</el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group title="编辑">
                <el-menu-item index="hallSeat">新增模版</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
        </div>
    </el-aside>
</template>
<script>
import BScroll from 'better-scroll'
import router from '../../router'

export default {
  props: {
    active: String
  },
  data () {
    return {
      defaultActive: ''
    }
  },
  router,
  beforeRouteEnter (to, from, next) {
    next(vm => {
      vm.defaultActive = to.path
    })
  },
  beforeRouteUpdate (to, from, next) {
    this.defaultActive = to.path
    next()
  },
  mounted () {
    this.$nextTick(() => {
      setTimeout(() => {
        this.defaultActive = this.$route.path
        console.log(this.$route.path)
      }, 100)
      if (!this.scroll) {
        this.scroll = new BScroll(this.$refs.contentScroll, {
          scrollbar: {
            fade: true,
            interactive: false
          },
          mouseWheel: {
            speed: 20,
            invert: false,
            easeTime: 300
          },
          scroll: true,
          click: true }
        )
      } else {
        this.scroll.refresh()
      }
    })
  }
}
</script>
<style lang="stylus" scoped>
  .scroll
    overflow: hidden
    position relative
    height 100vh
    border-right :solid 1px #e6e6e6
    .el-aside
      height 100vh
    .el-menu
      border none
</style>
