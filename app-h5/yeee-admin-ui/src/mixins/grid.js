import * as index from '@/utils/index'
import query from '@/utils/query'
// import qs from 'qs'

export default {
  data () {
    return {
      tableHeight: index.getClientHeight() - 300,
      // 设置属性
      module: '',                                              // 模块
      gridOptions: {
        isQuery: true,                                         // 是否需要通过Query查询
        isAct: true,                                           // 此页面是否在激活（进入）时，调用查询数据列表接口？
        isPage: true,                                          // 数据列表接口，是否需要分页？
        listUrl: null,                                         // 数据列表接口，API地址
        delUrl: null,                                          // 删除接口，API地址
        delKey: 'id',                                          // 删除主键
        exptUrl: null                                          // 导出接口，API地址
      },
      // 默认属性
      query: null,
      allParams: {},                                           // private只在私有参数
      params: {},
      defOrders: [],                                           // 默认排序
      dataForm: {},                                            // 查询条件
      dataMode: {},                                            // 查询模式
      dataList: [],                                            // 数据列表
      order: '',                                               // 排序，asc／desc
      orderField: '',                                          // 排序，字段
      pageNo: 1,                                               // 当前页码
      pageSize: 10,                                            // 每页数
      pageSizes: [5, 10, 20, 50, 100],
      total: 0,                                                // 总条数
      dataListLoading: false,                                  // 数据列表，loading状态
      dataListSelections: [],                                  // 数据列表，多选项
      infoVisible: false,                                      // 详情，弹窗visible状态
      editVisible: false,                                      // 新增／更新，弹窗visible状态
      imptVisible: false,                                      // 导入，弹窗visible状态
      searchStr: JSON.stringify(this.dataForm)                 // 解决查询条件变化，查询分页错误
    }
  },
  activated () {
    this.setUrl()
    this.setTableHeight()
    window.addEventListener('resize', this.setTableHeight)
    if (this.gridOptions.isAct) {
      this.listData()
    }
  },
  methods: {
    setUrl () {
      if (this.module) {
        if (!this.gridOptions.listUrl) {
          this.gridOptions.listUrl = this.module + 'page'
        }
        if (!this.gridOptions.delUrl) {
          this.gridOptions.delUrl = this.module + 'del'
        }
        if (!this.gridOptions.exptUrl) {
          this.gridOptions.exptUrl = this.module + 'expt'
        }
      }
    },
    setTableHeight () {
      this.$nextTick(() => {
        this.tableHeight = index.getClientHeight() - 245
      })
    },
    // 获取数据列表
    listData () {
      let currentSearchStr = JSON.stringify({...this.dataForm, ...this.params})
      if (currentSearchStr !== this.searchStr) {
        this.pageNo = 1
        this.searchStr = currentSearchStr
      }

      this.dataListLoading = true
      let qryUrl = this.gridOptions.listUrl

      this.allParams = []
      // 是否需要query查询
      if (this.gridOptions.isQuery) {
        let qry = query.new()
        if (this.query) {
          qry = this.query
        }
        if (this.orderField !== '') {
          query.toO(qry, this.orderField, this.order)
        } else {
          if (this.defOrders.length > 0) {
            this.defOrders.forEach(item => {
              query.toO(qry, item.k, item.t)
            })
          }
        }
        query.toF(qry, this.dataForm, this.dataMode)
        // 是否需要分页
        if (this.gridOptions.isPage) {
          query.toP(qry, this.pageNo, this.pageSize)
        }
        qryUrl += '?query=' + encodeURIComponent(query.toJsonStr(qry))
        this.allParams = {
          ...this.params
        }
      } else {
        this.allParams = {
          ...this.dataForm,
          ...this.params
        }
        if (this.gridOptions.isPage) {
          this.allParams.pageNo = this.pageNo
          this.allParams.pageSize = this.pageSize
        }
      }
      // this.$http.get(this.gridOptions.listUrl + '?query=' + encodeURIComponent(query.toJsonStr(qry)) + '&_t=' + new Date().getTime(), {
      this.$http.get(qryUrl, {
        params: this.allParams
      }).then(({ data: res }) => {
        this.dataListLoading = false
        if (res.code !== 200) {
          this.dataList = []
          this.total = 0
          return this.$message.error(res.message)
        }
        this.dataList = res.data.result
        this.total = this.gridOptions.isPage ? res.data.total : 0
      }).catch(() => {
        this.dataListLoading = false
      })
    },
    // 多选
    selectionChangeHandle (val) {
      this.dataListSelections = val
    },
    // 分页, 每页条数
    sizeChangeHandle (val) {
      this.pageNo = 1
      this.pageSize = val
      this.listData()
    },
    // 分页, 当前页
    currentChangeHandle (val) {
      this.pageNo = val
      this.listData()
    },
    // 排序
    sortChangeHandle (data) {
      if (!data.order || !data.prop) {
        this.order = ''
        this.orderField = ''
        return false
      }
      this.pageNo = 1
      // this.order = data.order.replace(/ending$/, '')
      // this.orderField = data.prop.replace(/([A-Z])/g, '_$1').toLowerCase()
      this.order = data.order.replace(/ending$/, '')
      this.orderField = data.prop
      this.listData()
    },
    // 新增 / 修改
    editHandle (id) {
      this.editVisible = true
      this.$nextTick(() => {
        if (!id) {
          id = null
        }
        this.$refs.edit.init(id)
      })
    },
    // 详情
    infoHandle (id) {
      this.infoVisible = true
      this.$nextTick(() => {
        this.$refs.info.init(id)
      })
    },
    // 导入
    imptHandle () {
      this.imptVisible = true
      this.$nextTick(() => {
        this.$refs.impt.init()
      })
    },
    // 导出
    exptHandle (id) {
      this.$nextTick(() => {
        let qryUrl = this.gridOptions.exptUrl

        this.allParams = []
        // 是否需要query查询
        if (this.gridOptions.isQuery) {
          let qry = query.new()
          if (this.query) {
            qry = this.query
          }
          if (this.orderField !== '') {
            query.toO(qry, this.orderField, this.order)
          } else {
            if (this.defOrders.length > 0) {
              this.defOrders.forEach(item => {
                query.toO(qry, item.k, item.t)
              })
            }
          }
          query.toF(qry, this.dataForm, this.dataMode)
          qryUrl += '?query=' + encodeURIComponent(query.toJsonStr(qry))
          this.allParams = {
            ...this.params
          }
        } else {
          this.allParams = {
            ...this.dataForm,
            ...this.params
          }
        }
        window.open(index.baseUrl() + qryUrl)
      })
    },
    delHandle (id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item[this.gridOptions.delKey]
      })
      this.$confirm(`确定删除?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.json().post(this.gridOptions.delUrl, {
          ids: ids
        }).then(({data: res}) => {
          if (res && res.code === 200) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 500,
              onClose: () => {
                this.listData()
              }
            })
          } else {
            this.$message.error(res.message)
          }
        })
      }).catch(() => {})
    },
    baseHandle (config) {
      this.$confirm('确定' + config.message || '' + '?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: config.url,
          method: config.method || 'get',
          params: config.params
        }).then(({data: res}) => {
          if (res && res.code === 0) {
            if (config.success) {
              config.success(res)
            }
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 500,
              onClose: () => {
                this.listData()
              }
            })
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {})
    }
  }
}
