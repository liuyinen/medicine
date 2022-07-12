<template>
  <div>
    <component class="menu-item" v-for="(value) in navList" :key="value.title+value.url" :index="value.url" :is="(value.children&&value.children.length>0)?'el-submenu':'el-menu-item'">
      <template slot="title">
        <i :class="[value.icon]"></i>
        <span>{{value.title}}</span>
      </template>
      <template v-if="value.children&&value.children.length>0">
        <nav-menu :navList="value.children"></nav-menu>
      </template>
    </component>
  </div>
</template>
<script>
  export default {
    name: 'NavMenu',
    props: {
      navList: {
        type: Array,
        default: () => []
      }
    }
  }
</script>
