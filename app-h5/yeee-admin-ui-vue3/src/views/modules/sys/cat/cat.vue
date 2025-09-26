<template>
  <div class="mod-cat">
    <el-form :inline="true" :model="dataForm" @keyup.enter="listData()">
      <el-form-item>
        <el-input v-model="dataForm.code" placeholder="编码" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.name" placeholder="名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="default" @click="listData()">查询</el-button>
        <el-button v-if="$hasPerm('sys:cat:add')" type="primary" size="default" @click="editHandle()">新增</el-button>
        <el-button v-if="$hasPerm('sys:cat:del')" type="danger" size="default" @click="delHandle()" :disabled="dataListSelections.length <= 0">删除</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border stripe v-loading="dataListLoading" :max-height="tableHeight"
            @selection-change="selectionChangeHandle" @sort-change="sortChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="code" label="编码" sortable="custom" header-align="center" align="center">
        <template #default="scope">
          <span v-if="dataForm.tid !== '-1'">{{ scope.row.code }}</span>
          <el-button v-else type="primary" link @click="childHandle(scope.row)">{{ scope.row.code }}</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="名称" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="sort" label="显示排序" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
        <template #default="scope">
          <el-button v-if="$hasPerm('sys:cat:info')" type="primary" link size="small" @click="infoHandle(scope.row.id)" :icon="Document" title="详情"></el-button>
          <el-button v-if="$hasPerm('sys:cat:upd')" type="primary" link size="small" @click="editHandle(scope.row.id)" :icon="Edit" title="编辑"></el-button>
          <el-button v-if="$hasPerm('sys:cat:del')" type="danger" link size="small" @click="delHandle(scope.row.id)" :icon="Delete" title="删除"></el-button>
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
      layout="->, total, sizes, prev, pager, next, jumper">
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
  import edit from './cat-edit.vue'
  import info from './cat-info.vue'
  import impt from './cat-impt.vue'
  import grid from '@/mixins/grid'
  import { mainRoutes, _import } from '@/router'
  import { Document, Edit, Delete } from '@element-plus/icons-vue'
  import { markRaw } from 'vue'
  import { useCommonStore } from '@/stores/common'

  export default {
    mixins: [grid],
    setup() {
      const commonStore = useCommonStore()
      return {
        commonStore
      }
    },
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
        },
        // 图标组件（使用 markRaw 避免响应式包装）
        Document: markRaw(Document),
        Edit: markRaw(Edit),
        Delete: markRaw(Delete)
      }
    },
    components: {
      edit, info, impt
    },
    computed: {
      mainTabs: {
        get () { return this.commonStore.mainTabs },
        set (val) { this.commonStore.updateMainTabs(val) }
      },
      mainTabsActiveName: {
        get () { return this.commonStore.mainTabsActiveName },
        set (val) { this.commonStore.updateMainTabsActiveName(val) }
      },
      menuActiveName: {
        get () { return this.commonStore.menuActiveName },
        set (val) { this.commonStore.updateMenuActiveName(val) }
      }
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
        // 使用系统标准的路由格式，遵循 url.replace('/', '-') 的规则
        // 对应后端菜单配置的 sys-cat/cat/item 格式
        var routePath = `/sys-cat/cat/item-${row.id}`
        var routeName = `sys-cat/cat/item-${row.id}`
        var route = window.SITE_CONFIG['dynamicRoutes'].filter(item => item.name === routeName)[0]
        var query = { 'pnm': row.nm }
        if (route) {
          return this.$router.push({ path: routePath, query: query })
        }
        // 否则: 添加并全局变量保存, 再跳转
        // 使用固定的组件导入路径，兼容 Vite 的动态导入限制
        // 使用与路由文件相同的动态导入方式
        let componentImporter
        try {
          // 使用 Vite 支持的动态导入模式
          const modules = import.meta.glob('@/views/**/*.vue')
          const moduleKey = '/src/views/modules/sys/cat/catItm.vue'

          if (modules[moduleKey]) {
            componentImporter = modules[moduleKey]
            console.log('组件加载成功:', moduleKey)
          } else {
            console.warn('组件不存在，使用404页面:', moduleKey)
            componentImporter = () => import('@/views/common/404.vue')
          }
        } catch (error) {
          console.error('组件加载失败，使用404页面:', error)
          componentImporter = () => import('@/views/common/404.vue')
        }

        // 使用标准的路径格式，符合系统的 url.replace('/', '-') 规则
        const simplePath = routePath // 使用标准路径格式: /sys-cat-cat-item-{id}

        route = {
          path: simplePath,
          component: componentImporter,
          name: routeName,
          meta: {
            ...window.SITE_CONFIG['contentTabDefault'],
            menuId: row.id, // 使用 row.id 作为 menuId
            title: `数据字典 - ${row.name}`, // 使用 row.name 作为标题
            isTab: true, // 确保作为tab页面打开
            isDynamic: true,
            // 传递数据给组件
            catCode: row.code,
            catId: row.id,
            catName: row.name
          },
          props: true // 启用 props 模式
        }
        // Vue Router 4.x 使用 addRoute 代替 addRoutes
        // 将子路由添加到现有的主路由下
        console.log('当前路由信息:', this.$route)
        console.log('当前路由路径:', this.$route.path)
        console.log('当前路由名称:', this.$route.name)
        console.log('准备添加动态路由:', route)
        console.log('当前路由器状态:', this.$router.getRoutes().map(r => r.name))

        // 查找主路由的实际名称 - 应该是包含动态路由的那个
        const mainDynamicRoute = this.$router.getRoutes().find(r => r.name === 'main-dynamic')
        const mainRouteName = mainDynamicRoute ? 'main-dynamic' : 'main'

        console.log('找到的主路由名称:', mainRouteName)
        try {
          // 确保路由添加到正确的父路由下
          this.$router.addRoute(mainRouteName, route)
          window.SITE_CONFIG['dynamicRoutes'].push(route)

          console.log('路由添加成功，准备导航到:', route.name, routePath, query)
          console.log('新添加的路由详情:', route)
          const allRoutes = this.$router.getRoutes()
          console.log('导航后的所有路由:', allRoutes.map(r => ({ name: r.name, path: r.path })))
          console.log('查找新添加的路由:', allRoutes.find(r => r.name === route.name))

          // 检查路由的父级结构
          const newRoute = allRoutes.find(r => r.name === route.name)
          const mainDynamicRouteInfo = allRoutes.find(r => r.name === 'main-dynamic')
          if (newRoute) {
            console.log('新路由的完整信息:', {
              name: newRoute.name,
              path: newRoute.path,
              component: newRoute.component,
              meta: newRoute.meta,
              parent: newRoute.parent?.name || 'none'
            })
          }
          if (mainDynamicRouteInfo) {
            console.log('main-dynamic路由信息:', {
              name: mainDynamicRouteInfo.name,
              path: mainDynamicRouteInfo.path,
              children: mainDynamicRouteInfo.children?.length || 0,
              component: !!mainDynamicRouteInfo.component
            })
          }

          // 添加到动态路由列表中，让 main-sidebar.vue 的 routeHandle 方法处理tab管理
          // 这样可以保持与标准路由处理流程的一致性
          const existingDynamicRoute = window.SITE_CONFIG['dynamicRoutes'].find(item => item.name === route.name)
          if (!existingDynamicRoute) {
            window.SITE_CONFIG['dynamicRoutes'].push(route)
            // 同步到 sessionStorage，确保 main-sidebar.vue 能访问到
            sessionStorage.setItem('dynamicMenuRoutes', JSON.stringify(window.SITE_CONFIG['dynamicRoutes']))

            // 触发主侧边栏重新加载动态路由数据
            // 通过事件总线或者直接更新来同步状态
            window.dispatchEvent(new CustomEvent('dynamicRouteAdded', {
              detail: { route: route }
            }))
          }

          this.$router.push({ name: route.name, query: query }).then(() => {
            console.log('导航成功')
            console.log('导航后的当前路由:', this.$route)
            console.log('导航后的路径:', this.$route.path)
            console.log('导航后的参数:', this.$route.params)
            console.log('导航后的路由 meta:', this.$route.meta)
            console.log('isTab 标志:', this.$route.meta.isTab)
            console.log('contentTabDefault 配置:', window.SITE_CONFIG['contentTabDefault'])

            // 检查组件是否正确加载
            this.$nextTick(() => {
              console.log('DOM 更新后的检查')
              console.log('当前页面内容:', document.querySelector('.site-content'))
              console.log('router-view 元素:', document.querySelector('router-view'))
              console.log('当前激活的tab名称:', this.mainTabsActiveName)
              console.log('mainTabs数组:', this.mainTabs)
            })
          }).catch((err) => {
            console.error('导航失败:', err)
            console.log('尝试使用路径导航:', routePath)
            // 如果导航失败，尝试直接用路径导航
            this.$router.push({
              path: routePath,
              query: query
            })
          })
        } catch (error) {
          console.error('添加路由失败:', error)
          console.log('尝试直接导航到路径:', routePath)
          // 如果添加路由失败，尝试直接导航到路径
          this.$router.push({
            path: routePath,
            query: query
          })
        }
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
