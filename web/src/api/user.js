import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/login.do',
    method: 'post',
    data
  })
}

export function getInfo(username) {
  return request({
    url: '/userInfo.do',
    method: 'get',
    params: {
      username
    }
  })
}

export function userMenus() {
  return request({
    url: '/userMenus.do',
    method: 'get'
  })
}

export function userList(data) {
  return request({
    url: '/userList.do',
    method: 'get',
    params: data,
  })
}

export function createUser(data) {
  return request({
    url: '/createUser.do',
    method: 'post',
    data
  })
}

export function updateUser(data) {
  return request({
    url: '/updateUser.do',
    method: 'post',
    data
  })
}

export function deleteUser(id) {
  return request({
    url: '/deleteUser.do',
    method: 'get',
    params: {
      id
    }
  })
}

export function batchDelete(ids) {
  return request({
    url: '/batchDeleteUser.do',
    method: 'get',
    params: {
      ids
    }
  })
}

export function logout() {
  return request({
    url: '/logout.do',
  })
}

export function reportData() {
  return request({
    url: '/reportData.do',
  })
}
