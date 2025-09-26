<template>
  <div class="mod-role">
    <el-form :inline="true" :model="dataForm" @keyup.enter="listData()">
      <el-form-item>
        <el-input v-model="dataForm.code" placeholder="编码" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.name" placeholder="名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="default" @click="listData()">查询</el-button>
        <el-button v-if="$hasPerm('sys:role:add')" size="default" type="primary" @click="editHandle()">新增</el-button>
        <el-button v-if="$hasPerm('sys:role:del')" size="default" type="danger" @click="delHandle()" :disabled="dataListSelections.length <= 0">删除</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border stripe v-loading="dataListLoading" :max-height="tableHeight"
            @selection-change="selectionChangeHandle" @sort-change="sortChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="code" label="编码" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="name" label="名称" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="remark" label="备注" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="100">
        <template #default="scope">
          <el-button v-if="$hasPerm('sys:role:info')" type="text" size="small" @click="infoHandle(scope.row.id)" :icon="Document" title="详情"></el-button>
          <el-button v-if="$hasPerm('sys:role:upd')" type="text" size="small" @click="editHandle(scope.row.id)" :icon="Edit" title="编辑"></el-button>
          <el-button v-if="$hasPerm('sys:role:del')" type="text" size="small" @click="delHandle(scope.row.id)" :icon="Delete" title="删除"></el-button>
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
  import edit from './role-edit.vue'
  import info from './role-info.vue'
  import impt from './role-impt.vue'
  import grid from '@/mixins/grid'
  import { Document, Edit, Delete } from '@element-plus/icons-vue'
  import { markRaw } from 'vue'
  export default {
    mixins: [grid],
    data () {
      return {
        module: '/manage/sys-role/',
        gridOptions: {},
        // defOrders: [
        //   {k: 'crtTm', t: 'desc'}
        // ],
        dataForm: {},
        // 图标组件（使用 markRaw 避免响应式包装）
        Document: markRaw(Document),
        Edit: markRaw(Edit),
        Delete: markRaw(Delete)
      }
    },
    components: {
      edit, info, impt
    }
  }
</script>
