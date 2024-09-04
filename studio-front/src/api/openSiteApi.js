import request from '@/utils/request'

// 查询网站配置
export function querySiteConfig(params) {
    return request({
        url: '/api/open/site/config',
        method: 'get',
        params: params
    })
}

// 查询网站基础数据统计
export function getSiteStatistics() {
    return request({
        url: '/api/open/site/statistics',
        method: 'get'
    })
}