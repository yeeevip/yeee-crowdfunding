<template>
  <div>
    <div v-for="(item, index) in filesArray" class="file-preview-item" :key="item + index" :title="item">
      <icon-svg name="file-pdf" class="filePreview-svg" v-if="(/.pdf$/).test(item.slice(item.lastIndexOf('.')))"></icon-svg>
      <img :src="item" @click="showImage(item)" :width="fileWidth" v-else-if="imageShow && (/.(jpg|jpeg|png|gif)$/).test(item.slice(item.lastIndexOf('.')))"/>
      <icon-svg name="file-image" class="filePreview-svg" v-else-if="!imageShow && (/.(jpg|jpeg|png|gif)$/).test(item.slice(item.lastIndexOf('.')))"></icon-svg>
      <icon-svg name="file-word" class="filePreview-svg" v-else-if="(/.(doc|docx|docm|dot)$/).test(item.slice(item.lastIndexOf('.')))"></icon-svg>
      <icon-svg name="file-ppt" class="filePreview-svg" v-else-if="(/.(ppt|pptx|pptm)$/).test(item.slice(item.lastIndexOf('.')))"></icon-svg>
      <icon-svg name="file-excel" class="filePreview-svg" v-else-if="(/.(xls|xlsx|xlsb|xlsm)$/).test(item.slice(item.lastIndexOf('.')))"></icon-svg>
      <icon-svg name="file" class="filePreview-svg" v-else></icon-svg>
      <el-button type="text" @click.native.prevent="showDoc(item)" :title="'点击查看文档：'+item.slice(item.lastIndexOf('/')+1)">{{item.slice(item.lastIndexOf('-')+1)}}</el-button>
    </div>
    <div style="both:clear"></div>
  </div>
</template>

<script>
import Viewer from 'viewerjs'
import 'viewerjs/dist/viewer.min.css'
export default {
  name: 'filePreview',
  props: {
    files: {
      type: [Array, String],
      default: function () {
        return []
      }
    },
    fileWidth: {
      type: String,
      default: '30px'
    },
    imageShow: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {}
  },
  computed: {
    filesArray () {
      // console.log(this.files)
      let urls = []
      if (Object.prototype.toString.apply(this.files) === '[object Array]') {
        urls = this.files
      } else if (Object.prototype.toString.apply(this.files) === '[object String]' && this.files.length > 0) {
        urls = this.files.split(',')
      }
      return urls.map(function (value, index, array) {
        return value
      })
    }
  },
  methods: {
    // filesArray () {
    //   console.log(this.files)
    //   let urls = []
    //   if (Object.prototype.toString.apply(this.files) === '[object Array]') {
    //     urls = this.files
    //   } else if (Object.prototype.toString.apply(this.files) === '[object String]' && this.files.length > 0) {
    //     urls = this.files.split(',')
    //   }
    //   return urls.map(function (value, index, array) {
    //     return value
    //   })
    // },
    showDoc (url) {
      var src = url
      if ((/.pdf$/).test(src)) {
        window.open(src, '_blank')
      } else if ((/.(doc|docx|docm|dot|ppt|pptx|pptm)$/).test(src)) {
        window.open('http://view.officeapps.live.com/op/view.aspx?src=' + encodeURIComponent(src), '_blank')
      } else if ((/.(xls|xlsx|xlsb|xlsm)$/).test(src)) {
        window.open('http://view.officeapps.live.com/op/view.aspx?src=' + encodeURIComponent(src), '_blank')
      } else if ((/.(jpg|jpeg|png|gif)$/).test(src)) {
        this.showImage(src)
      } else {
        this.downLoadFile(src)
      }
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
    // 下载非doc,excel,图片的其他类型文件
    downLoadFile (url) {
      let link = document.createElement('a')
      link.style.display = 'none'
      link.href = url
      let fileName = url.slice(url.lastIndexOf('/') + 1)
      let fileType = url.slice(url.lastIndexOf('.') + 1)
      link.setAttribute(
        'download',
        `${fileName}.${fileType}`
      )
      document.body.appendChild(link)
      link.click()
      link.remove()
    }
  }
}
</script>
<style scoped>
.file-preview-item {
  position: relative;
  padding-left: 20px;
  line-height: 0px;
  float: left;
}
.filePreview-svg {
  width: 30px;
  height: 30px
}
.inline{
  display: inline-block;
}
/* .file-preview-item img {
  width: 148px;
} */
</style>