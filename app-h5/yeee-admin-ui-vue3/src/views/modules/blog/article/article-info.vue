<template>
  <el-dialog title="详情" :close-on-click-modal="false" v-model="visible">
    <el-form :model="dataForm" ref="dataForm" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="分类:">
            <label>{{ dataForm.classifyId }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="标题:">
            <label>{{ dataForm.title }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="标题图:">
            <label>{{ dataForm.titleImg }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="作者:">
            <label>{{ dataForm.author }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否原创:">
            <label>{{ dataForm.oriFlag == 1 ? '是' : '否' }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="排序:">
            <label>{{ dataForm.sort }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="发布状态:">
            <label>{{ dataForm.status == 1 ? '是' : '否' }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="发布时间:">
            <label>{{ dataForm.publishTime }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="摘要:">
            <label>{{ dataForm.remark }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="创建人:">
            <label>{{ dataForm.createBy }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="创建时间:">
            <label>{{ dataForm.createTime }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="修改人:">
            <label>{{ dataForm.updateBy }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="修改时间:">
            <label>{{ dataForm.updateTime }}</label>
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
        this.dataForm = {}
        // this.$refs['dataForm'].resetFields()
        if (this.id) {
          this.getInfo()
        }
      })
    },
// 获取详情
    getInfo () {
      this.$http.json().post('/manage/blog/article/info', {
        'id': this.id
      }).then(({data: res}) => {
        if (res.code !== 200) {
          return this.$message.error(res.msg)
        }
        this.dataForm = {
          ...this.dataForm,
          ...res.data
        }
        this.dataFormOrigin = {
          ...this.dataFormOrigin,
          ...res.data
        }
      }).catch(() => {})
    }
  }
}
</script>
