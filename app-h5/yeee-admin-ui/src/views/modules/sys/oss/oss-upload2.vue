<template>
  <el-dialog title="上传文件2"  class="mod-oss-upload2"
    :close-on-click-modal="false" @close="closeHandle" :visible.sync="visible">

    <fileUpload :files="dataForm.url" @modify="uploadHandle" tip="多文件上传，包含文件预览"></fileUpload>

    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import fileUpload from '@/components/fileUpload'
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          url: null
        }
      }
    },
    components: {
      fileUpload
    },
    methods: {
      init () {
        this.visible = true
      },
      uploadHandle (val) {
        this.$set(this.dataForm, 'url', val)
      },
      // 弹窗关闭时
      closeHandle () {
        this.fileList = []
        this.$emit('refreshDataList')
      }
    }
  }
</script>
