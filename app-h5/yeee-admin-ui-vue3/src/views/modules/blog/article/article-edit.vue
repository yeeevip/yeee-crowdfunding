<template>
  <el-dialog :title="!id ? '新增' : '修改'" class="mod-article-edit"
             :close-on-click-modal="false"
             v-model="visible" width="90%">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="11">
          <el-form-item label="标题" prop="title">
            <el-input v-model="dataForm.title" placeholder="标题"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="标题图" prop="titleImg">
            <el-input v-model="dataForm.titleImg" placeholder="标题图"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="作者" prop="author">
            <el-input v-model="dataForm.author" placeholder="作者"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="分类" prop="classifyId">
            <el-select v-model="dataForm.classifyId" placeholder="请选择" clearable>
              <el-option
                v-for="item in classifyList"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="标签" prop="labelIds">
            <el-select v-model="dataForm.labelIds" placeholder="请选择" clearable multiple>
              <el-option
                v-for="item in labelList"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="专题" prop="topicIds">
            <el-select v-model="dataForm.topicIds" placeholder="请选择" clearable multiple>
              <el-option
                v-for="item in topicList"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="摘要" prop="remark">
            <el-input v-model="dataForm.remark" placeholder="说明"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="是否原创" prop="oriFlag">
            <el-select v-model="dataForm.oriFlag" clearable placeholder="是否原创">
              <el-option label="是" value='1' />
              <el-option label="否" value='0' />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="排序" prop="sort">
            <el-input type="number" v-model="dataForm.sort" placeholder="排序"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="发布状态" prop="status">
            <el-select v-model="dataForm.status" clearable placeholder="发布状态">
              <el-option label="已发布" value='1' />
              <el-option label="未发布" value='0' />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="发布时间" prop="publishTime">
            <el-date-picker
              v-model="dataForm.publishTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="类型" prop="type">
            <el-select v-model="dataForm.type" clearable placeholder="类型">
              <el-option label="原文" value='0' />
              <el-option label="外链" value='1' />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="外链地址" prop="linkUrl">
            <el-input v-model="dataForm.linkUrl" placeholder="外链地址"></el-input>
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
import query from '@/utils/query'

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
        title: '',
        titleImg: '',
        author: '',
        classifyId: null,
        labelIds: [],
        topicIds: [],
        remark: '',
        oriFlag: '',
        sort: null,
        status: '',
        publishTime: '',
        type: '',
        linkUrl: ''
      },
      dataFormOrigin: [],
      dataRule: {
        username: [
          { required: true, message: '用户名不能为空', trigger: 'blur' },
          { validator: validateFormDataUnique, trigger: 'blur' }
        ],
        email: [
          { validator: validateEmail, trigger: 'blur' }
        ],
        mobile: [
          { validator: validateMobile, trigger: 'blur' }
        ]
      },
      classifyList: [],
      labelList: [],
      topicList: []
    }
  },
  components: {
  },
  methods: {
    init (id) {
      this.dataForm.id = null
      this.dataFormOrigin = {}
      this.id = id
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        this.getClassifyList()
        this.getLabelList()
        this.getTopicList()
        if (this.id) {
          this.getInfo()
        }
      })
    },
    // 获取信息
    getInfo () {
      this.$http.json().post('/manage/blog/article/info', {
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
        this.dataForm.oriFlag = this.dataForm.oriFlag.toString()
        this.dataForm.status = this.dataForm.status.toString()
        this.dataForm.type = this.dataForm.type.toString()
      }).catch(() => {})
    },
    getClassifyList () {
      let qry = query.new()
      query.toP(qry, 1, 100)
      this.$http.get('/manage/blog/classify/page' + '?query=' + encodeURIComponent(query.toJsonStr(qry)))
        .then(({ data: res }) => {
          if (res.code === 200) {
            this.classifyList = res.data.result
          }
        })
    },
    getLabelList () {
      let qry = query.new()
      query.toP(qry, 1, 100)
      this.$http.get('/manage/blog/label/page' + '?query=' + encodeURIComponent(query.toJsonStr(qry)))
        .then(({ data: res }) => {
          if (res.code === 200) {
            this.labelList = res.data.result
          }
        })
    },
    getTopicList () {
      let qry = query.new()
      query.toP(qry, 1, 100)
      this.$http.get('/manage/blog/topic/page' + '?query=' + encodeURIComponent(query.toJsonStr(qry)))
        .then(({ data: res }) => {
          if (res.code === 200) {
            this.topicList = res.data.result
          }
        })
    },
    // 表单提交
    dataFormSubmit: debounce(function () {
      // console.log(this.dataForm)
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http.json().post(!this.dataForm.id ? '/manage/blog/article/add' : '/manage/blog/article/upd', {...this.dataForm}).then(({data: res}) => {
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
