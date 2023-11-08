<template>
  <div>
    <el-card style="width: 50%">
      <el-form :model="user" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <div style="margin: 15px; text-align: center">
          <el-upload
              class="avatar-uploader"
              :action="$baseUrl + '/files/upload'"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
          >
            <img v-if="user.avatar" :src="user.avatar" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <el-form-item label="username" prop="username">
          <el-input v-model="user.username" placeholder="username"></el-input>
        </el-form-item>
        <el-form-item label="name" prop="name">
          <el-input v-model="user.name" placeholder="name"></el-input>
        </el-form-item>
        <el-form-item label="password" prop="password">
          <el-input v-model="user.password" placeholder="password" show-password></el-input>
        </el-form-item>
        <el-form-item label="phone" prop="phone">
          <el-input v-model="user.phone" placeholder="phone"></el-input>
        </el-form-item>
        <el-form-item label="info" prop="info">
          <el-input v-model="user.info" placeholder="info"></el-input>
        </el-form-item>
        <el-form-item label="address" prop="address">
          <el-input v-model="user.address" placeholder="address"></el-input>
        </el-form-item>
        <el-form-item label="timeRange" prop="timeRange">
          <el-input v-model="user.timeRange" placeholder="time range"></el-input>
        </el-form-item>
        <el-form-item label="type" prop="type">
          <el-select style="width: 100%" v-model="user.type">
            <el-option v-for="item in ['milk tea', 'healthy salad', 'burger fries', 'special offer']" :key="item" :value="item" :label="item"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="license">
          <el-upload
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: user.token }"
              list-type="picture"
              :on-success="handleLicenseSuccess"
          >
            <el-button type="primary">Upload License</el-button>
          </el-upload>
        </el-form-item>
        <div style="text-align: center; margin-bottom: 20px">
          <el-button type="primary" @click="update">Save</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "BusinessPerson",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}')
    }
  },
  created() {

  },
  methods: {
    update() {
      // Save current user information to database
      this.$request.put('/business/update', this.user).then(res => {
        if (res.code === '200') {
          // update success
          this.$message.success('update success')

          // Update user information in browser cache
          localStorage.setItem('xm-user', JSON.stringify(this.user))

          //Trigger data update of parent
          this.$emit('update:user')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAvatarSuccess(response, file, fileList) {
      // Replace the user's avatar attribute with the link to the uploaded image
      this.$set(this.user, 'avatar', response.data)
    },
    handleLicenseSuccess(response, file, fileList) {
      // Replace the user's license attribute with the link to the uploaded image
      this.$set(this.user, 'license', response.data)
    },
  }
}
</script>

<style scoped>
/deep/.el-form-item__label {
  font-weight: bold;
}
/deep/.el-upload {
  border-radius: 50%;
}
/deep/.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-radius: 50%;
}
/deep/.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
  border-radius: 50%;
}
.avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 50%;
}
</style>
