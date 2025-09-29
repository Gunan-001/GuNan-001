import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";
Vue.use(VueRouter)

// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
const routes = [
  {
    path: '/',
    redirect: '/mall'
  },{
    path: '/login',
    name: 'login',
    meta: {
      title: '登入'
    },
    component: () => import('../views/admin/Login'),
  },{
    path: '/pay',
    name: 'pay',
    meta: {
      title: '订单支付'
    },
    component: () => import('../views/web/Pay'),
  },{
    path: '/mall',
    name: 'mall',
    redirect: '/mall/index',
    component: () => import('../views/Mall'),
    children: [
      {
        path: 'index',
        name: 'index',
        meta: {
          title: '首页'
        },
        component: () => import('../views/web/Index'),
      },{
        path: 'allgoods',
        name: 'allgoods',
        meta: {
          title: '全部商品'
        },
        component: () => import('../views/web/AllGoods'),
      },{
        path: 'detail',
        name: 'detail',
        meta: {
          title: '商品详情'
        },
        component: () => import('../views/web/Detail'),
      },{
        path: 'order',
        name: 'order',
        meta: {
          title: '我的订单'
        },
        component: () => import('../views/web/Order'),
      },{
        path: 'collect',
        name: 'collect',
        meta: {
          title: '我的收藏'
        },
        component: () => import('../views/web/Collect'),
      },{
        path: 'shopcar',
        name: 'shopcar',
        meta: {
          title: '我的购物车'
        },
        component: () => import('../views/web/Shopcar'),
      },{
        path: 'confirmorder',
        name: 'confirmorder',
        meta: {
          title: '确认订单'
        },
        component: () => import('../views/web/Confirmorder'),
      },{
        path: 'leavemessage',
        name: 'leavemessage',
        meta: {
          title: '留言板'
        },
        component: () => import('../views/web/Leavemessage'),
      }
    ]
  },{
    path: '/admin',
    name: 'admin',
    redirect: '/login',
    component: () => import('../views/admin/Admin'),
    children:[{
      path: 'goods',
      name: 'goods',
      meta: {
        title: '商品列表'
      },
      component: () => import("@/views/admin/Goods")
    },{
      path: 'goodstype',
      name: 'goodstype',
      meta: {
        title: '商品分类'
      },
      component: () => import("@/views/admin/GoodsType")
    },{
      path: 'banner',
      name: 'banner',
      meta: {
        title: '轮播图管理'
      },
      component: () => import("@/views/admin/Banner")
    },{
      path: 'message',
      name: 'message',
      meta: {
        title: '留言板管理'
      },
      component: () => import("@/views/admin/Message")
    },{
      path: 'order',
      name: 'order',
      meta: {
        title: '订单管理'
      },
      component: () => import("@/views/admin/Order")
    },{
      path: 'user',
      name: 'user',
      meta: {
        title: '用户列表'
      },
      component: () => import("@/views/admin/User")
    },{
      path: 'manager',
      name: 'manager',
      meta: {
        title: '管理员列表'
      },
      component: () => import("@/views/admin/Manager")
    },{
      path: 'role',
      name: 'role',
      meta: {
        title: '角色列表'
      },
      component: () => import("@/views/admin/Role")
    }]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  /* 路由发生变化修改页面title */
  window.sessionStorage.setItem('activePath',to.path)
  store.state.routepath = to.path
  if (to.meta.title) {
    document.title = to.meta.title;
  }
  /* 设置路由拦截 */
  let pageFilter = ['/mall/order','/mall/collect','/mall/shopcar','/mall/confirmorder','/mall/leavemessage']
  if (pageFilter.indexOf(to.path)>=0){
    if (sessionStorage.getItem('userId')===null||sessionStorage.getItem('userName')===null||sessionStorage.getItem('token')===null){
      window.sessionStorage.setItem('activePath',from.path)
      store.state.routepath = from.path
      document.title = from.meta.title;
      store.state.loginDialogVisible = true
    }else {
      next()
    }
  }else if (to.path.indexOf('/admin')>=0){
    if (sessionStorage.getItem('account')===null||sessionStorage.getItem('token')===null||sessionStorage.getItem('role')!=='manage'){
      window.sessionStorage.setItem('activePath',from.path)
      store.state.routepath = from.path
      document.title = from.meta.title;
      router.push('login')
    }else {
      next()
    }
  }else{
    next()
  }
})


export default router
