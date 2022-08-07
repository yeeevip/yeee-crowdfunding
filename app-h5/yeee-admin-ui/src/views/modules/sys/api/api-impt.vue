<template>
  <el-dialog title="导入" class="mod-api-impt"
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
          <el-table-column prop="akSecret" label="密钥secret" header-align="center" align="center"></el-table-column>
          <el-table-column prop="method" label="访问方法集合" header-align="center" align="center"></el-table-column>
          <el-table-column prop="subId" label="实体id[t]" header-align="center" align="center"></el-table-column>
          <el-table-column prop="vldFrTm" label="有效开始时间" header-align="center" align="center"></el-table-column>
          <el-table-column prop="vldToTm" label="有效结束时间" header-align="center" align="center"></el-table-column>
          <el-table-column prop="status" label="状态（0：正常，1：停用）" header-align="center" align="center"></el-table-column>
          <el-table-column prop="rmks" label="备注" header-align="center" align="center"></el-table-column>
          <el-table-column prop="arg1" label="参数1" header-align="center" align="center"></el-table-column>
          <el-table-column prop="arg2" label="参数2" header-align="center" align="center"></el-table-column>
          <el-table-column prop="arg3" label="参数3" header-align="center" align="center"></el-table-column>
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
        module: '/sys/api/',
        imptOptions: {}
      }
    }
  }
</script>
/* <style>
.mod-api-impt .el-dialog{
  width: 60%
}
</style> */
