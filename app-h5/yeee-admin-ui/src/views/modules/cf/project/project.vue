<template>
  <div class="mod-project">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="listData()">
      <el-form-item>
        <el-input v-model="dataForm.title" placeholder="标题" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.hasAudits" clearable  placeholder="审核状态">
          <el-option label="审核不通过" value=-1 />
          <el-option label="未审核" value=0 />
          <el-option label="审核通过" value=1 />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button size="small" @click="listData()">查询</el-button>
        <el-button v-if="$hasPerm('sys:user:del')" type="danger" size="small" @click="delHandle()" :disabled="dataListSelections.length <= 0">删除</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border stripe v-loading="dataListLoading" :max-height="tableHeight"
            @selection-change="selectionChangeHandle" @sort-change="sortChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="id" label="编号" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="seller.username" label="发起人" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="projectType" label="类型" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="title" label="标题" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="coverPath" label="封面" sortable="custom" header-align="center" align="center">
        <template slot-scope="scope" >
          <el-popover
            placement="right"
            width="400"
            trigger="click">
            <img  :src="getImgPath(scope.row.coverPath)" width="400px" height="400px" >
            <img  slot="reference" :src="getImgPath(scope.row.coverPath)" width="50px" height="50px" >
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="totalFundRaising" label="目标金额" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="daysRaising" label="总天数" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="hasFundRaising" label="已筹金额" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="launchDateRaising" label="发起时间" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="hasFinish" label="项目状态" sortable="custom" header-align="center" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.hasFinish === 1" size="small" type="success">完成</el-tag>
          <el-tag v-else-if="scope.row.hasFinish === -1" size="small" type="danger">失败</el-tag>
          <el-tag v-else-if="scope.row.hasFinish === 0" size="small" type="info">进行中</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="hasAudits" label="审核状态" sortable="custom" header-align="center" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.hasAudits === 0" size="small" type="info">未审核</el-tag>
          <el-tag v-if="scope.row.hasAudits === 1" size="small" type="success">通过</el-tag>
          <el-tag v-else-if="scope.row.hasAudits === -1" size="small" type="danger">拒绝</el-tag>
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
  </div>
</template>

<script>
  import grid from '@/mixins/grid'
  import * as index from '@/utils/index'
  import Vue from 'vue'
  export default {
    mixins: [grid],
    data () {
      return {
        module: '/manage/cf/project/',
        gridOptions: {
          isQuery: true
        },
        defOrders: [
          {k: 'launchDateRaising', t: 'desc'}
        ],
        // dataMode: {
        //   username: 'EQ'
        // },
        dataForm: {}
      }
    },
    components: {
    },
    methods: {
      infoHandle (id) {
        let api = index.baseUrl2()
        let token = Vue.cookie.get('yeee-manageweb-token')
        window.open(api + 'pages/admin/v1/shenhe.html?id=' + id + '&tt=' + token, '_blank')
      },
      getImgPath (path) {
        let domain = process.env.VUE_APP_BASE_HOST_URL
        return domain + path
      }
    }
  }
</script>
