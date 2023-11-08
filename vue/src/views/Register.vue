<template>
  <div class="container">
    <div style="width: 400px; padding: 30px; background-color: white; border-radius: 5px;">
      <div style="text-align: center; font-size: 20px; margin-bottom: 20px; color: #333">Welcome Registration</div>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user" placeholder="please enter username" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" placeholder="please enter password" show-password
                    v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPass">
          <el-input prefix-icon="el-icon-lock" placeholder="please confirm password" show-password
                    v-model="form.confirmPass"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%; background-color: #333; border-color: #333; color: white" @click="register">
            Register
          </el-button>
        </el-form-item>
        <div style="display: flex; align-items: center">
          <div style="flex: 1"></div>
          <div style="flex: 1; text-align: right">
            Already have account? <a href="/login">Login</a>
          </div>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    // make sure the second password matches first password
    const validateConfirmPassword = (rule, confirmPass, callback) => {
      if (confirmPass === '') {
        callback(new Error('please enter password'))
      } else if (confirmPass !== this.form.password) {
        callback(new Error('password inconsistent'))
      } else {
        callback()
      }
    }
    return {
      form: { role: 'BUSINESS' },
      rules: {
        username: [
          {required: true, message: 'please enter username', trigger: 'blur'},
          { min: 3, max: 10, message: 'length needs between 3 to 10 chars', trigger: 'blur' }
        ],
        password: [
          {required: true, message: 'please enter password', trigger: 'blur'},
          { min: 3, max: 10, message: 'length needs between 3 to 10 chars', trigger: 'blur' }
        ],
        confirmPass: [
          {validator: validateConfirmPassword, trigger: 'blur'}
        ]
      }
    }
  },
  created() {

  },
  methods: {
    register() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          // validation pass
          this.$request.post('/register', this.form).then(res => {
            if (res.code === '200') {
              this.$router.push('/login')  // to the login page
              this.$message.success('Register Success')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.container {
  height: 100vh;
  overflow: hidden;
  background-image: url("@/assets/imgs/bg1.jpg");
  background-size: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #666;
}

a {
  color: #2a60c9;
}
</style>
