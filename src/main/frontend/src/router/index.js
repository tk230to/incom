import Vue from 'vue'
import VueRouter from 'vue-router'
import firebase from 'firebase/app'

import Home from '@/views/Home.vue'
import About from '@/views/About.vue'
import Buy from '@/views/Buy.vue'
import Cart from '@/views/Cart.vue'
import Order from '@/views/Order.vue'
import Item from '@/views/item/ItemList.vue'
import ItemCreate from '@/views/item/ItemCreate.vue'
import ItemUpdate from '@/views/item/ItemUpdate.vue'
import ItemType from '@/views/item/ItemTypeList.vue'
import ItemTypeCreate from '@/views/item/ItemTypeCreate.vue'
import ItemTypeUpdate from '@/views/item/ItemTypeUpdate.vue'
import Customer from '@/views/customer/CustomerList.vue'
import CustomerCreate from '@/views/customer/CustomerCreate.vue'
import CustomerUpdate from '@/views/customer/CustomerUpdate.vue'
import CustomerSpecialPrice from '@/views/customer/CustomerSpecialPrice.vue'
import AddressCreate from '@/views/address/AddressCreate.vue'
import AddressUpdate from '@/views/address/AddressUpdate.vue'
import Login from '@/views/Login.vue'
import MyPage from '@/views/MyPage.vue'
import Rental from '@/views/Rental.vue'
import SignUp from '@/views/SignUp.vue'
import Contact from '@/views/Contact.vue'
import Company from '@/views/Company.vue'
import Privacy from '@/views/Privacy.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    component: About
  },
  {
    path: '/buy',
    name: 'Buy',
    component: Buy
  },
  {
    path: '/cart',
    name: 'Cart',
    component: Cart
  },
  {
    path: '/order',
    name: 'Order',
    component: Order
  },
  {
    path: '/item',
    name: 'Item',
    component: Item
  },
  {
    path: '/item/create',
    name: 'ItemCreate',
    component: ItemCreate
  },
  {
    path: '/item/update/:id',
    name: 'ItemUpdate',
    component: ItemUpdate
  },
  {
    path: '/itemType',
    name: 'ItemType',
    component: ItemType
  },
  {
    path: '/itemType/create',
    name: 'ItemTypeCreate',
    component: ItemTypeCreate
  },
  {
    path: '/itemType/update/:id',
    name: 'ItemTypeUpdate',
    component: ItemTypeUpdate
  },
  {
    path: '/customer',
    name: 'Customer',
    component: Customer
  },
  {
    path: '/customer/create',
    name: 'CustomerCreate',
    component: CustomerCreate
  },
  {
    path: '/customer/update/:id',
    name: 'CustomerUpdate',
    component: CustomerUpdate
  },
  {
    path: '/customer/specialPrice/:id',
    name: 'CustomerSpecialPrice',
    component: CustomerSpecialPrice
  },
  {
    path: '/address/create',
    name: 'AddressCreate',
    component: AddressCreate
  },
  {
    path: '/address/update/:id',
    name: 'AddressUpdate',
    component: AddressUpdate
  },
  {
    path: '/signup',
    name: 'SignUp',
    component: SignUp
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/mypage',
    name: 'MyPage',
    component: MyPage,
    meta: { requiresAuth: true }
  },
  {
    path: '/rental',
    name: 'Rental',
    component: Rental
  },
  {
    path: '/contact',
    name: 'Contact',
    component: Contact
  },
  {
    path: '/company',
    name: 'Company',
    component: Company
  },
  {
    path: '/privacy',
    name: 'Privacy',
    component: Privacy
  },
]

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes,
  scrollBehavior (to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { x: 0, y: 0 }
    }
  }
})

// ========================================================================
// 画面遷移前処理
// ========================================================================
router.beforeEach((to, from, next) => {

  // 認証要否取得
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth)

  // 認証不要の場合
  if (!requiresAuth) {
    next()
    return
  }

  // 未ログインの場合
  if (!firebase.auth().currentUser) {
    next({
      path: '/login',
      query: { redirect: to.fullPath }
    })
    return
  }

  // ゲストログインの場合
  if (firebase.auth().currentUser.isAnonymous) {
    next({
      path: '/login',
      query: { redirect: to.fullPath }
    })
    return
  }
  next()
})

export default router
