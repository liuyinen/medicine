<template>
  <div>
    <el-table :data="tableData" border fit highlight-current-row style="width: 100%;margin-right: 50px;">
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
