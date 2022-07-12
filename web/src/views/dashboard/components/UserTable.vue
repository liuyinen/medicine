<template>
  <div>
    <el-table :data="tableData" border fit highlight-current-row style="width: 100%;margin-right: 50px;">

      <el-table-column :label="$t('table.id')" prop="id" align="center" width="80"> <template slot-scope="{row}">
          <span>{{ row.id }}</span>
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
          <img v-if="row.avatar!=null" :src="'http://127.0.0.1:8080/api/upload/'+row.avatar" style="width: 120px;height: 120px;text-align: center;" />
          <img v-else src="@/assets/images/head_portrait.jpg" style="width: 120px;height: 120px;text-align: center;" />
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
    </el-table>

  </div>
</template>

<script>
  import myUpload from 'vue-image-crop-upload'

  export default {
    props: {
      tableData: {
        type: Array,
        default: true
      }
    }
  }
</script>

<style>
</style>
