<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="listData()">
      <el-form-item>
        <el-input v-model="dataForm.username" placeholder="用户名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" @click="listData()">查询</el-button>
        <el-button v-if="$hasPerm('sys:user:add')" type="primary" size="small" @click="editHandle()">新增</el-button>
        <el-button v-if="$hasPerm('sys:user:del')" type="danger" size="small" @click="delHandle()" :disabled="dataListSelections.length <= 0">删除</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border stripe v-loading="dataListLoading" :max-height="tableHeight"
            @selection-change="selectionChangeHandle" @sort-change="sortChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="id" label="编号" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="username" label="用户名" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="email" label="邮箱" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="sex" label="性别" sortable="custom" header-align="center" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.sex === 1" size="small" type="success">男</el-tag>
          <el-tag v-else-if="scope.row.sex === 2" size="small" type="danger">女</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="realName" label="真实姓名" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="idNumber" label="身份证号" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="age" label="年龄" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="mobile" label="电话" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="dateOfRegistration" label="注册时间" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="100">
        <template slot-scope="scope">
          <el-button v-if="$hasPerm('sys:user:del')" type="text" size="small" @click="infoHandle(scope.row.id)" icon="el-icon-document" title="详情"></el-button>
          <el-button v-if="$hasPerm('sys:user:upd')" type="text" size="small" @click="editHandle(scope.row.id)" icon="el-icon-edit" title="编辑"></el-button>
          <el-button v-if="$hasPerm('sys:user:del')" type="text" size="small" @click="delHandle(scope.row.id)" icon="el-icon-delete" title="删除"></el-button>
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
  </div>
</template>

<script>
  import edit from './user-edit'
  import info from './user-info'
  import grid from '@/mixins/grid'
  export default {
    mixins: [grid],
    data () {
      return {
        module: '/manage/cf/user/',
        gridOptions: {
          isQuery: true
        },
        defOrders: [
          {k: 'dateOfRegistration', t: 'desc'}
        ],
        // dataMode: {
        //   username: 'EQ'
        // },
        dataForm: {}
      }
    },
    components: {
      edit, info
    },
    methods: {
    }
  }
</script>
