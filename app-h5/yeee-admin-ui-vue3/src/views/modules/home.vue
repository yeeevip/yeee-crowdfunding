<template>
  <div class="mod-home">
    <!-- 欢迎区域 -->
    <div class="welcome-section">
      <div class="welcome-content">
        <h1 class="welcome-title">欢迎回来，{{ userName || '管理员' }}</h1>
        <p class="welcome-subtitle">{{ getCurrentTime() }}，祝您工作愉快！</p>
      </div>
      <div class="welcome-avatar">
        <img :src="userAvatar || defaultAvatar" alt="用户头像" class="avatar-img">
      </div>
    </div>



    <!-- 最近活动和系统信息 -->
    <el-row :gutter="20" class="bottom-section">
      <el-col :xs="24" :md="12">
        <div class="activity-section">
          <h2 class="section-title">最近活动</h2>
          <div class="activity-list">
            <div class="activity-item" v-for="(activity, index) in recentActivities" :key="index">
              <div class="activity-dot" :style="{ backgroundColor: activity.type === 'success' ? '#67C23A' : activity.type === 'warning' ? '#E6A23C' : '#409EFF' }"></div>
              <div class="activity-content">
                <p class="activity-text">{{ activity.text }}</p>
                <span class="activity-time">{{ activity.time }}</span>
              </div>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :md="12">
        <div class="system-info-section">
          <h2 class="section-title">系统信息</h2>
          <div class="info-list">
            <div class="info-item" v-for="(info, index) in systemInfo" :key="index">
              <span class="info-label">{{ info.label }}</span>
              <span class="info-value">{{ info.value }}</span>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import { useUserStore } from '@/stores/user'
  import defaultAvatarImg from '@/assets/img/avatar.png'
  
  export default {
    setup() {
      const userStore = useUserStore()
      
      return {
        userStore
      }
    },
  data () {
    return {
      defaultAvatar: defaultAvatarImg,
      quickActions: [
        {
          title: '用户管理',
          description: '管理系统用户信息',
          icon: 'admin',
          route: 'sys-user'
        },
        {
          title: '角色管理',
          description: '配置用户角色权限',
          icon: 'role',
          route: 'sys-role'
        },
        {
          title: '菜单管理',
          description: '设置系统菜单结构',
          icon: 'menu',
          route: 'sys-menu'
        },
        {
          title: '系统设置',
          description: '配置系统参数',
          icon: 'shezhi',
          route: 'sys-config'
        },
        {
          title: '日志管理',
          description: '查看系统运行日志',
          icon: 'log',
          route: 'sys-log'
        },
        {
          title: '文件管理',
          description: '管理系统文件资源',
          icon: 'oss',
          route: 'sys-oss'
        }
      ],
      recentActivities: [
        {
          text: '管理员登录系统',
          time: '2分钟前',
          type: 'success'
        },
        {
          text: '新增用户：张三',
          time: '15分钟前',
          type: 'info'
        },
        {
          text: '修改系统配置',
          time: '1小时前',
          type: 'warning'
        },
        {
          text: '数据库备份完成',
          time: '2小时前',
          type: 'success'
        },
        {
          text: '系统更新检查',
          time: '3小时前',
          type: 'info'
        }
      ],
      systemInfo: [
        {
          label: '系统版本',
          value: 'v2.1.0'
        },
        {
          label: '运行时间',
          value: '15天 8小时'
        },
        {
          label: '数据库',
          value: 'MySQL 8.0'
        },
        {
          label: '服务器',
          value: 'CentOS 7.9'
        },
        {
          label: '内存使用',
          value: '68%'
        },
        {
          label: '磁盘空间',
          value: '45%'
        }
      ]
    }
  },
  computed: {
    userName () {
      return this.userStore.name
    },
    userAvatar () {
      return this.userStore.avatar
    }
  },
  methods: {
    getCurrentTime () {
      const hour = new Date().getHours()
      if (hour < 6) {
        return '凌晨好'
      } else if (hour < 9) {
        return '早上好'
      } else if (hour < 12) {
        return '上午好'
      } else if (hour < 14) {
        return '中午好'
      } else if (hour < 17) {
        return '下午好'
      } else if (hour < 19) {
        return '傍晚好'
      } else if (hour < 22) {
        return '晚上好'
      } else {
        return '夜深了'
      }
    },
  }
  }
