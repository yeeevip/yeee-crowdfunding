<template>
  <el-dialog title="详情"  class="mod-api-info"
    :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" ref="dataForm" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="ID:">
            <label>{{ dataForm.id }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="密钥id:">
            <label>{{ dataForm.akId }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="密钥secret:">
            <label>{{ dataForm.akSecret }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="访问方法集合:">
            <label>{{ dataForm.method }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="实体id[t]:">
            <label>{{ dataForm.subId }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="有效开始时间:">
            <label>{{ dataForm.vldFrTm }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="有效结束时间:">
            <label>{{ dataForm.vldToTm }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态（0：正常，1：停用）:">
            <label>{{ dataForm.status }}</label>
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
          <el-form-item label="租户ID:">
            <label>{{ dataForm.tntId }}</label>
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
      // 获取系统密钥信息
      getInfo () {
        this.$http.get('/sys/api/info/' + this.id).then(({data: res}) => {
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
