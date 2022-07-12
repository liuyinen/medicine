<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button class="filter-item" style="margin-right: 30px;float: right;" type="primary" icon="el-icon-edit" @click="handleCreate">
        {{ $t('table.add') }}
      </el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-printer" @click="handlePrinter">
        {{ $t('table.printer') }}
      </el-button>
    </div>

    <el-table id="print-auth-content" :key="tableKey" v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%;margin-right: 50px;">

      <el-table-column :label="$t('table.id')" prop="id" align="center" width="80">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.auth')" width="180px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.role_name }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.description')" width="280px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.description }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.createTime')" width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.create_time | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.actions')" align="center" width="210" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleEdit(row)">
            {{ $t('table.edit') }}
          </el-button>
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row.id,$index)">
            {{ $t('table.delete') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 编辑弹框 -->
    <el-dialog :visible.sync="dialogVisible" title="编辑">
      <el-form :model="role" label-width="80px" label-position="left">
        <el-form-item :label="$t('table.auth')">
          <el-input v-model="role.role_name" placeholder="Role Name" />
        </el-form-item>
        <el-form-item :label="$t('table.description')">
          <el-input v-model="role.description" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="Role Description" />
        </el-form-item>
        <el-form-item label="Menus">
          <el-tree ref="tree" :check-strictly="checkStrictly" :data="routesData" :props="defaultProps" show-checkbox
            node-key="path" class="permission-tree" />
        </el-form-item>
      </el-form>
      <div style="text-align:right;">
        <el-button type="danger" @click="dialogVisible=false">
          {{ $t('permission.cancel') }}
        </el-button>
        <el-button type="primary" @click="confirmRole">
          {{ $t('permission.confirm') }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import path from 'path'
  import {
    getRoutes,
    getRoles,
    createRole,
    updateRole,
    deleteRole
  } from '@/api/role'
  import waves from '@/directive/waves' // waves directive
  import {
    parseTime
  } from '@/utils'
  import Pagination from '@/components/Pagination' // secondary package based on el-pagination
  import {
    deepClone
  } from '@/utils'
  const defaultRole = {
    id: '',
    role_name: '',
    description: '',
    mids: []
  }
  export default {
    name: 'menuList',
    components: {
      Pagination
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
        role: Object.assign({}, defaultRole),
        tableKey: 0,
        list: null,
        rolesList: null,
        total: 0,
        routes: [],
        ids: '',
        dialogVisible: false,
        checkStrictly: false,
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 10,
          importance: undefined,
          title: undefined,
          type: undefined,
        },
        showReviewer: false,
        dialogStatus: '',
        textMap: {
          update: 'Edit',
          create: 'Create'
        },
        dialogPvVisible: false,
        downloadLoading: false,
        defaultProps: {
          children: 'children',
          label: 'title'
        }
      }
    },
    computed: {
      routesData() {
        return this.routes
      }
    },
    created() {
      this.getList(),
        this.getRoutes()
    },
    methods: {
      handlePrinter() {
        //判断iframe是否存在，不存在则创建iframe
        var iframe = document.getElementById("print-iframe");
        if (!iframe) {
          var el = document.getElementById("print-auth-content");
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
      async getRoutes() {
        getRoutes().then(response => {
          this.serviceRoutes = response.list
          this.routes = this.generateRoutes(response.list)
        })
      },
      generateRoutes(routes, basePath = '/') {
        const res = []

        for (let route of routes) {

          const onlyOneShowingChild = this.onlyOneShowingChild(route.children, route)

          if (route.children && onlyOneShowingChild && !route.alwaysShow) {
            route = onlyOneShowingChild
          }

          const data = {
            path: path.resolve(basePath, route.path),
            title: route.meta && route.meta.title

          }

          // recursive child routes
          if (route.children) {
            data.children = this.generateRoutes(route.children, data.path)
          }
          res.push(data)
        }
        return res
      },
      onlyOneShowingChild(children = [], parent) {
        let onlyOneChild = null
        const showingChildren = children.filter(item => !item.hidden)

        // When there is only one child route, the child route is displayed by default
        if (showingChildren.length === 1) {
          onlyOneChild = showingChildren[0]
          onlyOneChild.path = path.resolve(parent.path, onlyOneChild.path)
          return onlyOneChild
        }

        // Show parent if there are no child route to display
        if (showingChildren.length === 0) {
          onlyOneChild = { ...parent,
            path: '',
            noShowingChildren: true
          }
          return onlyOneChild
        }

        return false
      },
      generateArr(routes) {
        let data = []
        routes.forEach(route => {
          data.push(route)
          if (route.children) {
            const temp = this.generateArr(route.children)
            if (temp.length > 0) {
              data = [...data, ...temp]
            }
          }
        })
        return data
      },
      getList() {
        this.listLoading = true
        getRoles(this.listQuery).then(response => {
          this.list = response.data
          this.rolesList = response.data
          this.total = response.total
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
          path: '/roleCreate'
        })
      },
      handleEdit(row) {
        this.dialogVisible = true
        this.checkStrictly = true
        this.role = deepClone(row)
        this.$nextTick(() => {
          const routes = this.generateRoutes(this.role.routes)
          this.$refs.tree.setCheckedNodes(this.generateArr(routes))
          // set checked state of a node not affects its father and child nodes
          this.checkStrictly = false
        })
      },
      generateTree(routes, basePath = '/', checkedKeys) {
        const res = []

        for (const route of routes) {
          const routePath = path.resolve(basePath, route.path)

          // recursive child routes
          if (route.children) {
            route.children = this.generateTree(route.children, routePath, checkedKeys)
          }

          if (checkedKeys.includes(routePath) || (route.children && route.children.length >= 1)) {
            res.push(route)
          }
        }
        return res
      },
      getRoleIds(ids) {
        for (var i = 0; i < ids.length; i++) {
          if (this.ids == '') {
            this.ids = ids[i].id
          } else {
            this.ids = this.ids + ',' + ids[i].id
          }

          if (ids[i].children && ids[i].children != null) {
            this.getRoleIds(ids[i].children)
          }
        }
      },
      async confirmRole() {
        const checkedKeys = this.$refs.tree.getCheckedKeys()
        const menus = this.generateTree(deepClone(this.serviceRoutes), '/', checkedKeys)
        console.log(menus)
        if (menus != null) {
          this.getRoleIds(menus)
        }
        this.role.mids = this.ids
        const tempData = this.role
        tempData.routes = null
        updateRole(tempData).then(response => {
          const index = this.list.findIndex(v => v.id === this.role.id)
          this.list.splice(index, 1, this.role)
          this.dialogVisible = false
          this.$notify({
            title: '成功',
            message: '更新成功！',
            type: 'success',
            duration: 2000
          })
        })
      },
      handleDelete(id, index) {
        this.$confirm('是否删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteRole(id).then(() => {
            this.$notify({
              title: '成功',
              message: '删除成功，请重新刷新！',
              type: 'success',
              duration: 2000
            })
            this.list.splice(index, 1)
          })
        }).catch(() => {

        })
      },
    }
  }
</script>
<style>
  .vue-image-crop-upload {
    background-color: rgba(0, 0, 0, .1);
  }
</style>
