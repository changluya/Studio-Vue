import request from '@/utils/request'

// 查询成果列表
export function listAchievement(query) {
  return request({
    url: '/team/achievement/list',
    method: 'get',
    params: query
  })
}

// 查询成果详细
export function getAchievement(id) {
  return request({
    url: '/team/achievement/' + id,
    method: 'get'
  })
}

// 新增成果
export function addAchievement(data) {
  return request({
    url: '/team/achievement',
    method: 'post',
    data: data
  })
}

// 修改成果
export function updateAchievement(data) {
  return request({
    url: '/team/achievement',
    method: 'put',
    data: data
  })
}

// 申请收录
export function approvedInclusion(data) {
  return request({
    url: '/team/achievement/approved',
    method: 'put',
    data: data
  })
}

// 取消收录
export function cancelInclusion(data) {
  return request({
    url: '/team/achievement/cancel',
    method: 'put',
    data: data
  })
}

// 删除成果
export function delAchievement(id) {
  return request({
    url: '/team/achievement/' + id,
    method: 'delete'
  })
}
