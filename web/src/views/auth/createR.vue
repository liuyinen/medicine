<template>
  <div class="app-container">
    <el-form :model="role" label-width="80px" label-position="left" style="width: 600px;">
      <el-form-item label="*角色名称">
        <el-input v-model="role.name" placeholder="角色名称" />
      </el-form-item>
      <el-form-item :label="$t('table.description')">
        <el-input v-model="role.description" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="角色描述" />
      </el-form-item>
      <el-form-item label="*菜单">
        <el-tree ref="tree" :check-strictly="checkStrictly" :data="routesData" :props="defaultProps" show-checkbox
          node-key="path" class="permission-tree" />
      </el-form-item>
      <div style="text-align:right;">
        <el-button type="primary" @click="confirmRole">
          {{ $t('permission.confirm') }}
        </el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
  import path from 'path'
  import {
    deepClone
  } from '@/utils'
  import {
    getRoutes,
    getRoles,
    addRole,
    deleteRole,
    updateRole
  } from '@/api/role'
  import {
    getMenus,
    createRole
  } from '@/api/role'
  import i18n from '@/lang'

  const defaultRole = {
    name: '',
    mids: '',
    description: ''
  }

  export default {
    name: 'roleCreate',
    data() {
      return {
        role: Object.assign({}, defaultRole),
        routes: [],
        dialogVisible: false,
        dialogType: 'new',
        checkStrictly: false,
        defaultProps: {
          children: 'children',
          label: 'title'
        },
        ids: ''
      }
    },
    computed: {
      routesData() {
        return this.routes
      }
    },
    created() {
      this.getRoutes()
    },
    methods: {
      async getRoutes() {
        getRoutes().then(response => {
          this.serviceRoutes = response.list
          this.routes = this.generateRoutes(response.list)

        })
      },
      generateRoutes(routes, basePath = '/') {
        const res = []
        for (let route of routes) {
          // skip some route
          if (route.hidden) {
            continue
          }
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
      confirmRole() {
        const checkedKeys = this.$refs.tree.getCheckedKeys()
        const menus = this.generateTree(deepClone(this.serviceRoutes), '/', checkedKeys)
        if (menus != null) {
          this.getRoleIds(menus)
        }

        this.role.mids = this.ids
        if (this.role.name == '' || this.role.mids == '') {
          this.$message({
            message: '角色名不能为空，请重新确认！',
            type: 'error'
          })
        } else {
          createRole(this.role).then(response => {
            this.$router.push({
              path: 'roleList'
            })
          })
        }
      }
    }
  }
</script>
