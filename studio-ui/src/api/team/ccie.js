import request from '@/utils/request'

// 查询ZfCcie列表
export function listCcie(query) {
  return request({
    url: '/team/ccie/list',
    method: 'get',
    params: query
  })
}

// 申请收录
export function approvedInclusion(data) {
  return request({
    url: '/team/ccie/approved',
    method: 'put',
    data: data
  })
}

// 取消收录
export function cancelInclusion(data) {
  return request({
    url: '/team/ccie/cancel',
    method: 'put',
    data: data
  })
}
