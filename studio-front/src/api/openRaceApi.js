import request from '@/utils/request'

// 查询竞赛列表
export function raceList(params) {
    return request({
        url: '/api/open/race/list',
        method: 'get',
        params: params
    })
}