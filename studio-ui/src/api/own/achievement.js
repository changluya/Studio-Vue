import request from '@/utils/request'

// 查询成果列表
export function listAchievement(query) {
  return request({
    url: '/own/achievement/list',
    method: 'get',
    params: query
  })
}

// 查询成果详细
export function getAchievement(id) {
  return request({
    url: '/own/achievement/' + id,
    method: 'get'
  })
}

// 新增成果
export function addAchievement(data) {
  return request({
    url: '/own/achievement',
    method: 'post',
    data: data
  })
}

// 修改成果
export function updateAchievement(data) {
  return request({
    url: '/own/achievement',
    method: 'put',
    data: data
  })
}

// 申请收录
export function applyInclusion(data) {
  return request({
    url: '/own/achievement/apply',
    method: 'put',
    data: data
  })
}

// 取消收留
export function cancelInclusion(data) {
  return request({
    url: '/own/achievement/cancel',
    method: 'put',
    data: data
  })
}

// 删除成果
export function delAchievement(id) {
  return request({
    url: '/own/achievement/' + id,
    method: 'delete'
  })
}
