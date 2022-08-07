<template>
  <div class="mod-apiLog">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="listData()">
      <el-form-item>
        <el-input v-model="dataForm.id" placeholder="主键" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" @click="listData()">查询</el-button>
        <el-button v-if="$hasPerm('sys:apiLog:add')" size="small" type="primary" @click="editHandle()">新增</el-button>
        <el-button v-if="$hasPerm('sys:apiLog:del')" size="small" type="danger" @click="delHandle()" :disabled="dataListSelections.length <= 0">删除</el-button>
        <el-button v-if="$hasPerm('sys:apiLog:expt')" size="small" @click="exptHandle()">导出</el-button>
        <el-button v-if="$hasPerm('sys:apiLog:impt')" size="small" @click="imptHandle()">导入</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border stripe v-loading="dataListLoading" :max-height="tableHeight"
            @selection-change="selectionChangeHandle" @sort-change="sortChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="id" label="ID" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="akId" label="密钥id" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="nm" label="接口名称" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="ip" label="IP" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="uri" label="URI" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="url" label="URL" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="rcdTm" label="记录时间" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="spendTime" label="运行时长" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="method" label="方法" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="userAgent" label="用户代理" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="channel" label="渠道" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="regArg" label="请求参数" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="result" label="返回内容" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="status" label="状态 (0：成功，1：失败)" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="tntId" label="租户ID" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="100">
        <template slot-scope="scope">
          <el-button v-if="$hasPerm('sys:apiLog:info')" type="text" size="small" @click="infoHandle(scope.row.id)" icon="el-icon-document" title="详情"></el-button>
          <el-button v-if="$hasPerm('sys:apiLog:upd')" type="text" size="small" @click="editHandle(scope.row.id)" icon="el-icon-edit" title="编辑"></el-button>
          <el-button v-if="$hasPerm('sys:apiLog:del')" type="text" size="small" @click="delHandle(scope.row.id)" icon="el-icon-delete" title="删除"></el-button>
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
  import info from './apiLog-info'
  import edit from './apiLog-edit'
  import impt from './apiLog-impt'
  import grid from '@/mixins/grid'
  export default {
    mixins: [grid],
    data () {
      return {
        module: '/sys/apiLog/',
        gridOptions: {},
        dataForm: {}
      }
    },
    components: {
      info, edit, impt
    }
  }
</script>
