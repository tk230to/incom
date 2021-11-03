<template>
<div class="container">
  <AddressBase title="納品先住所登録画面" :address="address" :errors="errors"></AddressBase>

  <div class="row">
    <div class="col-sm-6">
      <button class="btn btn-primary" @click="$router.back()">戻る</button>
    </div>
    <div class="col-sm-6 text-right">
      <button class="btn btn-primary" @click="createAddress()">登録</button>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios'
import AddressBase from './AddressBase'

export default {

  // **************************************************************************
  // * データ
  // **************************************************************************
  data: function() {
    return {
      address: {},
      errors: null
    }
  },

  // **************************************************************************
  // * コンポーネント
  // **************************************************************************
  components: {
    AddressBase
  },


  // **************************************************************************
  // * 算出プロパティ
  // **************************************************************************
  computed: {

    // ========================================================================
    // 顧客
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
  // * メソッド
  // **************************************************************************
  methods: {

    // ========================================================================
    // 納品先住所登録
    // ========================================================================
    createAddress: async function() {

      // HTTPリクエスト送信
      this.address.customer = this.user
      await axios.post('/addresses/', this.address)
      .then(response => {
        this.address = response.data
        this.$router.back()
      })

      .catch(error => {
        this.errors = error.response.data.errors
      })
    },
  }
};
</script>
