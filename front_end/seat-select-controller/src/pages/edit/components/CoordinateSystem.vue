<template>
<div class="pages">
  <div class="seatSelectArea" ref="seatSelectArea">
      <div class="seatArea" :style="{width:seatAreaWidth+'px',height:seatAreaHeight+ 'px'}" v-show="x!==0 && y!==0">
        <template v-for="(seatItem) in seatList">
          <div ref="seatItem" :x="seatItem.x" :y="seatItem.y" class="seatItem" :key="'x'+seatItem.x+'y'+seatItem.y"
          :style="{width:seatItemWidth+'px',height:seatItemWidth+'px',
          transform: 'translate3d(' + seatItem.translateX  + 'px,'+ seatItem.translateY + 'px,0px)',
          background:seatItem.background}">
            <img :src="normalImg">
          </div>
        </template>
      </div>
  </div>
  </div>
</template>

<script>
import { mapMutations, mapActions, mapState } from 'vuex'
export default {
  name: 'CoordinateSystem',
  props: ['prop_x', 'prop_y', 'prop_seatList', 'prop_templeteId'],
  data () {
    return {
      seatItemWidth: 40,
      shifting: 10, // 偏移值
      left: 0,
      top: 0,
      width: 0,
      height: 0,
      // startX: 0,
      // startY: 0,
      seatList: [],
      seatListBack: null,
      unSelect: '#C6E2FF',
      selecting: '#409EFF',
      selected: '#E9EEF3',
      timer: null,
      normalImg: require('../../../assets/images/0-0-1.png'),
      x: 0,
      y: 0,
      templeteId: null
    }
  },
  watch: {
    prop_seatList (value) {
      this.seatListBack = value
    },
    prop_x (value) {
      this.x = value
      this.refreshDom()
    },
    prop_y (value) {
      this.y = value
      this.refreshDom()
    },
    prop_templeteId (value) {
      this.templeteId = value
    }
  },
  computed: {
    ...mapState({
      templeteName: state => state.hallSeat.templeteName,
      templetePrice: state => state.hallSeat.templetePrice,
      reset: state => state.hallSeat.reset
    }),
    seatAreaWidth () {
      return (this.seatItemWidth + this.shifting) * this.x - this.shifting
    },
    seatAreaHeight () {
      return (this.seatItemWidth + this.shifting) * this.y - this.shifting
    },
    translateValue () {
      return this.seatItemWidth + this.shifting
    },
    selectingList () {
      let selectingList = []
      for (const index in this.seatList) {
        if (this.seatList[index].background === this.selecting) {
          let temp = { ...this.seatList[index] }
          temp.orgIndex = index
          selectingList.push(temp)
        }
      }
      var obj = {}
      for (let index in selectingList) {
        let seatRowList = selectingList[index].y
        if (seatRowList in obj) {
          obj[seatRowList].push(selectingList[index])
        } else {
          let seatArr = []

          seatArr.push(selectingList[index])
          obj[seatRowList] = seatArr
        }
      }
      return obj
    }
  },
  methods: {
    ...mapMutations([
      'changeConfirmButtonLoading']),
    ...mapActions([
      'clearHallSeat'
    ]),
    refreshDom () {
      let that = this
      let seatScale = 1
      let seatScaleX = 1
      let seatScaleY = 1
      let seatSelectAreaWidth = that.$refs.seatSelectArea.offsetWidth
      let seatSelectAreaHeight = that.$refs.seatSelectArea.offsetHeight
      let seatAreaWidth = 50 * that.x - that.shifting
      let seatAreaHeight = 50 * that.y - that.shifting
      if (seatAreaWidth > seatSelectAreaWidth) {
        seatScaleX = seatSelectAreaWidth / seatAreaWidth
      }
      if (seatAreaHeight > seatSelectAreaHeight) {
        seatScaleY = seatSelectAreaHeight / seatAreaHeight
      }
      if (seatScaleX < 1 || seatScaleY < 1) {
        seatScale = seatScaleX < seatScaleY ? seatScaleX : seatScaleY
      }
      that.seatItemWidth = 40 * seatScale
      that.shifting = 10 * seatScale
      that.seatList = []
      for (let x = 1; x <= that.x; x++) {
        for (let y = 1; y <= that.y; y++) {
          var data = {}
          if (!that.reset && that.seatListBack != null && that.seatListBack.length > 0) {
            let temp = that.seatListBack.find((el) => (el.x === x && el.y === y))
            if (temp) {
              debugger
              let backimg = this.normalImg
              data = {
                x: x,
                y: y,
                translateX: that.translateValue * (x - 1),
                translateY: that.translateValue * (y - 1),
                background: this.selected,
                backimg: backimg
              }
              that.seatList.push(data)
              continue
            }
          }
          data = {
            x: x,
            y: y,
            translateX: that.translateValue * (x - 1),
            translateY: that.translateValue * (y - 1),
            background: this.unSelect,
            backimg: null
          }
          that.seatList.push(data)
        }
      }
      this.$nextTick(() => {
        if ('seatItem' in that.$refs) {
          for (const item of that.$refs.seatItem) {
            let clientRect = item.getBoundingClientRect()
            let index = that.seatList.findIndex((el) => (el.x === parseInt(item.attributes.x.value) && el.y === parseInt(item.attributes.y.value)))
            let newObject = Object.assign({}, that.seatList[index], {
              top: clientRect.top,
              bottom: clientRect.bottom,
              left: clientRect.left,
              right: clientRect.right
            })
            this.$set(that.seatList, index, newObject)
          }
        }
      })
    },
    confirm () {
      let that = this
      if (this.templeteName === null) {
        this.$notify({
          title: '警告',
          message: '模版名称不能为空',
          type: 'warning',
          duration: 2000
        })
        this.changeConfirmButtonLoading(false)
        return
      }
      this.$post('areas', {
        area_name: this.templeteName,
        row_num: this.y,
        col_num: this.x
      }).then((response) => {
        this.$router.replace('/')
      }, err => {
        that.$notify.error({
          title: '警告',
          message: err.message,
          duration: 2000
        })
        that.changeConfirmButtonLoading(false)
      })
    }
  },
  created () {},
  mounted () {

  },
  updated () {

  }
}
</script>
<style lang="stylus" scoped>
.pages
  width 100%
  height 100vh;
  padding 100px 50px;
  box-sizing border-box;
  .seatSelectArea
    width 100%
    height 100%;
    position relative;
  .seatArea
    display flex
    flex-wrap: wrap;
    margin 0 auto;
    padding 5px
    border 2px dashed rgba(0,0,0,0.2)
    border-radius 5px;
    .seatItem
      position absolute;
      color white;
      line-height 40px;
      border-radius:5px;
      user-select:none;
      display flex;
      img
        width 100%
        height 100%
        border none
        object-fit cover
</style>
