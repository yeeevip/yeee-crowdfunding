<template>
  <div class="mod-accessLog">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="listData()">
      <el-form-item>
        <el-input v-model="dataForm.clientIp" placeholder="IP" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.event" clearable placeholder="操作事件" style="width: 120px;">
          <el-option label="阅读" value='read' />
          <el-option label="点击" value='click' />
          <el-option label="点赞" value='like' />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="dataForm.accessTime"
          type="datetimerange"
          value-format="yyyy-MM-dd HH:mm:ss"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button size="default" @click="listData()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border stripe v-loading="dataListLoading" :max-height="tableHeight"
              @selection-change="selectionChangeHandle" @sort-change="sortChangeHandle" style="width: 100%;">
      <el-table-column prop="clientIp" label="IP" header-align="center" align="center" width="200"></el-table-column>
      <el-table-column prop="referer" label="Referer" header-align="center" align="center" width="200"></el-table-column>
      <el-table-column prop="userAgent" label="User-Agent" header-align="center" align="center" width="200"></el-table-column>
      <el-table-column prop="remark" label="详情" header-align="center" align="center"></el-table-column>
      <el-table-column prop="accessTime" label="时间" header-align="center" align="center" width="200"></el-table-column>
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
  </div>
</template>

<script>
import grid from '@/mixins/grid'
export default {
  mixins: [grid],
  data () {
    return {
      module: '/manage/blog/accessLog/',
      gridOptions: {
        isQuery: true
      },
      defOrders: [
        {k: 'accessTime', t: 'desc'}
      ],
      dataMode: {
        accessTime: 'BT'
      },
      dataForm: {}
    }
  },
  components: {
  },
  methods: {
  }
}
</script>
