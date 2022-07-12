<template>
  <div class="app-container help-container">
    <el-container style="height: 100%;">
      <el-aside width="200px" style="background: rgba(0,0,0,0);padding: 0;">
        <el-menu unique-opened router :default-active="$route.path" class="left-menu" :collapse="leftMenu.isCollapse">
          <component class="menu-item" v-for="(value) in leftMenu.navList" :key="value.title" :index="value.flag" :is="(value.children&&value.children.length>0)?'el-submenu':'el-menu-item'">
            <template slot="title">
              <div @click="getIndex(value.index)">
                <i :class="[value.icon]"></i>
                <span>{{value.title}}</span>
              </div>
            </template>
            <template v-if="value.children&&value.children.length>0">
              <el-menu-item @click="getIndex(v.index)" class="menu-item" v-for="(v,i) in value.children" :key="v.title" index="">
                <i :class="[v.icon]"></i>
                <span slot="title">{{v.title}}</span>
              </el-menu-item>
            </template>
          </component>
        </el-menu>
      </el-aside>
      <el-container>
        <el-main>
          <div v-if="cindex == 'index'">
            <h1>首页</h1>
            <img style="width: 100%;" src="@/assets/help_images/dashboard.png" />
          </div>
          <div v-if="cindex == 'blist'">
            <h1>购买管理</h1>
            <h2>药品购买记录展示</h2>
            <img style="width: 100%;" src="@/assets/help_images/blist_1.png" />
            <h2>药品购买记录修改</h2>
            <img style="width: 100%;" src="@/assets/help_images/blist_2.png" />
            <h2>药品购买记录删除</h2>
            <p>药品购买记录删除分为单条删除和批量删除。</p>
            <p>批量删除药品购买记录，先选择要删除的记录，再点击批量删除按钮，确定，即可删除成功！</p>
            <img style="width: 100%;" src="@/assets/help_images/blist_3.png" />
          </div>
          <div v-if="cindex == 'badd'">
            <h1>购买管理</h1>
            <h2>顾客购买添加</h2>
            <p>购买记录添加成功后，会跳转到购买列表页。</p>
            <img style="width: 100%;" src="@/assets/help_images/badd.png" />
          </div>
          <div v-if="cindex == 'alist'">
            <h1>经办人管理</h1>
            <h2>经办人列表</h2>
            <img style="width: 100%;" src="@/assets/help_images/alist_1.png" />
            <h2>经办人信息修改</h2>
            <img style="width: 100%;" src="@/assets/help_images/alist_2.png" />
            <h2>经办人头像修改</h2>
            <img style="width: 100%;" src="@/assets/help_images/alist_4.png" />
            <h2>经办人信息删除</h2>
            <p>经办人删除分为单条删除和批量删除，且有为顾客服务的经办人不允许删除。</p>
            <p>批量删除经办人，先选择要删除的记录，再点击批量删除按钮，确定，即可删除成功！</p>
            <img style="width: 100%;" src="@/assets/help_images/alist_3.png" />
          </div>
          <div v-if="cindex == 'aadd'">
            <h1>经办人管理</h1>
            <h2>经办人创建</h2>
            <p>经办人创建成功后，会跳转到经办人列表页。</p>
            <img style="width: 100%;" src="@/assets/help_images/aadd.png" />
          </div>
          <div v-if="cindex == 'rlist'">
            <h1>权限管理</h1>
            <h2>角色列表</h2>
            <img style="width: 100%;" src="@/assets/help_images/rlist_1.png" />
            <h2>角色修改</h2>
            <img style="width: 100%;" src="@/assets/help_images/rlist_2.png" />
            <h2>角色删除</h2>
            <p>有经办人使用的角色不允许删除。</p>
            <img style="width: 100%;" src="@/assets/help_images/rlist_3.png" />
          </div>
          <div v-if="cindex == 'radd'">
            <h1>权限管理</h1>
            <h2>角色添加</h2>
            <p>角色添加成功后，会跳转到角色列表页。</p>
            <img style="width: 100%;" src="@/assets/help_images/radd.png" />
          </div>
          <div v-if="cindex == 'clist'">
            <h1>顾客管理</h1>
            <h2>顾客列表</h2>
            <img style="width: 100%;" src="@/assets/help_images/clist_1.png" />
            <h2>顾客修改</h2>
            <img style="width: 100%;" src="@/assets/help_images/clist_2.png" />
            <h2>顾客删除</h2>
            <p>顾客删除分为单条删除和批量删除，且有购买记录的顾客不允许删除。</p>
            <p>批量删除顾客，先选择要删除的记录，再点击批量删除按钮，确定，即可删除成功！</p>
            <img style="width: 100%;" src="@/assets/help_images/clist_3.png" />
          </div>
          <div v-if="cindex == 'cadd'">
            <h1>顾客管理</h1>
            <h2>顾客添加</h2>
            <p>顾客添加成功后，会跳转到顾客列表页。</p>
            <img style="width: 100%;" src="@/assets/help_images/radd.png" />
          </div>
          <div v-if="cindex == 'mlist'">
            <h1>药品管理</h1>
            <h2>药品列表</h2>
            <img style="width: 100%;" src="@/assets/help_images/mlist_1.png" />
            <h2>药品修改</h2>
            <img style="width: 100%;" src="@/assets/help_images/mlist_2.png" />
            <h2>药品删除</h2>
            <p>药品删除分为单条删除和批量删除，且有购买记录的药品不允许删除。</p>
            <p>批量删除药品，先选择要删除的记录，再点击批量删除按钮，确定，即可删除成功！</p>
            <img style="width: 100%;" src="@/assets/help_images/mlist_3.png" />
          </div>
          <div v-if="cindex == 'madd'">
            <h1>药品管理</h1>
            <h2>药品添加</h2>
            <p>药品添加成功后，会跳转到药品列表页。</p>
            <img style="width: 100%;" src="@/assets/help_images/madd.png" />
          </div>
          <div v-if="cindex == 'other'">
            <p>点击页面右侧设置按钮，即可弹出全部布局设置——系统主题颜色设置、是否开启Tags-View和固定Header。</p>
            <img style="width: 100%;" src="@/assets/help_images/other_1.png" />
            <img style="width: 100%;" src="@/assets/help_images/other_2.png" />
            <p>点击内容区头部左上角的小图标，即可缩小菜单列表。</p>
            <img style="width: 100%;" src="@/assets/help_images/other_3.png" />
            <p>右击头部标签，即可弹出关闭标签的各个选项。</p>
            <img style="width: 100%;" src="@/assets/help_images/other_4.png" />
            <p>点击内容区头部右上角的放大小图标，即可进入全屏模式。</p>
            <p>点击内容区头部右上角的头像，即可弹出退出登录选项。</p>
            <img style="width: 100%;" src="@/assets/help_images/other_5.png" />
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
  export default {
    name: 'helpIndex',
    data() {
      return {
        cindex: 'index',
        leftMenu: {
          isCollapse: false,
          navList: [{
            icon: 'el-icon-s-home',
            title: '首页',
            flag: '',
            index: 'index'
          }, {
            icon: 'el-icon-s-order',
            title: '购买管理',
            flag: 'buy',
            index: '',
            children: [{
              title: '购买列表',
              index: 'blist'
            }, {
              title: '购买添加',
              index: 'badd'
            }]
          }, {
            icon: 'el-icon-user-solid',
            title: '经办人管理',
            flag: 'ano',
            index: '',
            children: [{
              title: '经办人列表',
              index: 'alist'
            }, {
              title: '经办人创建',
              index: 'aadd'
            }]
          }, {
            icon: 'el-icon-s-help',
            title: '权限管理',
            flag: 'auth',
            index: '',
            children: [{
              title: '角色列表',
              index: 'rlist'
            }, {
              title: '角色添加',
              index: 'radd'
            }]
          }, {
            icon: 'el-icon-s-custom',
            title: '顾客管理',
            flag: 'client',
            index: '',
            children: [{
              title: '顾客列表',
              index: 'clist'
            }, {
              title: '顾客添加',
              index: 'cadd'
            }]
          }, {
            icon: 'el-icon-s-grid',
            title: '药品管理',
            flag: 'medicine',
            index: '',
            children: [{
              title: "药品列表",
              index: 'mlist'
            }, {
              title: '药品添加',
              index: 'madd'
            }]
          },{
            icon: 'el-icon-s-tools',
            title: '其他',
            flag: '',
            index: 'other'
          }]
        }
      }
    },
    methods: {
      getIndex(index) {
        if(index != '') {
          this.cindex = index
        }
      }
    }
  }
</script>

<style>
  .help-container,
  .help-container .el-menu {
    height: 100%;
  }

  .help-container .el-menu {
    max-height: 500px;
  }

  .el-menu {
    border: solid 1px #e6e6e6;
  }

  .el-submenu .el-menu-item {
    padding: 0 48px !important;
    min-width: 160px;
  }
</style>
