<template>
  <div class="mod-rsco">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="listData()">
      <el-form-item>
        <el-input v-model="dataForm.name" placeholder="名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" @click="listData()">查询</el-button>
        <el-button v-if="$hasPerm('sys:rsco:add')" size="small" type="primary" @click="editHandle()">新增</el-button>
        <el-button v-if="$hasPerm('sys:rsco:del')" size="small" type="danger" @click="delHandle()" :disabled="dataListSelections.length <= 0">删除</el-button>
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
        label="名称" sortable="custom" header-align="center" align="left"></table-tree-column>
      <el-table-column prop="url" label="资源路径" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="perm" label="权限标识" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="icon" label="图标" sortable="custom" header-align="center" align="center"  width="80">
         <template slot-scope="scope">
            <svg class="icon-svg" aria-hidden="true"><use :xlink:href="`#${scope.row.icon}`"></use></svg>
          </template>
      </el-table-column>
      <el-table-column prop="seq" label="排序" sortable="custom" header-align="center" align="center" width="80"></el-table-column>
      <el-table-column prop="type" label="类型" sortable="custom" header-align="center" align="center"  width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type === 0" size="small" type="success">菜单</el-tag>
          <el-tag v-else-if="scope.row.type === 1" size="small" type="info">功能</el-tag>
          <el-tag v-else size="small" type="warning">伪菜单</el-tag>
        </template>
      </el-table-column>
      <!-- <el-table-column prop="rmks" label="备注" sortable="custom" header-align="center" align="center"></el-table-column> -->
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="100">
        <template slot-scope="scope">
          <el-button v-if="$hasPerm('sys:rsco:info')" type="text" size="small" @click="infoHandle(scope.row.id)" icon="el-icon-document" title="详情"></el-button>
          <el-button v-if="$hasPerm('sys:rsco:upd')" type="text" size="small" @click="editHandle(scope.row.id)" icon="el-icon-edit" title="编辑"></el-button>
          <el-button v-if="$hasPerm('sys:rsco:del')" type="text" size="small" @click="delHandle(scope.row.id)" icon="el-icon-delete" title="删除"></el-button>
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
  import tableTreeColumn from '@/components/table-tree-column'
  import edit from './rsco-edit'
  import info from './rsco-info'
  import impt from './rsco-impt'
  import grid from '@/mixins/grid'
  export default {
    mixins: [grid],
    data () {
      return {
        module: '/manage/sys-menu/',
        gridOptions: {
          isPage: false,
          listUrl: '/manage/sys-menu/list'
        },
        dataForm: {}
      }
    },
    components: {
      tableTreeColumn, edit, info, impt
    }
  }
</script>
