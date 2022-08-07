<template>
  <el-dialog title="详情" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" ref="dataForm" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="名称:">
            <label>{{ dataForm.nm }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="上级名称:">
            <label>{{ dataForm.pnm }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="类型:">
            <label>{{ dataForm.type | typeFilter }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="图标:">
            <label><svg class="icon-svg" aria-hidden="true"><use :xlink:href="`#${dataForm.icon}`"></use></svg></label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="资源路径:">
            <label>{{ dataForm.url }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="权限标识:">
            <label>{{ dataForm.perm }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="排序:">
            <label>{{ dataForm.seq }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注:">
            <label>{{ dataForm.rmks }}</label>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        id: null,
        visible: false,
        dataForm: {}
      }
    },
    filters: {
      typeFilter: function (val) {
        if (val === 0) {
          return '菜单'
        } else if (val === 1) {
          return '功能'
        } else if (val === 2) {
          return '伪菜单'
        }
        return val
      }
    },
    methods: {
      init (id) {
        this.id = id
        this.visible = true
        this.$nextTick(() => {
          if (this.id) {
            this.getInfo()
          }
        })
      },
      // 获取系统权限资源信息
      getInfo () {
        this.$http.get('/sys/rsco/info/' + this.id).then(({data: res}) => {
          if (res.code !== 0) {
            return this.$message.error(res.msg)
          }
          this.dataForm = {
            ...this.dataForm,
            ...res.data
          }
        }).catch(() => {})
      }
    }
  }
</script>
