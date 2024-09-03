import request from '@/utils/request'

// 查询证书列表
export function ccieList(params) {
    return request({
        url: '/api/open/ccie/list',
        method: 'get',
        params: params
    })
}