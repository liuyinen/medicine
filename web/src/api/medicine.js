import request from '@/utils/request'

export function medicineList(data) {
  return request({
    url: '/medicineList.do',
    method: 'get',
    params: data,
  })
}

export function createMedicine(data) {
  return request({
    url: '/createMedicine.do',
    method: 'post',
    data
  })
}

export function updateMedicine(data) {
  return request({
    url: '/updateMedicine.do',
    method: 'post',
    data
  })
}

export function deleteMedicine(id) {
  return request({
    url: '/deleteMedicine.do',
    method: 'get',
    params: {
      id
    }
  })
}

export function batchDelete(ids) {
  return request({
    url: '/batchDeleteMedicine.do',
    method: 'get',
    params: {
      ids
    }
  })
}
