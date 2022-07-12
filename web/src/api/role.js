import request from '@/utils/request'

export function getRoles() {
  return request({
    url: 'roleList.do'
  })
}

export function getMenus(data) {
  return request({
    url: '/menuList.do'
  })
}

export function createRole(data) {
  return request({
    url: '/createRole.do',
    method: 'post',
    data
  })
}

export function updateRole(data) {
  return request({
    url: '/updateRole.do',
    method: 'post',
    data
  })
}

export function deleteRole(id) {
  return request({
    url: '/deleteRole.do',
    method: 'get',
    params: {
      id
    }
  })
}

export function getRoutes() {
  return request({
    url: 'menuList.do'
  })
}

export function addRole(data) {
  return request({
    url: '/vue-element-admin/role',
    method: 'post',
    data
  })
}
