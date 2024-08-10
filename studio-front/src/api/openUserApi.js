import request from '@/utils/request'

// 查询团队成员
export function queryTeamMembers() {
    return request({
        url: '/api/open/user/teamMembers',
        method: 'get'
    })
}