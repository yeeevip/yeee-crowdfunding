# CrowdFunding（众筹系统）

> 基于springboot+mybatis-plus+oauth2+vue开发众筹平台项目系统
>

# 运行步骤
### 1.环境要求
> maven + jdk8 + mysql>=5.7
### 2.将sql文件夹得sql脚本导入自己的数据库
### 3.按需修改application.yml文件(如数据库密码、文件上传路径等)

#### 4.此外项目有问题欢迎提出 ，安装运行过程中遇到问题可以联系我的 QQ：1324459373，QQ群：69405215



## 二、功能概述

基础流程介绍（发布项目--审核--用户支持购买项目服务）

后台项目审核页面地址  ${根页面url}/admin   ; 管理员账号：admin，密码：111111

### 一、注册登陆后发起项目
#### 1.基础信息
![image](http://oos.yeee.vip/1.%E5%8F%91%E8%B5%B7%E9%A1%B9%E7%9B%AE-%E5%9F%BA%E7%A1%80%E4%BF%A1%E6%81%AF.png)
#### 2.项目信息
![image](http://oos.yeee.vip/2.%E5%8F%91%E8%B5%B7%E9%A1%B9%E7%9B%AE-%E9%A1%B9%E7%9B%AE%E4%BF%A1%E6%81%AF.png)
#### 3.项目详情
![image](http://oos.yeee.vip/3.%E5%8F%91%E8%B5%B7%E9%A1%B9%E7%9B%AE-%E9%A1%B9%E7%9B%AE%E8%AF%A6%E6%83%85.png)
#### 4.回报设置
![image](http://oos.yeee.vip/4.%E5%8F%91%E8%B5%B7%E9%A1%B9%E7%9B%AE-%E5%9B%9E%E6%8A%A5%E8%AE%BE%E7%BD%AE.png)
#### 5.编辑预览
![image](http://oos.yeee.vip/5.%E5%8F%91%E8%B5%B7%E9%A1%B9%E7%9B%AE-%E7%BC%96%E8%BE%91%E9%A2%84%E8%A7%88.png)

### 二、前台页面
#### 1.项目详情
![image](http://oos.yeee.vip/13.%E6%B5%8B%E8%AF%95%E9%A1%B9%E7%9B%AE%20-%E8%AF%A6%E6%83%85%E9%A1%B5.png)
### 三、个人中心
#### 1.我的发起
![image](http://oos.yeee.vip/6.%E4%B8%AA%E4%BA%BA%E4%BF%A1%E6%81%AF-%E6%88%91%E7%9A%84%E5%8F%91%E8%B5%B7.png)
#### 2.我的订单
![image](http://oos.yeee.vip/14.%E4%B8%AA%E4%BA%BA%E4%BF%A1%E6%81%AF-%E6%88%91%E7%9A%84%E8%AE%A2%E5%8D%95.png)
#### 3.更新项目动态
![image](http://oos.yeee.vip/11.%E5%8F%91%E8%B5%B7%E8%80%85%E6%9B%B4%E6%96%B0%E9%A1%B9%E7%9B%AE%E5%8A%A8%E6%80%81.png)
#### 3.更新后页面
![image](http://oos.yeee.vip/12.%E6%9B%B4%E6%96%B0%E8%BF%9B%E5%B1%95%E5%90%8E.png)

### 四、平台后台管理
#### 1.管理员登录
![image](http://oos.yeee.vip/7.%E7%AE%A1%E7%90%86%E5%91%98%E7%99%BB%E9%99%86%E9%A1%B5%E9%9D%A2.png)
#### 2.待审核项目
![image](http://oos.yeee.vip/8.%E5%90%8E%E5%8F%B0%E7%AE%A1%E7%90%86%E4%B8%AD%E5%BF%83-%E5%BE%85%E5%AE%A1%E6%A0%B8%E9%A1%B9%E7%9B%AE.png)
#### 3.项目审核页面
![image](http://oos.yeee.vip/9.%E9%A1%B9%E7%9B%AE%E5%AE%A1%E6%A0%B8%E9%A1%B5%E9%9D%A2.png)
#### 4.通过审核
![image](http://oos.yeee.vip/10.%E9%80%9A%E8%BF%87%E5%AE%A1%E6%A0%B8.png)



# 技术架构

1. springSecurity&oauth2
> 不同类型的用户（系统用户及前台用户）的权限控制及单点登录
>

2. springboot

3.