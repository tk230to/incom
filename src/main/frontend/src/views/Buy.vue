<template>
  <div class="container">
    <h1>購入</h1>

    <div class="form-group row">
      <div v-for="(cartItem, index) in cartItems" class="col-lg-4 col-md-6" :key="index">

        <div class="card" style="width: 18rem;">
          <img :src="getImageSrc(cartItem.item.image)">
          <div class="card-body">
            <h5 class="card-title">{{cartItem.item.name}}</h5>
            <p class="card-text">￥{{cartItem.item.itemType.specialPrice.toLocaleString()}}</p>
            <div class="form-inline">
              <label>数量</label>
              <input class="form-control" type="number" v-model.number="cartItem.quantity" />
            </div>
            <button @click="addCart(cartItem)" class="btn btn-primary d-block mx-auto m-2 px-3 py-2" :disabled="cartItem.quantity <= 0">カートに入れる</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  mounted: function() {
    this.getItems()
    this.getUser()
  },

  // **************************************************************************
  // * データ
  // **************************************************************************
  data: function() {
    return {
      cartItems: [],
    };
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
    // カートに追加処理
    // ========================================================================
    addCart: async function(addCartItem) {

      this.mergeCart(addCartItem)

      // HTTPリクエスト送信
      await axios.post('customers/', this.user)
      .then(response => {
        this.user = response.data
      })
    },

    // ========================================================================
    // カート内の同じ商品をマージ
    // ========================================================================
    mergeCart: function(addCartItem) {

      let cartItem = this.user.cartItems.find((cartItem) => cartItem.item.id === addCartItem.item.id)
      if (cartItem) {
        cartItem.quantity += addCartItem.quantity
        return
      }

      this.user.cartItems.push(addCartItem);
    },

    // ========================================================================
    // 商品取得
    // ========================================================================
    getItems: async function() {
      let items
      await axios.get('items/')
      .then(function (response) {
        items = response.data
      })

      this.cartItems = []
      for (let item of items) {

        // カート用に変換
        let cartItem = {}
        cartItem.item = item
        cartItem.quantity = 0
        this.cartItems.push(cartItem)
      }
    },
  },
};
</script>

<style>
img {
  width: 17rem;
  height: 17rem;
  object-fit: contain;
}
</style>