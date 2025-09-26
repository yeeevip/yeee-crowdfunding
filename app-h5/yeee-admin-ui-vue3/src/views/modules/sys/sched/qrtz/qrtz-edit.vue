<template>
  <el-dialog :title="!id ? '新增' : '修改'"  class="mod-qrtz-edit"
    :close-on-click-modal="false" v-model="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter="dataFormSubmit()" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="11">
          <el-form-item label="任务编码" prop="jobName">
            <el-input v-model="dataForm.jobName" placeholder="如: testTask" :disabled="id > 0"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="任务组名" prop="jobGroup">
            <el-input v-model="dataForm.jobGroup" placeholder="如: DEFAULT"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="11">
          <el-form-item label="触发器名称" prop="triggerName">
            <el-input v-model="dataForm.triggerName" placeholder="如: testTaskTrigger"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="触发器组" prop="triggerGroup">
            <el-input v-model="dataForm.triggerGroup" placeholder="如: DEFAULT"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="22">
          <el-form-item label="cron表达式" prop="cronExpression">
            <el-input v-model="dataForm.cronExpression" placeholder="如: 0 0 12 * * ?"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="22">
          <el-form-item label="备注" prop="triggerDesc">
            <el-input v-model="dataForm.triggerDesc" placeholder="备注"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
  import debounce from 'lodash/debounce'
  export default {
    data () {
      return {
        id: 0,
        visible: false,
        dataForm: {
          jobName: '',
          jobGroup: '',
          triggerName: '',
          triggerGroup: '',
          cronExpression: '',
          triggerDesc: ''
        },
        dataRule: {
          jobName: [
            { required: true, message: '任务编码不能为空', trigger: 'blur' }
          ],
          jobGroup: [
            { required: true, message: '任务组名不能为空', trigger: 'blur' }
          ],
          triggerName: [
            { required: true, message: '触发器名称不能为空', trigger: 'blur' }
          ],
          triggerGroup: [
            { required: true, message: '触发器组不能为空', trigger: 'blur' }
          ],
          cronExpression: [
            { required: true, message: 'cron表达式不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (jobName, jobGroup) {
        this.dataForm.jobName = jobName || ''
        this.dataForm.jobGroup = jobGroup || ''
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.jobName && this.dataForm.jobGroup) {
            this.id = 1
            this.getInfo()
          } else {
            this.id = 0
          }
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
      },
      // 表单提交
      dataFormSubmit: debounce(function () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/manage/sys-schedule/${!this.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'jobName': this.dataForm.jobName,
                'jobGroup': this.dataForm.jobGroup,
                'triggerName': this.dataForm.triggerName,
                'triggerGroup': this.dataForm.triggerGroup,
                'cronExpression': this.dataForm.cronExpression,
                'triggerDesc': this.dataForm.triggerDesc
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }, 1000, { 'leading': true, 'trailing': false })
    }
  }
</script>