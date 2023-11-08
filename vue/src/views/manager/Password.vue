<template>
  <div>
    <el-card style="width: 50%">
      <el-form ref="formRef" :model="user" :rules="rules" label-width="100px" style="padding-right: 50px">
        <el-form-item label="password" prop="password">
          <el-input show-password v-model="user.password" placeholder="original password"></el-input>
        </el-form-item>
        <el-form-item label="new pass" prop="newPassword">
          <el-input show-password v-model="user.newPassword" placeholder="new password"></el-input>
        </el-form-item>
        <el-form-item label="confirm" prop="confirmPassword">
          <el-input show-password v-model="user.confirmPassword" placeholder="confirm new password"></el-input>
        </el-form-item>
        <div style="text-align: center; margin-bottom: 20px">
          <el-button type="primary" @click="update">Confirm Update</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Password",
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('please enter password'))
      } else if (value !== this.user.newPassword) {
        callback(new Error('password inconsistent'))
      } else {
        callback()
      }
    }

    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        password: [
          { required: true, message: 'please enter password', trigger: 'blur' },
        ],
        newPassword: [
          { required: true, message: 'please enter new password', trigger: 'blur' },
        ],
        confirmPassword: [
          { validator: validatePassword, required: true, trigger: 'blur' },
        ],
      }
    }
  },
  created() {

  },
  methods: {
    update() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request.put('/updatePassword', this.user).then(res => {
            if (res.code === '200') {
              // update success
              localStorage.removeItem('xm-user')   // clear storage
              this.$message.success('update password success')
              this.$router.push('/login')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
  }
}
</script>

<style scoped>
/deep/.el-form-item__label {
  font-weight: bold;
}
</style>
