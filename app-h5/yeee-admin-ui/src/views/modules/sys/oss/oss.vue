<template>
  <div class="mod-oss">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="listData()">
      <el-form-item>
        <el-input v-model="dataForm.origNm" placeholder="原文件名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.url" placeholder="url" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button size="small" @click="listData()">查询</el-button>
        <el-button v-if="$hasPerm('sys:oss:upload')" size="small" type="success" plain @click="uploadHandle()">上传文件</el-button>
        <el-button v-if="$hasPerm('sys:oss:upload')" size="small" type="success" plain @click="upload2Handle()">上传文件2</el-button>
        <el-button v-if="$hasPerm('sys:oss:config')" size="small" type="primary" @click="configHandle()">存储配置</el-button>
        <el-button v-if="$hasPerm('sys:oss:del')" size="small" type="danger" @click="delHandle()" :disabled="dataListSelections.length <= 0">删除</el-button>
        <el-button v-if="$hasPerm('sys:oss:expt')" size="small" @click="exptHandle()">导出</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border stripe v-loading="dataListLoading" :max-height="tableHeight"
            @selection-change="selectionChangeHandle" @sort-change="sortChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="sourceName" label="原文件名称" sortable="custom" header-align="center" align="center" width="250"></el-table-column>
      <!-- <el-table-column prop="storNm" label="存储名称" sortable="custom" header-align="center" align="center"></el-table-column> -->
      <el-table-column prop="ossUrl" label="存储地址" sortable="custom" header-align="center" align="center">
        <template slot-scope="scope">
          <filePreview :files="scope.row.ossUrl" :imageShow=true></filePreview>
        </template>
      </el-table-column>
      <el-table-column prop="size" label="大小" sortable="custom" header-align="center" align="center" width="100">
        <template slot-scope="scope">
          <label>{{ (scope.row.size / 1024).toFixed(2) + ' KB' }}</label>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="上传时间" sortable="custom" header-align="center" align="center" width="160"></el-table-column>
      <!-- <el-table-column prop="suffix" label="类型" sortable="custom" header-align="center" align="center"></el-table-column> -->
      <!-- <el-table-column prop="rmks" label="备注" sortable="custom" header-align="center" align="center"></el-table-column> -->
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="100">
        <template slot-scope="scope">
          <el-button v-if="$hasPerm('sys:oss:info')" type="text" size="small" @click="infoHandle(scope.row.id)" icon="el-icon-document" title="详情"></el-button>
          <!-- <el-button v-if="$hasPerm('sys:oss:upd')" type="text" size="small" @click="editHandle(scope.row.id)" icon="el-icon-edit" title="编辑"></el-button> -->
          <el-button v-if="$hasPerm('sys:oss:del')" type="text" size="small" @click="delHandle(scope.row.id)" icon="el-icon-delete" title="删除"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-if="this.gridOptions.isPage"
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageNo"
      :page-sizes="pageSizes"
      :page-size="pageSize"
      :total="total"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 上传 -->
    <upload v-if="uploadVisible" ref="upload" @refreshDataList="listData"></upload>
    <!-- 弹窗, 上传2 -->
    <upload2 v-if="upload2Visible" ref="upload2" @refreshDataList="listData"></upload2>
    <!-- 弹窗, 详情 -->
    <info v-if="infoVisible" ref="info"></info>
    <!-- 弹窗, 配置 -->
    <config v-if="configVisible" ref="config" @refreshDataList="listData"></config>
  </div>
</template>

<script>
  import upload from './oss-upload'
  import upload2 from './oss-upload2'
  import info from './oss-info'
  import config from './oss-config'
  import grid from '@/mixins/grid'
  import filePreview from '@/components/filePreview'
  export default {
    mixins: [grid],
    data () {
      return {
        configVisible: false,
        uploadVisible: false,
        upload2Visible: false,
        module: '/manage/sys-ossLog/',
        gridOptions: {},
        defOrders: [
          {k: 'createTime', t: 'desc'}
        ],
        dataForm: {}
      }
    },
    components: {
      upload, upload2, info, config, filePreview
    },
    methods: {
      configHandle () {
        this.configVisible = true
        this.$nextTick(() => {
          this.$refs.config.init()
        })
      },
      uploadHandle () {
        this.uploadVisible = true
        this.$nextTick(() => {
          this.$refs.upload.init()
        })
      },
      upload2Handle () {
        this.upload2Visible = true
        this.$nextTick(() => {
          this.$refs.upload2.init()
        })
      }
    }
  }
</script>
