<template>
  <div>
    <div>
      <el-form>
       <el-form-item class='input'>
        <el-input v-model="queryCondition" placeholder="请输入机器人搜索相关信息"></el-input>
      </el-form-item>
      <el-form-item class='button'>
        <el-button type="primary" @click="searchRobotHistory()" class="button-size">搜索</el-button>
      </el-form-item>
      </el-form>
    </div>
    <div class='tableDiv' element-loading-text="拼命加载中"> <!-- v-loading="loading" -->
      <el-table
        :data="robotData"
        border
        style="width: 100%">
        <el-table-column
          prop="id"
          v-if='false'>
        </el-table-column>
        <el-table-column
          fixed
          prop="robot"
          label="机器人编号"
          width="200">
        </el-table-column>
        <el-table-column
          prop="room"
          label="房间编号"
          width="200">
        </el-table-column>
        <el-table-column
          prop="bed"
          label="病床编号"
          width="200">
        </el-table-column>
        <el-table-column
          prop="startDate"
          label="起始日期"
          width="235">
        </el-table-column>
        <el-table-column
          prop="endDate"
          label="截止日期"
          width="240">
        </el-table-column>
        </el-table>
    </div>
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5,8,10]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalNum">
      </el-pagination>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      queryCondition: '',
      // loading: true,
      currentPage: 1,
      pageSize: 5,
      totalNum: 0,
      robotData: []
    }
  },
  created () {
    this.searchRobotHistory()
    // setTimeout(() => {
    //   this.loading = false
    // }, 1500)
  },
  methods: {
    handleSizeChange (val) {
      this.pageSize = val
      this.searchRobotHistory()
      // setTimeout(() => {
      //   this.loading = false
      // }, 1500)
    },
    handleCurrentChange (val) {
      this.currentPage = val
      this.searchRobotHistory()
      // setTimeout(() => {
      //   this.loading = false
      // }, 1500)
    },
    searchRobotHistory () {
      this.$axios.post(
        '/admin/listRecords',
        {
          queryCondition: this.queryCondition,
          state: 1,
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      ).then(response => {
        let responseData = response.data
        let { code, msg, data } = responseData
        if (code !== '0000') {
          this.$message.error(msg)
        } else {
          this.totalNum = data.totalNum
          this.robotData = data.robotData
        }
      }).catch(err => {
        this.$message.error(err)
      })
    }
  }
}
</script>
<style scoped>
  .tableDiv {
    width: 100%;
  }
  body {
    margin: 0;
  }
  .block {
    margin: 20px 28%;
  }
  .button {
    width: 18%;
    float: left;
    margin-left: 2%;
  }
  .input {
    width: 80%;
    float: left;
  }
</style>
