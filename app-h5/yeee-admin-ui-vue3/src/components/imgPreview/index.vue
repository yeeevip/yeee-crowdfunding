<template>
  <div>
    <div v-for="(item, index) in filesArray" class="file-preview-item" :key="item + index">
      <img :src="item" @click="showImage(item)" :width="fileWidth"/>
    </div>
    <div style="both:clear"></div>
  </div>
</template>

<script>
import Viewer from 'viewerjs'
import 'viewerjs/dist/viewer.min.css'
export default {
  name: 'imgPreview',
  props: {
    files: {
      type: [Array, String],
      default: function () {
        return []
      }
    },
    fileWidth: {
      type: String,
      default: '50px'
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
    }
  }
}
</script>
<style scoped>
.file-preview-item {
  position: relative;
  padding-left: 20px;
  line-height: 25px;
  float: left;
}
/* .file-preview-item img {
  width: 148px;
} */
</style>