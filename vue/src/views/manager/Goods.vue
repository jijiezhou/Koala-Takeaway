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
        <el-table-column prop="price" label="price"></el-table-column>
        <el-table-column prop="discount" label="discount"></el-table-column>
        <el-table-column prop="img" label="image">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px;" v-if="scope.row.img"
                        :src="scope.row.img" :preview-src-list="[scope.row.img]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="descr" label="description"></el-table-column>
        <el-table-column prop="origin" label="origin"></el-table-column>
        <el-table-column prop="taste" label="taste"></el-table-column>
        <el-table-column prop="specs" label="specific"></el-table-column>
        <el-table-column prop="date" label="date"></el-table-column>
        <el-table-column prop="status" label="status">
          <template v-slot="scope">
            <el-tag type="success" v-if="scope.row.status === 'available'">available</el-tag>
            <el-tag type="warning" v-if="scope.row.status === 'unavailable'">unavailable</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="businessName" label="busiNname"></el-table-column>
        <el-table-column prop="categoryName" label="cateName"></el-table-column>
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
        <el-form-item label="name" prop="name">
          <el-input v-model="form.name" placeholder="name"></el-input>
        </el-form-item>
        <el-form-item label="price" prop="price">
          <el-input v-model="form.price" placeholder="price"></el-input>
        </el-form-item>
        <el-form-item label="discount" prop="discount">
          <el-input v-model="form.discount" placeholder="discount: 1 stands for no discount，use float"></el-input>
        </el-form-item>
        <el-form-item label="image" prop="img">
          <el-upload
              :action="$baseUrl + '/files/upload'"
              :headers="{ token: user.token }"
              :on-success="handleFileSuccess"
          >
            <el-button type="primary">Upload</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="description" prop="descr">
          <el-input v-model="form.descr" placeholder="description"></el-input>
        </el-form-item>
        <el-form-item label="origin" prop="origin">
          <el-input v-model="form.origin" placeholder="original material"></el-input>
        </el-form-item>
        <el-form-item label="taste" prop="taste">
          <el-input v-model="form.taste" placeholder="taste"></el-input>
        </el-form-item>
        <el-form-item label="specification" prop="specs">
          <el-input v-model="form.specs" placeholder="specification"></el-input>
        </el-form-item>
        <el-form-item label="date" prop="date">
          <el-date-picker format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                          v-model="form.date" style="width: 100%"></el-date-picker>
        </el-form-item>
        <el-form-item label="status" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="available"></el-radio>
            <el-radio label="unavailable"></el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="category" prop="categoryId">
          <el-select style="width: 100%" v-model="form.categoryId">
            <el-option v-for="item in categoryList" :key="item.id" :value="item.id" :label="item.name"></el-option>
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
  name: "Goods",
  data() {
    return {
      tableData: [],  // all data
      pageNum: 1,   // current page
      pageSize: 10,  // number of items each page
      categoryList: [],
      total: 0,
      name: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [
          {required: true, message: 'please enter name', trigger: 'blur'},
        ],
        price: [
          {required: true, message: 'please enter price', trigger: 'blur'},
        ],
      },
      ids: []
    }
  },
  created() {
    this.load(1)
    let businessId = this.user.role === 'ADMIN' ? null : this.user.id
    this.$request.get('category/selectAll', {
      params: {
        businessId: businessId
      }
    }).then(res => {
      this.categoryList = res.data
    })
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
            url: this.form.id ? '/goods/update' : '/goods/add',
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
        this.$request.delete('/goods/delete/' + id).then(res => {
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
        this.$request.delete('/goods/delete/batch', {data: this.ids}).then(res => {
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
      this.$request.get('/goods/selectPage', {
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
    handleFileSuccess(response, file, fileList) {
      // Change the avatar attribute to the link of the uploaded image
      this.form.img = response.data
    },
  }
}
</script>

<style scoped>

</style>
