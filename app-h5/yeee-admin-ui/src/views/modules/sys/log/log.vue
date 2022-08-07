<template>
  <div class="mod-log">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="listData()">
      <el-form-item>
        <el-input v-model="dataForm.username" placeholder="用户名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.nm" placeholder="接口名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.uri" placeholder="接口路径" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.status" clearable  placeholder="状态">
          <el-option label="成功" value=0 />
          <el-option label="失败" value=1 />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="dataForm.rcdTm"
          type="daterange"
          value-format="yyyy-MM-dd"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button size="small" @click="listData()">查询</el-button>
        <!-- <el-button size="small" type="primary" @click="editHandle()">新增</el-button> -->
        <el-button v-if="$hasPerm('sys:log:del')" size="small" type="danger" @click="delHandle()" :disabled="dataListSelections.length <= 0">删除</el-button>
        <!-- <el-button size="small" @click="exptHandle()">导出</el-button>
        <el-button size="small" @click="imptHandle()">导入</el-button> -->
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border stripe v-loading="dataListLoading" :max-height="tableHeight"
            @selection-change="selectionChangeHandle" @sort-change="sortChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="username" label="用户名" sortable="custom" header-align="center" align="center"  width="120"></el-table-column>
      <el-table-column prop="ip" label="IP" sortable="custom" header-align="center" align="center" width="150"></el-table-column>
      <el-table-column prop="nm" label="接口名称" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="uri" label="接口路径" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="rcdTm" label="记录时间" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="spendTime" label="时长" sortable="custom" header-align="center" align="center"  width="80"></el-table-column>
      <el-table-column prop="status" label="状态" sortable="custom" header-align="center" align="center"  width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" size="small" type="success">成功</el-tag>
          <el-tag v-else size="small" type="danger">失败</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="100">
        <template slot-scope="scope">
          <el-button v-if="$hasPerm('sys:log:info')" type="text" size="small" @click="infoHandle(scope.row.id)" icon="el-icon-document" title="详情"></el-button>
          <!-- <el-button type="text" size="small" @click="editHandle(scope.row.id)" icon="el-icon-edit" title="编辑"></el-button> -->
          <el-button v-if="$hasPerm('sys:log:del')" type="text" size="small" @click="delHandle(scope.row.id)" icon="el-icon-delete" title="删除"></el-button>
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
  </div>
</template>

<script>
  import info from './log-info'
  import grid from '@/mixins/grid'
  export default {
    mixins: [grid],
    data () {
      return {
        module: '/sys/log/',
        gridOptions: {},
        defOrders: [
          {k: 'rcdTm', t: 'desc'}
        ],
        dataForm: {},
        dataMode: {
          rcdTm: 'BT'
        }
      }
    },
    components: {
      info
    }
  }
</script>
