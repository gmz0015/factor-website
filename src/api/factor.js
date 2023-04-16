import request from '@/utils/request'

export function factorList(data) {
    return request({
        url: '/factor/list',
        method: 'post',
        data
    })
}


export function factorRankList(data) {
    return request({
        url: '/factor/list/rank',
        method: 'post',
        data
    })
}

export function factorLogList(data) {
    return request({
        url: '/factor/list/log',
        method: 'post',
        data
    })
}

export function factorInfo(query) {
    return request({
        url: '/factor/info',
        method: 'get',
        params: query
    })
}

export function factorAdd(data) {
    return request({
        url: '/factor/add',
        method: 'post',
        data
    })
}

export function factorAddLogText(data) {
    return request({
        url: '/factor/add/log/text',
        method: 'post',
        data
    })
}

export function factorAddLogImage(config, data) {
    return request({
        url: '/factor/add/log/image',
        method: 'post',
        data: data,
        config: config
    })
}

export function factorEdit(data) {
    return request({
        url: '/factor/edit',
        method: 'post',
        data
    })
}

export function factorDelete(query) {
    return request({
        url: '/factor/delete',
        method: 'post',
        params: query
    })
}

export function factorDeleteLog(query) {
    return request({
        url: '/factor/delete/log',
        method: 'post',
        params: query
    })
}
