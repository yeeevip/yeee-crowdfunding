<template>
  <el-dialog :title="!id ? '新增' : '修改'"  class="mod-org-edit"
    :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="11">
          <el-form-item label="名称" prop="name">
            <el-input v-model="dataForm.name" placeholder="名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item prop="pid" label="上级组织">
            <input-tree :pid="dataForm.pid" :pnm="dataForm.pnm" url="/manage/sys-dept/page" ref="inputTree" @modify="inputTreeHandle"></input-tree>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="编码" prop="code">
            <el-input v-model="dataForm.code" placeholder="编码"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="排序" prop="sort">
            <el-input-number v-model="dataForm.sort" :min="1" :max="10000" label="排序"></el-input-number>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="备注" prop="remark">
            <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
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
        <el-col :span="11">
          <el-form-item label="参数5" prop="arg5">
            <el-input v-model="dataForm.arg5" placeholder="参数5"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="参数6" prop="arg6">
            <el-input v-model="dataForm.arg6" placeholder="参数6"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="参数7" prop="arg7">
            <el-input v-model="dataForm.arg7" placeholder="参数7"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="参数8" prop="arg8">
            <el-input v-model="dataForm.arg8" placeholder="参数8"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="参数9" prop="arg9">
            <el-input v-model="dataForm.arg9" placeholder="参数9"></el-input>
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
  import inputTree from '@/components/input-tree'

  export default {
    data () {
      return {
        id: null,
        visible: false,
        otherParamsVisible: false,
        dataForm: {
          id: '',
          code: '',
          name: '',
          pid: '',
          pnm: ' ',
          seq: '',
          remark: '',
          arg1: '',
          arg2: '',
          arg3: '',
          arg4: '',
          arg5: '',
          arg6: '',
          arg7: '',
          arg8: '',
          arg9: ''
        },
        dataRule: {
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    components: {
      inputTree
    },
    methods: {
      init (id) {
        this.dataForm.id = null
        this.id = id
        this.visible = true
        this.dataForm.pnm = ''
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.$refs.inputTree.init()
          if (this.id) {
            this.getInfo()
          }
        })
      },
      inputTreeHandle (pid, pnm) {
        this.$set(this.dataForm, 'pid', pid)
        this.$set(this.dataForm, 'pnm', pnm)
      },
      // 获取系统组织信息
      getInfo () {
        this.$http.json().post('/manage/sys-dept/info', {
          id: this.id
        }).then(({data: res}) => {
          if (res.code !== 200) {
            return this.$message.error(res.message)
          }
          this.dataForm = {
            ...this.dataForm,
            ...res.data
          }
        }).catch(() => {})
      },
      // 表单提交
      dataFormSubmit: debounce(function () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            let url = !this.id ? '/manage/sys-dept/add' : '/manage/sys-dept/upd'
            this.$http.post(url, {...this.dataForm}).then(({data: res}) => {
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
