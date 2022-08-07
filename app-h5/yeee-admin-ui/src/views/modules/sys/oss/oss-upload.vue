<template>
  <el-dialog title="上传文件"  class="mod-oss-upload"
    :close-on-click-modal="false" @close="closeHandle" :visible.sync="visible">
    <el-upload
      ref="upload"
      drag
      :action="qiniuUrl"
      :before-upload="beforeUploadHandle"
      :on-success="successHandle"
      :auto-upload="false"
      multiple
      :file-list="fileList"
      :data="qnFormData"
      style="text-align: center;">
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip">不要上传大文件</div>
    </el-upload>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="uploadStart">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import { baseUrl } from '@/utils/index'
  export default {
    data () {
      return {
        visible: false,
        url: '',
        qiniuUrl: 'https://upload.qiniup.com',
        qnFormData: {},
        num: 0,
        successNum: 0,
        fileList: []
      }
    },
    methods: {
      init () {
        this.visible = true
        this.url = baseUrl() + '/general/oss/upload'
        this.initQnToken()
      },
      // 上传之前
      beforeUploadHandle (file) {
        // if (file.type !== 'image/jpg' && file.type !== 'image/jpeg' && file.type !== 'image/png' && file.type !== 'image/gif') {
        //   this.$message.error('只支持jpg、png、gif格式的图片！')
        //   return false
        // }
        this.qnFormData.key = 'blog/' + file.name
        this.num++
        return true
      },
      // 上传成功
      successHandle (res, file, fileList) {
        this.fileList = fileList
        this.successNum++
        if (res && res.key) {
          this.saveOssUpLog(res, file)
          if (this.num === this.successNum) {
            this.$confirm('操作成功, 是否继续操作?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).catch(() => {
              this.visible = false
            })
          }
        } else {
          this.$message.error(res.msg)
        }
      },
      // 弹窗关闭时
      closeHandle () {
        this.fileList = []
        this.$emit('refreshDataList')
      },
      // 开始上传
      uploadStart () {
        this.$refs.upload.submit()
        // this.visible = false
      },
      initQnToken () {
        this.$http.form().post('/manage/sys-ossLog/upload/sts', {channel: 'qiniu'}).then(({data: res}) => {
          if (res.code !== 200) {
            return this.$message.error(res.msg)
          }
          this.qnFormData = {'token': res.data.info.upToken}
        }).catch(() => {})
      },
      saveOssUpLog (res, file) {
        this.$http.json().post('/manage/sys-ossLog/add', {
          sourceName: file.name,
          ossUrl: res.key,
          size: file.size
        }).then(({data: res}) => {
          if (res && res.code === 200) {
            //
          } else {
            this.$message.error(res.message)
          }
        })
      }
    }
  }
</script>
