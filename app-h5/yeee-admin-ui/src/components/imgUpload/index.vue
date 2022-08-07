<template>
  <div>
    <el-upload
      ref = "upload"
      :action = "actionUrl"
      list-type = "picture-card"
      :file-list = "urlComputed"
      :multiple = "multiple"
      :limit = "limit"
      :http-request = "upload"
      :on-preview = "handlePictureCardPreview"
      :on-exceed = "handleExceed"
      :on-remove = "handleRemove">
      <i class = "el-icon-plus"></i>
    </el-upload>
  </div>
</template>
<script>
  import Viewer from 'viewerjs'
  import 'viewerjs/dist/viewer.min.css'
  export default{
    name: 'imgUpload',
    props: {
      actionUrl: {
        type: String,
        default: 'general/oss/upload'
      },
      files: {
        type: [Array, String],
        default: function () {
          return []
        }
      },
      acceptFileType: {
        type: Array,
        default: function () {
          return []
        }
      },
      multiple: {
        type: Boolean,
        default: false
      },
      // drag: {
      //     type: Boolean,
      //     default: false
      // },
      // tip: {
      //   type: String,
      //   default: ''
      // },
      outPut: {
        type: String,
        default: 'string'   // 可以选择string, array  string 逗号分隔
      },
      postData: {
        type: Object,
        default: null
      },
      max: {
        type: Number,
        default: 1024 * 1024 * 10
      },
      limit: {
        type: Number,
        default: 999
      }
    },
    data () {
      return {}
    },
    computed: {
      filesArray () {
        let urls = []
        if (Object.prototype.toString.apply(this.files) === '[object Array]') {
          urls = this.files
        } else if (Object.prototype.toString.apply(this.files) === '[object String]' && this.files.length > 0) {
          urls = this.files.split(',')
        }
        return urls.map(function (value, index, array) {
          return value
        })
      },
      urlComputed () {
        if (this.files === '') {
          return []
        }
        let urls = []
        if (Object.prototype.toString.apply(this.files) === '[object Array]') {
          urls = this.files
        } else if (Object.prototype.toString.apply(this.files) === '[object String]') {
          urls = this.files.split(',')
        }
        return urls.map(function (value, index, array) {
          return {
            name: value.slice(value.indexOf('_') + 1),
            url: value
          }
        })
      }
    },
    methods: {
      handleExceed (file, fileList) {
        this.$message.error(`上传文件数量不能超过 ${this.limit} 个!`)
      },
      handleRemove (file, fileList) {
        let urlMap = fileList.map(item => {
          return item.url
        })
        this.submitModifyHandle(urlMap)
      },
      handlePictureCardPreview (file) {
        // var src = file.url
        // console.log(file)
        this.showImage(file.url)
      },
      // 图片预览
      showImage (url) {
        let divNode = document.createElement('div')
        divNode.style.display = 'none'
        let imageNode = document.createElement('img')
        imageNode.setAttribute('src', url)
        imageNode.setAttribute('alt', '图片')
        divNode.appendChild(imageNode)
        document.body.appendChild(divNode)
        let viewer = new Viewer(divNode, {
          zIndex: 99999,
          hidden () {
            viewer.destroy()
            divNode.remove()
          }
        })
        imageNode.click()
      },
      // 文件格式和大小验证
      validateFileHandle (file) {
        let isJPG = ['image/jpeg', 'image/png', 'image/jpg', 'image/webp', 'image/gif'].includes(file.type)

        if (this.acceptFileType.length > 0) {
          isJPG = this.acceptFileType.includes(file.type)
        }

        if (!isJPG) {
          this.$message.error(`上传的文件格式有误，只允许 ${this.acceptFileType.join(',')} 格式!`)
          return false
        }

        let isOverMax = file.size < this.max
        if (!isOverMax) {
          this.$message.error(`上传文件大小不能超过 ${this.max / (1024 * 1024)} M!`)
          return false
        }
        return true
      },
      // 自定义上传
      upload (item) {
        let _this = this
        let file = item.file
        let formData = new FormData()

        // 判断文件格式
        let isValidate = this.validateFileHandle(file)
        if (!isValidate) {
          item.onError()
          return false
        }

        formData.append('file', file, file.name)
        if (this.postData) {
          Object.keys(this.postData).forEach(key => {
            formData.append('key', _this.postData.key)
          })
        }
        this.$http['post'](this.actionUrl, formData).then(({data: res}) => {
          if (res && res.code === 0) {
            _this.addFileUrl(res.data)
            item.onSuccess('上传成功')
          } else {
            _this.$message({
              message: res.msg,
              type: 'error'
            })
            item.onError('上传失败')
          }
        }).catch(error => {
          item.onError('上传失败')
          console.log(error)
          if (error.response) {
            // The request was made and the server responded with a status code
            // that falls out of the range of 2xx
            console.log('配时文件上传失败(' + error.response.status + ')，' + error.response.data)
          } else if (error.request) {
            // The request was made but no response was received
            // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
            // http.ClientRequest in node.js
            console.log('配时文件上传失败，服务器端无响应')
          } else {
            // Something happened in setting up the request that triggered an Error
            console.log('配时文件上传失败，请求封装失败')
          }
        })
      },
      // 图片url
      addFileUrl (url) {
        if (!url) return
        this.submitModifyHandle(this.filesArray.concat([url]))
      },
      // 向父组件提交证件修改信息，触发父组件方法
      submitModifyHandle (dataArr) {
        this.$nextTick(() => {
          if (this.outPut === 'string') {
            this.$emit('modify', dataArr.join(','))
          } else {
            this.$emit('modify', dataArr)
          }
        })
      }
    }
  }
</script>
