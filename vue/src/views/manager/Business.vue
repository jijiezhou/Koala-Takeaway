<template>
  <div>
    <div class="search">
      <el-input placeholder="please enter username" style="width: 200px" v-model="username"></el-input>
      <el-input placeholder="please enter name" style="width: 200px; margin-left: 10px" v-model="name"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">Query</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">Reset</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">Add</el-button>
      <el-button type="danger" plain @click="delBatch">DeleteBatch</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="id" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="username" label="username"></el-table-column>
        <el-table-column prop="name" label="name"></el-table-column>
        <el-table-column label="logo">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px; border-radius: 50%" v-if="scope.row.avatar"
                        :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="role" label="role"></el-table-column>
        <el-table-column prop="phone" label="phone"></el-table-column>
        <el-table-column prop="info" label="info"></el-table-column>
        <el-table-column prop="address" label="address"></el-table-column>
        <el-table-column prop="license" label="license">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px" v-if="scope.row.license"
                        :src="scope.row.license" :preview-src-list="[scope.row.license]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="status">
          <template v-slot="scope">
            <el-tag type="info" v-if="scope.row.status === 'pending'">pending</el-tag>
            <el-tag type="success" v-if="scope.row.status === 'pass'">pass</el-tag>
            <el-tag type="dangerous" v-if="scope.row.status === 'reject'">reject</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="timeRange" label="time range"></el-table-column>
        <el-table-column prop="type" label="type"></el-table-column>

        <el-table-column label="operation" align="center" width="180">
          <template v-slot="scope">
            <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">Edit</el-button>
            <el-button size="mini" type="danger" plain @click="del(scope.row.id)">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total">
        </el-pagination>
      </div>
    </div>

    <el-dialog title="Business Form" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="status" prop="status">
          <el-select style="width: 100%" v-model="form.status">
            <el-option v-for="item in ['pending', 'pass', 'reject']" :key="item" :value="item" :label="item"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="username" prop="username">
          <el-input v-model="form.username" placeholder="username"></el-input>
        </el-form-item>
        <el-form-item label="name" prop="name">
          <el-input v-model="form.name" placeholder="name"></el-input>
        </el-form-item>
        <el-form-item label="password" prop="password">
          <el-input v-model="form.password" placeholder="password" show-password></el-input>
        </el-form-item>
        <el-form-item label="phone" prop="phone">
          <el-input v-model="form.phone" placeholder="phone"></el-input>
        </el-form-item>
        <el-form-item label="info" prop="info">
          <el-input v-model="form.info" placeholder="info"></el-input>
        </el-form-item>
        <el-form-item label="address" prop="address">
          <el-input v-model="form.address" placeholder="address"></el-input>
        </el-form-item>
        <el-form-item label="timeRange" prop="timeRange">
          <el-input v-model="form.timeRange" placeholder="time range"></el-input>
        </el-form-item>
        <el-form-item label="type" prop="type">
          <el-select style="width: 100%" v-model="form.type">
            <el-option v-for="item in ['milk tea', 'healthy salad', 'burger fries', 'special offer']" :key="item" :value="item" :label="item"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Logo">
          <el-upload
              class="avatar-uploader"
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: user.token }"
              :file-list="form.avatar ? [form.avatar] : []"
              list-type="picture"
              :on-success="handleAvatarSuccess"
          >
            <el-button type="primary">Upload Logo</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="license">
          <el-upload
              class="avatar-uploader"
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: user.token }"
              :file-list="form.license ? [form.license] : []"
              list-type="picture"
              :on-success="handleLicenseSuccess"
          >
            <el-button type="primary">Upload License</el-button>
          </el-upload>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">Cancel</el-button>
        <el-button type="primary" @click="save">Confirm</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Business",
  data() {
    return {
      tableData: [],  // all data
      pageNum: 1,   // current page
      pageSize: 10,  // number of items each page
      total: 0,
      username: null,
      name: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        username: [
          {required: true, message: 'please enter username', trigger: 'blur'},
        ],
        name: [
          {required: true, message: 'please enter name', trigger: 'blur'},
        ],
        password: [
          {required: true, message: 'please enter password', trigger: 'blur'},
        ],
        type: [
          {required: true, message: 'please enter type', trigger: 'blur'},
        ]
      },
      ids: []
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    //Add Business
    handleAdd() {
      this.form = {}  // clear form data
      this.fromVisible = true   // open dialog
    },
    //Edit Business
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))  // deep copy, set form initial value
      this.fromVisible = true   // open dialog
    },
    //The logic triggered by the save button will trigger new or updated
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/business/update' : '/business/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // save success
              this.$message.success('save success')
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // log error msg
            }
          })
        }
      })
    },
    //deleteById
    del(id) {
      this.$confirm('Are you sure to delete？', 'Confirm delete', {type: "warning"}).then(response => {
        this.$request.delete('/business/delete/' + id).then(res => {
          //delete success
          if (res.code === '200') {
            this.$message.success('delete success')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // log error msg
          }
        })
      }).catch(() => {
      })
    },
    //All row data currently selected
    handleSelectionChange(rows) {
      this.ids = rows.map(v => v.id)
    },
    //delete by batch
    delBatch() {
      if (!this.ids.length) {
        this.$message.warning('please select data')
        return
      }
      this.$confirm('Are you sure to delete by batch?', 'Confirm delete', {type: "warning"}).then(response => {
        this.$request.delete('/business/delete/batch', {data: this.ids}).then(res => {
          //delete success
          if (res.code === '200') {
            this.$message.success('delete success')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // log error info
          }
        })
      }).catch(() => {
      })
    },
    //pagination
    load(pageNum) {
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/business/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          name: this.name
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.username = null
      this.name = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    handleAvatarSuccess(response, file, fileList) {
      // Change the avatar attribute to the link of the uploaded image
      this.form.avatar = response.data
    },
    handleLicenseSuccess(response, file, fileList){
      // Change the avatar attribute to the link of the uploaded image
      this.form.license = response.data
    }
  }
}
</script>

<style scoped>

</style>
