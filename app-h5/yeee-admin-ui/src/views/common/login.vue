<template>
  <div class="aui-wrapper aui-page__login">
    <div class="aui-content__wrapper">
      <main class="aui-content">
        <div class="login-header">
          <h2 class="login-brand">管理系统</h2>
        </div>
        <div class="login-body">
          <h3 class="login-title">登录</h3>
          <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmitHandle()" status-icon>
            <!-- <el-form-item prop="username">
              <el-input v-model="dataForm.username" :placeholder="账号">
                <span slot="prefix" class="el-input__icon">
                  <svg aria-hidden="true"><use xlink:href="#icon-user"></use></svg>
                </span>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="dataForm.password" type="password" :placeholder="密码">
                <span slot="prefix" class="el-input__icon">
                  <svg aria-hidden="true"><use xlink:href="#icon-lock"></use></svg>
                </span>
              </el-input>
            </el-form-item> -->
            <el-form-item prop="username">
              <el-input v-model="dataForm.username" placeholder="帐号">
                <span slot="prefix" class="el-input__icon">
                  <svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-user"></use></svg>
                </span>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="dataForm.password" type="password" placeholder="密码" show-password>
                <span slot="prefix" class="el-input__icon">
                  <svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-lock"></use></svg>
                </span>
              </el-input>
            </el-form-item>
            <!-- <el-form-item prop="captcha">
              <el-row :gutter="20">
                <el-col :span="14">
                  <el-input v-model="dataForm.captcha" :placeholder="$t('login.captcha')">
                    <span slot="prefix" class="el-input__icon">
                      <svg class="icon-svg" aria-hidden="true"><use xlink:href="#icon-safetycertificate"></use></svg>
                    </span>
                  </el-input>
                </el-col>
                <el-col :span="10" class="login-captcha">
                  <img :src="captchaPath" @click="getCaptcha()">
                </el-col>
              </el-row>
            </el-form-item> -->
            <el-form-item>
              <el-button type="primary" @click="dataFormSubmitHandle()" class="w-percent-100">登录</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div class="login-footer">
          <p><a href="/" target="_blank">综合管理平台</a>yeee一页</p>
        </div>
      </main>
    </div>
  </div>
</template>

<script>
import debounce from 'lodash/debounce'
import md5 from 'js-md5'
export default {
  data () {
    return {
      dataForm: {
        username: '',
        password: ''
      }
    }
  },
  computed: {
    dataRule () {
      return {
        username: [
          { required: true, message: '帐号不能为空', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 表单提交
    dataFormSubmitHandle: debounce(function () {
      this.$refs['dataForm'].validate((valid) => {
        if (!valid) {
          return false
        }
        this.$http.form().post('/manage/sys-user/login', {
          username: this.dataForm.username,
          password: md5(this.dataForm.password)
        }).then(({data: res}) => {
          if (res && res.code === 200) {
            localStorage.setItem('yeee-manageweb-token', res.data.token)
            this.$router.replace({ name: 'home' })
          } else {
            this.$message.error(res.message)
          }
        })
      })
    }, 1000, { 'leading': true, 'trailing': false })
  }
}
</script>

<style lang="scss">
.aui-page__login {
  &::before,
  &::after {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: -1;
    content: "";
  }
  &::before {
    background-image: url(~@/assets/img/login_bg.jpg);
    background-size: cover;
  }
  &::after {
    background-color: rgba(38, 50, 56, .4);
  }
  .aui-content {
    display: flex;
    flex-flow: column wrap;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    padding: 50px 20px 150px;
    text-align: center;
    &__wrapper {
      height: 100vh;
      background-color: transparent;
      overflow-x: hidden;
      overflow-y: auto;
    }
  }
  .login-header {
    padding: 20px;
    color: #fff;
    .login-brand {
      margin: 0 0 15px;
      font-size: 40px;
      font-weight: 400;
      letter-spacing: 2px;
      text-transform: uppercase;
    }
    .login-intro {
      padding: 0;
      margin: 0;
      list-style: none;
      > li {
        font-size: 16px;
        line-height: 1.5;
        color: rgba(255, 255, 255, .6);
        & + li {
          margin-top: 5px;
        }
      }
    }
  }
  .icon-svg {
    width: 1em;
    height: 1em;
    fill: currentColor;
    vertical-align: middle;
    overflow: hidden;
  }
  .w-percent-100 {
    width: 100%!important;
  }
  .login-body,
  .login-footer {
    width: 460px;
  }
  .login-body {
    padding: 20px 30px;
    background-color: #fff;
    .login-title {
      font-size: 18px;
      font-weight: 400;
    }
    .el-input__prefix .el-input__icon {
      font-size: 16px;
    }
    // .login-captcha {
    //   height: $--input-height;
    //   line-height: $--input-height -2px;
    //   > img {
    //     max-width: 100%;
    //     cursor: pointer;
    //   }
    // }
    .login-shortcut {
      margin-bottom: 20px;
      &__title {
        position: relative;
        margin: 0 0 15px;
        font-weight: 400;

        &::before {
          position: absolute;
          top: 50%;
          right: 0;
          left: 0;
          z-index: 1;
          content: "";
          height: 1px;
          margin-top: -.5px;
          // background-color: $--border-color-base;
          overflow: hidden;
        }
        > span {
          position: relative;
          z-index: 2;
          padding: 0 20px;
          color: rgba(0, 0, 0, .3);
          background-color: #fff;
        }
      }
      &__list {
        padding: 0;
        margin: 0;
        list-style: none;
        font-size: 0;
        > li {
          display: inline-block;
          vertical-align: middle;
          margin: 0 10px;
          font-size: 28px;
        }
      }
    }
    .login-guide {
      color: rgba(0, 0, 0, .3);
    }
  }
  .login-footer {
    position: absolute;
    bottom: 0;
    padding: 20px;
    color: rgba(255, 255, 255, .6);
    p {
      margin: 10px 0;
    }
    a {
      padding: 0 5px;
      color: rgba(255, 255, 255, .6);
      &:focus,
      &:hover {
        color: #fff;
      }
    }
  }
  // 右侧垂直风格
  &--right-vertical {
    .aui-content {
      flex-flow: row nowrap;
      justify-content: flex-start;
      align-items: stretch;
      padding: 0;
    }
    .login-header {
      flex: 1;
      display: flex;
      flex-flow: column wrap;
      justify-content: center;
      padding: 30px 120px;
      text-align: left;
    }
    .login-body {
      position: relative;
      display: flex;
      flex-flow: column wrap;
      justify-content: center;
      padding: 120px 30px 150px;
      text-align: center;
      .login-guide {
        margin-top: 0;
      }
    }
    .login-footer {
      display: flex;
      z-index: -1000;
      // right: 0;
      // // color: $--color-text-regular;
      // a {
      //   // color: $--color-text-regular;
      //   &:focus,
      //   &:hover {
      //     // color: $--color-primary;
      //   }
      // }
    }
  }
}
@media (max-width: 991px) {
  .aui-page__login {
    &--right-vertical {
      .login-header {
        padding: 30px;
      }
    }
  }
}
@media (max-width: 767px) {
  .aui-page__login {
    &--right-vertical {
      .login-header {
        .login-brand,
        .login-intro {
          display: none;
        }
      }
    }
  }
}
@media (max-width: 575px) {
  .aui-page__login {
    .login-body,
    .login-footer {
      width: 100%;
    }
    .login-captcha {
      text-align: left;
      > img {
        width: 136px;
      }
    }
    &--right-vertical {
      .login-header {
        display: none;
      }
    }
  }
}
</style>
