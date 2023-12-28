<template>
  <div class="col" style="background: #efefef;">
    <Header>
      <template v-slot:titleView>
        <span style="font-size: 18px;">订单</span>
      </template>
    </Header>
    <div style="overflow-y: auto; height: 88vh;">
      <select v-model="selectOrderType" style="width: 100px; height:30px; font-size: 16px; margin: 0 0 0 8px;">
        <option value="0" style="font-size: 16px;">未签到订单</option>
        <option value="1" style="font-size: 16px;">历史订单</option>
      </select>
      <div class="row" v-for="item in orderList" :key="item.order_id" style="height: 100px;justify-content: space-between;
        font-size: 16px; background: white;margin: 8px; padding: 8px; border-radius: 10px;">
          <div class="col" style="justify-content:space-around;height: 100%;">
            <div>订单号：{{ item.order_id }}</div>
            <div>图书馆名称：{{ item.area_name }}</div>
            <div>座位：{{ item.seat.seat_row + '排' + item.seat.seat_col + '列'}}</div>
            <div>预约时间：{{ item.time }}</div>
          </div>
          <div  class="col" style="justify-content:center;height: 100%; margin-right: 12px;color: blue;" @click="cancleOrder(item.order_id)">
            取消预约
          </div>
      </div>
    </div>
</div>
</template>

<script>
import Header from '../../components/Header'

export default {
  name: 'Order',
  components: {
    Header
  },
  data () {
    return {
      selectOrderType: '0',
      orderList: []
    }
  },
  mounted () {
    this.getOrderList()
  },
  watch: {
    selectOrderType (val) {
      if (val === '0') {
        this.getOrderList()
      } else if (val === '1') {
        this.getHistoryOrderList()
      }
    }
  },
  methods: {
    getOrderList () {
      this.$get('/orders', {
        order_id: this.selectOrderType
      }).then(res => {
        console.log(res)
        this.orderList = res.data.order_info
      })
    },
    getHistoryOrderList () {
      this.$get('/orders/history').then(res => {
        console.log(res)
        this.orderList = res.data.order_info
      })
    },
    cancleOrder (id) {
      this.$delete('/orders', {
        order_id: id
      }).then(res => {
        if (res.code === 200) {
          if (this.selectOrderType === '0') {
            this.getOrderList()
          } else if (this.selectOrderType === '1') {
            this.getHistoryOrderList()
          }
        }
      })
    }
  }
}
</script>

<style scoped>
</style>
