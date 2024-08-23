import request from '@/utils/request'

// 查询团队成员
export function showAchievements(params) {
    return request({
        url: '/api/open/achievement/show',
        method: 'get',
        params: params
    })
}