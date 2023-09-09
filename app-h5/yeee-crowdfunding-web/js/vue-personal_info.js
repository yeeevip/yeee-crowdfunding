function initVuePersonal() {
    new Vue({
        el: '#grzx_pinglunBox',
        data: function() {
            return {
                activeName: 'first',
                dataListLoading: false,
                dataListLoading2: false,
                dataList: [],
                dataList2: [],
                tableHeight: this.getClientHeight() - 300,
                queryForm: {
                    pageNum: 1,                                               // 当前页码
                    pageSize: 10
                },
                queryForm2: {
                    pageNum: 1,                                               // 当前页码
                    pageSize: 10
                },
                total: 0,
                total2: 0,
                pageSizes: [5, 10, 20, 50, 100],
            }
        },
        methods: {
            listData () {
                this.dataListLoading = true
                let token = localStorage.getItem('crowdfunding-token');
                var $$this = this
                HttpRequest.postJson(API_BASE_URL + '/api/cf/comment/receive', JSON.stringify(this.queryForm), {"Utoken": token ? ('Bearer ' + JSON.parse(token).token) : ''}).then(function (res) {
                    $$this.dataListLoading = false
                    $$this.dataList = res.data.result
                    $$this.total = res.data.total
                }).catch(function (res) {
                    $$this.dataList = []
                    $$this.total = 0
                    return $$this.$message.error(res.message)
                })
            },
            listData2 () {
                this.dataListLoading2 = true
                let token = localStorage.getItem('crowdfunding-token');
                var $$this = this
                HttpRequest.postJson(API_BASE_URL + '/api/cf/comment/send', JSON.stringify(this.queryForm2), {"Utoken": token ? ('Bearer ' + JSON.parse(token).token) : ''}).then(function (res) {
                    $$this.dataListLoading2 = false
                    $$this.dataList2 = res.data.result
                    $$this.total2 = res.data.total
                }).catch(function (res) {
                    $$this.dataList = []
                    $$this.total = 0
                    return $$this.$message.error(res.message)
                })
            },
            init () {
                this.setTableHeight()
                window.addEventListener('resize', this.setTableHeight)
                this.listData()
            },
            // 分页, 每页条数
            sizeChangeHandle (val) {
                this.queryForm.pageNum = 1
                this.queryForm.pageSize = val
                this.listData()
            },
            // 分页, 当前页
            currentChangeHandle (val) {
                this.queryForm.pageNum = val
                this.listData()
            },
            sizeChangeHandle2 (val) {
                this.queryForm2.pageNum = 1
                this.queryForm2.pageSize = val
                this.listData2()
            },
            currentChangeHandle2 (val) {
                this.queryForm2.pageNum = val
                this.listData2()
            },
            setTableHeight () {
                this.$nextTick(() => {
                    this.tableHeight = this.getClientHeight() - 245
                })
            },
            getClientHeight () {
                let winHeight = 0
                if (window.innerHeight) {
                    winHeight = window.innerHeight
                } else if ((document.body) && (document.body.clientHeight)) {
                    winHeight = document.body.clientHeight
                }
                return winHeight
            },
            handleClickTab(tab, event) {
                if (tab.name === 'first') {
                    this.listData()
                } else {
                    this.listData2()
                }
            }
        },
        mounted () {
            this.init()
        },
    })
}