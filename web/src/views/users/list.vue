<template>
  <div class="app-container">
    <div class="filter-container" style="display: flex;flex-wrap: wrap;">
      <el-input style="width: 120px;margin-right: 10px;" v-model="listQuery.username" :placeholder="$t('table.ano')"
        class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input style="width: 130px;margin-right: 10px;" v-model="listQuery.phone" :placeholder="$t('table.phone')"
        class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select style="width: 100px;margin-right: 10px;" v-model="listQuery.rid" :placeholder="$t('table.auth')"
        clearable class="filter-item">
        <el-option v-for="item in role" :key="item.id" :label="item.role_name" :value="item.id" />
      </el-select>
      <div class="block" style="margin-right: 10px;">
        <el-date-picker v-model="date" value-format="yyyy-MM-dd HH:mm:ss" type="datetimerange" :picker-options="pickerOptions"
          range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" align="right">
        </el-date-picker>
      </div>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        {{ $t('table.search') }}
      </el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-edit" @click="handleCreate">
        {{ $t('table.add') }}
      </el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-delete" @click="handleBatchDelete">
        {{ $t('table.batchDelete') }}
      </el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-printer" @click="handlePrinter">
        {{ $t('table.printer') }}
      </el-button>
    </div>

    <el-table id="print-user-content" :key="tableKey" v-loading="listLoading" :data="list" border fit highlight-current-row
      style="width: 100%;" @selection-change="selsChange">

      <el-table-column class="selection" type="selection" width="55"></el-table-column>

      <el-table-column :label="$t('table.id')" prop="id" align="center" width="80"> <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.auth')" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.role_name }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.ano')" width="120px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.username }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.sex')" width="100px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.sex }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.phone')" width="120px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.phone }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.avatar')" width="160px" align="center">
        <template slot-scope="{row}">
          <my-upload field="img" @crop-success="cropSuccess" @crop-upload-success="cropUploadSuccess" @crop-upload-fail="cropUploadFail"
            v-model="show" :width="120" :height="120" :url="'http://localhost:9527/api/uploadImg.do?id='+currentId"
            :params="params" :headers="headers" img-format="png" :withCredentials="true"></my-upload>
          <img v-if="row.avatar!=null" :src="'http://127.0.0.1:8080/api/upload/'+row.avatar" style="width: 120px;height: 120px;text-align: center;"
            @click="toggleShow(row.id)">
          <img v-else src="@/assets/images/head_portrait.jpg" style="width: 120px;height: 120px;text-align: center;"
            @click="toggleShow(row.id)" />
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
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row.id,$index)">
            {{ $t('table.delete') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <!-- 编辑弹框 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item :label="$t('table.ano')">
          <el-input v-model="temp.username" placeholder="用户名称" />
        </el-form-item>
        <el-form-item :label="$t('table.password')">
          <el-input v-model="temp.password" type="password" placeholder="用户密码" />
        </el-form-item>
        <el-form-item :label="$t('table.sex')">
          <label v-for="(item, index) in sexData" :key="index">
            <input type="radio" v-model="temp.sex" :value="item.value" />
            {{ item.value }}
          </label>
        </el-form-item>
        <el-form-item :label="$t('table.phone')">
          <el-input v-model="temp.phone" placeholder="用户电话" />
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
  import {
    userList,
    createUser,
    updateUser,
    deleteUser,
    batchDelete
  } from '@/api/user'
  import {
    getRoles
  } from '@/api/role'
  import waves from '@/directive/waves' // waves directive
  import {
    parseTime
  } from '@/utils'
  import Pagination from '@/components/Pagination' // secondary package based on el-pagination
  import 'babel-polyfill' // es6 shim
  import myUpload from 'vue-image-crop-upload'

  export default {
    name: 'userList',
    components: {
      Pagination,
      'my-upload': myUpload
    },
    inject: ['reload'],
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
        date: '',
        pickerOptions: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
        sels: [], //选中的值显示
        sexData: [{
            value: '男'
          },
          {
            value: '女'
          }
        ],
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
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 10,
          importance: undefined,
          title: undefined,
          type: undefined
        },
        showReviewer: false,
        temp: {
          id: undefined,
          role_id: '',
          username: '',
          password: '',
          sex: '',
          phone: '',
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
		//打印
      handlePrinter() {
        //判断iframe是否存在，不存在则创建iframe
        var iframe = document.getElementById("print-iframe");
        if (!iframe) {
          var el = document.getElementById("print-user-content");
          iframe = document.createElement('IFRAME');
          var doc = null;
          iframe.setAttribute("id", "print-iframe");
          iframe.setAttribute('style', 'position:absolute;width:0px;height:0px;left:-500px;top:-500px;');
          document.body.appendChild(iframe);
          doc = iframe.contentWindow.document;
          var selAll = document.getElementsByName("selection");
          doc.write('<div>' + el.innerHTML + '</div>');
          doc.close();
          iframe.contentWindow.focus();
        }
        iframe.contentWindow.print();
        if (navigator.userAgent.indexOf("MSIE") > 0) {
          document.body.removeChild(iframe);
        }
      },
      selsChange(sels) {
        this.sels = sels
      },
      getList() {
        this.listLoading = true
        if (this.date != null && this.date.length != 0) {
          if (this.date.length == 2) {
            this.listQuery.startTime = this.date[0]
            this.listQuery.endTime = this.date[1]
          } else {
            this.$message({
              message: '时间范围选择有错',
              type: 'error'
            })
          }
        }
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
        })
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
        this.$router.push({
          path: '/userCreate'
        })
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
            updateUser(tempData).then(() => {
              const index = this.list.findIndex(v => v.id === this.temp.id)
              this.list.splice(index, 1, this.temp)
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '更新成功！',
                type: 'success',
                duration: 2000
              })
            })
          }
        })
      },
      handleDelete(id, index) {
        this.$confirm('是否删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteUser(id).then(() => {
            this.$notify({
              title: '成功',
              message: '删除成功！',
              type: 'success',
              duration: 2000
            })
            this.list.splice(index, 1)
          })
        }).catch(() => {

        })
      },
      handleBatchDelete() {
        var ids = this.sels.map(item => item.id).join() //获取所有选中行的id组成的字符串，以逗号分隔
        if (ids.length == 0) {
          this.$message({
            message: '请选择要删除的记录',
            type: 'warning'
          })
        } else {
          this.$confirm('是否删除?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            batchDelete(ids).then(() => {
              this.$notify({
                title: '成功',
                message: '删除成功！',
                type: 'success',
                duration: 2000
              })
              this.reload()
            })
          }).catch(() => {

          })
        }
      },
      toggleShow(id) {
        this.currentId = id
        this.show = !this.show
      },
      cropSuccess(imgDataUrl, field) {
        console.log('-------- crop success --------');
        this.imgDataUrl = imgDataUrl;
      },
      cropUploadSuccess(jsonData, field) {
        console.log('-------- upload success --------');
        console.log(jsonData);
        console.log('field: ' + field);
      },
      cropUploadFail(status, field) {
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
