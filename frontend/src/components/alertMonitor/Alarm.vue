<template>
  <div class='tableDiv'>
    <el-table
      :data="alarmData"
      border
      style="width: 100%">
      <el-table-column
        prop="id"
        v-if='false'>
      </el-table-column>
      <el-table-column
        fixed
        prop="crtDate"
        label="预警时间"
        width="200">
      </el-table-column>
      <el-table-column
        prop="userInfo.name"
        label="病人姓名"
        width="150">
      </el-table-column>
      <el-table-column
        prop="robotId"
        label="机器人编号"
        width="150">
      </el-table-column>
      <el-table-column
        prop="room"
        label="房间号"
        width="150">
      </el-table-column>
      <el-table-column
        prop="userInfo.bed"
        label="病床号"
        width="150">
      </el-table-column>
      <el-table-column
        prop="userInfo.age"
        label="年龄"
        width="100">
      </el-table-column>
      <el-table-column
        label="操作"
        width="156">
        <template slot-scope="scope">
          <el-button @click="handleConfirm(scope.$index, scope.row)" type="text" size="medium">确认预警信息</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[3, 4, 5]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalNum">
      </el-pagination>
    </div> -->
  </div>
</template>
<script>
export default {
  data () {
    return {
      // totalNum: null,
      // pageSize: 3,
      // currentPage: 0,
      alarmData: []
    }
  },
  created () {
    this.getAlarmData()
  },
  methods: {
    handleSizeChange (val) {
      this.pageSize = val
      this.getAlarmData()
    },
    handleCurrentChange (val) {
      this.currentPage = val
      this.getAlarmData()
    },
    handleConfirm (index, row) {
      this.$confirm('此操作将确认预警信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.alarmData.splice(index, 1)
        // this.totalNum--
        this.confirmAlarm(row.id)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消确认'
        })
      })
    },
    confirmAlarm (id) {
      this.$axios.post(
        '/admin/handelWarning',
        {
          id: id
        }
      ).then (response => {
        let { code, msg } = response.data
        if (code !== '0000') {
          this.$message.error(msg)
        } else {
          this.$message.success('确认成功')
        }
      }).catch (err => {
        this.$message.error(err)
      })
    },
    getAlarmData () {
      this.$axios.post('/admin/getWarningInfo').then(response => {
        let { code, msg, data } = response.data
        this.alarmData = data
        if (code !== '0000') {
          this.$message.error('请求异常！')
        }
      }).catch(err => {
        this.$message.error(err)
      })
    }
  }
}
</script>
<style scoped>
  .block {
    margin: 20px 28%;
  }
</style>
