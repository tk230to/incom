<template>
<div class="container">
  <itemTypeBase title="商品種別登録画面" :itemType="itemType" :errors="errors"></itemTypeBase>

  <div class="row">
    <div class="col-sm-6">
      <router-link class="btn btn-primary" to="/itemType">戻る</router-link>
    </div>
    <div class="col-sm-6 text-right">
      <button class="btn btn-primary" @click="createItemType()">登録</button>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios'
import ItemTypeBase from './ItemTypeBase'

export default {

  // **************************************************************************
  // * データ
  // **************************************************************************
  data: function() {
    return {
      itemType: {},
      errors: null
    }
  },

  // **************************************************************************
  // * コンポーネント
  // **************************************************************************
  components: {
    ItemTypeBase
  },

  // **************************************************************************
  // * メソッド
  // **************************************************************************
  methods: {

    // ========================================================================
    // 商品種別登録
    // ========================================================================
    createItemType: async function() {

      // HTTPリクエスト送信
      await axios.post('/itemTypes/', this.itemType)
      .then(response => {
        this.itemType = response.data
        this.$router.push('/itemType')
      })

      .catch(error => {
        this.errors = error.response.data.errors
      })
    },
  }
};
</script>
