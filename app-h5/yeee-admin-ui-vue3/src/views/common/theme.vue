<template>
  <el-form>
    <h2>布局设置</h2>
    <el-form-item label="导航条类型">
      <el-radio-group v-model="navbarLayoutType">
        <el-radio label="default" border>default</el-radio>
        <el-radio label="inverse" border>inverse</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="侧边栏皮肤">
      <el-radio-group v-model="sidebarLayoutSkin">
        <el-radio label="light" border>light</el-radio>
        <el-radio label="dark" border>dark</el-radio>
      </el-radio-group>
    </el-form-item>
  </el-form>
</template>

<script>
  import { useCommonStore } from '@/stores/common'
  
  export default {
    setup() {
      const commonStore = useCommonStore()
      
      return {
        commonStore
      }
    },
    computed: {
      navbarLayoutType: {
        get () { return this.commonStore.navbarLayoutType },
        set (val) { this.commonStore.updateNavbarLayoutType(val) }
      },
      sidebarLayoutSkin: {
        get () { return this.commonStore.sidebarLayoutSkin },
        set (val) { this.commonStore.updateSidebarLayoutSkin(val) }
      }
    }
  }
</script>

