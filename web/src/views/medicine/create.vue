<template>
  <div class="app-container">
    <el-form enctype="multipart/form-data" :model="medicine" label-width="80px" style="width: 600px;" label-position="left">
      <el-form-item label="*药品编号">
        <el-input v-model="medicine.mno" placeholder="药品编号" />
      </el-form-item>
      <el-form-item label="*药品名称">
        <el-input v-model="medicine.mname" placeholder="药品名称" />
      </el-form-item>
      <el-form-item label="*药品库存">
        <el-input v-model="medicine.stock" placeholder="药品库存" />
      </el-form-item>
      <el-form-item :label="$t('table.mode')">
        <label v-for="(item, index) in modeData" :key="index">
          <input type="radio" v-model="medicine.mmode" :value="item.id" />
          {{ item.value }}
        </label>
      </el-form-item>
      <el-form-item label="*功效">
        <el-input v-model="medicine.mefficacy" placeholder="功效" />
      </el-form-item>
      <div style="text-align:right;">
        <el-button type="primary" @click="confirmMedicine">
          {{ $t('permission.confirm') }}
        </el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
  import path from 'path'
  import {
    createMedicine
  } from '@/api/medicine'
  import i18n from '@/lang'

  const defaultMedicine = {
    mno: '',
    mname: '',
    stock: 0,
    mmode: 0,
    mefficacy: ''
  }

  export default {
    name: 'medicineCreate',
    data() {
      return {
        modeData: [{
            id: 0,
            value: '内服'
          },
          {
            id: 1,
            value: '外服'
          }
        ],
        medicine: Object.assign({}, defaultMedicine),
        dialogVisible: false,
        dialogType: 'new',
        checkStrictly: false,
        defaultProps: {
          children: 'children',
          label: 'title'
        }
      }
    },
    methods: {
      confirmMedicine() {
        if (this.medicine.mno == '' || this.medicine.mnane == '' || this.medicine.stock == '' || this.medicine.mefficacy == '') {
          this.$message({
            message: '药品编号、名称、库存和功效不能为空，请重新确认！',
            type: 'error'
          })
        } else if (isNaN(this.medicine.mno) || isNaN(this.medicine.stock)) {
          this.$message({
            message: '药品编号和库存为数值，请重新确认！',
            type: 'error'
          })
        } else {
          createMedicine(this.medicine).then(response => {
            this.$router.push({
              path: 'medicineList'
            })
          })
        }
      }
    }
  }
</script>
