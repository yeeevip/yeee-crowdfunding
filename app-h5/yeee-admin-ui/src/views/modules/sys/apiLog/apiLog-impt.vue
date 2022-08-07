<template>
  <el-dialog title="导入" class="mod-apiLog-impt"
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
          <el-table-column prop="id" label="ID" header-align="center" align="center"></el-table-column>
          <el-table-column prop="akId" label="密钥id" header-align="center" align="center"></el-table-column>
          <el-table-column prop="nm" label="接口名称" header-align="center" align="center"></el-table-column>
          <el-table-column prop="ip" label="IP" header-align="center" align="center"></el-table-column>
          <el-table-column prop="uri" label="URI" header-align="center" align="center"></el-table-column>
          <el-table-column prop="url" label="URL" header-align="center" align="center"></el-table-column>
          <el-table-column prop="rcdTm" label="记录时间" header-align="center" align="center"></el-table-column>
          <el-table-column prop="spendTime" label="运行时长" header-align="center" align="center"></el-table-column>
          <el-table-column prop="method" label="方法" header-align="center" align="center"></el-table-column>
          <el-table-column prop="userAgent" label="用户代理" header-align="center" align="center"></el-table-column>
          <el-table-column prop="channel" label="渠道" header-align="center" align="center"></el-table-column>
          <el-table-column prop="regArg" label="请求参数" header-align="center" align="center"></el-table-column>
          <el-table-column prop="result" label="返回内容" header-align="center" align="center"></el-table-column>
          <el-table-column prop="status" label="状态 (0：成功，1：失败)" header-align="center" align="center"></el-table-column>
          <el-table-column prop="tntId" label="租户ID" header-align="center" align="center"></el-table-column>
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
        module: '/sys/apiLog/',
        imptOptions: {}
      }
    }
  }
</script>
/* <style>
.mod-apiLog-impt .el-dialog{
  width: 60%
}
</style> */
