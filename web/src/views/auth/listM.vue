<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.username" :placeholder="$t('table.username')" style="width: 160px;margin-right: 30px;"
        class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.auth" :placeholder="$t('table.auth')" clearable style="width: 90px;margin-right: 30px;"
        class="filter-item">
        <el-option v-for="item in role" :key="item.id" :label="item.role_name" :value="item.id" />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter" style="float: right;">
        {{ $t('table.search') }}
      </el-button>
      <el-button class="filter-item" style="margin-right: 30px;float: right;" type="primary" icon="el-icon-edit" @click="handleCreate">
        {{ $t('table.add') }}
      </el-button>
    </div>

    <el-table :key="tableKey" v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%;margin-right: 50px;">

      <el-table-column :label="$t('table.id')" prop="id" align="center" width="80">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.auth')" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ currentRole(row.role_id) }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.username')" width="120px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.username }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.avatar')" width="160px" align="center">
        <template slot-scope="{row}">
         	<my-upload field="img"
                 @crop-success="cropSuccess"
                 @crop-upload-success="cropUploadSuccess"
                 @crop-upload-fail="cropUploadFail"
                 v-model="show"
         		:width="120"
         		:height="120"
         	  :url="'http://localhost:9527/api/uploadImg.do?id='+currentId"
         		:params="params"
         		:headers="headers"
         		img-format="png"
            :withCredentials="true"></my-upload>
         	<img v-if="row.avatar!=null" :src="'http://127.0.0.1:8080/api/upload/'+row.avatar" style="width: 120px;height: 120px;text-align: center;" @click="toggleShow(row.id)">
          <img v-else src="@/assets/images/head_portrait.jpg" style="width: 120px;height: 120px;text-align: center;" @click="toggleShow(row.id)" />
          <!-- <span>{{ row.avatar }}</span> -->
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.introduction')" width="188px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.introduction }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.createTime')" width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.create_time | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.actions')" align="center" width="210" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            {{ $t('table.edit') }}
          </el-button>
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row.id)">
            {{ $t('table.delete') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <!-- 编辑弹框 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item :label="$t('table.username')">
          <el-input v-model="temp.username" placeholder="用户名称" />
        </el-form-item>
        <el-form-item :label="$t('table.password')">
          <el-input v-model="temp.password" type="password" placeholder="用户密码" />
        </el-form-item>
        <el-form-item :label="$t('table.introduction')">
          <el-input v-model="temp.introduction" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="用户简介" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          {{ $t('table.cancel') }}
        </el-button>
        <el-button type="primary" @click="updateData()">
          {{ $t('table.confirm') }}
        </el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
  import { userList,createUser,updateUser,deleteUser} from '@/api/user'
  import { getRoles } from '@/api/role'
  import waves from '@/directive/waves' // waves directive
  import { parseTime } from '@/utils'
  import Pagination from '@/components/Pagination' // secondary package based on el-pagination
  import 'babel-polyfill'; // es6 shim
  import myUpload from 'vue-image-crop-upload';

  export default {
    name: 'roleList',
    components: {
      Pagination,
      'my-upload': myUpload
    },
    directives: {
      waves
    },
    filters: {
      statusFilter(status) {
        const statusMap = {
          published: 'success',
          draft: 'info',
          deleted: 'danger'
        }
        return statusMap[status]
      },
      typeFilter(type) {
        return calendarTypeKeyValue[type]
      }
    },
    data() {
      return {
        currentId: 0,
        show: false,
        params: {
          token: '123456798',
          name: 'avatar'
        },
        headers: {
          smail: '*_~'
        },

        tableKey: 0,
        list: null,
        role: null,
        total: 0,
        totalR: 0,
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 10,
          importance: undefined,
          title: undefined,
          type: undefined,
        },
        showReviewer: false,
        temp: {
          id: undefined,
          role_id: '',
          username: new Date(),
          avatar: '',
          introduction: '',
          create_time: ''
        },
        dialogFormVisible: false,
        dialogStatus: '',
        textMap: {
          update: 'Edit',
          create: 'Create'
        },
        dialogPvVisible: false,
        downloadLoading: false
      }
    },
    created() {
      this.getList()
      this.getRole()
    },
    methods: {
      getList() {
        this.listLoading = true
        userList(this.listQuery).then(response => {
          this.list = response.data.list
          this.total = response.data.total
          // Just to simulate the time of the request
          setTimeout(() => {
            this.listLoading = false
          }, 1.5 * 1000)
        })
      },
      getRole() {
        getRoles(this.listQuery).then(response => {
          this.role = response.data
          this.totalR = response.total
        })
      },
      currentRole(id) {
        for (var i = 0; i < this.totalR; i++) {
          if (this.role[i].id == id) {
            return this.role[i].role_name
          }
        }
        return '暂无'
      },
      handleFilter() {
        this.listQuery.page = 1
        this.getList()
      },
      handleModifyStatus(row, status) {
        this.$message({
          message: '操作成功',
          type: 'success'
        })
        row.status = status
      },
      handleCreate() {
        this.$router.push({ path: '/userCreate' })
      },
      handleUpdate(row) {
        this.temp = Object.assign({}, row) // copy obj
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      },
      updateData() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            const tempData = Object.assign({}, this.temp)
            tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
            updateUser(tempData).then(() => {
              const index = this.list.findIndex(v => v.id === this.temp.id)
              this.list.splice(index, 1, this.temp)
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '更新成功，请重新刷新！',
                type: 'success',
                duration: 2000
              })
            })
          }
        })
      },
      handleDelete(id) {
        console.log(id)
        deleteUser(id).then(() => {
          this.$notify({
            title: '成功',
            message: '删除成功，请重新刷新！',
            type: 'success',
            duration: 2000
          })
        })
      },
      toggleShow(id) {
        this.currentId = id
      				this.show = !this.show
      			},
      			cropSuccess(imgDataUrl, field){
      				console.log('-------- crop success --------');
      				this.imgDataUrl = imgDataUrl;
      			},
      			cropUploadSuccess(jsonData, field){
      				console.log('-------- upload success --------');
      				console.log(jsonData);
      				console.log('field: ' + field);
      			},
      			cropUploadFail(status, field){
      				console.log('-------- upload fail --------');
      				console.log(status);
      				console.log('field: ' + field);
      			}
    }
  }
</script>
<style>
  .vue-image-crop-upload {
    background-color: rgba(0, 0, 0, .1);
  }
</style>
