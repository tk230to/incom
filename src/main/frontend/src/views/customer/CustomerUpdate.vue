<template>
<div class="container">
  <CustomerBase title="顧客更新画面" :customer="customer" :errors="errors"></CustomerBase>

  <div class="row">
    <div class="col-sm-6">
      <router-link class="btn btn-primary" to="/customer">戻る</router-link>
    </div>
    <div class="col-sm-6 text-right">
      <button class="btn btn-primary" @click="updateCustomer()">更新</button>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios'
import CustomerBase from './CustomerBase'

export default {

    // **************************************************************************
    // * 表示前処理
    // **************************************************************************
    mounted: function() {
      this.getCustomer()
    },

    // **************************************************************************
  // * データ
  // **************************************************************************
  data: function() {
    return {
      customer: {
        role: {},
      },
      errors: null
    }
  },

  // **************************************************************************
  // * コンポーネント
  // **************************************************************************
  components: {
    CustomerBase
  },

  // **************************************************************************
  // * メソッド
  // **************************************************************************
  methods: {

    // ========================================================================
    // 顧客取得
    // ========================================================================
    getCustomer: async function() {

      // HTTPリクエスト送信
      let customer
      await axios.get('/customers/' + this.$route.params.id)
      .then(function (response) {
        customer = response.data
      })

      this.customer = customer
    },

    // ========================================================================
    // 顧客更新
    // ========================================================================
    updateCustomer: async function() {

      // HTTPリクエスト送信
      await axios.put('/customers/' + this.customer.id, this.customer)
      .then(response => {
        this.customer = response.data
        this.$router.push('/customer')
      })

      .catch(error => {
        this.errors = error.response.data.errors
      })
    },
  },
};
</script>
