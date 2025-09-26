<template>
  <el-dialog title="详情"  class="mod-qrtz-info"
    :close-on-click-modal="false" v-model="visible">
    <el-form :model="dataForm" ref="dataForm" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="任务编码:">
            <label>{{ dataForm.jobName }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="任务组:">
            <label>{{ dataForm.jobGroup }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="任务URL:">
            <label>{{ dataForm.jobDesc }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="时间表达式:">
            <label>{{ dataForm.cronExpression }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注:">
            <label>{{ dataForm.triggerDesc }}</label>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="visible = false">取消</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        id: null,
        jobName: '',
        jobGroup: '',
        visible: false,
        dataForm: {}
      }
    },
    methods: {
      init (jobName, jobGroup) {
        this.id = 1
        this.jobName = jobName
        this.jobGroup = jobGroup
        this.visible = true
        this.$nextTick(() => {
          if (this.id) {
            this.getInfo()
          }
        })
      },
      // 获取系统角色信息
      getInfo () {
        this.$http.get('/sched/qrtz/info', {
          params: {
            jobName: this.jobName,
            jobGroup: this.jobGroup
          }
        }).then(({data: res}) => {
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
