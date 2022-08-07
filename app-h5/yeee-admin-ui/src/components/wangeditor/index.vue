<template>
  <div style="border: 1px solid #ccc;">
    <Toolbar
      style="border-bottom: 1px solid #ccc"
      :editor="editor"
      :defaultConfig="toolbarConfig"
      mode="default"
    />
    <Editor
      style="height: 500px; overflow-y: hidden;"
      v-model="html"
      :defaultConfig="editorConfig"
      mode="default"
      @onCreated="onCreated"
      @onChange="updateHtml"
    />
  </div>
</template>
<style src="@wangeditor/editor/dist/css/style.css"></style>
<script>
  import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
  export default{
    name: 'wangeditor',
    data () {
      return {
        editor: null,
        html: 'Hello!!!',
        toolbarConfig: { },
        editorConfig: { placeholder: '请输入内容...' }
      }
    },
    props: ['getEditConts', 'insertImg'],
    mounted () {
      let _this = this

      let baseUrl = process.env.NODE_ENV !== 'production' && process.env.OPEN_PROXY ? '/proxyApi/' : window.SITE_CONFIG.baseUrl
      this.editorConfig.uploadImgServer = baseUrl + '/general/file/upload'

      this.editorConfig.uploadImgHooks = {
        before: function (xhr, editor, files) {
        // 图片上传之前触发
        // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，files 是选择的图片文件

        // 如果返回的结果是 {prevent: true, msg: 'xxxx'} 则表示用户放弃上传
        // return {
        //     prevent: true,
        //     msg: '放弃上传'
        // }
        },
        success: function (xhr, editor, result) {
          // 图片上传并返回结果，图片插入成功之后触发
          // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
        },
        fail: function (xhr, editor, result) {
          // 图片上传并返回结果，但图片插入错误时触发
          // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
        },
        error: function (xhr, editor) {
          // 图片上传出错时触发
          // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
        },
        timeout: function (xhr, editor) {
          // 图片上传超时时触发
          // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
        },

        // 如果服务器端返回的不是 {errno:0, data: [...]} 这种格式，可使用该配置
        // （但是，服务器端返回的必须是一个 JSON 格式字符串！！！否则会报错）
        customInsert: function (insertImg, result, editor) {
          // 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
          // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果

          // 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
          if (Object.prototype.toString.call(_this.insertImg) === '[object Function]') {
            _this.insertImg(result, insertImg, editor)
          }
          // result 必须是一个 JSON 格式字符串！！！否则报错
        }
      }
    },
    beforeDestroy () {
      const editor = this.editor
      if (editor == null) return
      editor.destroy() // 组件销毁时，及时销毁编辑器
    },
    methods: {
      init () {
        this.editorContents = ''
      },
      setEditorContent (value) {
        this.editorContents = value
        this.html = value
      },
      setEditorDisable () {
        this.editor.$textElem.attr('contenteditable', false)
      },
      setEditorEnable () {
        this.editor.$textElem.attr('contenteditable', true)
      },
      onCreated (editor) {
        this.editor = Object.seal(editor) // 一定要用 Object.seal() ，否则会报错
      },
      updateHtml (html) {
        this.editorContents = this.html
        this.getEditConts(this.html)
      }
    },
    components: {
      Editor, Toolbar
    }
  }
</script>
