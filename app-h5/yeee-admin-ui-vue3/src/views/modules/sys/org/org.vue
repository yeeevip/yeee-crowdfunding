<template>
  <div class="mod-org">
    <el-form :inline="true" :model="dataForm" @keyup.enter="listData()">
      <el-form-item>
        <el-input v-model="dataForm.name" placeholder="名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="default" @click="listData()">查询</el-button>
        <el-button v-if="$hasPerm('sys:dept:add')" size="default" type="primary" @click="editHandle()">新增</el-button>
        <el-button v-if="$hasPerm('sys:dept:del')" size="default" type="danger" @click="delHandle()" :disabled="dataListSelections.length <= 0">删除</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="processedDataList" border stripe v-loading="dataListLoading" :max-height="tableHeight"
            @selection-change="selectionChangeHandle" @sort-change="sortChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <table-tree-column prop="name"
        treeKey="id"
        parentKey="pid"
        levelKey="level"
        childKey="children"
        label="名称" header-align="center" align="left"
        @toggle-expand="handleToggleExpand"></table-tree-column>
      <el-table-column prop="code" label="编码" header-align="center" align="center"></el-table-column>
      <el-table-column prop="sort" label="显示排序" header-align="center" align="center"></el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="100">
        <template #default="scope">
          <!--<el-button v-if="$hasPerm('sys:dept:info')" type="text" size="small" @click="infoHandle(scope.row.id)" :icon="Document" title="详情"></el-button>-->
          <el-button v-if="$hasPerm('sys:dept:upd')" type="text" size="small" @click="editHandle(scope.row.id)" :icon="Edit" title="编辑"></el-button>
          <el-button v-if="$hasPerm('sys:dept:del')" type="text" size="small" @click="delHandle(scope.row.id)" :icon="Delete" title="删除"></el-button>
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
      layout="->, total, sizes, prev, pager, next, jumper">
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
  import edit from './org-edit.vue'
  import info from './org-info.vue'
  import impt from './org-impt.vue'
  import grid from '@/mixins/grid'
  import { Document, Edit, Delete } from '@element-plus/icons-vue'
  import { markRaw } from 'vue'
  export default {
    mixins: [grid],
    data () {
      return {
        module: '/manage/sys-dept/',
        gridOptions: {
          isPage: false
        },
        dataForm: {},
        flatDataList: [], // 扁平化的数据列表
        // 图标组件（使用 markRaw 避免响应式包装）
        Document: markRaw(Document),
        Edit: markRaw(Edit),
        Delete: markRaw(Delete)
      }
    },
    computed: {
      processedDataList () {
        return this.flatDataList.filter(item => item._visible !== false)
      }
    },
    methods: {
      // 将树形数据转换为扁平化数据
      flattenTreeData (data, level = 1, parentExpanded = true) {
        let result = []
        if (!data || !Array.isArray(data)) {
          return result
        }

        data.forEach(item => {
          const flatItem = {
            ...item,
            level,
            _visible: parentExpanded,
            _expanded: false
          }
          result.push(flatItem)

          if (item.children && Array.isArray(item.children) && item.children.length > 0) {
            const childItems = this.flattenTreeData(item.children, level + 1, false)
            result = result.concat(childItems)
          }
        })

        return result
      },
      // 处理展开/收起
      handleToggleExpand ({ row, index, expanded }) {
        this.toggleChildren(row.id, expanded)
      },
      // 切换子节点的可见性
      toggleChildren (parentId, expanded) {
        this.flatDataList.forEach(item => {
          if (item.pid === parentId) {
            item._visible = expanded
            if (!expanded) {
              // 如果收起，同时收起所有子节点
              item._expanded = false
              this.toggleChildren(item.id, false)
            }
          }
        })
      }
    },
    watch: {
      // 监听dataList变化，重新生成扁平化数据
      dataList: {
        handler (newVal) {
          this.flatDataList = this.flattenTreeData(newVal)
        },
        immediate: true,
        deep: true
      }
    },
    components: {
      tableTreeColumn, edit, info, impt
    }
  }
</script>
