<template>
  <div class="mod-qrtz">
    <el-form :inline="true" :model="dataForm" @keyup.enter="listData()">
      <el-form-item>
        <el-form-item>
          <el-input v-model="dataForm.jobName" placeholder="编码" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="dataForm.triggerState" clearable placeholder="状态" style="width: 120px;">
            <el-option label="正常" value='NORMAL' />
            <el-option label="暂停" value='PAUSED' />
          </el-select>
        </el-form-item>
        <el-button @click="listData()">查询</el-button>
        <el-button v-if="$hasPerm('sched:qrtz:add')" type="primary" @click="editHandle()">添加任务</el-button>
        <!-- <el-button size="small" @click="triggerJobBtn" :disabled="dataListSelections.length <= 0 || dataListSelections.length >1">立即运行一次</el-button>
        <el-button size="small" type="warning" @click="updJobBtn" :disabled="dataListSelections.length <= 0 || dataListSelections.length >1">更新任务</el-button>
        <el-button size="small" type="success" @click="updJobBtn" :disabled="dataListSelections.length <= 0 || dataListSelections.length >1">恢复任务</el-button>
        <el-button size="small" type="warning" @click="updJobBtn" :disabled="dataListSelections.length <= 0 || dataListSelections.length >1">暂停任务</el-button>
        <el-button size="small" type="danger" @click="deleteJobBtn" :disabled="dataListSelections.length <= 0">删除任务</el-button> -->
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border stripe v-loading="dataListLoading" :max-height="tableHeight"
              @selection-change="selectionChangeHandle" @sort-change="sortChangeHandle" style="width: 100%;">
      <el-table-column prop="jobName" label="任务编码" header-align="center" align="center"></el-table-column>
      <el-table-column prop="jobDesc" label="任务URL" header-align="center" align="center" width="300"></el-table-column>
      <el-table-column prop="cronExpression" label="时间表达式" header-align="center" align="center" width="100"></el-table-column>
      <el-table-column prop="triggerDesc" label="描述" header-align="center" align="center"></el-table-column>
      <el-table-column prop="triggerState" label="状态" header-align="center" align="center" width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.triggerState === 'PAUSED'" type="warning">暂停</el-tag>
          <el-tag v-else-if="scope.row.triggerState === 'NORMAL'" type="success">正常</el-tag>
          <el-tag v-else type="info">{{scope.row.triggerState}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="nextFireTime" label="下次触发时间" header-align="center" align="center" width="140"></el-table-column>
      <el-table-column prop="previousFireTime" label="上次触发时间" header-align="center" align="center" width="140"></el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="180">
        <template #default="scope">
          <el-button v-if="$hasPerm('sched:qrtz:info')" type="primary" link @click="infoHandle(scope.row.jobName, scope.row.jobGroup)" title="详情">
            <el-icon><Document /></el-icon>
          </el-button>
          <el-button v-if="$hasPerm('sched:qrtz:upd')" type="primary" link @click="editHandle(scope.row.jobName, scope.row.jobGroup)" title="编辑">
            <el-icon><Edit /></el-icon>
          </el-button>
          <el-button v-if="$hasPerm('sched:qrtz:del')" type="danger" link @click="delHandle(scope.row.jobName, scope.row.jobGroup)" title="删除">
            <el-icon><Delete /></el-icon>
          </el-button>
          <el-button v-if="$hasPerm('sched:qrtz:exec')" type="success" link @click="triggerHandle(scope.row.jobName, scope.row.jobGroup)" title="立刻运行一次">
            <el-icon><VideoPlay /></el-icon>
          </el-button>
          <el-button v-if="$hasPerm('sched:qrtz:resume')" type="warning" link @click="resumeHandle(scope.row.jobName, scope.row.jobGroup)" title="恢复任务">
            <el-icon><VideoPause /></el-icon>
          </el-button>
          <el-button v-if="$hasPerm('sched:qrtz:pause')" type="info" link @click="pauseHandle(scope.row.jobName, scope.row.jobGroup)" title="暂停任务">
            <el-icon><Close /></el-icon>
          </el-button>
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
    <!-- 弹窗, 新增 / 修改 -->
    <edit v-if="editVisible" ref="edit" @refreshDataList="listData"></edit>
    <!-- 弹窗, 详情 -->
    <info v-if="infoVisible" ref="info"></info>
  </div>
</template>

<script>
import { Document, Edit, Delete, VideoPlay, VideoPause, Close } from '@element-plus/icons-vue'
import { markRaw } from 'vue'
import edit from './qrtz-edit.vue'
import info from './qrtz-info.vue'
import grid from '@/mixins/grid'
export default {
  mixins: [grid],
  data () {
    return {
      module: '/sched/qrtz/',
      gridOptions: {
        isQuery: false,
        isPage: false,
        isAct: true,
        listUrl: '/sched/qrtz/listJobs'
      }
    }
  },
  components: {
    edit, info,
    Document: markRaw(Document),
    Edit: markRaw(Edit),
    Delete: markRaw(Delete),
    VideoPlay: markRaw(VideoPlay),
    VideoPause: markRaw(VideoPause),
    Close: markRaw(Close)
  },
  methods: {
    // 重写listData方法以适配特殊的API响应格式
    listData () {
      this.dataListLoading = true
      this.$http.get(this.gridOptions.listUrl, {
        params: this.dataForm
      }).then(({ data: res }) => {
        this.dataListLoading = false
        if (res.code !== 0) {
          this.dataList = []
          this.total = 0
          return this.$message.error(res.msg)
        }
        var resData = res.data.list || []
        // 客户端过滤（因为后端API不支持查询参数）
        if (this.dataForm.jobName || this.dataForm.triggerState) {
          resData = resData.filter(item => {
            let matchJobName = !this.dataForm.jobName || item.jobName.indexOf(this.dataForm.jobName) !== -1
            let matchState = !this.dataForm.triggerState || item.triggerState === this.dataForm.triggerState
            return matchJobName && matchState
          })
        }
        this.dataList = resData
        this.total = resData.length
      }).catch(() => {
        this.dataListLoading = false
      })
    },
    // 新增 / 修改
    editHandle (jobName, jobGroup) {
      this.editVisible = true
      this.$nextTick(() => {
        this.$refs.edit.init(jobName, jobGroup)
      })
    },
    // 详情
    infoHandle (jobName, jobGroup) {
      this.infoVisible = true
      this.$nextTick(() => {
        this.$refs.info.init(jobName, jobGroup)
      })
    },
    delHandle (jobName, jobGroup) {
      let config = {
        url: '/sched/qrtz/deleteJob',
        message: '删除',
        params: {
          jobName: jobName,
          jobGroup: jobGroup
        }
      }
      this.baseHandle(config)
    },
    triggerHandle (jobName, jobGroup) {
      let config = {
        url: '/sched/qrtz/triggerJob',
        message: '立刻运行一次',
        params: {
          jobName: jobName,
          jobGroup: jobGroup
        }
      }
      this.baseHandle(config)
    },
    resumeHandle (jobName, jobGroup) {
      let config = {
        url: '/sched/qrtz/resumeJob',
        message: '恢复任务',
        params: {
          jobName: jobName,
          jobGroup: jobGroup
        }
      }
      this.baseHandle(config)
    },
    pauseHandle (jobName, jobGroup) {
      let config = {
        url: '/sched/qrtz/pauseJob',
        message: '暂停任务',
        params: {
          jobName: jobName,
          jobGroup: jobGroup
        }
      }
      this.baseHandle(config)
    }
  }
}
</script>
