import request from '@/utils/request'

// 查询成果分类列表
export function getPocsMenu() {
  return request({
    url: '/team/pocs/menu',
    method: 'get'
  })
}

// 查询成果分类列表
export function listPocs(query) {
  return request({
    url: '/team/pocs/list',
    method: 'get',
    params: query
  })
}

// 查询成果分类详细
export function getPocs(id) {
  return request({
    url: '/team/pocs/' + id,
    method: 'get'
  })
}

// 新增成果分类
export function addPocs(data) {
  return request({
    url: '/team/pocs',
    method: 'post',
    data: data
  })
}

// 修改成果分类
export function updatePocs(data) {
  return request({
    url: '/team/pocs',
    method: 'put',
    data: data
  })
}

// 删除成果分类
export function delPocs(id) {
  return request({
    url: '/team/pocs/' + id,
    method: 'delete'
  })
}
