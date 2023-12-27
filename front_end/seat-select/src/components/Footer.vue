<template>
  <div class="footer">
    <div class="footer-item" @click="goTo('/hall-seat')">
      <img src="../assets/images/seat.png" alt="">
      <span>选座</span>
    </div>
    <div class="footer-item" @click="goTo('/order')">
      <img src="../assets/images/order.png" alt="">
      <span>订单</span>
    </div>
    <div class="footer-item" @click="goTo('/login')">
      <img src="../assets/images/logout.png" alt="">
      <span>退出</span>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    goTo (path) {
      this.$router.push(path)
    }
  }
}
</script>

<style>

</style>
