<template>
  <el-dialog title="存储配置" class="mod-oss-config" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
      <el-form-item size="mini" label="存储类型">
        <el-radio-group v-model="dataForm.type">
          <el-radio :label="1">本地上传</el-radio>
          <el-radio :label="2">阿里云</el-radio>
          <el-radio :label="3">七牛</el-radio>
          <el-radio :label="4">百度云</el-radio>
        </el-radio-group>
      </el-form-item>
      <template v-if="dataForm.type === 1">
        <el-form-item label="域名">
          <el-input v-model="dataForm.localDomain" placeholder="项目域名"></el-input>
        </el-form-item>
        <el-form-item label="路径前缀">
          <el-input v-model="dataForm.localPrefix" placeholder="不设置默认为空，如：lic"></el-input>
        </el-form-item>
        <el-form-item label="存储目录">
          <el-input v-model="dataForm.localPath" placeholder="D:\\upload"></el-input>
        </el-form-item>
      </template>
      <template v-else-if="dataForm.type === 2">
        <el-form-item label="域名">
          <el-input v-model="dataForm.aliyunDomain" placeholder="阿里云绑定的域名"></el-input>
        </el-form-item>
        <!-- <el-form-item label="路径前缀">
          <el-input v-model="dataForm.aliyunPrefix" placeholder="不设置默认为空"></el-input>
        </el-form-item> -->
        <el-form-item label="EndPoint">
          <el-input v-model="dataForm.aliyunEndPoint" placeholder="阿里云EndPoint"></el-input>
        </el-form-item>
        <el-form-item label="AccessKeyId">
          <el-input v-model="dataForm.aliyunAccessKeyId" placeholder="阿里云AccessKeyId"></el-input>
        </el-form-item>
        <el-form-item label="AccessKeySecret">
          <el-input v-model="dataForm.aliyunAccessKeySecret" placeholder="阿里云AccessKeySecret"></el-input>
        </el-form-item>
        <el-form-item label="BucketName">
          <el-input v-model="dataForm.aliyunBucketName" placeholder="阿里云BucketName"></el-input>
        </el-form-item>
      </template>
      <template v-else-if="dataForm.type === 3">
        <el-form-item label="域名">
          <el-input v-model="dataForm.qiniuDomain" placeholder="七牛绑定的域名"></el-input>
        </el-form-item>
        <!-- <el-form-item label="路径前缀">
          <el-input v-model="dataForm.qiniuPrefix" placeholder="不设置默认为空"></el-input>
        </el-form-item> -->
        <el-form-item label="AccessKey">
          <el-input v-model="dataForm.qiniuAccessKey" placeholder="七牛AccessKey"></el-input>
        </el-form-item>
        <el-form-item label="SecretKey">
          <el-input v-model="dataForm.qiniuSecretKey" placeholder="七牛SecretKey"></el-input>
        </el-form-item>
        <el-form-item label="BucketName">
          <el-input v-model="dataForm.qiniuBucketName" placeholder="七牛BucketName"></el-input>
        </el-form-item>
      </template>
      <template v-else-if="dataForm.type === 4">
        <el-form-item label="域名">
          <el-input v-model="dataForm.baiduDomain" placeholder="百度云绑定的域名"></el-input>
        </el-form-item>
        <el-form-item label="EndPoint">
          <el-input v-model="dataForm.baiduEndPoint" placeholder="百度云EndPoint"></el-input>
        </el-form-item>
        <el-form-item label="AccessKeyId">
          <el-input v-model="dataForm.baiduAccessKeyId" placeholder="百度云AccessKeyId"></el-input>
        </el-form-item>
        <el-form-item label="AccessKeySecret">
          <el-input v-model="dataForm.baiduAccessKeySecret" placeholder="百度云AccessKeySecret"></el-input>
        </el-form-item>
        <el-form-item label="BucketName">
          <el-input v-model="dataForm.baiduBucketName" placeholder="百度云BucketName"></el-input>
        </el-form-item>
      </template>
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
        visible: false,
        dataForm: {
          type: 1
        },
        dataForm2: {
          cd: 'sys-oss',
          val: ''
        },
        dataRule: {}
      }
    },
    methods: {
      init (id) {
        this.visible = true
        this.$nextTick(() => {
          // this.$refs['dataForm'].resetFields()
          this.getInfo()
        })
      },
      getInfo () {
        this.$http.get(`/sys/param/getByKey?key=${this.dataForm2.cd}`).then(({data: res}) => {
          if (res.code !== 0) {
            return this.$message.error(res.msg)
          }
          if (res.data) {
            this.dataForm = {
              ...this.dataForm,
              ...JSON.parse(res.data.val)
            }
          }
        }).catch(() => {})
      },
      // 表单提交
      dataFormSubmit: debounce(function () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.dataForm2.val = JSON.stringify(this.dataForm)
            this.$http.post('/sys/param/config', {...this.dataForm2}).then(({data: res}) => {
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

