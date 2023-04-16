import request from '@/utils/request'

export function userRegister(data) {
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}

export function login(data) {
  return request({
    url: '/oauth/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/oauth/info',
    method: 'post'
  })
}

export function logout() {
  return request({
    url: '/oauth/logout',
    method: 'post'
  })
}

export function userList(data) {
  return request({
    url: '/user/list',
    method: 'post',
    data
  })
}

export function userListLog(data) {
  return request({
    url: '/user/list/log',
    method: 'post',
    data
  })
}

export function userRankList(data) {
  return request({
    url: '/user/list/rank',
    method: 'post',
    data
  })
}

export function userInfo(query) {
  return request({
    url: '/user/info',
    method: 'get',
    params: query
  })
}

export function userEdit(data) {
  return request({
    url: '/user/edit',
    method: 'post',
    data
  })
}

export function userDelete(query) {
  return request({
    url: '/user/delete',
    method: 'post',
    params: query
  })
}
