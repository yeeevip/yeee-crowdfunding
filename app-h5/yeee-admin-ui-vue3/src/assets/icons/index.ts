/**
 * 字体图标, 统一使用SVG Sprite矢量图标(http://www.iconfont.cn/）
 * Vue 3 版本
 */
import './iconfont.js'

// 获取所有SVG文件
const svgFiles = import.meta.glob('./svg/*.svg', { eager: true })

const iconList = Object.keys(svgFiles).map(path => {
  const name = path.replace('./svg/', '').replace('.svg', '')
  return { name, path }
})

export default {
  // 获取图标名称列表
  getNameList() {
    return iconList.map(item => item.name.replace('icon-', ''))
  },
  iconList
}
