<template>
  <div class="app-container">
    <div class="filter-container" style="display: flex;flex-wrap: wrap;">
      <el-input v-model="listQuery.cname" :placeholder="$t('table.clientname')" style="width: 120px;margin-right: 10px;"
        class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.uname" :placeholder="$t('table.ano')" style="width: 120px;margin-right: 10px;" class="filter-item"
        @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.mno" :placeholder="$t('table.mno')" style="width: 160px;margin-right: 10px;" class="filter-item"
        @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.symptom" :placeholder="$t('table.symptom')" style="width: 160px;margin-right: 10px;"
        class="filter-item" @keyup.enter.native="handleFilter" />
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

    <el-table id="print-records-content" :key="tableKey" v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%;margin-right: 50px;"
      @selection-change="selsChange">

      <el-table-column type="selection" width="55"></el-table-column>

      <el-table-column :label="$t('table.id')" prop="id" align="center" width="80">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.clientname')" width="120px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.cname }}</span>
        </template>
      </el-table-column>

      <el-table-column label="经办人" width="120px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.uname }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.mno')" width="100px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.mno }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.symptom')" width="100px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.symptom }}</span>
        </template>
      </el-table-column>

      <el-table-column label="购买数量" width="100px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.number }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.remark')" width="188px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.remark }}</span>
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
        <el-form-item :label="$t('table.mno')">
          <el-input v-model="temp.mno" placeholder="药品编号" />
        </el-form-item>
        <el-form-item label="经办人">
          <el-input v-model="temp.uname" placeholder="经办人" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="temp.remark" placeholder="备注" />
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
    recordsList,
    updateRecords,
    deleteRecords,
    batchDeleteRecords
  } from '@/api/client'
  import waves from '@/directive/waves' // waves directive
  import {
    parseTime
  } from '@/utils'
  import Pagination from '@/components/Pagination' // secondary package based on el-pagination

  export default {
    name: 'recordsList',
    components: {
      Pagination
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
        tableKey: 0,
        list: null,
        total: 0,
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
          uname: '',
          remark: ''
        },
        dialogFormVisible: false,
        dialogStatus: '',
        textMap: {
          update: '更新',
          create: '新增'
        },
        dialogPvVisible: false,
        downloadLoading: false
      }
    },
    created() {
      this.getList()
    },
    methods: {
      handlePrinter() {
        //判断iframe是否存在，不存在则创建iframe
        var iframe = document.getElementById("print-iframe");
        if (!iframe) {
          var el = document.getElementById("print-records-content");
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
        recordsList(this.listQuery).then(response => {
          this.list = response.data.list
          this.total = response.data.total
          // Just to simulate the time of the request
          setTimeout(() => {
            this.listLoading = false
          }, 1.5 * 1000)
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
          path: '/recordsCreate'
        })
      },
      handleUpdate(row) {
        this.temp = Object.assign({}, row)
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
            updateRecords(tempData).then(() => {
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
        this.$confirm('是否刪除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteRecords(id).then(() => {
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
          this.$confirm('是否刪除?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            batchDeleteRecords(ids).then(() => {
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
    }
  }
</script>
<style>
  .vue-image-crop-upload {
    background-color: rgba(0, 0, 0, .1);
  }
</style>
