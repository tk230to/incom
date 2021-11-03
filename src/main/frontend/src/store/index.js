import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({

  // **************************************************************************
  // * データ
  // **************************************************************************
  state: {

    // ユーザ
    user: {},

    // 注文
    salesOrder: {},

    // 顧客リスト
    customers: {},
  },

  // **************************************************************************
  // * mutations
  // **************************************************************************
  mutations: {
    setUser(state, user) {
      state.user = user
    },

    setSalesOrder(state, salesOrder) {
      state.salesOrder = salesOrder
    },

    setCustomers(state, customers) {
      state.customers = customers
    },
  },
})
