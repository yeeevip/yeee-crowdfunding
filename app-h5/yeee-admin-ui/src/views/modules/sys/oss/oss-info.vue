<template>
  <el-dialog title="详情"  class="mod-oss-info"
    :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" ref="dataForm" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="11">
          <el-form-item label="原文件名称:">
            <label>{{ dataForm.origNm }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="存储名称:">
            <label>{{ dataForm.storNm }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="大小:">
            <label>{{ (dataForm.size / 1024).toFixed(2) + ' KB' }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="上传时间:">
            <label>{{ dataForm.tm }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="存储地址:">
            <label>{{ dataForm.url }}</label>
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
      // 获取系统资源存储信息
      getInfo () {
        this.$http.get('/sys/oss/info/' + this.id).then(({data: res}) => {
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
