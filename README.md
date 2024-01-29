
## 系统说明

- 基于 Spring Boot + SpringSecurity-Oauth2 + Jwt + Vue 开发的 **众筹平台系统**
- 轻量级 + 组件化 + 可扩展

## 程序预览

### 前台

![](doc/crowdfunding/images/前台-首页.png)

### 后台

![](doc/crowdfunding/images/后台-首页v2.png)

[详情预览](doc/crowdfunding/detail.md)

## 快速开始

### 核心依赖

| 依赖                          | 版本                                                  |
|-----------------------------|-----------------------------------------------------|
| Spring Boot                 | 2.6.3                                               |
| yeee-memo                   | [通用web脚手架](https://gitee.com/yeeevip/yeee-memo.git) |

### 模块说明/结构目录

```lua
yeee-crowdfunding
├── app-h5
    ├── yeee-admin-ui -- 后台管理系统｜vue
    └── yeee-crowdfunding-web -- 前台用户页面
├── doc -- 文档sql脚本
├── yeee-crowdfunding-bootstrap -- 主启动工程
├── yeee-crowdfunding-client -- 众筹前台用户服务
├── yeee-app-common -- 公共模块
├── yeee-crowdfunding-manage -- 众筹管理端服务
├── yeee-sys-manage -- 系统权限管理服务
```

### 本地开发 运行

#### 环境要求

- jdk1.8
- mysql >= 5.7
- maven 3.6+

```
# 下载yeee-memo工程
git clone https://gitee.com/yeeevip/yeee-memo.git

# 打包[JavaWeb通用脚手架]
cd yeee-memo/memo-parent && mvn clean install

# 下载众筹项目
git clone https://gitee.com/yeeevip/yeee-crowdfunding.git

# 运行web服务
cd yeee-crowdfunding && mvn clean install && java -jar yeee-crowdfunding-bootstrap/target/yeee-crowdfunding-bootstrap-1.0.0-SNAPSHOT.jar

# 运行众筹管理端ui
cd yeee-crowdfunding/app-h5/yeee-admin-ui && npm install && npm run dev

```

### 其他说明

1. 欢迎提交 [PR](https://www.yeee.vip)

2. 欢迎提交 [issue](https://gitee.com/yeeevip/yeee-crowdfunding/issues)，请写清楚遇到问题的原因、开发环境、复显步骤。

## 交流群

- 作者QQ：1324459373

| 微信群                                  | QQ群                                                                           |
|--------------------------------------|-------------------------------------------------------------------------------|
| ![作者微信](doc/yeee/images/yeee_wp.jpg) | ![](doc/yeee/images/yeee-crowdfunding-qq.jpg)                                 |
| 回复：加群，邀您加入群聊                         | <a href="https://qm.qq.com/q/WNiHwithgy" target="_blank">点击加入QQ群：69405215</a> |

