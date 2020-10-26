<template>
  <div>
    <div>
      <el-form class='form'>
        <el-form-item class='input'>
          <el-input v-model="queryCondition" placeholder="请输入机器人搜索相关信息"></el-input>
        </el-form-item>
        <el-form-item class='button'>
          <el-button type="primary" @click="searchRobotofRoom()" class="button-size">搜索</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class='tableDiv'>
      <el-table
        :data="tableData"
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
          width="150">
        </el-table-column>
        <el-table-column
          prop="room"
          label="房间编号"
          width="150">
        </el-table-column>
        <el-table-column
          prop="bed"
          label="病床编号"
          width="150">
        </el-table-column>
        <el-table-column
          prop="startDate"
          label="起始日期"
          width="200">
        </el-table-column>
        <el-table-column
          prop="endDate"
          label="截止日期"
          width="200">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="200">
          <template slot-scope="scope">
            <div>
              <el-popover
                ref="popover"
                placement="bottom"
                width="500"
                trigger="click">
                <el-table
                  :data="detailData"
                  border
                  style="width: 100%">
                  <el-table-column
                    prop="id"
                    v-if='false'>
                  </el-table-column>
                  <el-table-column
                    prop="date"
                    label="日期"
                    width="100">
                  </el-table-column>
                  <el-table-column
                    prop="startTime"
                    label="起始时间"
                    width="100">
                  </el-table-column>
                  <el-table-column
                    prop="endTime"
                    label="截止时间"
                    width="100">
                  </el-table-column>
                  <el-table-column
                    label="操作"
                    width="200">
                    <template slot-scope="scope">
                      <el-button @click="handleEdit(scope.$index, scope.row)" type="text" size="medium">编辑</el-button>
                      <el-button @click="handleDeletePart(scope.$index, scope.row)" type="text" size="medium">解除</el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </el-popover>
            </div>
            <el-button v-popover:popover @click="handleLookInfo(scope.row)" type="text" size="medium">查看详情</el-button>
            <el-button @click="handleDeleteAll(scope.$index, scope.row)" type="text" size="medium">解除</el-button>
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
    <el-dialog title="详情信息" :visible.sync="dialogFormVisible">
      <el-form :model="detailForm">
        <el-form-item :label-width="formLabelWidth">
          <el-input v-model="detailForm.id" auto-complete="off" v-if='false'></el-input>
        </el-form-item>
        <el-form-item label="日期" :label-width="formLabelWidth">
          <el-input v-model="detailForm.date" auto-complete="off" readonly style='width:350px'></el-input>
        </el-form-item>
        <el-form-item label="起止时间点" prop="time">
          <el-time-picker
            is-range
            v-model="detailForm.time"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            placeholder="选择时间范围"
            type='datetime'
            format='HH:mm'
            value-format='HH:mm'>
          </el-time-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel()">取 消</el-button>
        <el-button type="primary" @click="submitForm('detailForm')">确 定</el-button>
      </div>
    </el-dialog>
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
      dialogFormVisible: false,
      formLabelWidth: '80px',
      currentIndex: '',
      tableData: [],
      detailData: [],
      detailForm: {
        id: '',
        date: '',
        time: ''
      }
    }
  },
  created () {
    this.getRobotofRoom()
    // setTimeout(() => {
    //   this.loading = false
    // }, 1500)
  },
  methods: {
    handleSizeChange (val) {
      this.pageSize = val
      this.loading = true
      this.getRobotofRoom()
      // setTimeout(() => {
      //   this.loading = false
      // }, 1500)
    },
    handleCurrentChange (val) {
      this.currentPage = val
      this.loading = true
      this.getRobotofRoom()
      // setTimeout(() => {
      //   this.loading = false
      // }, 1500)
    },
    handleEdit (index, row) {
      this.detailForm = this.detailData[index]
      this.currentIndex = index
      this.dialogFormVisible = true
    },
    cancel () {
      this.dialogFormVisible = false
    },
    handleLookInfo (row) {
      this.$axios.post(
        '/admin/detailTime',
        {
          id: row.id
        }
      ).then(response => {
        let responseData = response.data
        let { code, msg, data } = responseData
        if (code !== '0000') {
          this.$message.error(msg)
        } else {
          this.detailData = data
        }
      }).catch(err => {
        this.$message.error(err)
      })
    },
    handleDeleteAll (index, row) {
      this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.tableData.splice(index, 1)
        this.totalNum--
        this.deleteRobotofRoomAll(row.id)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    handleDeletePart (index, row) {
      this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.detailData.splice(index, 1)
        this.totalNum--
        this.deleteRobotofRoomPart(row)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    searchRobotofRoom () {
      this.$axios.post(
        '/admin/listRecordsByRobot',
        {
          state: 0,
          queryCondition: this.queryCondition,
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
          this.robotofRoomList = data.robotData
          this.tableData = data.robotData
        }
      }).catch(err => {
        this.$message.error(err)
      })
    },
    getRobotofRoom: function () {
      this.$axios.post(
        '/admin/listRecords',
        {
          state: 0,
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }
      ).then(response => {
        let responseData = response.data
        let { code, msg, data } = responseData
        if (code !== '0000') {
          this.$message.error('出现未知错误')
        } else {
          this.totalNum = data.totalNum
          this.tableData = data.robotData
        }
      }).catch(err => {
        this.$message.error(err.message)
      })
    },
    submitForm (formName) {
      this.$axios.post(
        '/admin/changeDetailTime',
        {
          id: this.detailForm.id,
          startTime: this.detailForm.time[0],
          endTime: this.detailForm.time[1]
        }
      ).then(response => {
        let responseData = response.data
        let { code, msg } = responseData
        if (code !== '0000') {
          this.$message.error(msg)
        } else {
          this.$message.success(msg)
          this.dialogFormVisible = false
        }
      }).catch(err => {
        this.$message.error(err)
      })
    },
    deleteRobotofRoomAll (id) {
      this.$axios.post(
        '/admin/unbind',
        {
          id: id
        }
      ).then(response => {
        let responseData = response.data
        let { code, msg } = responseData
        if (code !== '0000') {
          this.$message.error(msg)
        } else {
          this.$message.success(msg)
          this.getRobotofRoom()
        }
      }).catch(err => {
        this.$message.error(err)
      })
    },
    deleteRobotofRoomPart (row) {
      this.$axios.post(
        '/admin/deleteDetailTime',
        {
          id: row.id
        }
      ).then(response => {
        let responseData = response.data
        let { code, msg } = responseData
        if (code !== '0000') {
          this.$message.error(msg)
        } else {
          this.$message.success(msg)
          this.getRobotofRoom()
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
  .block {
    margin: 20px 28%;
  }
  body {
    margin: 0;
  }
  .button {
    width: 18%;
    float: left;
    margin-left: 2%;
  }
  .queryCondition-input {
    width: 380px;
  }
  .input {
    width: 80%;
    float: left;
  }
</style>
