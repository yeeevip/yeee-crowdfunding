<template>
  <div>
    <el-popover
      ref="pListPopover"
      v-model:visible="pListVisible"
      placement="bottom-start"
      trigger="manual"
      :width="300">
      <el-tree
        :data="pList"
        :props="props"
        node-key="id"
        ref="pListTree"
        :highlight-current="true"
        :expand-on-click-node="false"
        accordion
        @current-change="pListTreeCurrentChangeHandle">
      </el-tree>
      <template #reference>
        <el-input v-model="pnm2" :readonly="true" placeholder="上级" class="inputTree" @click="togglePopover">
          <template #suffix>
            <span v-if="pid2" @click.stop="pListTreeSetDefaultHandle()" class="el-input__icon" style="cursor: pointer; color: #c0c4cc;">
              <el-icon><Close /></el-icon>
            </span>
          </template>
        </el-input>
      </template>
    </el-popover>
  </div>
</template>

<script>
import { Close } from '@element-plus/icons-vue'

export default {
  name: 'input-tree',
  components: {
    Close
  },
  props: {
    props: {
      type: Object,
      default: function () {
        return {
          label: 'name',
          children: 'children'
        }
      }
    },
    pid: {
      type: [Number, String],
      default: function () {
        return 0
      }
    },
    pnm: {
      type: String,
      default: function () {
        return ''
      }
    },
    url: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      pListVisible: false,
      pList: [],
      pid2: '',
      pnm2: ''
    }
  },
  watch: {
    pid: function (newval, oldVal) {
      console.log('pid-new: %s, old: %s', newval, oldVal)
      // console.log('pid: %s, pnm: %s', this.pid, this.pnm)
      this.pid2 = newval
    },
    pnm: function (newval, oldVal) {
      console.log('pnm-new: %s, old: %s', newval, oldVal)
      this.pnm2 = newval
    }
  },
  mounted () {
    this.pid2 = this.pid
    this.pnm2 = this.pnm
  },
  methods: {
    init () {
      // this.pListTreeSetDefaultHandle()
      this.listTreeData()
    },
    togglePopover () {
      this.pListVisible = !this.pListVisible
    },
    listTreeData () {
      let qry = this.$query.new()
      return this.$http.get(this.url + '?query=' + encodeURIComponent(this.$query.toJsonStr(qry))).then(({ data: res }) => {
        if (res.code !== 200) {
          return this.$message.error(res.message)
        }
        this.pList = res.data.result
      }).catch(() => {})
    },
    pListTreeCurrentChangeHandle (data) {
      this.pid2 = data.id
      this.pnm2 = data.name
      this.pListVisible = false
      this.submitModifyHandle()
    },
    // 父级, 设置默认值
    pListTreeSetDefaultHandle () {
      this.pid2 = ''
      this.pnm2 = ''
      this.submitModifyHandle()
    },
    // 向父组件提交修改信息，触发父组件方法
    submitModifyHandle () {
      this.$nextTick(() => {
        this.$emit('modify', this.pid2, this.pnm2)
      })
    }
  }
}
</script>
<style lang="scss">
.inputTree {
  .p-list {
    .el-input__inner,
    .el-input__suffix {
      cursor: pointer;
    }
  }
  .input-bar i {
    padding-top: 6px;
    font-size: 24px;
    color: #3a8ee6;
    transition-property: color;
    transition-duration: 0.15s;
    transition-timing-function: linear;
    transition-delay: initial;
  }
}
</style>
