<template>
<div class="container">
  <ItemBase title="商品更新画面" :item="item" :errors="errors"></ItemBase>

  <div class="row">
    <div class="col-sm-6">
      <router-link class="btn btn-primary" to="/item">戻る</router-link>
    </div>
    <div class="col-sm-6 text-right">
      <button class="btn btn-primary" @click="updateItem()">更新</button>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios'
import ItemBase from './ItemBase'

export default {

    // **************************************************************************
    // * 表示前処理
    // **************************************************************************
    mounted: function() {
      this.getItem()
    },

    // **************************************************************************
  // * データ
  // **************************************************************************
  data: function() {
    return {
      item: {
        itemType: {},
      },
      errors: null
    }
  },

  // **************************************************************************
  // * コンポーネント
  // **************************************************************************
  components: {
    ItemBase
  },

  // **************************************************************************
  // * メソッド
  // **************************************************************************
  methods: {

    // ========================================================================
    // 商品取得
    // ========================================================================
    getItem: async function() {

      // HTTPリクエスト送信
      let item
      await axios.get('/items/' + this.$route.params.id)
      .then(function (response) {
        item = response.data
      })

      this.item = item
    },

    // ========================================================================
    // 商品更新
    // ========================================================================
    updateItem: async function() {

      // HTTPリクエスト送信
      await axios.put('/items/' + this.item.id, this.item)
      .then(response => {
        this.item = response.data
        this.$router.push('/item')
      })

      .catch(error => {
        this.errors = error.response.data.errors
      })
    },
  },
};
</script>
