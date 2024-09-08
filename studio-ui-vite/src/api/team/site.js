import request from '@/utils/request'

// 新增或更新网站配置
const addOrUpdateSiteConfig = function(data) {
  return request({
    url: '/team/site/addOrUpdateSiteConfig',
    method: 'post',
    data: data
  })
}

// 查询网站配置
const selectConfigValueByConfigKey = function(query) {
  return request({
    url: '/team/site/selectConfigValueByConfigKey',
    method: 'get',
    params: query
  })
}

// 测试上传文件配置连通性
const testConn = function(data) {
  return request({
    url: '/zf/api/testConn',
    method: 'post',
    data: data
  })
}

export default { addOrUpdateSiteConfig, selectConfigValueByConfigKey, testConn }
