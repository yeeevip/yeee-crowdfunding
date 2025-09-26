<template>
  <div class="site-wrapper site-page--not-found">
    <div class="site-content__wrapper">
      <div class="site-content">
        <h2 class="not-found-title">400</h2>
        <p class="not-found-desc">抱歉！您访问的页面<em>失联</em>啦 ...</p>
        <el-button @click="$router.go(-1)">返回上一页</el-button>
        <el-button type="primary" class="not-found-btn-gohome" @click="$router.push({ name: 'home' })">进入首页</el-button>
      </div>
    </div>
    <!-- 底部版权条带 -->
    <div class="site-footer-bar">
      <div class="site-footer-bar__content">
        <span class="copyright-text">&copy; {{ currentYear }} 一页一科技. 保留所有权利.</span>
        <span class="divider">|</span>
        <span class="version-text">版本 v1.0.0 | 技术支持: <a href="https://yeee.vip" target="_blank">YEEE.VIP</a></span>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    computed: {
      currentYear () {
        return new Date().getFullYear()
      }
    }
  }
</script>

<style lang="scss">
  .site-wrapper.site-page--not-found {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    overflow: hidden;
    .site-content__wrapper {
      padding: 0;
      margin: 0;
      background-color: #fff;
    }
    .site-content {
      position: fixed;
      top: 15%;
      left: 50%;
      z-index: 2;
      padding: 30px;
      text-align: center;
      transform: translate(-50%, 0);
    }
    .not-found-title {
      margin: 20px 0 15px;
      font-size: 10em;
      font-weight: 400;
      color: rgb(55, 71, 79);
    }
    .not-found-desc {
      margin: 0 0 30px;
      font-size: 26px;
      text-transform: uppercase;
      color: rgb(118, 131, 143);
      > em {
        font-style: normal;
        color: #ee8145;
      }
    }
    .not-found-btn-gohome {
      margin-left: 30px;
    }

    /* 底部版权条带样式 - 仿顶部导航栏 */
    .site-footer-bar {
      position: fixed;
      bottom: 0;
      right: 0;
      left: 0;
      z-index: 1020;
      height: 40px;
      //background-color: #334157; /* 与顶部导航栏相同的颜色 */
      //box-shadow: 0 -2px 4px rgba(0, 0, 0, .08);
      display: flex;
      align-items: center;
      justify-content: center;

      &__content {
        display: flex;
        align-items: center;
        color: #abb1ba;
        font-size: 14px;

        .copyright-text {
          color: #abb1ba;
        }

        .divider {
          margin: 0 15px;
          color: #abb1ba;
        }

        .version-text {
          color: #abb1ba;
          font-size: 13px;
        }
      }
    }
  }
</style>
