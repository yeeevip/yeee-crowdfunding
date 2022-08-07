<template>
  <el-dialog :title="!id ? '新增' : '修改'"  class="mod-apiLog-edit"
    :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="11">
          <el-form-item label="ID" prop="id">
            <el-input-number v-model="dataForm.id" :min="1" label="ID"></el-input-number>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="密钥id" prop="akId">
            <el-input v-model="dataForm.akId" placeholder="密钥id"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="接口名称" prop="nm">
            <el-input v-model="dataForm.nm" placeholder="接口名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="IP" prop="ip">
            <el-input v-model="dataForm.ip" placeholder="IP"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="URI" prop="uri">
            <el-input v-model="dataForm.uri" placeholder="URI"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="URL" prop="url">
            <el-input v-model="dataForm.url" placeholder="URL"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="记录时间" prop="rcdTm">
            <el-date-picker v-model="dataForm.rcdTm" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="记录时间"></el-date-picker>
              
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="运行时长" prop="spendTime">
            <el-input-number v-model="dataForm.spendTime" :min="1" label="运行时长"></el-input-number>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="方法" prop="method">
            <el-input v-model="dataForm.method" placeholder="方法"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="用户代理" prop="userAgent">
            <el-input v-model="dataForm.userAgent" placeholder="用户代理"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="渠道" prop="channel">
            <el-input v-model="dataForm.channel" placeholder="渠道"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="请求参数" prop="regArg">
            <el-input v-model="dataForm.regArg" placeholder="请求参数"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="返回内容" prop="result">
            <el-input v-model="dataForm.result" placeholder="返回内容"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="状态 (0：成功，1：失败)" prop="status">
            <el-input-number v-model="dataForm.status" :min="1" label="状态 (0：成功，1：失败)"></el-input-number>
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
        this.$http.get('sys/apiLog/hasExist?query=' + encodeURIComponent(this.$query.toJsonStr(qry))).then(({data: res}) => {
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
          nm: '',
          ip: '',
          uri: '',
          url: '',
          rcdTm: '',
          spendTime: '',
          method: '',
          userAgent: '',
          channel: '',
          regArg: '',
          result: '',
          status: '',
          tntId: ''
        },
        dataFormOrigin: [],
        dataRule: {
          id: [
            { required: true, message: 'ID不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ],
          akId: [
            { required: true, message: '密钥id不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ],
          nm: [
            { required: true, message: '接口名称不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ],
          ip: [
            { required: true, message: 'IP不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ],
          uri: [
            { required: true, message: 'URI不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ],
          url: [
            { required: true, message: 'URL不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ],
          rcdTm: [
            { required: true, message: '记录时间不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ],
          spendTime: [
            { required: true, message: '运行时长不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ],
          method: [
            { required: true, message: '方法不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ],
          userAgent: [
            { required: true, message: '用户代理不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ],
          channel: [
            { required: true, message: '渠道不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ],
          regArg: [
            { required: true, message: '请求参数不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ],
          result: [
            { required: true, message: '返回内容不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ],
          status: [
            { required: true, message: '状态 (0：成功，1：失败)不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ],
          tntId: [
            { required: true, message: '租户ID不能为空', trigger: 'blur' },
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
      // 获取系统Api日志信息
      getInfo () {
        this.$http.get('/sys/apiLog/info/' + this.id).then(({data: res}) => {
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
            let url = !this.id ? '/sys/apiLog/add' : '/sys/apiLog/upd'
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
