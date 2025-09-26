<template>
  <div class="mod-catItm">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="listData()">
      <el-form-item>
        <el-button size="small" @click="listData()">查询</el-button>
        <el-button v-if="$hasPerm('sys:cat:add')" size="small" type="primary" @click="editHandle()">新增</el-button>
        <el-button v-if="$hasPerm('sys:cat:del')" size="small" type="danger" @click="delHandle()" :disabled="dataListSelections.length <= 0">删除</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border stripe v-loading="dataListLoading" :max-height="tableHeight"
            @selection-change="selectionChangeHandle" @sort-change="sortChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <table-tree-column prop="name"
        treeKey="id"
        parentKey="pid"
        levelKey="level"
        childKey="children"
        label="名称" header-align="center" align="left"></table-tree-column>
      <el-table-column prop="code" label="编码" header-align="center" align="center"></el-table-column>
      <el-table-column prop="sort" label="显示排序" header-align="center" align="center"></el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="100">
        <template #default="scope">
          <el-button v-if="$hasPerm('sys:cat:info')" type="text" size="small" @click="infoHandle(scope.row.id)" :icon="Document" title="详情"></el-button>
          <el-button v-if="$hasPerm('sys:cat:upd')" type="text" size="small" @click="editHandle(scope.row.id)" :icon="Edit" title="编辑"></el-button>
          <el-button v-if="$hasPerm('sys:cat:del')" type="text" size="small" @click="delHandle(scope.row.id)" :icon="Delete" title="删除"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-if="this.gridOptions.isPage"
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageNo"
      :page-sizes="pageSizes"
      :page-size="pageSize"
      :total="total"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <edit v-if="editVisible" ref="edit" @refreshDataList="listData"></edit>
    <!-- 弹窗, 详情 -->
    <info v-if="infoVisible" ref="info"></info>
    <!-- 弹窗, 导入 -->
    <impt v-if="imptVisible" ref="impt" @refreshDataList="listData"></impt>
  </div>
</template>

<script>
  import tableTreeColumn from '@/components/table-tree-column/index.vue'
  import edit from './catItm-edit.vue'
  import info from './cat-info.vue'
  import impt from './cat-impt.vue'
  import grid from '@/mixins/grid'
  import { Document, Edit, Delete } from '@element-plus/icons-vue'
  import { markRaw } from 'vue'
  export default {
    mixins: [grid],
    props: {
      // 从路由 meta 或 props 接收数据
      catCode: {
        type: String,
        default: ''
      },
      catId: {
        type: [String, Number],
        default: ''
      },
      catName: {
        type: String,
        default: ''
      }
    },
    mounted() {
      console.log('catItm.vue mounted - 组件已加载')
      console.log('catItm.vue - 当前路由参数:', this.$route.params)
      console.log('catItm.vue - 接收到的props:', { catCode: this.catCode, catId: this.catId, catName: this.catName })
      console.log('catItm.vue - 路由meta:', this.$route.meta)
      // 初始化参数和数据
      this.initializeWithRouteParams()
    },
    data () {
      return {
        module: '/manage/sys-cat/',
        gridOptions: {
          isAct: false,
          isQuery: true,
          isPage: false,
          listUrl: '/manage/sys-cat/item-tree'
        },
        pcd: '',
        pid: null,
        pnm: null,
        dataForm: {
          tid: null
        },
        dataMode: {
          tid: 'EQ'
        },
        // 图标组件（使用 markRaw 避免响应式包装）
        Document: markRaw(Document),
        Edit: markRaw(Edit),
        Delete: markRaw(Delete)
      }
    },
    components: {
      tableTreeColumn, edit, info, impt
    },
    activated () {
      console.log('catItm.vue activated - 组件被激活')
      this.initializeWithRouteParams()
    },
    watch: {
      // 监听路由变化，确保路由参数改变时重新加载数据
      '$route' (to, from) {
        console.log('catItm.vue - 路由变化:', { from: from.path, to: to.path })
        console.log('catItm.vue - 新路由meta:', to.meta)
        this.initializeWithRouteParams()
      },
      // 监听props变化
      catId (newVal, oldVal) {
        if (newVal !== oldVal) {
          console.log('catItm.vue - catId props变化:', { old: oldVal, new: newVal })
          this.initializeWithRouteParams()
        }
      },
      catCode (newVal, oldVal) {
        if (newVal !== oldVal) {
          console.log('catItm.vue - catCode props变化:', { old: oldVal, new: newVal })
          this.initializeWithRouteParams()
        }
      },
      catName (newVal, oldVal) {
        if (newVal !== oldVal) {
          console.log('catItm.vue - catName props变化:', { old: oldVal, new: newVal })
          this.initializeWithRouteParams()
        }
      }
    },
    methods: {
      // 初始化路由参数和数据加载
      initializeWithRouteParams () {
        console.log('catItm.vue - 开始初始化路由参数')
        
        // 每次都重新获取参数，不使用缓存的值
        const newPid = this.catId || this.$route.meta?.catId || this.$route.params.pid || 0
        const newPnm = this.catName || this.$route.meta?.catName || this.$route.query.pnm || null
        const newPcd = this.catCode || this.$route.meta?.catCode || this.$route.params.pcd || ''
        
        // 检查参数是否发生变化
        const paramsChanged = this.pid !== newPid || this.pnm !== newPnm || this.pcd !== newPcd
        
        console.log('catItm.vue - 参数对比:', {
          旧参数: { pid: this.pid, pnm: this.pnm, pcd: this.pcd },
          新参数: { pid: newPid, pnm: newPnm, pcd: newPcd },
          参数是否变化: paramsChanged
        })
        
        // 更新参数
        this.pid = newPid
        this.pnm = newPnm
        this.pcd = newPcd
        this.dataForm.tid = this.pid
        
        console.log('catItm.vue - 最终使用的参数:', {
          pid: this.pid,
          pnm: this.pnm,
          pcd: this.pcd
        })
        
        // 加载数据
        this.listData()
      },
      // 新增 / 修改
      editHandle (id) {
        console.log('editHandle 被调用，参数 id:', id)
        console.log('当前权限检查结果:', this.$hasPerm('sys:cat:add'))
        console.log('设置 editVisible 为 true')
        this.editVisible = true
        console.log('editVisible 当前值:', this.editVisible)
        
        // 使用双重 nextTick 确保组件完全渲染
        this.$nextTick(() => {
          this.$nextTick(() => {
            console.log('双重 nextTick 执行，准备初始化编辑组件')
            console.log('传递参数:', { pid: this.pid, pnm: this.pnm, pcd: this.pcd })
            if (this.$refs.edit) {
              console.log('编辑组件引用存在，开始设置参数')
              this.$refs.edit.pid = this.pid
              this.$refs.edit.pnm = this.pnm
              this.$refs.edit.pcd = this.pcd
              this.$refs.edit.init(id)
              console.log('编辑组件初始化完成')
            } else {
              console.error('编辑组件引用仍然不存在，尝试延时处理')
              // 如果仍然找不到引用，尝试短暂延时
              setTimeout(() => {
                if (this.$refs.edit) {
                  console.log('延时后找到编辑组件引用')
                  this.$refs.edit.pid = this.pid
                  this.$refs.edit.pnm = this.pnm
                  this.$refs.edit.pcd = this.pcd
                  this.$refs.edit.init(id)
                } else {
                  console.error('延时后仍然找不到编辑组件引用')
                }
              }, 100)
            }
          })
        })
      }
    }
  }
</script>
