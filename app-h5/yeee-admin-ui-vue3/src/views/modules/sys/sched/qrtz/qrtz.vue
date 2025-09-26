<template>
  <div class="mod-qrtz">
    <el-form :inline="true" :model="dataForm" @keyup.enter="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.jobName" placeholder="任务编码" clearable></el-input>
      </el-form-item>
        <el-form-item>
        <el-input v-model="dataForm.jobGroup" placeholder="任务组名" clearable></el-input>
        </el-form-item>
        <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="$hasPermission('sys:schedule:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="$hasPermission('sys:schedule:pause')" type="success" @click="pauseHandle()">批量暂停</el-button>
        <el-button v-if="$hasPermission('sys:schedule:resume')" type="info" @click="resumeHandle()">批量恢复</el-button>
        <el-button v-if="$hasPermission('sys:schedule:run')" type="warning" @click="runHandle()">批量立即执行</el-button>
        <el-button v-if="$hasPermission('sys:schedule:delete')" type="danger" @click="deleteHandle()">批量删除</el-button>
        <el-button v-if="$hasPermission('sys:schedule:log')" type="info" @click="logHandle()">日志列表</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="jobName"
        header-align="center"
        align="center"
        width="180"
        label="任务编码">
      </el-table-column>
      <el-table-column
        prop="jobGroup"
        header-align="center"
        align="center"
        width="120"
        label="任务组名">
      </el-table-column>
      <el-table-column
        prop="triggerName"
        header-align="center"
        align="center"
        width="180"
        label="触发器名称">
      </el-table-column>
      <el-table-column
        prop="triggerGroup"
        header-align="center"
        align="center"
        width="120"
        label="触发器组">
      </el-table-column>
      <el-table-column
        prop="cronExpression"
        header-align="center"
        align="center"
        width="150"
        label="cron表达式">
      </el-table-column>
      <el-table-column
        prop="triggerState"
        header-align="center"
        align="center"
        width="80"
        label="状态">
        <template #default="scope">
          <el-tag v-if="scope.row.triggerState === 'PAUSED'" size="small" type="danger">暂停</el-tag>
          <el-tag v-else-if="scope.row.triggerState === 'NORMAL'" size="small" type="success">正常</el-tag>
          <el-tag v-else-if="scope.row.triggerState === 'COMPLETE'" size="small" type="info">完成</el-tag>
          <el-tag v-else size="small">{{ scope.row.triggerState }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="triggerDesc"
        header-align="center"
        align="center"
        label="备注">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template #default="scope">
          <el-button v-if="$hasPermission('sys:schedule:info')" type="primary" link @click="addOrUpdateHandle(scope.row.jobName, scope.row.jobGroup)">
            <el-icon><Edit /></el-icon>
          </el-button>
          <el-button v-if="$hasPermission('sys:schedule:delete')" type="primary" link @click="deleteHandle(scope.row.jobName, scope.row.jobGroup)">
            <el-icon><Delete /></el-icon>
          </el-button>
          <el-button v-if="$hasPermission('sys:schedule:pause')" type="primary" link @click="pauseHandle(scope.row.jobName, scope.row.jobGroup)">
            <el-icon><VideoPause /></el-icon>
          </el-button>
          <el-button v-if="$hasPermission('sys:schedule:resume')" type="primary" link @click="resumeHandle(scope.row.jobName, scope.row.jobGroup)">
            <el-icon><VideoPlay /></el-icon>
          </el-button>
          <el-button v-if="$hasPermission('sys:schedule:run')" type="primary" link @click="runHandle(scope.row.jobName, scope.row.jobGroup)">
            <el-icon><CaretRight /></el-icon>
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
                   @size-change="sizeChangeHandle"
                   @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
                   :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper"
      style="margin-top: 15px; text-align: right;">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './qrtz-edit.vue'
  import { Edit, Delete, VideoPause, VideoPlay, CaretRight } from '@element-plus/icons-vue'
  import { markRaw } from 'vue'
export default {
  data () {
    return {
        dataForm: {
          jobName: '',
          jobGroup: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
    }
  },
  components: {
      AddOrUpdate,
      Edit: markRaw(Edit),
      Delete: markRaw(Delete),
      VideoPause: markRaw(VideoPause),
      VideoPlay: markRaw(VideoPlay),
      CaretRight: markRaw(CaretRight)
    },
    activated () {
      this.getDataList()
  },
  methods: {
      listData2 (page, limit, params) {
        return this.$http({
          url: this.$http.adornUrl('/manage/sys-schedule/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': page,
            'limit': limit,
            'jobName': params.jobName,
            'jobGroup': params.jobGroup
          })
        })
      },
      // 获取数据列表
      getDataList () {
      this.dataListLoading = true
        this.listData2(this.pageIndex, this.pageSize, this.dataForm).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
          this.dataList = []
            this.totalPage = 0
          }
        this.dataListLoading = false
      })
    },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
    // 新增 / 修改
      addOrUpdateHandle (jobName, jobGroup) {
        this.addOrUpdateVisible = true
      this.$nextTick(() => {
          this.$refs.addOrUpdate.init(jobName, jobGroup)
        })
      },
      // 删除
      deleteHandle (jobName, jobGroup) {
        var jobNames = jobName ? [{ 'jobName': jobName, 'jobGroup': jobGroup }] : this.dataListSelections.map(item => { return { 'jobName': item.jobName, 'jobGroup': item.jobGroup } })
        this.$confirm(`确定对选中项进行[${ jobName ? '删除' : '批量删除' }]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/manage/sys-schedule/delete'),
            method: 'post',
            data: this.$http.adornData(jobNames)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {})
      },
      // 暂停
      pauseHandle (jobName, jobGroup) {
        var jobNames = jobName ? [{ 'jobName': jobName, 'jobGroup': jobGroup }] : this.dataListSelections.map(item => { return { 'jobName': item.jobName, 'jobGroup': item.jobGroup } })
        this.$confirm(`确定对选中项进行[${ jobName ? '暂停' : '批量暂停' }]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/manage/sys-schedule/pause'),
            method: 'post',
            data: this.$http.adornData(jobNames)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {})
      },
      // 恢复
    resumeHandle (jobName, jobGroup) {
        var jobNames = jobName ? [{ 'jobName': jobName, 'jobGroup': jobGroup }] : this.dataListSelections.map(item => { return { 'jobName': item.jobName, 'jobGroup': item.jobGroup } })
        this.$confirm(`确定对选中项进行[${ jobName ? '恢复' : '批量恢复' }]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/manage/sys-schedule/resume'),
            method: 'post',
            data: this.$http.adornData(jobNames)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {})
      },
      // 立即执行
      runHandle (jobName, jobGroup) {
        var jobNames = jobName ? [{ 'jobName': jobName, 'jobGroup': jobGroup }] : this.dataListSelections.map(item => { return { 'jobName': item.jobName, 'jobGroup': item.jobGroup } })
        this.$confirm(`确定对选中项进行[${ jobName ? '立即执行' : '批量立即执行' }]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/manage/sys-schedule/run'),
            method: 'post',
            data: this.$http.adornData(jobNames)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {})
      },
      // 日志列表
      logHandle () {
        this.$router.push({ name: 'sys-scheduleLog' })
    }
  }
}
</script>
