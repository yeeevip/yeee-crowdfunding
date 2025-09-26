<template>
  <el-dialog :title="!id ? '新增' : '修改'" class="mod-topic-edit"
             :close-on-click-modal="false"
             v-model="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="11">
          <el-form-item label="名称" prop="name">
            <el-input v-model="dataForm.name" placeholder="名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="封面图" prop="coverImg">
            <el-input v-model="dataForm.coverImg" placeholder="封面图"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="排序" prop="sort">
            <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="状态" prop="status">
            <el-select v-model="dataForm.status" clearable placeholder="状态">
              <el-option label="启用" value='1' />
              <el-option label="禁用" value='0' />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="说明" prop="remark">
            <el-input v-model="dataForm.remark" placeholder="说明"></el-input>
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
import { isEmail, isMobile } from '@/utils/validate'
export default {
  data () {
    var validateEmail = (rule, value, callback) => {
      if (value && !isEmail(value)) {
        callback(new Error('邮箱格式错误'))
      } else {
        callback()
      }
    }
    var validateMobile = (rule, value, callback) => {
      if (value && !isMobile(value)) {
        callback(new Error('手机号格式错误'))
      } else {
        callback()
      }
    }
    var validateFormDataUnique = (rule, value, callback) => {
      let qry = this.$query.new()
      this.$query.toW(qry, rule.field, value, 'EQ')
      this.$http.get('/manage/cf-user/exist?query=' + encodeURIComponent(this.$query.toJsonStr(qry))).then(({data: res}) => {
        if (res.code !== 200) {
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
      otherParamsVisible: false,
      roleList: [],
      orgList: [],
      dataForm: {
        id: null,
        name: '',
        coverImg: '',
        sort: '',
        status: '1', // 默认启用
        remark: ''
      },
      dataFormOrigin: {},
      dataRule: {
        name: [
          { required: true, message: '名称不能为空', trigger: 'blur' }
        ],
        coverImg: [
          { required: true, message: '封面图不能为空', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '排序不能为空', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '状态不能为空', trigger: 'change' }
        ]
      }
    }
  },
  methods: {
    init (id) {
      this.dataForm.id = null
      this.dataFormOrigin = {}
      this.id = id
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.id) {
          this.getInfo()
        }
      })
    },
    // 获取信息
    getInfo () {
      this.$http.json().post('/manage/blog/topic/info', {
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
        this.dataForm.status = this.dataForm.status.toString()
      }).catch(() => {})
    },
    // 表单提交
    dataFormSubmit: debounce(function () {
      // console.log(this.dataForm)
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http.json().post(!this.dataForm.id ? '/manage/blog/topic/add' : '/manage/blog/topic/upd', {...this.dataForm}).then(({data: res}) => {
            if (res && res.code === 200) {
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
              this.$message.error(res.message)
            }
          })
        }
      })
    }, 1000, { 'leading': true, 'trailing': false })
  }
}
</script>
<style lang="scss">
.mod-topic-edit {
  .input-bar i {
    padding-top: 6px;
    font-size: 24px;
    color: #3a8ee6;
    transition-property: color;
    transition-duration: 0.15s;
    transition-timing-function: linear;
    transition-delay: initial;
  }
}
</style>
