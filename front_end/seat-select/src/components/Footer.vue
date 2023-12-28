<template>
  <div class="footer">
    <div class="footer-item" @click="goTo('/hall-seat')">
      <img class="icon" src="../assets/images/seat.png" alt="">
      <span>选座</span>
    </div>
    <div class="footer-item" @click="goTo('/order')">
      <img class="icon" src="../assets/images/order.png" alt="">
      <span>订单</span>
    </div>
    <div class="footer-item" @click="logout">
      <img class="icon" src="../assets/images/logout.png" alt="">
      <span>退出</span>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    goTo (path) {
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
