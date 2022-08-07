<template>
  <el-dialog :title="!id ? '新增' : '修改'"  class="mod-catItm-edit"
    :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="11">
          <el-form-item prop="pid" label="父级" class="cat-list">
            <el-popover v-model="catListVisible" ref="catListPopover" placement="bottom-start" trigger="click">
              <el-tree
                :data="catList"
                :props="{ label: 'name', children: 'children' }"
                node-key="id"
                ref="catListTree"
                :highlight-current="true"
                :expand-on-click-node="false"
                accordion
                @current-change="catListTreeCurrentChangeHandle">
              </el-tree>
            </el-popover>
            <el-input v-model="dataForm.pnm" v-popover:catListPopover :readonly="true" :placeholder="dataForm.pnm">
              <i v-if="dataForm.pid" slot="suffix" @click.stop="catListTreeSetDefaultHandle()" class="el-icon-circle-close el-input__icon"></i>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="11">
          <el-form-item label="编码" prop="code">
            <el-input v-model="dataForm.code" placeholder="编码"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="名称" prop="name">
            <el-input v-model="dataForm.name" placeholder="名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="显示排序" prop="sort">
            <el-input-number v-model="dataForm.sort" :min="1" :max="100000" label="显示排序"></el-input-number>
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
        this.$http.get('/manage/sys-cat/exist?query=' + encodeURIComponent(this.$query.toJsonStr(qry))).then(({data: res}) => {
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
        pid: null,
        pnm: null,
        pcd: null,
        visible: false,
        otherParamsVisible: false,
        catListVisible: false,
        catList: [],
        dataForm: {
          id: '',
          code: '',
          name: '',
          pnm: '',
          pid: null,
          tid: null,
          sort: 0,
          busSeq: 0,
          rmks: '',
          arg1: '',
          arg2: '',
          arg3: '',
          arg4: '',
          arg5: ''
        },
        dataFormOrigin: {},
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
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          // this.dataForm.pid = this.pid
          this.dataForm.tid = this.pid
          this.dataForm.pnm = this.pnm
          this.listCat().then(() => {
            if (this.id) {
              this.getInfo()
            }
          })
        })
      },
      // 获取菜单列表
      listCat () {
        let qry = this.$query.new()
        this.$query.toW(qry, 'tid', this.pid, 'EQ')
        return this.$http.get(`/manage/sys-cat/item-tree?query=` + encodeURIComponent(this.$query.toJsonStr(qry))).then(({ data: res }) => {
          if (res.code !== 200) {
            return this.$message.error(res.message)
          }
          this.catList = res.data.result
        }).catch(() => {})
      },
      // 获取系统字典信息
      getInfo () {
        this.$http.json().post('/manage/sys-cat/info', {
          id: this.id
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
      catListTreeCurrentChangeHandle (data) {
        this.dataForm.pid = data.id
        this.dataForm.pnm = data.name
        this.dataForm.code = data.code + '.'
        this.catListVisible = false
      },
      // 上级菜单树, 设置默认值
      catListTreeSetDefaultHandle () {
        this.dataForm.pid = this.pid
        this.dataForm.pnm = this.pnm
        this.dataForm.cd = this.pcd + '.'
      },
      // 表单提交
      dataFormSubmit: debounce(function () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            let url = !this.id ? '/manage/sys-cat/add' : '/manage/sys-cat/upd'
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
<style lang="scss">
.mod-catItm {
  .cat-list,
  .icon-list {
    .el-input__inner,
    .el-input__suffix {
      cursor: pointer;
    }
  }
  &-icon-popover {
    width: 458px;
    overflow: hidden;
  }
  &-icon-inner {
    width: 478px;
    max-height: 258px;
    overflow-x: hidden;
    overflow-y: auto;
  }
  &-icon-list {
    width: 458px;
    padding: 0;
    margin: -8px 0 0 -8px;
    > .el-button {
      padding: 8px;
      margin: 8px 0 0 8px;
      > span {
        display: inline-block;
        vertical-align: middle;
        width: 18px;
        height: 18px;
        font-size: 18px;
      }
    }
  }
}
.mod-catItm-edit {
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
