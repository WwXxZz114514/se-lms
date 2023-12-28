<template>
    <el-container>
      <el-header>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>模版列表</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="back">
        <el-button icon="el-icon-full-screen" circle @click="handleFullScreen"></el-button>
        <el-button
                size="small"
                type="danger"
                @click="handleMultipleDelete()">删除选中</el-button>
        <el-button
          size="small"
          @click="logout()">退出登录</el-button>
      </div>
      </el-header>

      <el-main>
        <el-table
        @selection-change="handleSelectionChange"
        :data="tableData" :stripe="true" height="100%">
          <el-table-column
            fixed
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column
            prop="area_id"
            label="id"
            width="250"
            >
          </el-table-column>

          <el-table-column
            prop="area_name"
            align="center"
            label="名称"
            width="250"
            >
          </el-table-column>
          <el-table-column
            prop="row_num"
            align="center"
            label="排数"
            width="250"
            >
          </el-table-column>
          <el-table-column
            prop="col_num"
            align="center"
            label="列数"
            width="250"
            >
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            show-overflow-tooltip>
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.row)">编辑</el-button>
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
export default {
  data () {
    return {
      tableData: [],
      multipleSelection: []
    }
  },
  computed: {
    ...mapState({
      fullscreen: state => state.config.fullscreen
    })
  },
  methods: {
    ...mapMutations([
      'makeFullscreen'
    ]),
    handleSelectionChange (val) {
      this.multipleSelection = val
      console.log(this.multipleSelection)
    },
    getTempleteList () {
      this.$get('/areas', {
        area_id: 0
      }).then((response) => {
        console.log(response)
        this.tableData = response.data.areas_info
      }, err => {
        this.$notify.error({
          title: '警告',
          message: err.message,
          duration: 2000
        })
      })
    },
    handleEdit (row) {
      this.$prompt('请修改模板名称', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^.+$/,
        inputErrorMessage: '模板名称不能为空'
      }).then(({ value }) => {
        this.$patch('/areas', {
          area_id: row.area_id,
          area_name: value
        }).then(res => {
          if (res.code === 200) {
            this.$message({
              type: 'success',
              message: '修改成功!'
            })
            this.getTempleteList()
          } else {
            this.$message({
              type: 'error',
              message: '修改失败!'
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        })
      })
    },
    handleDelete (area) {
      this.$confirm('此操作将永久删除模板, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log(area)
        this.$delete('/areas', {
          area_id: area.area_id
        }).then((response) => {
          console.log(response)
          this.getTempleteList()
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }, err => {
          this.$notify.error({
            title: '警告',
            message: err.message,
            duration: 2000
          })
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    handleMultipleDelete () {
      this.multipleSelection.forEach((el) => {
        this.$delete('/areas', {
          area_id: el.area_id
        }).then((response) => {
          console.log(response)
          this.getTempleteList()
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }, err => {
          this.$notify.error({
            title: '警告',
            message: err.message,
            duration: 2000
          })
        })
      })
    },
    handleFullScreen () {
      let element = document.documentElement
      if (this.fullscreen) {
        if (document.exitFullscreen) {
          document.exitFullscreen()
        } else if (document.webkitCancelFullScreen) {
          document.webkitCancelFullScreen()
        } else if (document.mozCancelFullScreen) {
          document.mozCancelFullScreen()
        } else if (document.msExitFullscreen) {
          document.msExitFullscreen()
        }
        this.$message({
          showClose: true,
          message: '已退出全屏模式',
          type: 'success'
        })
      } else {
        if (element.requestFullscreen) {
          element.requestFullscreen()
        } else if (element.webkitRequestFullScreen) {
          element.webkitRequestFullScreen()
        } else if (element.mozRequestFullScreen) {
          element.mozRequestFullScreen()
        } else if (element.msRequestFullscreen) {
          // IE11
          element.msRequestFullscreen()
        }
        this.$message({
          showClose: true,
          message: '已进入全屏幕模式,如需退出请按ESC按键或再次点击',
          type: 'success'
        })
      }
      this.makeFullscreen(!this.fullscreen)
    },
    logout () {
      this.$post('/users/admin/logout').then(res => {
        if (res.code === 200) {
          this.$router.push('/login')
        } else {
          this.$message({
            type: 'error',
            message: '退出失败!'
          })
        }
      })
    }
  },

  mounted () {
    this.getTempleteList()
  }
}
</script>

<style lang="stylus" scoped>
  .el-header
    height:20vh;
    background-color: #E9EEF3;
    display flex
    align-items center
    justify-content space-between
  .el-main
    height 80vh;
    background-color: #E9EEF3;
    text-align: center;
    padding 0;
    .name-wrapper
      display inline-block

</style>
