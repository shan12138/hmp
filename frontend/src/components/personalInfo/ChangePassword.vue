<template>
   <div class='passwordForm'>
    <el-form :model="passwordForm" status-icon :rules="rules" ref="passwordForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="初始密码" prop="oldPassword">
        <el-input type="password" v-model="passwordForm.oldPassword" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input type="password" v-model="passwordForm.newPassword" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="确认新密码" prop="againPassword">
        <el-input type="password" v-model="passwordForm.againPassword" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('passwordForm')">确认修改</el-button>
        <el-button @click="resetForm('passwordForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  data () {
    return {
      passwordForm: {
        oldPassword: '',
        newPassword: '',
        againPassword: ''
      },
      rules: {
        oldPassword: [
          { required: true, message: '请输入原密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        againPassword: [
          { required: true, message: '请再次输入新密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid && this.passwordForm.newPassword === this.passwordForm.againPassword) {
          this.$axios.post(
            '/authentication/changePassword',
            {
              originalPwd: this.passwordForm.oldPassword,
              newPwd: this.passwordForm.newPassword
            }
          )
            .then(response => {
              let responseData = response.data
              let { code, msg, data } = responseData
              if (code === '0000') {
                this.$message.success('修改密码成功')
                this.$store.dispatch('setUserIdInfo', '')
                this.$store.dispatch('setUsername', '')
                this.$store.dispatch('setRealName', '')
                sessionStorage.removeItem('user_id')
                sessionStorage.removeItem('username')
                sessionStorage.removeItem('realName')
                this.$router.replace({ path: '/admin/login' })
              } else {
                this.$message.error(msg)
              }
            })
            .catch(err => {
              this.$message.error(err)
            })
        } else {
          this.$message.error('原密码错误或两次密码不一致，请检查后重新输入')
          return false
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>
<style scoped>
  .passwordForm {
    margin-left: 40px;
    width: 50%;
  }
</style>
