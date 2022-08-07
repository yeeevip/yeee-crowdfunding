<template>
  <div class="mod-cat">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="listData()">
      <el-form-item>
        <el-input v-model="dataForm.code" placeholder="编码" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.name" placeholder="名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" @click="listData()">查询</el-button>
        <el-button v-if="$hasPerm('sys:cat:add')"  size="small" type="primary" @click="editHandle()">新增</el-button>
        <el-button v-if="$hasPerm('sys:cat:del')" size="small" type="danger" @click="delHandle()" :disabled="dataListSelections.length <= 0">删除</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border stripe v-loading="dataListLoading" :max-height="tableHeight"
            @selection-change="selectionChangeHandle" @sort-change="sortChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="code" label="编码" sortable="custom" header-align="center" align="center">
        <template slot-scope="scope">
          <span v-if="dataForm.tid !== '-1'">{{ scope.row.code }}</span>
          <el-button v-else type="text" @click="childHandle(scope.row)">{{ scope.row.code }}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="名称" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="sort" label="显示排序" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="100">
        <template slot-scope="scope">
          <el-button v-if="$hasPerm('sys:cat:info')" type="text" size="small" @click="infoHandle(scope.row.id)" icon="el-icon-document" title="详情"></el-button>
          <el-button v-if="$hasPerm('sys:cat:upd')" type="text" size="small" @click="editHandle(scope.row.id)" icon="el-icon-edit" title="编辑"></el-button>
          <el-button v-if="$hasPerm('sys:cat:del')" type="text" size="small" @click="delHandle(scope.row.id)" icon="el-icon-delete" title="删除"></el-button>
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
  import edit from './cat-edit'
  import info from './cat-info'
  import impt from './cat-impt'
  import grid from '@/mixins/grid'
  import { mainRoutes } from '@/router'
  export default {
    mixins: [grid],
    data () {
      return {
        module: '/manage/sys-cat/',
        gridOptions: {},
        defOrders: [
          {k: 'sort', t: 'asc'}
        ],
        dataForm: {
          tid: '-1'
        },
        dataMode: {
          tid: 'EQ'
        }
      }
    },
    components: {
      edit, info, impt
    },
    // activated () {
    //   // 通过路由参数pid, 控制列表请求操作
    //   this.dataForm.pid = this.$route.params.pid || null
    //   if (this.dataForm.pid !== null) {
    //     this.gridOptions.listUrl = '/sys/dict/list'
    //     this.gridOptions.isPage = false
    //     this.dataForm.cd = this.$route.params.cd || ''
    //   }
    //   this.listData()
    // },
    methods: {
      // 子级
      childHandle (row) {
        // 组装路由名称, 并判断是否已添加, 如是: 则直接跳转
        var routeName = `${this.$route.name}Itm__${row.id}_${row.code}_${row.name}`
        var route = window.SITE_CONFIG['dynamicRoutes'].filter(item => item.name === routeName)[0]
        var params = { 'pid': row.id, 'pnm': row.nm, 'pcd': row.code }
        if (route) {
          return this.$router.push({ name: routeName, params: params })
        }
        // 否则: 添加并全局变量保存, 再跳转
        route = {
          path: routeName,
          component: () => import(`@/views/modules/${this.$route.name.replace(/-/g, '/')}Itm`),
          name: routeName,
          meta: {
            ...window.SITE_CONFIG['contentTabDefault'],
            menuId: this.$route.meta.menuId,
            title: `${this.$route.meta.title} - ${row.code}`
          }
        }
        this.$router.addRoutes([
          {
            ...mainRoutes,
            name: `main-dynamic__${route.name}`,
            children: [route]
          }
        ])
        window.SITE_CONFIG['dynamicRoutes'].push(route)
        this.$router.push({ name: route.name, params: params })
      }
      // 新增 / 修改
      // addOrUpdateHandle (row = {}) {
      //   this.addOrUpdateVisible = true
      //   this.$nextTick(() => {
      //     this.$refs.addOrUpdate.dataForm.id = row.id
      //     this.$refs.addOrUpdate.dataForm.pid = this.dataForm.pid
      //     this.$refs.addOrUpdate.dataForm.dictType = row.dictType || this.dataForm.dictType || ''
      //     this.$refs.addOrUpdate.init()
      //   })
      // }
    }
  }
</script>
