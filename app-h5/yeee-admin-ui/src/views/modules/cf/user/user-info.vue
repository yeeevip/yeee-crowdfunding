<template>
  <el-dialog title="详情" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" ref="dataForm" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="编号:">
            <label>{{ dataForm.id }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="用户名:">
            <label>{{ dataForm.username }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="邮箱:">
            <label>{{ dataForm.email }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="性别:">
            <label>{{ dataForm.sex == 1 ? '男' : (dataForm.sex == 2 ? '女' : '') }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="真实姓名:">
            <label>{{ dataForm.realName }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="身份证号:">
            <label>{{ dataForm.idNumber }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="年龄:">
            <label>{{ dataForm.age }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="电话:">
            <label>{{ dataForm.mobile }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="注册时间:">
            <label>{{ dataForm.dateOfRegistration }}</label>
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
      // 获取用户信息
      getInfo () {
        this.$http.json().post('/manage/cf/user/info', {
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
