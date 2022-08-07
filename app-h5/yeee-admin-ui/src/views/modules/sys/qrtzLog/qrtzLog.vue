<template>
  <div class="mod-qrtzLog">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="listData()">
      <el-form-item>
        <el-input v-model="dataForm.jobKey" placeholder="任务标识" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.url" placeholder="url" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.rmks" placeholder="备注" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.status" clearable  placeholder="状态">
          <el-option label="成功" value="0"/>
          <el-option label="失败" value="1"/>
          <el-option label="异常" value="2"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="dataForm.preFireTm"
          type="daterange"
          value-format="yyyy-MM-dd"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button size="small" @click="listData()">查询</el-button>
        <el-button v-if="$hasPerm('sys:qrtzLog:add')" size="small" type="primary" @click="editHandle()">新增</el-button>
        <el-button v-if="$hasPerm('sys:qrtzLog:del')" size="small" type="danger" @click="delHandle()" :disabled="dataListSelections.length <= 0">删除</el-button>
        <el-button v-if="$hasPerm('sys:qrtzLog:expt')" size="small" @click="exptHandle()">导出</el-button>
        <el-button v-if="$hasPerm('sys:qrtzLog:impt')" size="small" @click="imptHandle()">导入</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border stripe v-loading="dataListLoading" :max-height="tableHeight" 
            @selection-change="selectionChangeHandle" @sort-change="sortChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="url" label="url" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="jobKey" label="任务标识" sortable="custom" header-align="center" align="center" width="180"></el-table-column>
      <!-- <el-table-column prop="rmks" label="备注" sortable="custom" header-align="center" align="center"></el-table-column> -->
      <el-table-column prop="spendTime" label="时长" sortable="custom" header-align="center" align="center" width="80"></el-table-column>
      <el-table-column prop="nextFireTm" label="下次执行时间" sortable="custom" header-align="center" align="center" width="150"></el-table-column>
      <el-table-column prop="preFireTm" label="执行时间" sortable="custom" header-align="center" align="center" width="150"></el-table-column>
      <el-table-column prop="status" label="状态" sortable="custom" header-align="center" align="center" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" size="small">成功</el-tag>
          <el-tag v-else-if="scope.row.status === 1" size="small" type="danger">失败</el-tag>
          <el-tag v-else size="small" type="danger">异常</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="100">
        <template slot-scope="scope">
          <el-button v-if="$hasPerm('sys:qrtzLog:info')" type="text" size="small" @click="infoHandle(scope.row.id)" icon="el-icon-document" title="详情"></el-button>
          <el-button v-if="$hasPerm('sys:qrtzLog:del')" type="text" size="small" @click="delHandle(scope.row.id)" icon="el-icon-delete" title="删除"></el-button>
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
  import info from './qrtzLog-info'
  import grid from '@/mixins/grid'
  export default {
    mixins: [grid],
    data () {
      return {
        module: '/sys/qrtzLog/',
        gridOptions: {},
        dataForm: {},
        defOrders: [
          {k: 'preFireTm', t: 'desc'}
        ],
        dataMode: {
          preFireTm: 'BT'
        }
      }
    },
    components: {
      info
    }
  }
</script>
