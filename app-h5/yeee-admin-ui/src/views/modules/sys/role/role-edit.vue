<template>
  <el-dialog :title="!id ? '新增' : '修改'" class="mod-role-edit"
    :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="20">
          <el-form-item label="编码" prop="code">
            <el-input v-model="dataForm.code" placeholder="编码"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="20">
          <el-form-item label="名称" prop="name">
            <el-input v-model="dataForm.name" placeholder="名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="20">
          <el-form-item label="备注" prop="remark">
            <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="14">
          <el-form-item label="资源授权" size="mini">
            <el-tree
              :data="rscoList"
              :props="{ label: 'name', children: 'children' }"
              node-key="id"
              ref="rscoListTree"
              show-checkbox>
            </el-tree>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import debounce from 'lodash/debounce'

  export default {
    data () {
      var validateFormDataUnique = (rule, value, callback) => {
        let qry = this.$query.new()
        this.$query.toW(qry, rule.field, value, 'EQ')
        this.$http.get('/manage/sys-role/exist?query=' + encodeURIComponent(this.$query.toJsonStr(qry))).then(({data: res}) => {
          if (res.code !== 200) {
            callback(new Error('服务器异常,校验失败'))
          }
          if (value !== this.dataFormOrigin[rule.field] && res.data) {
            callback(new Error('已被使用'))
          } else {
            callback()
          }
        }).catch(() => {
          callback(new Error('校验失败'))
        })
      }
      return {
        id: null,
        visible: false,
        rscoList: [],
        dataForm: {
          code: '',
          name: '',
          rscoIdList: []
        },
        dataFormOrigin: [],
        dataRule: {
          code: [
            { required: true, message: '编码不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ],
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = null
        this.dataFormOrigin = {}
        this.id = id
        this.visible = true
        this.rscoList = []
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.$refs.rscoListTree.setCheckedKeys([])
          // Promise.all([
          //   this.listRsco()
          // ]).then(() => {
          //   if (this.id) {
          //     this.getInfo()
          //   }
          // })
          this.listRsco()
          if (this.id) {
            this.getInfo()
          }
        })
      },
      listRsco () {
        this.$http.get('/manage/sys-menu/list-set', {
          params: {
            roleId: this.id
          }
        }).then(({data: res}) => {
          if (res.code !== 200) {
            return this.$message.error(res.message)
          }
          this.rscoList = res.data.list
          this.$nextTick(() => {
            res.data.checkedKeys.forEach(item => this.$refs.rscoListTree.setChecked(item, true))
          })
        }).catch(() => {})
      },
      // 获取系统角色信息
      getInfo () {
        this.$http.json().post('/manage/sys-role/info', {
          'id': this.id
        }).then(({data: res}) => {
          if (res.code !== 200) {
            return this.$message.error(res.message)
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
      },
      // 表单提交
      dataFormSubmit: debounce(function () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.dataForm.rscoIdList = [
              ...this.$refs.rscoListTree.getCheckedKeys(),
              ...this.$refs.rscoListTree.getHalfCheckedKeys()
            ]

            let url = !this.id ? '/manage/sys-role/add' : '/manage/sys-role/upd'
            this.$http.json().post(url, {...this.dataForm}).then(({data: res}) => {
              if (res && res.code === 200) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(res.message)
              }
            })
          }
        })
      }, 1000, { 'leading': true, 'trailing': false })
    }
  }
</script>
<style>
.mod-role-edit .el-dialog{
  width: 45%
}
</style>
