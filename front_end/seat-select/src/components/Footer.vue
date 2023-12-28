<template>
  <div class="footer">
    <div class="footer-item" @click="goTo('/hall-seat','/hall-seat')">
      <div class="footer-item" v-if="active === '/hall-seat'">
        <img class="icon" src="../assets/images/seat-active-footer.png" alt="">
        <span style="color: #1296db;">选座</span>
      </div>
      <div class="footer-item"  v-else >
        <img class="icon" src="../assets/images/seat.png" alt="">
        <span>选座</span>
      </div>
    </div>
    <div class="footer-item" @click="goTo('/order','/order')">
      <div class="footer-item"  v-if="active === '/order'" >
        <img class="icon" src="../assets/images/order-active.png" alt="">
        <span style="color: #1296db;">订单</span>
      </div>
      <div class="footer-item"  v-else>
        <img class="icon" src="../assets/images/order.png" alt="">
        <span>订单</span>
      </div>
    </div>
    <div class="footer-item" @click="logout">
      <img class="icon" src="../assets/images/logout.png" alt="">
      <span>退出</span>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      active: 'seat'
    }
  },
  watch: {
    $route (to, from) {
      this.active = to.path
    }
  },
  methods: {
    goTo (path, bar) {
      this.active = bar
      this.$router.push(path)
    },
    logout () {
      this.$post('/users/user/logout').then(res => {
        if (res.code === 200) {
          this.goTo('/login')
        }
      })
    }
  }
}
</script>

<style scoped>
.footer {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  font-size: 30px;
  background: white;
  padding: 10px 0 10px 0;
}
.footer-item{
  display: flex;
  flex-direction: column;
  align-items: center;
}
.icon {
  width: 40px;
  height: 40px;
}
</style>
