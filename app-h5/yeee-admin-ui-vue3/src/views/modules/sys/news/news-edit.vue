<template>
  <el-dialog :title="!id ? '新增' : '修改'"  class="mod-news-edit"
    :close-on-click-modal="false" v-model="visible" width="90%">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="11">
          <el-form-item label="名称" prop="name">
            <el-input v-model="dataForm.name" placeholder="名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="作者" prop="author">
            <el-input v-model="dataForm.author" placeholder="作者"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="备注" prop="remark">
            <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="22">
          <el-form-item label="图片" prop="imgUrl">
            <imgUpload :files = "dataForm.imgUrl" @modify="imgUploadHandle" :limit="limit"></imgUpload>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="22">
          <el-form-item label="内容" prop="content">
            <wangeditor ref="wangeditor" :getEditConts="getEditConts" :insertImg="insertImgHandle"></wangeditor>
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
  import imgUpload from '@/components/imgUpload/index.vue'
  import wangeditor from '@/components/wangeditor/index.vue'
  export default {
    data () {
      return {
        id: null,
        visible: false,
        limit: 3,
        dataForm: {
          name: '',
          content: '',
          author: '',
          imgUrl: '',
          remark: ''
        },
        dataRule: {
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    components: {
      wangeditor, imgUpload
    },
    methods: {
      init (id) {
        this.id = id
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.$refs.wangeditor.init()
          if (this.id) {
            this.getInfo()
          }
        })
      },
      // 获取系统新闻信息
      getInfo () {
        this.$http.json().post('/manage/sys-news/info', {
          'id': this.id
        }).then(({data: res}) => {
          if (res.code !== 200) {
            return this.$message.error(res.msg)
          }
          this.dataForm = {
            ...this.dataForm,
            ...res.data
          }
          this.$refs.wangeditor.setEditorContent(res.data.content)
        }).catch(() => {})
      },
      imgUploadHandle (val) {
        // console.log(val)
        this.$set(this.dataForm, 'imgUrl', val)
      },
      // 获取编辑器内容 格式为html
      getEditConts (value) {
        this.dataForm.content = value
      },
      // 插入图片操作
      insertImgHandle (res, insertImg, editor) {
        if (res && res.code === 0) {
          insertImg(res.data)
        }
      },
      // 表单提交
      dataFormSubmit: debounce(function () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http.json().post(!this.dataForm.id ? '/manage/sys-news/add' : '/manage/sys-news/upd', {...this.dataForm}).then(({data: res}) => {
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
