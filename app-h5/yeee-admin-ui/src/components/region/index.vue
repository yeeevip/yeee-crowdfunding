<template>
  <div>
    <el-cascader
      :value="regionArray"
      @active-item-change="regionItemChangeHandle"
      @change="regionChangeHandle"
      :options="regionOptions"
      :props="props"
      clearable
    ></el-cascader>
  </div>
</template>

<script>
export default {
  name: 'region',
  props: {
    codes: {
      type: [Array, String],
      default: function () {
        return []
      }
    },
    props: {
      type: Object,
      default: function () {
        return {
          value: 'id',
          label: 'nm',
          children: 'children'
        }
      }
    },
    outPut: {
      type: String,
      default: 'string'   // 可以选择string, array
    }
  },
  data () {
    return {
      regionOptions: []
    }
  },
  computed: {
    regionArray () {
      let regions = []
      if (Object.prototype.toString.apply(this.codes) === '[object Array]') {
        regions = this.codes
      } else if (Object.prototype.toString.apply(this.codes) === '[object String]' && this.codes.length > 0) {
        let regionsTemp = this.codes.split('-')
        regionsTemp.forEach(function (data, index, arr) {
          regions.push(+data)
        })
      }
      // console.log(regions)
      return regions
    }
  },
  watch: {
    codes: function (newval, oldVal) {
      // console.log('new: %s, old: %s', newval, oldVal)
      this.defaultRegionHandle()
    }
  },
  mounted () {
    this.listProv()
  },
  methods: {
    regions () {
      let regions = []
      if (Object.prototype.toString.apply(this.codes) === '[object Array]') {
        regions = this.codes
      } else if (Object.prototype.toString.apply(this.codes) === '[object String]' && this.codes.length > 0) {
        let regionsTemp = this.codes.split('-')
        regionsTemp.forEach(function (data, index, arr) {
          regions.push(+data)
        })
      }
      return regions
    },
    listProv () {
      let qry = this.$query.new()
      this.$query.toW(qry, 'pid', 100000, 'EQ')
      this.$query.toW(qry, 'status', 0, 'EQ')
      this.$http.get('/sys/region/list?query=' + this.$query.toEncode(qry)).then(({data: res}) => {
        if (res.code !== 0) {
          return this.$message.error(res.msg)
        }
        this.regionOptions = res.data.list
        for (let item of this.regionOptions) {
          this.$set(item, 'children', [])
        }
        // this.defaultRegionHandle()
      }).catch(() => {})
    },
    regionItemChangeHandle (val) {
      let region = this.getCascaderItm(val, this.regionOptions)
      // 已经获取过不重复获取
      if (region[region.length - 1].children.length > 0) {
        return
      }
      let qry = this.$query.new()
      this.$query.toW(qry, 'pid', region[region.length - 1].id, 'EQ')
      this.$query.toW(qry, 'status', 0, 'EQ')
      this.$http.get('/sys/region/list?query=' + this.$query.toEncode(qry)).then(({data: res}) => {
        this.loading = false
        if (res.code !== 0) {
          return this.$message.error(res.msg)
        }
        // 最多三层结构
        if (region.length < 2) {
          for (let i of res.data.list) {
            this.$set(i, 'children', [])
          }
        }
        this.$set(region[region.length - 1], 'children', res.data.list)
      }).catch(() => {})
    },
    // 获取级联选择器的值
    getCascaderItm (valArr, regionOptions) {
      return valArr.map(function (value, index, array) {
        for (var itm of regionOptions) {
          if (itm.id === value) {
            regionOptions = itm.children
            return itm
          }
        }
        return null
      })
    },
    // 设置初始值
    defaultRegionHandle () {
      // console.log(this.regions())
      if (this.regions().length > 0) {
        this.listDefaultRegionHandle(this.regions(), 0)
      }
    },
    listDefaultRegionHandle (val, ind) {
      // console.log(val)
      let region = this.getCascaderItm(val, this.regionOptions)
      // console.log(region)
      // 因为已经定位三层结构，所以最后一层不为null，即不重复查询
      // 已经获取过，不重复获取
      if (region[region.length - 1]) {
        return
      }

      let qry = this.$query.new()
      this.$query.toW(qry, 'pid', region[ind].id, 'EQ')
      this.$query.toW(qry, 'status', 0, 'EQ')
      this.$http.get('/sys/region/list?query=' + this.$query.toEncode(qry)).then(({data: res}) => {
        if (res.code !== 0) {
          return this.$message.error(res.msg)
        }
        if (ind < 1) {
          for (let item of res.data.list) {
            this.$set(item, 'children', [])
          }
        }
        this.$set(region[ind], 'children', res.data.list)
        if (ind < 1) {
          this.listDefaultRegionHandle(val, 1)
        }
      })
    },
    regionChangeHandle (valArr) {
      this.submitModifyHandle(valArr)
    },
    // 向父组件提交修改信息，触发父组件方法
    submitModifyHandle (valArr) {
      this.$nextTick(() => {
        let region = this.getCascaderItm(valArr, this.regionOptions)
        let nmArr = []
        region.forEach(function (data, index, arr) {
          nmArr.push(data.nm)
        })
        if (this.outPut === 'string') {
          this.$emit('modify', valArr.join('-'), nmArr.join('-'))
        } else {
          this.$emit('modify', valArr, nmArr)
        }
      })
    }
  }
}
</script>