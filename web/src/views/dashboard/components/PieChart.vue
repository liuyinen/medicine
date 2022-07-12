<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
  import echarts from 'echarts'
  require('echarts/theme/macarons') // echarts theme
  import resize from './mixins/resize'

  export default {
    mixins: [resize],
    props: {
      className: {
        type: String,
        default: 'chart'
      },
      width: {
        type: String,
        default: '100%'
      },
      height: {
        type: String,
        default: '300px'
      },
      getData: {
        type: Array,
        default: true
      },
      chartTitle: {
        type: String,
        require: true
      }
    },
    computed: {
      chartChangData() {
        const {
          getData,
          chartTitle
        } = this;
        return {
          getData,
          chartTitle
        }
      }
    },
    watch: {
      chartChangData: {
        deep: true,
        handler: function(newval, oldval) {
          this.getData = newval.getData
          this.chartTitle = newval.chartTitle
          this.getTitleName()
          this.initChart()
        }
      }
    },
    data() {
      return {
        chart: null,
        legendData: null,
      }
    },
    mounted() {
      this.$nextTick(() => {
        this.initChart()
      })
    },
    beforeDestroy() {
      if (!this.chart) {
        return
      }
      this.chart.dispose()
      this.chart = null
    },
    methods: {
      getTitleName() {
        for(var i = 0;i<this.getData.length;i++) {
          this.legendData.push(this.getData[i].name)
        }
      },
      initChart() {
        this.chart = echarts.init(this.$el, 'macarons')
        var pieData = this.getData
        var title = this.chartTitle
        var legendData = this.legendData

        this.chart.setOption({
          title: {
            text: title,
            left: 'center'
          },
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          legend: {
            left: 'center',
            bottom: '10',
            data: legendData
          },
          series: [{
            name: title,
            type: 'pie',
            roseType: 'radius',
            radius: [15, 95],
            center: ['50%', '45%'],
            data: pieData,
            animationEasing: 'cubicInOut',
            animationDuration: 2600
          }]
        })
      }
    }
  }
</script>
