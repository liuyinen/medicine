<template>
  <div class="app-container">
    <el-form enctype="multipart/form-data" :model="user" label-width="80px" style="width: 600px;" label-position="left">
      <el-form-item label="角色">
        <el-select v-model="user.roleId" placeholder="权限" clearable style="width: 90px;margin-right: 30px;" class="filter-item">
          <el-option v-for="item in role" :key="item.id" :label="item.role_name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="*经办人名">
        <el-input v-model="user.username" placeholder="经办人名" />
      </el-form-item>
      <el-form-item label="*密码">
        <el-input v-model="user.password" type="password" placeholder="密码" />
      </el-form-item>
      <el-form-item :label="$t('table.sex')">
        <label v-for="(item, index) in sexData" :key="index">
          <input type="radio" v-model="user.sex" :value="item.value" />
           {{ item.value }}
        </label>
      </el-form-item>
      <el-form-item label="*电话号码">
        <el-input v-model="user.phone" placeholder="电话号码" />
      </el-form-item>
      <el-form-item :label="$t('table.introduction')">
        <el-input v-model="user.introduction" :autosize="{ minRows: 6, maxRows: 10}" type="textarea" placeholder="简介" />
      </el-form-item>
      <div style="text-align:right;">
        <el-button type="primary" @click="confirmUser">
          {{ $t('permission.confirm') }}
        </el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
  import path from 'path'
  import { createUser } from '@/api/user'
  import { getRoles } from '@/api/role'
  import i18n from '@/lang'

  const defaultUser = {
    roleId: 1,
    username: '',
    password: '',
    sex: '男',
    phone: '',
    introduction: ''
  }

  export default {
    name: 'userCreate',
    data() {
      return {
        user: Object.assign({}, defaultUser),
        sexData: [{
            value: '男'
          },
          {
            value: '女'
          }
        ],
        role: null,
        dialogVisible: false,
        dialogType: 'new',
        checkStrictly: false,
        defaultProps: {
          children: 'children',
          label: 'title'
        }
      }
    },
    created() {
      this.getRole()
    },
    methods: {
      getRole() {
        getRoles(this.listQuery).then(response => {
          this.role = response.data
        })
      },
      confirmUser() {
        if (this.user.username == '' || this.user.password == '' || this.user.phone == '') {
          this.$message({
            message: '经办人名、密码和电话号码不能为空，请重新确认！',
            type: 'error'
          })
        } else if(isNaN(this.user.roleId) || isNaN(this.user.phone)) {
          this.$message({
            message: '电话号码和角色id为数值，请重新确认！',
            type: 'error'
          })
        } else {
          createUser(this.user).then(response => {
            this.$router.push({
              path: 'userList'
            })
          })
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
  .app-container {
    .roles-table {
      margin-top: 30px;
    }

    .permission-tree {
      margin-bottom: 30px;
    }
  }
</style>
