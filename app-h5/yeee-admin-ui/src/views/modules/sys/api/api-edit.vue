<template>
  <el-dialog :title="!id ? '新增' : '修改'"  class="mod-api-edit"
    :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="20">
          <el-form-item label="密钥id" prop="akId">
            <el-input v-model="dataForm.akId" placeholder="密钥id"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="20">
          <el-form-item label="密钥secret" prop="akSecret">
            <el-input v-model="dataForm.akSecret" placeholder="密钥secret"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="20">
          <el-form-item label="访问方法集合" prop="method">
            <el-input v-model="dataForm.method" placeholder="访问方法集合"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="实体id[t]" prop="subId">
            <el-input-number v-model="dataForm.subId" :min="1" label="实体id[t]"></el-input-number>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="有效开始时间" prop="vldFrTm">
            <el-date-picker v-model="dataForm.vldFrTm" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="有效开始时间"></el-date-picker>

          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="有效结束时间" prop="vldToTm">
            <el-date-picker v-model="dataForm.vldToTm" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="有效结束时间"></el-date-picker>

          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="状态（0：正常，1：停用）" prop="status">
            <el-input-number v-model="dataForm.status" :min="1" label="状态（0：正常，1：停用）"></el-input-number>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="备注" prop="rmks">
            <el-input v-model="dataForm.rmks" placeholder="备注"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="参数1" prop="arg1">
            <el-input v-model="dataForm.arg1" placeholder="参数1"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="参数2" prop="arg2">
            <el-input v-model="dataForm.arg2" placeholder="参数2"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="参数3" prop="arg3">
            <el-input v-model="dataForm.arg3" placeholder="参数3"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="租户ID" prop="tntId">
            <el-input-number v-model="dataForm.tntId" :min="1" label="租户ID"></el-input-number>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import debounce from 'lodash/debounce'
  export default {
    data () {
      var validateFormDataUnique = (rule, value, callback) => {
        let qry = this.$query.new()
        this.$query.toW(qry, rule.field, value, 'EQ')
        this.$http.get('sys/api/hasExist?query=' + encodeURIComponent(this.$query.toJsonStr(qry))).then(({data: res}) => {
          if (res.code !== 0) {
            callback(new Error('服务器异常,校验失败'))
          }
          if (value !== this.dataFormOrigin[rule.field] && res.data) {
            callback(new Error('已被使用'))
          } else {
            callback()
          }
        }).catch(() => {
          callback(new Error('校验失败'))
        })
      }
      return {
        id: null,
        visible: false,
        dataForm: {
          id: '',
          akId: '',
          akSecret: '',
          method: '',
          subId: '',
          vldFrTm: '',
          vldToTm: '',
          status: '',
          rmks: '',
          arg1: '',
          arg2: '',
          arg3: '',
          tntId: ''
        },
        dataFormOrigin: [],
        dataRule: {
          akId: [
            { required: true, message: '密钥id不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ],
          akSecret: [
            { required: true, message: '密钥secret不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ],
          status: [
            { required: true, message: '状态（0：正常，1：停用）不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.id = id
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.id) {
            this.getInfo()
          }
        })
      },
      // 获取系统密钥信息
      getInfo () {
        this.$http.get('/sys/api/info/' + this.id).then(({data: res}) => {
          if (res.code !== 0) {
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
      },
      // 表单提交
      dataFormSubmit: debounce(function () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            let url = !this.id ? '/sys/api/add' : '/sys/api/upd'
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
