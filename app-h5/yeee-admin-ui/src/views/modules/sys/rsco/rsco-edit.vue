<template>
  <el-dialog
    :title="!id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="11">
          <el-form-item label="类型" prop="type">
            <el-radio-group v-model="dataForm.type" :disabled="id > 0">
              <el-radio :label=0>菜单</el-radio>
              <el-radio :label=1>功能</el-radio>
              <el-radio :label=2>伪菜单</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item prop="pid" label="上级菜单" class="menu-list">
            <el-popover v-model="menuListVisible" ref="menuListPopover" placement="bottom-start" trigger="click">
              <el-tree
                :data="menuList"
                :props="{ label: 'name', children: 'children' }"
                node-key="id"
                ref="menuListTree"
                :highlight-current="true"
                :expand-on-click-node="false"
                accordion
                @current-change="menuListTreeCurrentChangeHandle">
              </el-tree>
            </el-popover>
            <el-input v-model="dataForm.pnm" v-popover:menuListPopover :readonly="true" placeholder="上级菜单">
              <i v-if="dataForm.pid" slot="suffix" @click.stop="menuListTreeSetDefaultHandle()" class="el-icon-circle-close el-input__icon"></i>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="名称" prop="name">
            <el-input v-model="dataForm.name" placeholder="名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="排序" prop="seq">
            <el-input-number v-model="dataForm.seq" :min="1" :max="10000" label="排序"></el-input-number>
            <!-- <el-input v-model="dataForm.seq" placeholder="排序"></el-input> -->
          </el-form-item>
        </el-col>
        <el-col :span="22">
          <el-form-item v-if="dataForm.type=== 1" label="资源路径" prop="url">
            <el-input v-model="dataForm.url" placeholder="资源路径"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="22" v-if="dataForm.type !== 2">
          <el-form-item label="权限标识" prop="perm">
            <el-input v-model="dataForm.perm" placeholder="权限标识"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11" v-if="dataForm.type=== 0">
          <el-form-item prop="icon" label="图标" class="icon-list">
            <el-popover v-model="iconListVisible" ref="iconListPopover" placement="bottom-start" trigger="click" popper-class="mod-rsco-icon-popover">
              <div class="mod-rsco-icon-inner">
                <div class="mod-rsco-icon-list">
                  <el-button
                    v-for="(item, index) in iconList"
                    :key="index"
                    @click="iconListCurrentChangeHandle(item)"
                    :class="{ 'is-active': dataForm.icon === item }">
                    <svg class="icon-svg" aria-hidden="true"><use :xlink:href="`#${item}`"></use></svg>
                  </el-button>
                </div>
              </div>
            </el-popover>
            <el-input v-model="dataForm.icon" v-popover:iconListPopover :readonly="true" placeholder="图标"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="备注" prop="remark">
            <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
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
  import { getIconList } from '@/utils'

  export default {
    data () {
      return {
        id: null,
        visible: false,
        menuListVisible: false,
        menuList: [],
        iconListVisible: false,
        iconList: [],
        dataForm: {
          id: '',
          pid: null,
          name: '',
          pnm: '',
          url: '',
          perm: '',
          icon: '',
          seq: 0,
          rmks: '',
          type: 0
        },
        dataRule: {
          pnm: [
            { required: true, message: '父主键不能为空', trigger: 'blur' }
          ],
          nm: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ],
          seq: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '类型不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    watch: {
      'dataForm.type' (val) {
        this.$refs['dataForm'].clearValidate()
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = null
        this.id = id
        this.visible = true
        this.dataForm.pnm = ''
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.iconList = getIconList()
          this.listMenu().then(() => {
            if (this.id) {
              this.getInfo()
            }
          })
        })
      },
      // 获取菜单列表
      listMenu () {
        let qry = this.$query.new()
        this.$query.toW(qry, 'type', '0, 1', 'IN')
        return this.$http.get('/manage/sys-menu/list?query=' + this.$query.toEncode(qry)).then(({ data: res }) => {
          if (res.code !== 200) {
            return this.$message.error(res.message)
          }
          this.menuList = res.data.result
        }).catch(() => {})
      },
      // 获取系统权限资源信息
      getInfo () {
        this.$http.json().post('/manage/sys-menu/info', {
          id: this.id
        }).then(({data: res}) => {
          if (res.code !== 200) {
            return this.$message.error(res.message)
          }
          this.dataForm = {
            ...this.dataForm,
            ...res.data
          }
          if (!this.dataForm.pid) {
            return this.menuListTreeSetDefaultHandle()
          }
          this.$refs.menuListTree.setCurrentKey(this.dataForm.pid)
        }).catch(() => {})
      },
      menuListTreeCurrentChangeHandle (data) {
        this.dataForm.pid = data.id
        this.dataForm.pnm = data.name
        this.menuListVisible = false
      },
      // 上级菜单树, 设置默认值
      menuListTreeSetDefaultHandle () {
        this.dataForm.pid = null
        this.dataForm.pnm = ''
      },
      // 图标, 选中
      iconListCurrentChangeHandle (icon) {
        this.dataForm.icon = icon
        this.iconListVisible = false
      },
      // 表单提交
      dataFormSubmit: debounce(function () {
        console.log(this.dataForm)
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            let url = !this.id ? '/manage/sys-menu/add' : '/manage/sys-menu/upd'
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
<style lang="scss">
.mod-rsco {
  .menu-list,
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
</style>
