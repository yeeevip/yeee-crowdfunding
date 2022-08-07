<template>
  <el-dialog title="详情"  class="mod-apiLog-info"
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
          <el-form-item label="接口名称:">
            <label>{{ dataForm.nm }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="IP:">
            <label>{{ dataForm.ip }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="URI:">
            <label>{{ dataForm.uri }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="URL:">
            <label>{{ dataForm.url }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="记录时间:">
            <label>{{ dataForm.rcdTm }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="运行时长:">
            <label>{{ dataForm.spendTime }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="方法:">
            <label>{{ dataForm.method }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="用户代理:">
            <label>{{ dataForm.userAgent }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="渠道:">
            <label>{{ dataForm.channel }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="请求参数:">
            <label>{{ dataForm.regArg }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="返回内容:">
            <label>{{ dataForm.result }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态 (0：成功，1：失败):">
            <label>{{ dataForm.status }}</label>
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
      // 获取系统Api日志信息
      getInfo () {
        this.$http.get('/sys/apiLog/info/' + this.id).then(({data: res}) => {
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
