<template>
  <el-sub-menu
    v-if="menu.children && menu.children.length >= 1"
    :index="menu.id + ''"
    :popper-class="'site-sidebar--' + sidebarLayoutSkin + '-popper'">
    <template #title>
      <svg class="icon-svg aui-sidebar__menu-icon" aria-hidden="true"><use :xlink:href="`#${menu.icon}`"></use></svg>
      <span>{{ menu.name }}</span>
    </template>
    <MainSidebarSubMenu
      v-for="item in menu.children"
      :key="item.id"
      :menu="item"
      :dynamicMenuRoutes="dynamicMenuRoutes">
    </MainSidebarSubMenu>
  </el-sub-menu>
  <el-menu-item v-else :index="menu.id + ''" @click="gotoRouteHandle(menu)">
    <svg class="icon-svg aui-sidebar__menu-icon" aria-hidden="true"><use :xlink:href="`#${menu.icon}`"></use></svg>
    <span>{{ menu.name }}</span>
  </el-menu-item>
</template>

<script setup lang="ts" name="MainSidebarSubMenu">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useCommonStore } from '@/stores/common'

// Props
interface Props {
  menu: {
    id: number | string
    name: string
    icon?: string
    children?: any[]
  }
  dynamicMenuRoutes: any[]
}

const props = defineProps<Props>()

// Stores and router
const commonStore = useCommonStore()
const router = useRouter()

// Computed
const sidebarLayoutSkin = computed(() => commonStore.sidebarLayoutSkin)

// Methods
const gotoRouteHandle = (menu: any) => {
  const route = props.dynamicMenuRoutes.filter(item => item.meta.menuId === menu.id)
  if (route.length >= 1) {
    router.push({ name: route[0].name })
  }
}
</script>

<style lang="scss" scoped>
.aui-sidebar__menu-icon {
  font-size: 20px;
  margin-right: 0;
  color: inherit; // 确保SVG图标继承父元素颜色
}
</style>
