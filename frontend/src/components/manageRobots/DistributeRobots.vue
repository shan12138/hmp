<template>
  <el-container>
    <el-main>
      <el-form :model="robotForm" status-icon :rules="rules" ref="robotForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="机器人编号" prop="robot">
          <el-select v-model="robotForm.robot" clearable placeholder="请选择机器人编号">
            <el-option
              v-for="item in robotList"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="房间号" prop="room">
          <el-select v-model="robotForm.room" clearable placeholder="请选择房间号">
            <el-option
              v-for="item in roomList"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="病床号" prop="bed">
          <el-select v-model="robotForm.bed" clearable placeholder="请选择病床号">
            <el-option
              v-for="item in bedList"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="日期" prop="date">
          <el-date-picker
            v-model="robotForm.date"
            type="daterange"
            value-format="yyyy-MM-dd"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="起止时间点" prop="time">
          <el-time-picker
            is-range
            v-model="robotForm.time"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            placeholder="选择时间范围"
            type='datetime'
            format='HH:mm'
            value-format='HH:mm'>
          </el-time-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('robotForm')">确定</el-button>
          <el-button @click="resetForm('robotForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
export default {
  data () {
    return {
      robotForm: {
        robot: '',
        room: '',
        bed: '',
        date: '',
        time: ''
      },
      rules: {
        robot: [
          {
            required: true, message: '请选择机器人编号', trigger: 'change'
          }
        ],
        room: [
          {
            required: true, message: '请选择房间编号', trigger: 'change'
          }
        ],
        bed: [
          {
            required: true, message: '请选择病床编号', trigger: 'change'
          }
        ],
        date: [
          {
            required: true, message: '请选择星期', trigger: 'change'
          }
        ],
        time: [
          {
            required: true, message: '请选择时间', trigger: 'change'
          }
        ]
      },
      robotList: [],
      roomList: [],
      bedList: []
    }
  },
  watch: {
    'robotForm.room' (val, oldVal) {
      this.$axios.post(
        '/admin/listBeds',{ "room": val }
      )
        .then(response => {
          let { code, msg, data } = response.data
          this.bedList = data
        })
        .catch(err => {
          this.$message.error(err)
        })
    }
  },
  created () {
    this.$axios.post('/admin/listRobots').then(response => {
      let { code, msg, data } = response.data
      this.robotList = data
      sessionStorage.setItem('robotList', this.robotList)
      this.$store.dispatch('setRobotListInfo', sessionStorage.getItem('robotList'))
    }).catch(err => {
      this.$message.error(err)
    })
    this.$axios.post('/admin/listRooms').then(response => {
      let { code, msg, data } = response.data
      this.roomList = data
      sessionStorage.setItem('roomList', this.roomList)
      this.$store.dispatch('setRoomListInfo', sessionStorage.getItem('roomList'))
    }).catch(err => {
      this.$message.error(err)
    })
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post(
            '/admin/distributeRobots',
            {
              robot: this.robotForm.robot,
              room: this.robotForm.room,
              bed: this.robotForm.bed,
              startDate: this.robotForm.date[0],
              endDate: this.robotForm.date[1],
              startTime: this.robotForm.time[0],
              endTime: this.robotForm.time[1]
            }
          ).then(response => {
            let responseData = response.data
            let { code, msg, data } = responseData
            if (code === '0000') {
              this.$message.success(msg)
            } else {
              this.$message.error(msg)
            }
          }).catch(err => {
            this.$message.error(err)
          })
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    }
  },
  computed: {
  }
}
</script>

<style scoped>
  .el-input {
    width: 300px;
  }
  .demo-ruleForm {
    margin-left: 10px;
  }
</style>
