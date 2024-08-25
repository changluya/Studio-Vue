import request from '@/utils/request'

// 获取路由
export const getRouters = () => {
  return request({
    url: '/getRouters',
    method: 'get'
  })
}

// 公共菜单：获取类别路由
const getTypeMenu = (type) => {
  return request({
    url: '/menu/' + type,
    method: 'get'
  })
}

// 获取ccie类别菜单接口
export const getCcieTypeMenu = () => {
  return getTypeMenu('ccie')
}
