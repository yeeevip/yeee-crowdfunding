<template>
  <el-table-column :prop="prop" v-bind="$attrs">
    <template #default="scope">
      <span v-if="scope.row" @click.prevent="toggleHandle(scope.$index, scope.row)" :style="childStyles(scope.row)" style="display: flex; align-items: center;">
        <el-icon :style="iconStyles(scope.row)" :size="8" style="margin-right: 4px; display: inline-flex; align-items: center;">
          <ArrowRight v-if="!scope.row._expanded" />
          <ArrowDown v-else />
        </el-icon>
        <span>{{ scope.row[prop] }}</span>
      </span>
    </template>
  </el-table-column>
</template>

<script>
  import { ArrowDown, ArrowRight } from '@element-plus/icons-vue'
  import isArray from 'lodash/isArray'
  import { reactive } from 'vue'
  
  export default {
    name: 'table-tree-column',
    components: {
      ArrowDown,
      ArrowRight
    },
    props: {
      prop: {
        type: String
      },
      treeKey: {
        type: String,
        default: 'id'
      },
      parentKey: {
        type: String,
        default: 'parentId'
      },
      levelKey: {
        type: String,
        default: '_level'
      },
      childKey: {
        type: String,
        default: 'children'
      }
    },
    methods: {
      childStyles (row) {
        if (!row) return {}
        return { 'padding-left': (row[this.levelKey] > 1 ? row[this.levelKey] * 10 : 0) + 'px' }
      },
      iconStyles (row) {
        if (!row) return {}
        return { 'visibility': this.hasChild(row) ? 'visible' : 'hidden' }
      },
      hasChild (row) {
        if (!row) return false
        return (isArray(row[this.childKey]) && row[this.childKey].length >= 1) || false
      },
      // 切换处理
      toggleHandle (index, row) {
        if (this.hasChild(row)) {
          // 使用 Vue 3 的响应式系统
          if (row._expanded === undefined) {
            this.$set ? this.$set(row, '_expanded', false) : (row._expanded = false)
          }
          row._expanded = !row._expanded
          // 强制更新以确保响应式
          this.$forceUpdate && this.$forceUpdate()
          // 触发父组件重新计算数据
          this.$emit('toggle-expand', { row, index, expanded: row._expanded })
        }
      },
    }
  }
</script>
