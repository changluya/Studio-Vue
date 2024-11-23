// constants.js
// 角色定义
const Roles = {
  'ROLE_ADMIN': {
    'name': "管理员",
    'roleId': 1,
    'roleKey': 'admin'
  },
  'ROLE_MANAGE': {
    'name': "负责人",
    'roleId': 2,
    'roleKey': 'manage'
  },
  'ROLE_MEMBER': {
    'name': "成员",
    'roleId': 3,
    'roleKey': 'member'
  },
  'ROLE_PAST_MANAGE': {
    'name': "历届负责人",
    'roleId': 4,
    'roleKey': 'pastmanage'
  },
  'ROLE_TEACHER': {
    'name': "指导老师",
    'roleId': 5,
    'roleKey': 'teacher'
  }
}

// 网站配置参数key
const siteConfigKeys = {
  SITE_BASIC_CONFIG: {
    configName: '网站基础配置',
    configKey: 'site.basicConfig'
  },
  SITE_PAGE_MAIN_CONFIG: {
    configName: '网站主页配置',
    configKey: 'site.page.mainConfig'
  },
  SITE_PAGE_FOOTER_CONFIG: {
    configName: '网站底部栏目配置',
    configKey: 'site.page.footerConfig'
  },
  SITE_PAGE_TIME_CONFIG: {
    configName: '时光轴配置',
    configKey: 'site.page.timeConfig'
  },
  SITE_PAGE_SKILL_CONFIG: {
    configName: '网站主页技术栈配置',
    configKey: 'site.page.skillConfig'
  },
  SITE_UPLOAD_OPTION: {
    configName: '文件上传配置选项',
    configKey: 'site.upload.option'
  },
  SITE_UPLOAD_FILE: {
    configName: '本地文件上传配置',
    configKey: 'site.upload.file'
  },
  SITE_UPLOAD_OSS: {
    configName: 'OSS资源上传配置',
    configKey: 'site.upload.oss'
  },
  // 参数常量配置
  SITE_PARAMS_INVITE_CODE: {
    configName: '网站常量参数-邀请码',
    configKey: 'site.params.invitecode'
  }
}

export const MY_CONSTANT = {
  Roles,
  siteConfigKeys
}
