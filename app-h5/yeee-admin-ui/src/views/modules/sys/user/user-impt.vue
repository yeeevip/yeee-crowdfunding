<template>
  <el-dialog title="导入" class="mod-user-impt"
    :close-on-click-modal="false" :visible.sync="visible">
    <el-form label-width="120px">
      <el-row :gutter="24">
        <el-col :span="12">
          <el-form-item label="Excel文件上传:">
            <el-upload :action="getImptUrl()" :on-change="uploadChangeHandle" :before-upload="beforeUploadHandle"
                :on-success="uploadSuccessHandle" :on-error= "uploadErrorHandle" :file-list="files" :disabled="uploadDisabled" :show-file-list=false>
              <el-button size="small" type="primary">{{ funcTip }}</el-button>
              <span slot="tip" class="el-upload__tip yeah-upload-tip">[列表展示错误数据]</span>
            </el-upload>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Excel模板下载:">
            <el-button type="primary" size="small" @click="exptCaseHandle()">点击下载</el-button>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="24">
        <el-table :data="dataList" border stripe :max-height="tableHeight" style="width: 100%;">
          <el-table-column prop="username" label="登陆名" header-align="center" align="center"></el-table-column>
          <el-table-column prop="vldFrTm" label="时间" header-align="center" align="center"></el-table-column>
          <el-table-column prop="nickname" label="昵称" header-align="center" align="center"></el-table-column>
          <el-table-column prop="mob" label="手机" header-align="center" align="center"></el-table-column>
          <el-table-column prop="email" label="邮箱" header-align="center" align="center"></el-table-column>
          <el-table-column prop="crtTm" label="创建时间" header-align="center" align="center"></el-table-column>
        </el-table>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">关闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import impt from '@/mixins/impt'
  export default {
    mixins: [impt],
    data () {
      return {
        module: '/sys/user/',
        imptOptions: {}
      }
    }
  }
</script>
/* <style>
.mod-user-impt .el-dialog{
  width: 60%
}
</style> */
