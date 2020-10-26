<template>
  <el-container>
    <div class="img">
      <div class="font">养老机器人后台管理系统</div>
      <div class="formstyle">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>登录窗口</span>
          </div>
          <div>
            <el-form
              :model="loginForm"
              status-icon
              :rules="rules"
              ref="loginForm"
              label-width="100px"
              class="demo-ruleForm"
            >
              <el-form-item label="账号" prop="username">
                <el-input v-model="loginForm.username"></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input type="password" v-model="loginForm.password" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitForm('loginForm')">登录</el-button>
                <el-button @click="resetForm('loginForm')">重置</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-card>
      </div>
    </div>
  </el-container>
</template>

<script>
export default {
  name: 'AdminLogin',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入账户', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$axios
            .post(
              '/authentication/login',
              this.$Qs.stringify({
                username: this.loginForm.username,
                password: this.loginForm.password
              }),
              {
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
              }
            )
            .then(response => {
              let responseData = response.data
              let { code, msg, data } = responseData
              if (code === '0000') {
                this.$message.success('登录成功!')
                this.$store.dispatch('setUserIdInfo', data.id)
                this.$store.dispatch('setUsernameInfo', this.username)
                this.$store.dispatch('setRealNameInfo', data.realName)
                sessionStorage.setItem('user_id', data.id)
                sessionStorage.setItem('username', this.username)
                sessionStorage.setItem('realName', data.realName)
                this.$store.dispatch('setWebsocketInfo')
                this.$router.push({ path: '/admin/DistributeRobots' })
              } else {
                this.$message.error(msg)
              }
            })
            .catch(err => {
              this.$message.error(err)
            })
        } else {
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>

<style scoped>
.box-card {
  opacity: 0.8;
}
.font {
  font-size: 50px;
  font-family: '平方SC';
  margin-left: 30%;
  margin-top: 10%;
  color: white;
}
.formstyle {
  width: 30%;
  height: 200px;
  margin-left: 33%;
  margin-top: 5%;
}
.img {
  width: 100%;
  height: 100%;
  background: url(../../../static/3.jpg);
  background-repeat: no-repeat;
  background-size: 100% 100%;
  position: fixed;
}
</style>
