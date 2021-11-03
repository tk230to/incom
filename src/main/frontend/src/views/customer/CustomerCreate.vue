<template>
<div class="container">
  <CustomerBase title="顧客登録画面" :customer="customer" :errors="errors"></CustomerBase>

  <div class="row">
    <div class="col-sm-6">
      <router-link class="btn btn-primary" to="/customer">戻る</router-link>
    </div>
    <div class="col-sm-6 text-right">
      <button class="btn btn-primary" @click="createCustomer()">登録</button>
    </div>
  </div>
</div>
</template>

<script>
import CustomerBase from './CustomerBase'

export default {

  // **************************************************************************
  // * データ
  // **************************************************************************
  data: function() {
    return {
      customer: {},
      errors: null
    }
  },

  // **************************************************************************
  // * 算出プロパティ
  // **************************************************************************
  computed: {

    // ========================================================================
    // ユーザ
    // ========================================================================
    user: {
      get () {
        return this.$store.state.user
      },
      set (value) {
        this.$store.commit('setUser', value)
      }
    },
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
    // 顧客登録
    // ========================================================================
    createCustomer: async function() {

      this.validateUser(this.customer)
      .then(() => {
        this.createFirebaseUser(this.customer)
        this.$router.push('/customer')
      })

      .catch((error) => {
        this.errors = error.response.data.errors
      });
    },
  }
};
</script>
