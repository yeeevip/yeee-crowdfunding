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
          <el-form-item label="任务组名:">
            <label>{{ dataForm.jobGroup }}</label>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="触发器名称:">
            <label>{{ dataForm.triggerName }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="触发器组:">
            <label>{{ dataForm.triggerGroup }}</label>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="cron表达式:">
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
        visible: false,
        dataForm: {
          jobName: '',
          jobGroup: '',
          triggerName: '',
          triggerGroup: '',
          cronExpression: '',
          triggerDesc: ''
        }
      }
    },
    methods: {
      init (jobName, jobGroup) {
        this.dataForm.jobName = jobName || ''
        this.dataForm.jobGroup = jobGroup || ''
        this.visible = true
        this.$nextTick(() => {
          this.getInfo()
        })
      },
      // 获取信息
      getInfo () {
        this.$http({
          url: this.$http.adornUrl(`/manage/sys-schedule/info`),
          method: 'get',
          params: this.$http.adornParams({
            'jobName': this.dataForm.jobName,
            'jobGroup': this.dataForm.jobGroup
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataForm.triggerName = data.schedule.triggerName
            this.dataForm.triggerGroup = data.schedule.triggerGroup
            this.dataForm.cronExpression = data.schedule.cronExpression
            this.dataForm.triggerDesc = data.schedule.triggerDesc
          }
        })
      }
    }
  }
</script>