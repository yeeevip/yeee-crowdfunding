<template>
  <el-dialog :title="!id ? '新增' : '修改'" class="mod-user-edit"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="11">
          <el-form-item label="登录帐号" prop="username">
            <el-input v-model="dataForm.username" placeholder="登录帐号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="昵称" prop="nickName">
            <el-input v-model="dataForm.nickName" placeholder="昵称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="dataForm.phone" placeholder="手机号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="dataForm.email" placeholder="邮箱"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="角色" size="mini" prop="roleIdList">
            <el-checkbox-group v-model="dataForm.roleIdList">
              <el-checkbox v-for="role in roleList" :key="role.id" :label="role.id">{{ role.name }}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="组织机构" size="mini">
            <el-tree
              :data="orgList"
              :props="{ label: 'name', children: 'children' }"
              node-key="id"
              ref="orgListTree"
              show-checkbox>
            </el-tree>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="更多参数" class="input-bar">
              <i class="el-icon-circle-plus-outline" v-show="!otherParamsVisible" @click="otherParamsVisible = true"></i>
              <i class="el-icon-remove-outline" v-show="otherParamsVisible" @click="otherParamsVisible = false"></i>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20" v-show="otherParamsVisible">
        <el-col :span="11">
          <el-form-item label="参数1" prop="arg1">
            <el-input v-model="dataForm.arg1" placeholder="参数1"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="参数2" prop="arg2">
            <el-input v-model="dataForm.arg2" placeholder="参数2"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="参数3" prop="arg3">
            <el-input v-model="dataForm.arg3" placeholder="参数3"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="参数4" prop="arg4">
            <el-input v-model="dataForm.arg4" placeholder="参数4"></el-input>
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
  import { isEmail, isMobile } from '@/utils/validate'
  export default {
    data () {
      var validatePassword = (rule, value, callback) => {
        if (!this.id && !/\S/.test(value)) {
          callback(new Error('密码不能为空'))
        } else {
          callback()
        }
      }
      var validateComfirmPassword = (rule, value, callback) => {
        if (!this.id && !/\S/.test(value)) {
          callback(new Error('确认密码不能为空'))
        } else if (this.dataForm.password !== value) {
          callback(new Error('确认密码与密码输入不一致'))
        } else {
          callback()
        }
      }
      var validateEmail = (rule, value, callback) => {
        if (value && !isEmail(value)) {
          callback(new Error('邮箱格式错误'))
        } else {
          callback()
        }
      }
      var validateMobile = (rule, value, callback) => {
        if (value && !isMobile(value)) {
          callback(new Error('手机号格式错误'))
        } else {
          callback()
        }
      }
      var validateFormDataUnique = (rule, value, callback) => {
        let qry = this.$query.new()
        this.$query.toW(qry, rule.field, value, 'EQ')
        this.$http.get('/manage/sys-user/exist?query=' + encodeURIComponent(this.$query.toJsonStr(qry))).then(({data: res}) => {
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
        otherParamsVisible: false,
        roleList: [],
        orgList: [],
        dataForm: {
          username: '',
          password: '',
          comfirmPassword: '',
          roleIdList: [],
          orgIdList: []
        },
        dataFormOrigin: [],
        dataRule: {
          username: [
            { required: true, message: '用户名不能为空', trigger: 'blur' },
            { validator: validateFormDataUnique, trigger: 'blur' }
          ],
          password: [
            { validator: validatePassword, trigger: 'blur' }
          ],
          comfirmPassword: [
            { validator: validateComfirmPassword, trigger: 'blur' }
          ],
          email: [
            { validator: validateEmail, trigger: 'blur' }
          ],
          mob: [
            { validator: validateMobile, trigger: 'blur' }
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
        this.roleList = []
        this.orgList = []
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.id) {
            this.getInfo()
          }
          this.listRole()
          this.listOrg()
        })
      },
      // 获取用户信息
      getInfo () {
        this.$http.json().post('/manage/sys-user/info', {
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
      },
      listOrg () {
        this.$http.get('/manage/sys-dept/list-set', {
          params: {
            userId: this.id
          }
        }).then(({data: res}) => {
          if (res.code !== 200) {
            return this.$message.error(res.message)
          }
          this.orgList = res.data.list
          this.$nextTick(() => {
            res.data.checkedKeys.forEach(item => this.$refs.orgListTree.setChecked(item, true))
          })
        }).catch(() => {})
      },
      listRole () {
        this.$http.get('/manage/sys-role/list-set', {
          params: {
            userId: this.id
          }
        }).then(({data: res}) => {
          if (res.code !== 200) {
            return this.$message.error(res.message)
          }
          this.roleList = res.data.list
          this.dataForm.roleIdList = res.data.checkedKeys
        }).catch(() => {})
      },
      // 表单提交
      dataFormSubmit: debounce(function () {
        // console.log(this.dataForm)
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.dataForm.orgIds = [
              ...this.$refs.orgListTree.getCheckedKeys(),
              ...this.$refs.orgListTree.getHalfCheckedKeys()
            ]
            this.dataForm.roleIds = [
              ...this.dataForm.roleIdList
            ]
            this.$http.json().post(!this.dataForm.id ? '/manage/sys-user/add' : '/manage/sys-user/upd', {...this.dataForm}).then(({data: res}) => {
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
<style lang="scss">
.mod-user-edit {
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
