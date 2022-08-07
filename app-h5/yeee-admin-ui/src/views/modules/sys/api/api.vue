<template>
  <div class="mod-api">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="listData()">
      <el-form-item>
        <el-input v-model="dataForm.id" placeholder="主键" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" @click="listData()">查询</el-button>
        <el-button v-if="$hasPerm('sys:api:add')" size="small" type="primary" @click="editHandle()">新增</el-button>
        <el-button v-if="$hasPerm('sys:api:del')" size="small" type="danger" @click="delHandle()" :disabled="dataListSelections.length <= 0">删除</el-button>
        <el-button v-if="$hasPerm('sys:api:expt')" size="small" @click="exptHandle()">导出</el-button>
        <el-button v-if="$hasPerm('sys:api:impt')" size="small" @click="imptHandle()">导入</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border stripe v-loading="dataListLoading" :max-height="tableHeight"
            @selection-change="selectionChangeHandle" @sort-change="sortChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="id" label="ID" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="akId" label="密钥id" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="akSecret" label="密钥secret" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="method" label="访问方法集合" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="subId" label="实体id[t]" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="vldFrTm" label="有效开始时间" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="vldToTm" label="有效结束时间" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="status" label="状态（0：正常，1：停用）" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="100">
        <template slot-scope="scope">
          <el-button v-if="$hasPerm('sys:api:info')" type="text" size="small" @click="infoHandle(scope.row.id)" icon="el-icon-document" title="详情"></el-button>
          <el-button v-if="$hasPerm('sys:api:upd')" type="text" size="small" @click="editHandle(scope.row.id)" icon="el-icon-edit" title="编辑"></el-button>
          <el-button v-if="$hasPerm('sys:api:del')" type="text" size="small" @click="delHandle(scope.row.id)" icon="el-icon-delete" title="删除"></el-button>
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
    <!-- 弹窗, 详情 -->
    <info v-if="infoVisible" ref="info"></info>
    <!-- 弹窗, 新增 / 修改 -->
    <edit v-if="editVisible" ref="edit" @refreshDataList="listData"></edit>
    <!-- 弹窗, 导入 -->
    <impt v-if="imptVisible" ref="impt" @refreshDataList="listData"></impt>
  </div>
</template>

<script>
  import info from './api-info'
  import edit from './api-edit'
  import impt from './api-impt'
  import grid from '@/mixins/grid'
  export default {
    mixins: [grid],
    data () {
      return {
        module: '/sys/api/',
        gridOptions: {},
        dataForm: {}
      }
    },
    components: {
      info, edit, impt
    }
  }
</script>
