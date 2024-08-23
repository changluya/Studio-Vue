import request from '@/utils/request'

// 查询团队成员
export function pocsList(params) {
    return request({
        url: '/api/open/pocs/list',
        method: 'get'
    })
}