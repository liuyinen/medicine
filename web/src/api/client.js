import request from '@/utils/request'

export function createClient(data) {
  return request({
    url: '/createClient.do',
    method: 'post',
    data
  })
}

export function clientList(data) {
  return request({
    url: '/clientList.do',
    method: 'get',
    params: data,
  })
}

export function updateClient(data) {
  return request({
    url: '/updateClient.do',
    method: 'post',
    data
  })
}

export function deleteClient(id) {
  return request({
    url: '/deleteClient.do',
    method: 'get',
    params: {
      id
    }
  })
}

export function batchDelete(ids) {
  return request({
    url: '/batchDeleteClient.do',
    method: 'get',
    params: {
      ids
    }
  })
}

export function batchDeleteClient(ids) {
  return request({
    url: '/batchDeleteClient.do',
    method: 'get',
    params: {
      ids
    }
  })
}

export function batchDeleteRecords(ids) {
  return request({
    url: '/batchDeleteRecords.do',
    method: 'get',
    params: {
      ids
    }
  })
}

export function createRecords(data) {
  return request({
    url: '/createRecords.do',
    method: 'post',
    data
  })
}

export function recordsList(data) {
  return request({
    url: '/recordsList.do',
    method: 'get',
    params: data,
  })
}

export function updateRecords(data) {
  return request({
    url: '/updateRecords.do',
    method: 'post',
    data
  })
}

export function deleteRecords(id) {
  return request({
    url: '/deleteRecords.do',
    method: 'get',
    params: {
      id
    }
  })
}
