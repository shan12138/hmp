<template>
<el-container style='height: 100%'>
  <el-header>
    <span class='title'>养老机器人后台管理系统</span>
    <span class='font'>欢迎你 {{realName}}</span>
  </el-header>
  <el-container>
    <el-aside class='aside'>
      <!-- #AFD7F3 -->
      <el-menu
      default-active="2"
      class="el-menu-vertical-demo"
      @select="handleSelect"
      background-color="#202D40"
      text-color="#fff"
      active-text-color="#ffd04b">
      <el-submenu index="1">
          <template slot="title">
          <i class="el-icon-menu"></i>
          <span>机器人管理</span>
          </template>
          <el-menu-item-group>
          <el-menu-item index="DistributeRobots">分配机器人</el-menu-item>
          <el-menu-item index="ManageRobots">管理机器人</el-menu-item>
          <el-menu-item index="RobotHistory">历史记录</el-menu-item>
          </el-menu-item-group>
      </el-submenu>
      <el-menu-item index="LookUserHistory">
          <i class="el-icon-menu"></i>
          <span slot="title">查看用户记录</span>
      </el-menu-item>
      <el-submenu index="3">
          <template slot="title">
          <i class="el-icon-menu"></i>
          <span>用药提醒</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="SetMedicineRemind">设置用药提醒</el-menu-item>
            <el-menu-item index="LookMedicineHistory">查看记录</el-menu-item>
          </el-menu-item-group>
      </el-submenu>
      <el-menu-item index="ExaminationResults">
          <i class="el-icon-menu"></i>
          <span slot="title">体检结果统计</span>
      </el-menu-item>
      <!-- <el-submenu index="4">
          <template slot="title">
          <i class="el-icon-menu"></i>
          <span>健康管理</span>
          </template>
          <el-menu-item-group>
          <el-menu-item index="RealtimeExaminationData">实时体检数据</el-menu-item>
            <el-menu-item index="ExaminationResults">体检结果统计</el-menu-item>
            <el-menu-item index="ExaminationData">体检数据统计</el-menu-item>
            <el-menu-item index="ExaminationDataHistory">历史体检数据</el-menu-item>
          </el-menu-item-group>
      </el-submenu> -->
      <el-menu-item index="VideoMonitor">
          <i class="el-icon-menu"></i>
          <span slot="title">视频监控</span>
      </el-menu-item>
      <!-- <el-submenu index="5">
          <template slot="title">
          <i class="el-icon-menu"></i>
          <span>视频监控</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="VideoMonitor">视频监控</el-menu-item>
            <el-menu-item index="VideoHistory">历史数据</el-menu-item>
          </el-menu-item-group>
      </el-submenu> -->
      <el-menu-item index="Alarm">
          <i class="el-icon-menu"></i>
          <span slot="title">报警中心</span>
      </el-menu-item>
      <!-- <el-submenu index="6">
          <template slot="title">
          <i class="el-icon-menu"></i>
          <span>紧急报警提示</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="Alarm">报警</el-menu-item>
            <el-menu-item index="AbnormalVitalWarning">生命体征异常预警</el-menu-item>
          </el-menu-item-group>
      </el-submenu> -->
      <el-submenu index="7">
        <template slot="title">
        <i class="el-icon-menu"></i>
        <span>个人中心</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="ChangePassword">修改密码</el-menu-item>
          <el-menu-item index='8' @click='logout'>退出登录</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      </el-menu>
    </el-aside>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</el-container>
</template>

<script>
export default {
  data () {
    return {
      realName: ''
    }
  },
  created () {
    this.realName = this.$store.state.realName
  },
  methods: {
    handleSelect (key, keyPath) {
      this.$router.push('/admin/' + key)
    },
    logout () {
      this.$store.dispatch('setUserIdInfo', '')
      this.$store.dispatch('setUsername', '')
      this.$store.dispatch('setRealName', '')
      sessionStorage.removeItem('user_id')
      sessionStorage.removeItem('username')
      sessionStorage.removeItem('realName')
      this.$router.replace({ path: '/admin/login' })
    }
  }
}
</script>

<style scoped>
  .el-header {
    background-color: #E6F7FF;
    /* #AFD7F3; */
    /* #0676C2; */
    /* color: #333; */
    text-align: center;
    font-size: 20px;
    height: 100px;
  }
  .title {
    font-family: '平方SC';
    color: black;
    font-size: 35px;
  }
  .font {
    font-family: '平方SC';
    color: black;
    font-size: 15px;
    float: right;
    margin-top: 30px;
  }
  .el-aside {
    background-color: #202D40!important;
    width: 280px;
    overflow: auto;
  }
</style>
