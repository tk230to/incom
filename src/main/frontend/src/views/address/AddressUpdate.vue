<template>
<div class="container">
  <AddressBase title="納品先住所更新画面" :address="address" :errors="errors"></AddressBase>

  <div class="row">
    <div class="col-sm-6">
      <button class="btn btn-primary" @click="$router.back()">戻る</button>
    </div>
    <div class="col-sm-6 text-right">
      <button class="btn btn-primary" @click="updateAddress()">更新</button>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios'
import AddressBase from './AddressBase'

export default {

    // **************************************************************************
    // * 表示前処理
    // **************************************************************************
    mounted: function() {
      this.getAddress()
    },

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
  // * コンポーネント
  // **************************************************************************
  components: {
    AddressBase
  },

  // **************************************************************************
  // * メソッド
  // **************************************************************************
  methods: {

    // ========================================================================
    // 納品先住所取得
    // ========================================================================
    getAddress: async function() {

      // HTTPリクエスト送信
      let address
      await axios.get('/addresses/' + this.$route.params.id)
      .then(function (response) {
        address = response.data
      })

      this.address = address
    },

    // ========================================================================
    // 納品先住所更新
    // ========================================================================
    updateAddress: async function() {

      // HTTPリクエスト送信
      this.address.customer = this.user
      await axios.put('/addresses/' + this.address.id, this.address)
      .then(response => {
        this.address = response.data
        this.$router.back()
      })

      .catch(error => {
        this.errors = error.response.data.errors
      })
    },
  },
};
</script>
