 <template>
  <div>
    <el-form :model="healthyForm" status-icon ref="healthyForm" label-width="100px" class="demo-ruleForm">
      <el-form-item>
        <el-col :span='1'>房间号</el-col>
        <el-col :span='3'>
          <el-select v-model="healthyForm.room" clearable placeholder="请选择房间号">
            <el-option
              v-for="item in roomList"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span='1'>&nbsp;</el-col>
        <el-col :span='1'>病床号</el-col>
        <el-col :span='3'>
          <el-select v-model="healthyForm.bed" clearable placeholder="请选择病床号">
            <el-option
              v-for="item in bedList"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span='1'>&nbsp;</el-col>
        <el-col :span='2'>时间范围</el-col>
        <el-col :span='9'>
          <el-date-picker
            v-model="healthyForm.date"
            type="daterange"
            value-format="yyyy-MM-dd"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-col>
        <el-col :span='2'>
          <el-button type="primary" @click="submitForm('robotForm')">确定</el-button>
        </el-col>
      </el-form-item>
    </el-form>
    <div>
      <el-carousel :interval="5000" arrow="always" initialSlideWidth={400} height="400px">
        <el-carousel-item>
          <ve-line :data="heartReatData" :settings="heartReatDataSettings" ref="chart1"></ve-line>
        </el-carousel-item>
        <el-carousel-item>
          <ve-line :data="bodyTemperatureData" :settings="bodyTemperatureDataSettings" ref="chart2"></ve-line>
        </el-carousel-item>
        <el-carousel-item>
          <ve-line :data="bloodSugarData" :settings="bloodSugarDataSettings" ref="chart3"></ve-line>
        </el-carousel-item>
        <el-carousel-item>
          <ve-line :data="bloodPressureData" :settings="bloodPressureDataSettings" ref="chart4"></ve-line>
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
import VeLine from 'v-charts/lib/line'
import 'v-charts/lib/style.css'
import { log } from 'util'

export default {
  components: {
    VeLine
  },
  data () {
    this.heartReatDataSettings = {
      min: [60],
      max: [100]
    }
    this.bodyTemperatureDataSettings = {
      min: [35],
      max: [40]
    }
    this.bloodSugarDataSettings = {
      min: [3.9],
      max: [6.0]
    }
    this.bloodPressureDataSettings = {
      min: [80],
      max: [130]
    }
    return {
      bannerHeight:1000,
      screenWidth: 1920,
      heartReatData: {
        columns: ['时间', '心率'],
        rows: []
      },
      bodyTemperatureData: {
        columns: ['时间', '体温'],
        rows: []
      },
      bloodSugarData: {
        columns: ['时间', '血糖'],
        rows: []
      },
      bloodPressureData: {
        columns: ['时间', '血压'],
        rows: []
      },
      healthyForm: {
        room: '',
        bed: '',
        date: ''
      },
      roomList: [],
      bedList: [],
      type:''
    }
  },
  watch: {
    'healthyForm.room' (val, oldVal) {
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
  created() {
    const roomSession = this.$store.state.roomList
    if(roomSession){
      this.roomList = roomSession.split(',')
    }
  },
  methods: {
    submitForm (formName) {
      this.$axios.post(
        '/admin/searchData',
        {
          room: this.healthyForm.room,
          bed: this.healthyForm.bed,
          startDate: this.healthyForm.date[0],
          endDate: this.healthyForm.date[1],
        }
      ).then(response => {
        let { code, msg, data } = response.data
        if (code !== '0000') {
          this.$message.error(msg)
        } else {
              this.heartReatData.rows = this.getData(this.healthyForm.date[0],this.healthyForm.date[1],
                                                  '心率', data[0])
              this.bodyTemperatureData.rows = this.getData(this.healthyForm.date[0],this.healthyForm.date[1],
                                                  '体温', data[1])
              this.bloodSugarData.rows = this.getData(this.healthyForm.date[0],this.healthyForm.date[1],
                                                  '血糖', data[2])
              this.bloodPressureData.rows = this.getData(this.healthyForm.date[0],this.healthyForm.date[1],
                                                  '血压', data[3])           
                                                                                                              
        }
      }).catch(err => {
        this.$message.error(err)
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    getData:function(startDate,endDate,type,data) {
      let myarr = new Array()
      if(startDate==endDate){
        var data1 = {}
        data1['时间'] = '0-2hour'
        data1[type] = data.hour1
        myarr.push(data1)
        var data2 = {}
        data2['时间'] = '3-5hour'
        data2[type] = data.hour2
        myarr.push(data2)
        var data3 = {}
        data3['时间'] = '6-8hour'
        data3[type] = data.hour3
        myarr.push(data3)
        var data4 = {}
        data4['时间'] = '9-11hour'
        data4[type] = data.hour4
        myarr.push(data4)
        var data5 = {}
        data5['时间'] = '12-14hour'
        data5[type] = data.hour5
        myarr.push(data5)
        var data6 = {}
        data6['时间'] = '15-17hour'
        data6[type] = data.hour6
        myarr.push(data6)
        var data7 = {}
        data7['时间'] = '18-20hour'
        data7[type] = data.hour7
        myarr.push(data7)
        var data8 = {}
        data8['时间'] = '21-23hour'
        data8[type] = data.hour8
        myarr.push(data8)
      }else{
        for(let i=0; i < data.length; i++){
          var temperatureData = {}
          temperatureData['时间'] = data[i].day
          temperatureData[type] = data[i].avg
          myarr.push(temperatureData)
        }
      }
      return myarr
    }
  }
}
</script>
<style>
  .demo-ruleForm {
    margin-left: -10%;
  }
  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>
