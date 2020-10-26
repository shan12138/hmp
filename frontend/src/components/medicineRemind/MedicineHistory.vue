<template>
  <div>
    <el-form>
      <el-form-item class='button'>
        <el-button type="primary" @click="searchMedicineUsing()" class="button-use">正在使用</el-button>
        <el-button type="primary" @click="searchMedicineHistory()" class="button-history">历史记录</el-button>
      </el-form-item>
    </el-form>
    <div class='tableDiv'>
      <el-table
        :data="medicineData"
        border
        style="width: 100%">
        <el-table-column
          prop="id"
          v-if='false'>
        </el-table-column>
        <el-table-column
          prop="real_name"
          label="创建者"
          width="80">
        </el-table-column>
        <el-table-column
          prop="releaseTime"
          label="创建时间"
          width="190">
        </el-table-column>
        <el-table-column
          prop="robot"
          label="机器人编号"
          width="80">
        </el-table-column>
        <el-table-column
          prop="room"
          label="房间编号"
          width="80">
        </el-table-column>
        <el-table-column
          prop="bed"
          label="病床编号"
          width="80">
        </el-table-column>
        <el-table-column
          prop="drugName"
          label="药品名称"
          width="80">
        </el-table-column>
        <el-table-column
          prop="morningTime"
          label="早晨提醒时间"
          width="80">
        </el-table-column>
        <el-table-column
          prop="morningAmount"
          label="早晨剂量"
          width="50">
        </el-table-column>
        <el-table-column
          prop="noonTime"
          label="中午提醒时间"
          width="80">
        </el-table-column>
        <el-table-column
          prop="noonAmount"
          label="中午剂量"
          width="50">
        </el-table-column>
        <el-table-column
          prop="eveningTime"
          label="晚上提醒时间"
          width="80">
        </el-table-column>
        <el-table-column
          prop="eveningAmount"
          label="晚上剂量"
          width="50">
        </el-table-column>
        <el-table-column
          prop="attention"
          label="注意事项"
          width="120">
        </el-table-column>
        <el-table-column
          label="操作"
          width="100"
          v-if=flag>
          <template slot-scope="scope">
            <!-- <el-button @click="handleEdit(scope.$index, scope.row)" type="text" size="medium">编辑</el-button> -->
            <el-button @click="handleDelete(scope.$index, scope.row)" type="text" size="medium">删除</el-button>
          </template>
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
      flag: true,
      medicineData: []
    }
  },
  created () {
    this.searchMedicineUsing()
    // setTimeout(() => {
    //   this.loading = false
    // }, 1500)
  },
  methods: {
    handleSizeChange (val) {
      this.pageSize = val
      this.loading = true
      if (this.flag === true) {
        this.searchMedicineUsing()
      } else {
        this.searchMedicineHistory()
      }
      // setTimeout(() => {
      //   this.loading = false
      // }, 1500)
    },
    handleCurrentChange (val) {
      this.currentPage = val
      this.loading = true
      if (this.flag === true) {
        this.searchMedicineUsing()
      } else {
        this.searchMedicineHistory()
      }
      // setTimeout(() => {
      //   this.loading = false
      // }, 1500)
    },
    searchMedicineHistory () {
      this.flag = false
      this.loading = true
      this.$axios.post(
        '/admin/queryMedicineRemind',
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
          this.medicineData = data.robotData
          this.tableData = data.robotData
        }
      }).catch(err => {
        this.$message.error(err)
      })
      // setTimeout(() => {
      //   this.loading = false
      // }, 1500)
    },
    searchMedicineUsing () {
      this.flag = true
      this.loading = true
      this.$axios.post(
        '/admin/queryMedicineRemind',
       {
          queryCondition: this.queryCondition,
          state: 0,
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      ).then(response => {
        let { code, msg, data } = response.data
        if (code !== '0000') {
          this.$message.error(msg)
        } else {
          this.totalNum = data.totalNum
          this.medicineData = data.robotData
          this.tableData = data.robotData
        }
      }).catch(err => {
        this.$message.error(err)
      })
      // setTimeout(() => {
      //   this.loading = false
      // }, 1500)
    },
    handleDelete (index, row) {
      this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.medicineData.splice(index, 1)
        this.totalNum--
        this.deleteMedicine(row.id)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    deleteMedicine (id) {
      this.$axios.post(
        '/admin/deleteMedicine',
        {
          id: id
        }
      ).then (response => {
        let { code, msg } = response.data
        if (code !== '0000') {
          this.$message.error(msg)
        } else {
          this.$message.success('删除成功')
        }
      }).catch (err => {
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
</style>
