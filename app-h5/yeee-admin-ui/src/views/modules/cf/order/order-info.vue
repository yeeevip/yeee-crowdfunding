<template>
  <el-dialog title="详情" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" ref="dataForm" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="编号:">
            <label>{{ dataForm.code }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="项目名称:">
            <label>{{ dataForm.projectVO.title }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="下单金额:">
            <label>{{ dataForm.payPrice }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="收货人:">
            <label>{{ dataForm.receiveInfoVO.receiver }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="收货地址:">
            <label>{{ dataForm.receiveInfoVO.address }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="电话:">
            <label>{{ dataForm.receiveInfoVO.phone }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="创建时间:">
            <label>{{ dataForm.orderDate }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否付款:">
            <label>{{ dataForm.hasPay == 1 ? '是' : '否' }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否发货:">
            <label>{{ dataForm.hasSend == 1 ? '是' : '否' }}</label>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        id: null,
        visible: false,
        dataForm: {}
      }
    },
    methods: {
      init (id) {
        this.id = id
        this.visible = true
        this.$nextTick(() => {
          this.dataForm = {}
          // this.$refs['dataForm'].resetFields()
          if (this.id) {
            this.getInfo()
          }
        })
      },
      // 获取用户信息
      getInfo () {
        this.$http.json().post('/manage/cf/order/info', {
          'id': this.id
        }).then(({data: res}) => {
          if (res.code !== 200) {
            return this.$message.error(res.msg)
          }
          this.dataForm = {
            ...this.dataForm,
            ...res.data
          }
          this.dataFormOrigin = {
            ...this.dataFormOrigin,
            ...res.data
          }
        }).catch(() => {})
      }
    }
  }
</script>
