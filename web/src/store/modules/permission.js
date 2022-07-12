import { asyncRoutes, constantRoutes } from '@/router'
import { userMenus } from '@/api/user'

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes) {
  const accessedRouters = routes.filter(router => {
    if (router.component) {
      const component = router.component
      router.component = loadView(component)
    }
    if (router.children && router.children.length) {
      router.children = filterAsyncRoutes(router.children)
    }
    return true
  })
  return accessedRouters
}

export const loadView = (view) => { // 路由懒加载
  if (view == 'layout') {
  return (resolve) => require([`@/layout`],resolve)
  }else {
    return (resolve) => require([`@/views/${view}`],resolve)
  }

}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, roles) {
    return new Promise((resolve, reject) => {
      console.log('in generateRoutes')
      userMenus().then(response => {
        const { detail } = response
        const asyncRouter = filterAsyncRoutes(detail)
        commit('SET_ROUTES', asyncRouter)
        resolve(asyncRouter)
      }).catch(error => {
        reject(error)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
