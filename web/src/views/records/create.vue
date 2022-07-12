<template>
  <div class="app-container">
    <el-form enctype="multipart/form-data" :model="records" label-width="80px" style="width: 600px;" label-position="left">
      <el-form-item label="*顾客名">
        <el-input v-model="records.cname" placeholder="顾客名" />
      </el-form-item>
      <el-form-item label="*药品编号">
        <el-input v-model="records.mno" placeholder="药品编号" />
      </el-form-item>
      <el-form-item label="购买数量">
        <el-input v-model="records.number" placeholder="购买数量" />
      </el-form-item>
      <el-form-item :label="$t('table.symptom')">
        <el-input v-model="records.symptom" placeholder="症状" />
      </el-form-item>
      <el-form-item :label="$t('table.remark')">
        <el-input v-model="records.remark" placeholder="备注" />
      </el-form-item>
      <div style="text-align:right;">
        <el-button type="primary" @click="confirmRecords">
          {{ $t('permission.confirm') }}
        </el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
  import path from 'path'
  import {
    createRecords
  } from '@/api/client'
  import i18n from '@/lang'

  const defaultRecords = {
    cname: '',
    mno: '',
    symptom: '',
    remark: '',
    number: 1
  }

  export default {
    name: 'recordsCreate',
    data() {
      return {
        records: Object.assign({}, defaultRecords),
        dialogVisible: false,
        dialogType: 'new',
        checkStrictly: false,
        defaultProps: {
          children: 'children',
          label: 'title'
        }
      }
    },
    methods: {
      confirmRecords() {
        if (this.records.cname == '' || this.records.mno == '' || this.records.number == '') {
          this.$message({
            message: '用户名、药品编号和购买数量不能为空，请重新确认！',
            type: 'error'
          })
        } else if(isNaN(this.records.mno) || isNaN(this.records.number)) {
          this.$message({
            message: '药品编号和购买数量为数值，请重新确认！',
            type: 'error'
          })
        } else {
          createRecords(this.records).then(response => {
            this.$router.push({
              path: 'recordsList'
            })
          })
        }
      }
    }
  }
</script>
