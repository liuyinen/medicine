<template>
  <div class="dashboard-editor-container">
    <panel-group v-if="total!=null" @handleSetLineChartData="handleSetLineChartData" :total="total" />

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="10" v-if="roleData!=null">
        <div class="chart-wrapper">
          <pie-chart :chartTitle="pieChartTitle.role" :getData="roleData" />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="14" v-if="recordsData!=null">
        <div class="chart-wrapper">
          <pie-chart-legend :chartTitle="pieChartTitle.records" :getData="recordsData" />
        </div>
      </el-col>
    </el-row>

    <el-row v-if="lineChartList != null" style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="listChart" :subData="sub" :chartTitle="title" />
    </el-row>

<!--    <el-row v-if="type == 'client' && listTable != null">
      <client-table :table-data="listTable" />
    </el-row>
    <el-row v-if="type == 'user' && listTable != null">
      <user-table :table-data="listTable" />
    </el-row>
    <el-row v-if="type == 'medicine' && listTable != null">
      <medicine-table :table-data="listTable" />
    </el-row>
    <el-row v-if="type == 'records' && listTable != null">
      <records-table :table-data="listTable" />
    </el-row> -->
  </div>
</template>

<script>
  import {
    reportData
  } from '@/api/user'
  import PanelGroup from './components/PanelGroup'
  import LineChart from './components/LineChart'
  import RaddarChart from './components/RaddarChart'
  import PieChart from './components/PieChart'
  import PieChartLegend from './components/PieChartLegend'
  import BarChart from './components/BarChart'
  import ClientTable from './components/ClientTable'
  import UserTable from './components/UserTable'
  import MedicineTable from './components/MedicineTable'
  import RecordsTable from './components/RecordsTable'
  import waves from '@/directive/waves'

  export default {
    name: 'DashboardAdmin',
    components: {
      PanelGroup,
      LineChart,
      RaddarChart,
      PieChart,
      PieChartLegend,
      BarChart,
      ClientTable,
      UserTable,
      MedicineTable,
      RecordsTable
    },
    directives: {
      waves
    },
    created() {
      this.getList()
    },
    computed: {
      dataChange() {
        const {
          lineChartList,
          newLists
        } = this;
        return {
          lineChartList,
          newLists
        }
      }
    },
    watch: {
      dataChange: {
        deep: true,
        handler: function(newval, oldval) {
          this.handleSetLineChartData("client")
        }
      }
    },
    data() {
      return {
        lineChartList: null,
        newLists: null,
        roleData: null,
        recordsData: null,
        sub: null,
        total: {
          client: 0,
          medicine: 0,
          records: 0,
          client: 0
        },
        listChart: null,
        listTable: null,
        title: '',
        type: 'client',
        chartNames: {
          client: "每日新增顾客数",
          user: "每日新增经办人数",
          medicine: "每日新增药品数",
          records: "每日总销量"
        },
        pieChartTitle: {
          role: "经办人角色占比",
          records: "药品购买占比"
        }
      }
    },
    methods: {
      getList() {
        reportData().then(response => {
          this.lineChartList = response.data.lineChart
          this.newLists = response.data.newLists
          this.sub = response.data.sub
          this.total = response.data.total
          this.roleData = response.data.userVoRole
          this.recordsData = response.data.recordsVoMedicine
          setTimeout(() => {
            this.listLoading = false
          }, 1.5 * 1000)
        })
      },
      handleSetLineChartData(type) {
        this.type = type
        this.title = this.chartNames[type]
        if (this.newLists[type].length > 0) {
          this.listTable = this.newLists[type]
        }
        this.listChart = this.lineChartList[type]
      },
    }
  }
</script>

<style lang="scss" scoped>
  .dashboard-editor-container {
    padding: 32px;
    background-color: rgb(240, 242, 245);
    position: relative;

    .github-corner {
      position: absolute;
      top: 0px;
      border: 0;
      right: 0;
    }

    .chart-wrapper {
      background: #fff;
      padding: 16px 16px 0;
      margin-bottom: 32px;
    }
  }

  @media (max-width:1024px) {
    .chart-wrapper {
      padding: 8px;
    }
  }
</style>
