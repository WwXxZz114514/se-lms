<template>
  <div>
    <Header>
      <template v-slot:titleView>
        <select v-model="selectArea" style="font-size:18px;width: 80px; background: none; color: white; outline-color: white; margin: 4px;">
          <option v-for="area in areas_info" :key="area.area_id" :value="area.area_id" style="color: black;width: 80px; ">{{ area.area_name }}</option>
        </select>
        <select v-model="time"  style="font-size:14px;width: 140px; background: none; color: white; outline-color: white; margin: 4px;">
          <option :value="item" v-for="(item,index) in timeList" :key="index" style="color: black;width: 80px; ">{{item}}</option>
        </select>
      </template>
    </Header>
    <div class="col" style="height: 550px;overflow: auto; padding: 4px;">
      <div v-for="i in row" :key="i">
        <div class="row" style="align-items: center; height: 40px;">
        <!-- 排标签 -->
          <div class="number" style="width: 50px;">第{{ i }}排</div>
          <div class="col" v-for="j in col" :key="j" style="justify-content: center;">
            <div class="col" v-if="getSeatType(i,j) === 1"   @click="handleClick(i, j)" style="margin: 1px;height: 30px; justify-content: center;">
              <img v-if="selectRow === i && selectCol === j" src="../../assets/images/seat-active.png"  style="height: 24px; width: 24px;">
              <img v-else src="../../assets/images/seat.png"  style="height: 24px; width: 24px;">
            </div>
            <div v-else class="col" style="background: red; margin: 1px;height: 30px; justify-content: center;">
              <img src="../../assets/images/seat.png"  style="height: 24px; width: 24px;">
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-if="selectRow && selectCol" class='selected' style="font-size: 24px;">
      <div>
        <span style="font-size: 20px;">已选座位:</span>
        {{ selectRow }}排{{ selectCol }}座
      </div>

    </div>
    <div class='confirm' @click="lockSeat()">
      确认选座
    </div>
  </div>
</template>
<script>
import Header from '../../components/Header'
export default {
  name: 'HallSeat',
  components: {
    Header
  },
  data () {
    return {
      areas_info: [],
      selectArea: '',
      seats_info: [],
      row: 0,
      col: 0,
      selectRow: '',
      selectCol: '',
      timer: null,
      // 预约时间
      time: '',
      isSelectSeat: false,
      timeList: [],
      selectSeatId: ''
    }
  },
  watch: {
    selectArea (val) {
      this.$get('/areas', {
        area_id: val
      }).then(res => {
        this.row = res.data.areas_info.find(item => item.area_id === val).row_num
        this.col = res.data.areas_info.find(item => item.area_id === val).col_num
      })
      this.getAllSeat()
      console.log(this.selectArea)
    },
    time (val) {
      this.getAllSeat()
    }
  },
  mounted () {
    this.getAllArea()
    this.getTimeList()
    // this.timer = setInterval(() => {
    //   this.getAllArea()
    // }, 5000)
  },
  beforeDestroy () {
    clearInterval(this.timer)
  },
  methods: {
    handleClick (row, col) {
      this.selectRow = row
      this.selectCol = col
      this.isSelectSeat = true
      this.selectSeatId = this.seats_info.find(item => item.seat_row === row && item.seat_col === col).seat_id
    },
    getAllSeat () {
      if (!this.selectArea) {
        return
      }
      this.$get('/seats', {
        area_id: this.selectArea,
        appointment_time: this.time
      }).then(res => {
        if (res.code === 200) {
          this.seats_info = res.data.seats_info
        } else {
          alert(res.msg)
        }
      })
    },
    getSeatType (row, col) {
      let seat = this.seats_info.find(item => item.seat_row === row && item.seat_col === col)
      if (seat) {
        return seat.is_available
      } else {
        return 1
      }
    },
    getAllArea () {
      this.$get('/areas', {
        area_id: 0
      }).then(res => {
        this.areas_info = res.data.areas_info
        if (!this.selectArea) {
          this.selectArea = this.areas_info[0].area_id
        }
      })
    },
    // 0是今天，1是明天
    getTimeList () {
      this.timeList = []
      let date = new Date()
      let year = date.getFullYear()
      let month = date.getMonth() + 1
      let day = date.getDate()
      let hour = date.getHours()
      if (hour < 9) {
        this.timeList.push(`${year}-${month}-${day} 09:00`)
      }
      if (hour < 13) {
        this.timeList.push(`${year}-${month}-${day} 13:00`)
      }

      date.setDate(date.getDate() + 1)
      year = date.getFullYear()
      month = date.getMonth() + 1
      day = date.getDate()
      hour = date.getHours()
      this.timeList.push(`${year}-${month}-${day} 09:00`)
      this.timeList.push(`${year}-${month}-${day} 13:00`)
      this.time = this.timeList[0]
    },
    lockSeat () {
      if (this.isSelectSeat) {
        this.addOrder()
      } else {
        alert('请选择座位')
      }
    },
    addOrder () {
      this.$post('/orders', {
        seat_id: this.selectSeatId,
        appointment_time: this.time
      }).then(res => {
        if (res.code === 200) {
          this.$router.push('/order')
        } else {
          alert(res.msg)
        }
      }).catch(err => {
        alert('重复预约或网络异常')
      })
    }
  }
}
</script>
<style scoped>
.row {
  display: flex;
  flex-direction: row;
}
.col {
  display: flex;
  flex-direction: column;
}
.number {
  font-size: 30px;
}
.confirm{
  position: fixed;
  bottom: 100px;
  z-index: 10;
  width: 100%;
  background: linear-gradient(to right, #6F50F5,#C26DFE);
  line-height: 90px;
  text-align: center;
  color: white;
  font-size:30px;
}
.selected{
  position: fixed;
  z-index: 30;
  bottom: 200px;
  background: #ffffff;
  width: 100%;
  padding: 20px 0 20px 20px;
  overflow: hidden;
}
</style>
