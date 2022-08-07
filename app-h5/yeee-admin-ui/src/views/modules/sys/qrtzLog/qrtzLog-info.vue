<template>
  <el-dialog title="详情"  class="mod-qrtzLog-info"
    :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" ref="dataForm" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="url:">
            <label>{{ dataForm.url }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="任务标识:">
            <label>{{ dataForm.jobKey }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态">
            <label>{{  dataForm.status  === 0 ? '成功' : '失败'  }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="运行时长:">
            <label>{{ dataForm.spendTime }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注:">
            <label>{{ dataForm.rmks }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="这次执行时间:">
            <label>{{ dataForm.preFireTm }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="下次执行时间:">
            <label>{{ dataForm.nextFireTm }}</label>
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
          if (this.id) {
            this.getInfo()
          }
        })
      },
      // 获取系统调度日志信息
      getInfo () {
        this.$http.get('/sys/qrtzLog/info/' + this.id).then(({data: res}) => {
          if (res.code !== 0) {
            return this.$message.error(res.msg)
          }
          this.dataForm = {
            ...this.dataForm,
            ...res.data
          }
        }).catch(() => {})
      }
    }
  }
</script>
