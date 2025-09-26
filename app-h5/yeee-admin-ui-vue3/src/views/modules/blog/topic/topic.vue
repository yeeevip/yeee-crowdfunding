<template>
  <div class="mod-topic">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="listData()">
      <el-form-item>
        <el-input v-model="dataForm.name" placeholder="名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.status" clearable placeholder="状态" style="width: 120px;">
          <el-option label="启用" value=1 />
          <el-option label="禁用" value=0 />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button size="default" @click="listData()">查询</el-button>
        <el-button v-if="$hasPerm('blog:topic:add')" type="primary" size="default" @click="editHandle()">新增</el-button>
        <el-button v-if="$hasPerm('blog:topic:del')" type="danger" size="default" @click="delHandle()" :disabled="dataListSelections.length <= 0">删除</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border stripe v-loading="dataListLoading" :max-height="tableHeight"
              @selection-change="selectionChangeHandle" @sort-change="sortChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="name" label="名称" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="coverImg" label="封面图" sortable="custom" header-align="center" align="center">
        <template #default="scope">
          <filePreview :files="scope.row.coverImg" :imageShow=true></filePreview>
        </template>
      </el-table-column>
      <el-table-column prop="sort" label="排序" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="remark" label="说明" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="updateTime" label="修改时间" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="status" label="状态" sortable="custom" header-align="center" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.status === 1" type="success">启用</el-tag>
          <el-tag v-else-if="scope.row.status === 0" type="danger">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
        <template #default="scope">
          <el-button v-if="$hasPerm('blog:topic:info')" type="primary" link size="small" @click="infoHandle(scope.row.id)" :icon="Document" title="详情"></el-button>
          <el-button v-if="$hasPerm('blog:topic:upd')" type="primary" link size="small" @click="editHandle(scope.row.id)" :icon="Edit" title="编辑"></el-button>
          <el-button v-if="$hasPerm('blog:topic:del')" type="danger" link size="small" @click="delHandle(scope.row.id)" :icon="Delete" title="删除"></el-button>
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
  </div>
</template>

<script>
import edit from './topic-edit.vue'
import info from './topic-info.vue'
import grid from '@/mixins/grid'
import filePreview from '@/components/filePreview/index.vue'
import { Document, Edit, Delete } from '@element-plus/icons-vue'
import { markRaw } from 'vue'
export default {
  mixins: [grid],
  data () {
    return {
      module: '/manage/blog/topic/',
      gridOptions: {
        isQuery: true
      },
      defOrders: [
        {k: 'createTime', t: 'desc'}
      ],
      dataMode: {
        name: 'LK'
      },
      dataForm: {},
      // 图标组件（使用 markRaw 避免响应式包装）
      Document: markRaw(Document),
      Edit: markRaw(Edit),
      Delete: markRaw(Delete)
    }
  },
  components: {
    edit, info, filePreview
  },
  methods: {
  }
}
</script>
