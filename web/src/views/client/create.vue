<template>
  <div class="app-container">
    <el-form enctype="multipart/form-data" :model="client" label-width="80px" style="width: 600px;" label-position="left">
      <el-form-item label="*顾客名">
        <el-input v-model="client.cname" placeholder="顾客名" />
      </el-form-item>
      <el-form-item :label="$t('table.sex')">
        <label v-for="(item, index) in sexData" :key="index">
          <input type="radio" v-model="client.csex" :value="item.value" />
          {{ item.value }}
        </label>
      </el-form-item>
      <el-form-item :label="$t('table.age')">
        <el-input v-model="client.cage" placeholder="年龄" />
      </el-form-item>
      <el-form-item :label="$t('table.address')">
        <el-input v-model="client.caddress" placeholder="地址" />
      </el-form-item>
      <el-form-item label="*电话号码">
        <el-input v-model="client.cphone" placeholder="电话号码" />
      </el-form-item>
      <div style="text-align:right;">
        <el-button type="primary" @click="confirmClient">
          {{ $t('permission.confirm') }}
        </el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
  import path from 'path'
  import {
    createClient
  } from '@/api/client'
  import i18n from '@/lang'

  const defaultClient = {
    cname: '',
    csex: '男',
    cage: '',
    caddress: '',
    cphone: ''
  }

  export default {
    name: 'clientCreate',
    data() {
      return {
        client: Object.assign({}, defaultClient),
        sexData: [{
            value: '男'
          },
          {
            value: '女'
          }
        ],
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
      confirmClient() {
        if (this.client.cname == '' || this.client.cphone == '') {
          this.$message({
            message: '顾客名和电话号码不能为空，请重新确认！',
            type: 'error'
          })
        } else if (isNaN(this.client.cphone) || isNaN(this.client.cage)) {
          this.$message({
            message: '电话号码为数值，请重新确认！',
            type: 'error'
          })
        } else {
          createClient(this.client).then(response => {
            this.$router.push({
              path: 'clientList'
            })
          })
        }
      }
    }
  }
</script>
