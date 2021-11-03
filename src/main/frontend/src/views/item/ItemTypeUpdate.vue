<template>
<div class="container">
  <ItemTypeBase title="商品種別更新画面" :itemType="itemType" :errors="errors"></ItemTypeBase>

  <div class="row">
    <div class="col-sm-6">
      <router-link class="btn btn-primary" to="/itemType">戻る</router-link>
    </div>
    <div class="col-sm-6 text-right">
      <button class="btn btn-primary" @click="updateItemType()">更新</button>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios'
import ItemTypeBase from './ItemTypeBase'

export default {

    // **************************************************************************
    // * 表示前処理
    // **************************************************************************
    mounted: function() {
      this.getItemType()
    },

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
    // 商品種別取得
    // ========================================================================
    getItemType: async function() {

      // HTTPリクエスト送信
      let itemType
      await axios.get('/itemTypes/' + this.$route.params.id)
      .then(function (response) {
        itemType = response.data
      })

      this.itemType = itemType
    },

    // ========================================================================
    // 商品種別更新
    // ========================================================================
    updateItemType: async function() {

      // HTTPリクエスト送信
      await axios.put('/itemTypes/' + this.itemType.id, this.itemType)
      .then(response => {
        this.itemType = response.data
        this.$router.push('/itemType')
      })

      .catch(error => {
        this.errors = error.response.data.errors
      })
    },
  },
};
</script>