</script>

<style lang="scss" scoped>
.mod-home {
  padding: 10px;
  background-color: #f1f4f5;
  min-height: calc(100vh - 70px);

  /* 欢迎区域 */
  .welcome-section {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px;
    margin-bottom: 15px;
    background: linear-gradient(to right, #334157 0%, #2c3a4a 100%);
    border-radius: 12px;
    color: white;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);

    .welcome-content {
      flex: 1;

      .welcome-title {
        font-size: 28px;
        font-weight: 600;
        margin: 0 0 10px 0;
        text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      }

      .welcome-subtitle {
        font-size: 16px;
        opacity: 0.9;
        margin: 0;
      }
    }

    .welcome-avatar {
      .avatar-img {
        width: 80px;
        height: 80px;
        border-radius: 50%;
        border: 4px solid rgba(255, 255, 255, 0.3);
        object-fit: cover;
      }
    }
  }


  /* 快捷操作 */
  .quick-actions-section {
    margin-bottom: 15px;

    .section-title {
      font-size: 20px;
      font-weight: 600;
      color: #2c3e50;
      margin: 0 0 20px 0;
      padding-left: 12px;
      border-left: 4px solid #409EFF;
    }

    .action-card {
      display: flex;
      align-items: center;
      padding: 20px;
      background: white;
      border-radius: 12px;
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
      cursor: pointer;
      transition: all 0.3s ease;
      margin-bottom: 10px;

      &:hover {
        transform: translateY(-3px);
        box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
        border-left: 4px solid #409EFF;
      }

      .action-icon {
        width: 50px;
        height: 50px;
        background: linear-gradient(135deg, #409EFF, #67C23A);
        border-radius: 10px;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-right: 15px;

        .action-icon-svg {
          width: 24px;
          height: 24px;
          color: white;
        }
      }

      .action-content {
        flex: 1;

        .action-title {
          font-size: 16px;
          font-weight: 600;
          color: #2c3e50;
          margin: 0 0 5px 0;
        }

        .action-desc {
          font-size: 13px;
          color: #7f8c8d;
          margin: 0;
        }
      }
    }
  }

  /* 底部区域 */
  .bottom-section {
    .section-title {
      font-size: 18px;
      font-weight: 600;
      color: #2c3e50;
      margin: 0 0 20px 0;
      padding-left: 12px;
      border-left: 4px solid #409EFF;
    }
  }

  /* 最近活动 */
  .activity-section {
    background: white;
    border-radius: 12px;
    padding: 15px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);

    .activity-list {
      .activity-item {
        display: flex;
        align-items: flex-start;
        padding: 12px 0;
        border-bottom: 1px solid #f0f0f0;

        &:last-child {
          border-bottom: none;
        }

        .activity-dot {
          width: 8px;
          height: 8px;
          border-radius: 50%;
          margin: 6px 12px 0 0;
          flex-shrink: 0;
        }

        .activity-content {
          flex: 1;

          .activity-text {
            font-size: 14px;
            color: #2c3e50;
            margin: 0 0 5px 0;
          }

          .activity-time {
            font-size: 12px;
            color: #7f8c8d;
          }
        }
      }
    }
  }

  /* 系统信息 */
  .system-info-section {
    background: white;
    border-radius: 12px;
    padding: 15px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);

    .info-list {
        .info-item {
          display: flex;
          justify-content: space-between;
          align-items: center;
          padding: 8px 0;
          border-bottom: 1px solid #f0f0f0;

        &:last-child {
          border-bottom: none;
        }

        .info-label {
          font-size: 14px;
          color: #7f8c8d;
        }

        .info-value {
          font-size: 14px;
          font-weight: 600;
          color: #2c3e50;
        }
      }
    }
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .mod-home {
    padding: 8px;

    .welcome-section {
      flex-direction: column;
      text-align: center;
      padding: 15px;

      .welcome-content {
        margin-bottom: 15px;

        .welcome-title {
          font-size: 24px;
        }
      }
    }


    .action-card {
      padding: 15px !important;
    }

    .bottom-section {
      .el-col {
        margin-bottom: 15px;
      }
    }
  }
  }
</style>

