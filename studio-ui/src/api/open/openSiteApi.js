import request from '@/utils/request'

// 查询网站配置
export function querySiteConfig(params) {
  return request({
    url: '/open/site/config',
    method: 'get',
    params: params
  })
}
