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
          <el-form-item label="任务组" prop="jobGroup">
            <el-input v-model="dataForm.jobGroup" placeholder="任务组"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="任务URL" prop="jobDesc">
            <el-input v-model="dataForm.jobDesc" placeholder="任务URL"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="时间表达式" prop="cronExpression">
            <el-input v-model="dataForm.cronExpression" placeholder="如: 0 0 12 * * ?"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
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
      var validateJobName = (rule, value, callback) => {
        if (!this.id && /[\u4E00-\u9FA5]/.test(value)) {
          callback(new Error('不能输入中文汉字'))
        } else {
          callback()
        }
      }
      return {
        id: null,
        visible: false,
        dataForm: {
          jobName: '',
          jobGroup: 'default',
          jobDesc: '',
          cronExpression: '',
          triggerDesc: ''
        },
        dataRule: {
          jobName: [
            { required: true, message: '任务编码不能为空', trigger: 'blur' },
            { validator: validateJobName, trigger: 'blur' }
          ],
          jobGroup: [
            { required: true, message: '任务组不能为空', trigger: 'blur' }
          ],
          jobDesc: [
            { required: true, message: '任务URL不能为空', trigger: 'blur' }
          ],
          cronExpression: [
            { required: true, message: '时间表达式不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (jobName, jobGroup) {
        this.id = null
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (jobGroup) {
            this.dataForm.jobGroup = jobGroup
          }
          if (jobName) {
            this.id = 1
            this.dataForm.jobName = jobName
          }
          if (this.id) {
            this.getInfo()
          }
        })
      },
      // 获取系统角色信息
      getInfo () {
        this.$http.get('/sched/qrtz/info', {
          params: {
            jobName: this.dataForm.jobName,
            jobGroup: this.dataForm.jobGroup
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
      },
      // 表单提交
      dataFormSubmit: debounce(function () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            let url = !this.id ? '/sched/qrtz/addJob' : '/sched/qrtz/updJob'
            this.$http.post(url, {...this.dataForm}).then(({data: res}) => {
              if (res && res.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(res.msg)
              }
            })
          }
        })
      }, 1000, { 'leading': true, 'trailing': false })
    }
  }
</script>
