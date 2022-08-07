<template>
  <el-dialog :title="!id ? '新增' : '修改'"  class="mod-org-edit"
    :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="11">
          <el-form-item label="名称" prop="nm">
            <el-input v-model="dataForm.nm" placeholder="名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item prop="pid" label="上级组织" class="p-list">
            <el-popover v-model="pListVisible" ref="pListPopover" placement="bottom-start" trigger="click">
              <el-tree
                :data="pList"
                :props="{ label: 'nm', children: 'children' }"
                node-key="id"
                ref="pListTree"
                :highlight-current="true"
                :expand-on-click-node="false"
                accordion
                @current-change="pListTreeCurrentChangeHandle">
              </el-tree>
            </el-popover>
            <el-input v-model="dataForm.pnm" v-popover:pListPopover :readonly="true" placeholder="上级组织">
              <i v-if="dataForm.pid" slot="suffix" @click.stop="pListTreeSetDefaultHandle()" class="el-icon-circle-close el-input__icon"></i>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="编码" prop="cd">
            <el-input v-model="dataForm.cd" placeholder="编码"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="排序" prop="seq">
            <el-input-number v-model="dataForm.seq" :min="1" :max="10000" label="排序"></el-input-number>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="备注" prop="rmks">
            <el-input v-model="dataForm.rmks" placeholder="备注"></el-input>
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

  export default {
    data () {
      return {
        id: null,
        visible: false,
        otherParamsVisible: false,
        pListVisible: false,
        pList: [],
        dataForm: {
          id: '',
          cd: '',
          nm: '',
          pid: '',
          pnm: '',
          seq: '',
          rmks: '',
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
          nm: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.id = id
        this.visible = true
        this.dataForm.pnm = ''
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.listP().then(() => {
            if (this.id) {
              this.getInfo()
            }
          })
        })
      },
      // 获取父级列表
      listP () {
        return this.$http.get('/sys/org/listTreeNode').then(({ data: res }) => {
          if (res.code !== 0) {
            return this.$message.error(res.msg)
          }
          this.pList = res.data.list
        }).catch(() => {})
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
          if (!this.dataForm.pid) {
            return this.pListTreeSetDefaultHandle()
          }
          this.$refs.pListTree.setCurrentKey(this.dataForm.pid)
        }).catch(() => {})
      },
      pListTreeCurrentChangeHandle (data) {
        this.dataForm.pid = data.id
        this.dataForm.pnm = data.nm
        this.pListVisible = false
      },
      // 父级, 设置默认值
      pListTreeSetDefaultHandle () {
        this.dataForm.pid = null
        this.dataForm.pnm = ''
      },
      // 表单提交
      dataFormSubmit: debounce(function () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            let url = !this.id ? '/sys/org/add' : '/sys/org/upd'
            this.$http.post(url, {...this.dataForm}).then(({data: res}) => {
              if (res && res.code === 0) {
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
                this.$message.error(res.msg)
              }
            })
          }
        })
      }, 1000, { 'leading': true, 'trailing': false })
    }
  }
</script>
<style lang="scss">
.mod-org-edit {
  .p-list {
    .el-input__inner,
    .el-input__suffix {
      cursor: pointer;
    }
  }
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