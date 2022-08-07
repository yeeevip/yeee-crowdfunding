<template>
  <el-dialog title="详情"  class="mod-org-info"
    :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" ref="dataForm" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="名称:">
            <label>{{ dataForm.nm }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="上级:">
            <label>{{ dataForm.pnm }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="编码:">
            <label>{{ dataForm.cd }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="显示排序:">
            <label>{{ dataForm.seq }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注:">
            <label>{{ dataForm.rmks }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="参数1:">
            <label>{{ dataForm.arg1 }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="参数2:">
            <label>{{ dataForm.arg2 }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="参数3:">
            <label>{{ dataForm.arg3 }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="参数4:">
            <label>{{ dataForm.arg4 }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="参数5:">
            <label>{{ dataForm.arg5 }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="参数6:">
            <label>{{ dataForm.arg6 }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="参数7:">
            <label>{{ dataForm.arg7 }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="参数8:">
            <label>{{ dataForm.arg8 }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="参数9:">
            <label>{{ dataForm.arg9 }}</label>
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
      // 获取系统组织信息
      getInfo () {
        this.$http.get('/sys/org/info/' + this.id).then(({data: res}) => {
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
