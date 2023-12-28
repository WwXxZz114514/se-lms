<template>
  <div class="login">
    <div class="login-container">
      <div class="tittle">欢迎进入图书馆订座系统</div>
      <div class="inputData">
        <div>
          账号：
          <input v-model="username" class="input" type="text" placeholder="请输入账号" />
        </div>
        <div>
          密码：
          <input v-model="password" class="input" type="password" placeholder="请输入密码" />
        </div>
      </div>
      <div class="login-free">
        <input v-model="isLoginFree" type="checkbox" id="loginFree" name="loginFree" value="loginFree">
        <label for="loginFree" style="margin-left: 4px;">记住密码</label><br>
      </div>
      <div>
        <button class="btn" @click="login">登录</button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      username: '',
      password: '',
      isLoginFree: false
    }
  },
  mounted () {
    if (localStorage.getItem('username')) {
      this.username = localStorage.getItem('username')
    }
    if (localStorage.getItem('password')) {
      this.password = localStorage.getItem('password')
      this.isLoginFree = true
    }
  },
  methods: {
    login () {
      this.$post('/users/user/login', {
        username: this.username,
        password: this.password
      }).then(res => {
        console.log(res)
        if (res.code === 200) {
          if (this.isLoginFree) {
            localStorage.setItem('username', this.username)
            localStorage.setItem('password', this.password)
          } else {
            localStorage.removeItem('username')
            localStorage.removeItem('password')
          }
          this.$router.push('/hall-seat')
        }
      })
    }
  }

}
</script>

<style lang="stylus" rel="stylesheet/stylus" scoped="scoped">
.login
  height 100%
  width 100%
  background-image url("../../assets/images/login.jpg")
  background-size 100% 100%
  background-position center center
  overflow auto
  background-repeat no-repeat
  position fixed
  .login-container
    width 80%
    background #fff
    margin-top 500px
    display flex
    flex-direction column
    align-items center
    background-color: rgba(255, 255, 255, .9);
    border-radius 15px
    padding 30px
    margin-left auto
    margin-right auto
    .tittle
      font-size 50px
      shadow 0 2px 2px #ccc
      margin-bottom 40px
    .inputData
      font-size 36px
      width 600px
      display flex
      flex-direction column
      align-items center
      .input
        height 60px
        width 400px
        border 1px solid #ccc
        border-radius 5px
        margin-top 20px
        padding-left 10px
        box-sizing border-box
        &:focus
          border-color: #409eff;
          outline: none
    .login-free
      font-size 24px
      width 500px
      margin 15px 0 0 15px
      display flex
      align-items center
      justify-content flex-start
    .btn
      height 60px
      font-size 30px
      width 400px
      border-radius 5px
      background-color #409eff
      border none
      color #fff
      margin-top 40px

</style>
