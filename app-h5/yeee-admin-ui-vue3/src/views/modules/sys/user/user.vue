<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter="listData()">
      <el-form-item>
        <el-input v-model="dataForm.username" placeholder="登陆名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.phone" placeholder="手机" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="default" @click="listData()">查询</el-button>
        <el-button v-if="$hasPerm('sys:user:add')" type="primary" size="default" @click="editHandle()">新增</el-button>
        <el-button v-if="$hasPerm('sys:user:del')" type="danger" size="default" @click="delHandle()" :disabled="dataListSelections.length <= 0">删除</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border stripe v-loading="dataListLoading" :max-height="tableHeight"
            @selection-change="selectionChangeHandle" @sort-change="sortChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>


      <el-table-column prop="username" label="登陆名" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="nickName" label="昵称" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="phone" label="手机" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="email" label="邮箱" sortable="custom" header-align="center" align="center"></el-table-column>
      <!-- <el-table-column prop="status" label="状态" sortable="custom" header-align="center" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.statCd === 0" size="small" type="danger">禁用</el-tag>
          <el-tag v-else size="small">正常</el-tag>
        </template>
      </el-table-column> -->
      <el-table-column prop="createTime" label="创建时间" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="100">
        <template #default="scope">
          <el-button v-if="$hasPerm('sys:user:info')" type="text" size="small" @click="infoHandle(scope.row.id)" :icon="Document" title="详情"></el-button>
          <el-button v-if="$hasPerm('sys:user:upd')" type="text" size="small" @click="editHandle(scope.row.id)" :icon="Edit" title="编辑"></el-button>
          <el-button v-if="$hasPerm('sys:user:del')" type="text" size="small" @click="delHandle(scope.row.id)" :icon="Delete" title="删除"></el-button>
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
  import edit from './user-edit.vue'
  import info from './user-info.vue'
  import impt from './user-impt.vue'
  import grid from '@/mixins/grid'
  import { Document, Edit, Delete } from '@element-plus/icons-vue'
  import { markRaw } from 'vue'
  export default {
    mixins: [grid],
    data () {
      return {
        module: '/manage/sys-user/',
        gridOptions: {
          isQuery: true
        },
        defOrders: [
          {k: 'createTime', t: 'desc'}
        ],
        // dataMode: {
        //   username: 'EQ'
        // },
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
