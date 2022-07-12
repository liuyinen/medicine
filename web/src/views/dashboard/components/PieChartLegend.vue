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
          this.getSelected()
          this.initChart()
        }
      }
    },
    data() {
      return {
        chart: null,
        legendData: [],
        selected: {}
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
    created() {
      this.getSelected()
      this.getTitleName()
    },
    methods: {
      getSelected() {
        const data = this.getData
        for (var i = 0, len = data.length; i < len; i++) {
          this.selected[data[i].name] = i < 6
        }
      },
      getTitleName() {
        const data = this.getData
        for (var i = 0, len = data.length; i < len; i++) {
          this.legendData.push(data[i].name)
        }
      },
      initChart() {
        this.chart = echarts.init(this.$el, 'macarons')
        var pieData = this.getData
        var title = this.chartTitle
        var legendData = this.legendData
        var selected = this.selected

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
            type: 'scroll',
            orient: 'vertical',
            right: 10,
            top: 20,
            bottom: 20,
            data: legendData,
            selected: selected
          },
          series: [{
            name: title,
            type: 'pie',
            roseType: 'radius',
            radius: [15, 95],
            center: ['50%', '45%'],
            data: pieData,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            },
            animationEasing: 'cubicInOut',
            animationDuration: 2600
          }]
        })
      }
    }
  }
</script>
