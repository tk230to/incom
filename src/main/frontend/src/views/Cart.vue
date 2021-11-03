<template>
  <div class="container">
    <h1>カート</h1>

    <div class="row">
      <h2>小計: {{ subtotalPrice }}</h2>
    </div>

    <div class="form-group row">
      <div v-for="(cartItem, index) in this.user.cartItems" class="col-lg-4 col-md-6" :key="index">
        <div class="card" style="width: 18rem;">
          <img class="card-img" :src="getImageSrc(cartItem.item.image)">
          <div class="card-body">
            <h5 class="card-title">{{cartItem.item.name}}</h5>
            <p class="card-text">￥{{cartItem.item.itemType.specialPrice.toLocaleString()}}</p>
            <div class="form-inline">
              <label>数量</label>
              <input @change="updateItem(index)" class="form-control" type="number" min="1" v-model.number="cartItem.quantity" />
            </div>
            <button @click="deleteItem(cartItem.id)" class="btn btn-secondary d-block mx-auto m-2 px-3 py-2">削除</button>
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-sm-6">
        <router-link class="btn btn-primary" to="/order">ご注文に進む</router-link>
      </div>
    </div>

  </div>
</template>

<script>
import axios from 'axios';

export default {

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

    subtotalPrice: function() {
      let subtotal = 0
      if (this.user.cartItems) {
        for (let cartItem of this.user.cartItems) {
          subtotal = subtotal + cartItem.item.itemType.specialPrice * cartItem.quantity
        }
      }
      return subtotal
    },
  },

  // **************************************************************************
  // * メソッド
  // **************************************************************************
  methods: {

    // ========================================================================
    // カート商品削除
    // ========================================================================
    deleteItem: async function(id) {
      await axios.delete('/cartitems/' + id)
      .then(response => {
        console.log(response)
        this.getUser()
      })

      .catch(error => {
        this.errors = error.response.data.errors
      })
    },

    // ========================================================================
    // カート商品更新
    // ========================================================================
    updateItem: async function(index) {
      let cartItem = this.user.cartItems[index]
      cartItem.customer = {}
      cartItem.customer.id = this.user.id
      await axios.put('/cartitems/' + cartItem.id, cartItem)
      .then(response => {
        console.log(response)
        this.getUser()
      })

      .catch(error => {
        this.errors = error.response.data.errors
      })
    },
  }
};
</script>
<style>
img.card-img {
  width: 17rem;
  height: 17rem;
  object-fit: contain;
}
</style>
