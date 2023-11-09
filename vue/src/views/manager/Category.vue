<template>
  <div>
    <div class="search">
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
        <el-table-column prop="name" label="name"></el-table-column>
        <el-table-column prop="businessId" label="businessId"></el-table-column>
        <el-table-column prop="businessName" label="businessName"></el-table-column>
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

    <el-dialog title="Category Form" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="name" prop="name">
          <el-input v-model="form.name" placeholder="name"></el-input>
        </el-form-item>
        <el-form-item label="businessId" prop="businessId" v-if="user.role === 'ADMIN'">
          <el-select style="width: 100%" v-model="form.businessId">
            <el-option v-for="item in businessList" :key="item.id" :value="item.id" :label="item.name"></el-option>
          </el-select>
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
  name: "Category",
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
        name: [
          {required: true, message: 'please enter name', trigger: 'blur'},
        ],
      },
      ids: [],
      businessList: []
    }
  },
  created() {
    this.load(1)
    this.$request.get('/business/selectAll', {
      params: {
        status: 'pass'
      }
    }).then(res => {
      this.businessList = res.data || []
    })
  },
  methods: {
    //Add Category
    handleAdd() {
      this.form = {}  // clear form data
      this.fromVisible = true   // open dialog
    },
    //Edit Category
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))  // deep copy, set form initial value
      this.fromVisible = true   // open dialog
    },
    //The logic triggered by the save button will trigger new or updated
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/category/update' : '/category/add',
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
        this.$request.delete('/category/delete/' + id).then(res => {
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
        this.$request.delete('/category/delete/batch', {data: this.ids}).then(res => {
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
      this.$request.get('/category/selectPage', {
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
  }
}
</script>

<style scoped>

</style>
