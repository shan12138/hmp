<template>
  <el-container>
    <el-main>
      <el-form :model="medicineForm" status-icon ref="medicineForm" label-width="100px" class="demo-ruleForm">
        <el-form-item>
          <el-col :span='2'>机器人编号</el-col>
          <el-col :span='5'>
            <el-select v-model="medicineForm.robot" clearable placeholder="请选择机器人编号">
              <el-option
                v-for="item in robotList"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span='1'>&nbsp;</el-col>
          <el-col :span='2'>房间号</el-col>
          <el-col :span='5'>
            <el-select v-model="medicineForm.room" clearable placeholder="请选择房间号">
              <el-option
                v-for="item in roomList"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item>
          <el-col :span='2'>病床号</el-col>
          <el-col :span='5'>
            <el-select v-model="medicineForm.bed" clearable placeholder="请选择病床号">
              <el-option
                v-for="item in bedList"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span='1'>&nbsp;</el-col>
          <el-col :span='2'>药品名字</el-col>
          <el-col :span='5'>
            <el-select v-model="medicineForm.drugName" clearable placeholder="请选择药品名字">
              <el-option
                v-for="item in medicineList"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item>
          <el-col :span='2'>早晨</el-col>
          <el-col :span="6">
            <el-time-picker
              v-model="medicineForm.morningTime"
              placeholder="选择通知时间"
              type='datetime'
              format='HH:mm'
              value-format='HH:mm'>
            </el-time-picker>
          </el-col>
          <el-col :span="2">用药剂量</el-col>
          <el-col :span="5">
            <el-input v-model="medicineForm.morningAmount" placeholder="请输入早晨药品剂量" ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item>
          <el-col :span='2'>中午</el-col>
          <el-col :span="6">
            <el-time-picker
              v-model="medicineForm.noonTime"
              placeholder="选择通知时间"
              type='datetime'
              format='HH:mm'
              value-format='HH:mm'>
            </el-time-picker>
          </el-col>
          <el-col :span="2">用药剂量</el-col>
          <el-col :span="5">
            <el-input v-model="medicineForm.noonAmount" placeholder="请输入中午药品剂量" ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item>
          <el-col :span='2'>晚上</el-col>
          <el-col :span='6'>
            <el-time-picker
              v-model="medicineForm.eveningTime"
              placeholder="选择通知时间"
              type='datetime'
              format='HH:mm'
              value-format='HH:mm'>
            </el-time-picker>
          </el-col>
          <el-col :span='2'>用药剂量</el-col>
          <el-col :span='5'>
            <el-input v-model="medicineForm.eveningAmount" placeholder="请输入晚上药品剂量" ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item>
          <el-col :span='2'>注意事项</el-col>
          <el-col :span='13'>
            <el-input
              type="textarea"
              :rows="2"
              placeholder="请输入注意事项"
              v-model="medicineForm.attention">
            </el-input>
          </el-col>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('medicineForm')">确定</el-button>
          <el-button @click="resetForm('medicineForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>
<script>
export default {
  data () {
    return {
      medicineForm: {
        robot: '',
        room: '',
        bed: '',
        drugName: '',
        releaseMen: '',
        morningTime: '00:00',
        morningAmount: 0,
        noonTime: '00:00',
        noonAmount: 0,
        eveningTime: '00:00',
        eveningAmount: 0,
        attention: ''
      },
      robotList: [],
      roomList: [],
      bedList: [],
      medicineList: []
    }
  },
  // computed: {
  //   robotList: function(){
  //       return this.$store.state.robotList
  //   },
  //   roomList: function(){
  //       return this.$store.state.roomList
  //   }
  // },
  watch: {
    'medicineForm.room' (val, oldVal) {
      this.$axios.post(
        '/admin/listBeds',
        { room: val }
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
  beforeUpdate() {
    const robotSession = this.$store.state.robotList
    if(robotSession){
      this.robotList = robotSession.split(',')
    }
    const roomSession = this.$store.state.roomList
    if(roomSession){
      this.roomList = roomSession.split(',')
    }
  },
  created () {
    this.$axios.post('/admin/listMedicines').then(response => {
      let { code, msg, data } = response.data
      this.medicineList = data
    }).catch(err => {
      this.$message.error(err)
    })
  },
  methods: {
    submitForm (formName) {
      this.$axios.post(
        '/admin/setMedicineRemind',
        {
          robot: this.medicineForm.robot,
          room: this.medicineForm.room,
          bed: this.medicineForm.bed,
          drugName: this.medicineForm.drugName,
          releaseMen: this.$store.state.user_id,
          morningTime: this.medicineForm.morningTime,
          morningAmount: this.medicineForm.morningAmount,
          noonTime: this.medicineForm.noonTime,
          noonAmount: this.medicineForm.noonAmount,
          eveningTime: this.medicineForm.eveningTime,
          eveningAmount: this.medicineForm.eveningAmount,
          attention: this.medicineForm.attention,
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
  }
}
</script>
<style scoped>

</style>
