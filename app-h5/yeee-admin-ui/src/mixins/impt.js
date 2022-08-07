import * as index from '@/utils/index'

export default {
  data () {
    return {
      tableHeight: index.getClientHeight() - 260,
      // 设置属性
      module: '',                                              // 模块
      imptOptions: {
        exptCaseUrl: null,                                     // 导出Excel模板接口，API地址
        imptUrl: null                                          // 导入Excel数据接口，API地址
      },
      files: [],
      uploadDisabled: false,
      funcTip: '点击上传',
      dataList: [],
      visible: false                                           // visible状态
    }
  },
  mounted () {
    this.setUrl()
    this.setTableHeight()
    window.addEventListener('resize', this.setTableHeight)
  },
  methods: {
    init () {
      this.visible = true
      this.dataList = []
      this.files = []
      this.uploadDisabled = false
      this.funcTip = '点击上传'
    },
    setTableHeight () {
      this.$nextTick(() => {
        this.tableHeight = index.getClientHeight() - 260
      })
    },
    setUrl () {
      if (this.module) {
        if (!this.imptOptions.exptCaseUrl) {
          this.imptOptions.exptCaseUrl = this.module + 'exptCase'
        }
        if (!this.imptOptions.imptUrl) {
          this.imptOptions.imptUrl = this.module + 'impt'
        }
      }
    },
    getImptUrl () {
      return index.baseUrl() + this.imptOptions.imptUrl
    },
    // excel模板导出
    exptCaseHandle () {
      this.$nextTick(() => {
        window.open(index.baseUrl() + this.imptOptions.exptCaseUrl)
      })
    },
    uploadChangeHandle (file, fileList) {
      this.uploadDisabled = false
      this.funcTip = '点击上传'
    },
    beforeUploadHandle (file) {
      let name = file.name
      let ind = name.lastIndexOf('.')
      let type = name.substr(ind + 1)
      let isExcel = ['xlsx', 'xls'].includes(type)
      if (!isExcel) {
        this.$message.error(`上传的文件格式有误，只允许 Excel 格式!`)
        return false
      }
      this.uploadDisabled = true
      this.funcTip = '正在上传中，请耐心等候'
      return true
    },
    uploadSuccessHandle (res, file, fileList) {
      if (res && res.code === 0) {
        this.dataList = res.data.list
        let msg = '操作成功'
        let type = 'success'
        if (this.dataList.length > 0) {
          msg = '有[' + this.dataList.length + ']条数据导入失败'
          type = 'warning'
        }
        this.$message({
          message: msg,
          type: type,
          duration: 1500,
          onClose: () => {}
        })
      } else {
        this.$message.error(res.msg)
      }
    },
    uploadErrorHandle (err, file, fileList) {
      this.$message.error(err)
    }
  }
}
