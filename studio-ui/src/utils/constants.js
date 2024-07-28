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

export const MY_CONSTANT = {
  Roles
}
