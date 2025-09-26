<template>
  <el-dialog title="详情"  class="mod-news-info"
    :close-on-click-modal="false" v-model="visible">
    <el-form :model="dataForm" ref="dataForm" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="名称:">
            <label>{{ dataForm.nm }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="作者:">
            <label>{{ dataForm.author }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="赞数量:">
            <label>{{ dataForm.upQty }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="踩数量:">
            <label>{{ dataForm.downQty }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="记录时间:">
            <label>{{ dataForm.rcdTm }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="发布时间:">
            <label>{{ dataForm.releTm }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="阅读数量:">
            <label>{{ dataForm.readQty }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注:">
            <label>{{ dataForm.rmks }}</label>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="图片:">
            <!-- <img v-if = "dataForm.imgUrl !== ''" :src="dataForm.imgUrl" @click="showImage(dataForm.imgUrl)"/> -->
            <imgPreview :files="dataForm.imgUrl" fileWidth="148px"></imgPreview>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="22">
          <el-form-item label="内容:">
            <!-- <p>{{ dataForm.cont }}</p> -->
            <div v-html="dataForm.cont"></div>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="visible = false">取消</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
  import imgPreview from '@/components/imgPreview/index.vue'
  export default {
    data () {
      return {
        id: null,
        visible: false,
        dataForm: {}
      }
    },
    components: {
      imgPreview
    },
    methods: {
      init (id) {
        this.id = id
        this.visible = true
        this.$nextTick(() => {
          if (this.id) {
            this.getInfo()
          }
        })
      },
      // 获取系统新闻信息
      getInfo () {
        this.$http.get('/sys/news/info/' + this.id).then(({data: res}) => {
          if (res.code !== 0) {
            return this.$message.error(res.msg)
          }
          this.dataForm = {
            ...this.dataForm,
            ...res.data
          }
        }).catch(() => {})
      }
    }
  }
</script>
