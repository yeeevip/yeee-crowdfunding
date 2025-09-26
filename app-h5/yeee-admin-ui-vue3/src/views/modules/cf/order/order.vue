<template>
  <div class="mod-order">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="listData()">
      <el-form-item>
        <el-input v-model="dataForm.code" placeholder="订单号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" @click="listData()">查询</el-button>
        <el-button v-if="$hasPerm('sys:user:del')" type="danger" size="small" @click="delHandle()" :disabled="dataListSelections.length <= 0">删除</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border stripe v-loading="dataListLoading" :max-height="tableHeight"
            @selection-change="selectionChangeHandle" @sort-change="sortChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="code" label="编号" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="projectVO.title" label="项目名称" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="payPrice" label="下单金额" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="receiveInfoVO.receiver" label="收货人" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="receiveInfoVO.address" label="收货地址" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="receiveInfoVO.phone" label="电话" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="orderDate" label="创建时间" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="hasPay" label="是否付款" sortable="custom" header-align="center" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.hasPay === 1" size="small" type="success">是</el-tag>
          <el-tag v-else-if="scope.row.hasPay === 0" size="small" type="danger">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="hasSend" label="是否发货" sortable="custom" header-align="center" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.hasSend === 1" size="small" type="success">是</el-tag>
          <el-tag v-else-if="scope.row.hasSend === 0" size="small" type="danger">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="100">
        <template slot-scope="scope">
          <el-button v-if="$hasPerm('sys:user:del')" type="text" size="small" @click="infoHandle(scope.row.id)" icon="el-icon-document" title="详情"></el-button>
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
    <!-- 弹窗, 详情 -->
    <info v-if="infoVisible" ref="info"></info>
  </div>
</template>

<script>
  import info from './order-info'
  import grid from '@/mixins/grid'
  export default {
    mixins: [grid],
    data () {
      return {
        module: '/manage/cf/order/',
        gridOptions: {
          isQuery: true
        },
        defOrders: [
          {k: 'orderDate', t: 'desc'}
        ],
        // dataMode: {
        //   username: 'EQ'
        // },
        dataForm: {}
      }
    },
    components: {
      info
    },
    methods: {
    }
  }
</script>
