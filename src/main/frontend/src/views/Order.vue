<template>
<div class="container">
  <h1>ご注文</h1>

  <div class="row">
    <h2>小計: {{ subtotalPrice }}</h2>
  </div>

  <div class="form-group row">
    <div v-for="(cartItem, index) in this.user.cartItems"
      class="col-lg-4 col-md-6" :key="index">
      <div class="card" style="width: 18rem;">
        <img class="card-img" :src="getImageSrc(cartItem.item.image)">
        <div class="card-body">
          <h5 class="card-title">商品：{{cartItem.item.name}}</h5>
          <p class="card-text text-danger">金額：￥{{cartItem.item.itemType.specialPrice.toLocaleString()}}</p>
          <p class="card-text">数量：{{cartItem.quantity}}</p>
        </div>
      </div>
    </div>
  </div>

  <h1>ご利用日程</h1>

  <div class="form-group row">
    <label class="col-sm-2 col-form-label">納品希望日 <span class="badge badge-danger">必須</span></label>
    <div class="col-sm-10">
      <input type="date" class="form-control" :class="{ 'is-invalid' : getErrorMessage('deliveryDate') }" v-model="salesOrder.deliveryDate">
      <div :class="{ 'invalid-feedback' : getErrorMessage('deliveryDate') }">{{getErrorMessage('deliveryDate')}}</div>
    </div>

    <label class="col-sm-2 col-form-label">返却予定日 <span class="badge badge-danger">必須</span></label>
    <div class="col-sm-10">
      <input type="date" class="form-control" :class="{ 'is-invalid' : getErrorMessage('returnDate') }" v-model="salesOrder.returnDate">
      <div :class="{ 'invalid-feedback' : getErrorMessage('returnDate') }">{{getErrorMessage('returnDate')}}</div>
    </div>
  </div>

  <h1>納品先</h1>

  <div class="row">
    <div class="text-danger">{{getErrorMessage('address')}}</div>
    <div v-for="(address, index) in this.user.addresses" class="col-lg-4 col-md-6" :key="index">
      <div class="card" style="width: 18rem;">
        <div class="card-header">
          <h5 class="card-title">{{address.name}}</h5>
        </div>
        <div class="card-body">
          <div class="card-text">
            〒{{address.postalCode}}<br>
            {{address.level1}} {{address.level2}}<br>
            {{address.line1}}<br>
            {{address.line2}}
          </div>
          <div class="card-text">
            <button @click="setAddress(address)"
              class="btn btn-secondary d-block">この住所に送付</button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-sm-12">
      <router-link class="btn btn-primary" to="/address/create">納品先を追加</router-link>
    </div>
  </div>

  <div class="row mt-5">
    <div class="col-sm-6">
      <button class="btn btn-primary" @click="$router.back()">戻る</button>
    </div>
    <div class="col-sm-6 text-right">
      <button @click="createOrder()" class="btn btn-warning">注文確定</button>
    </div>
  </div>

</div>
</template>

<script>
import axios from 'axios';

export default {

  // **************************************************************************
  // * 初期処理
  // **************************************************************************
  mounted: function() {
    this.getUser()
  },

  // **************************************************************************
  // * データ
  // **************************************************************************
  data: function() {
    return {
      errors: null,
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

    // ========================================================================
    // 注文
    // ========================================================================
    salesOrder: {
      get () {
        return this.$store.state.salesOrder
      },
      set (value) {
        this.$store.commit('setSalesOrder', value)
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
    // 納品先設定
    // ========================================================================
    setAddress: function(address) {
      this.salesOrder.address = address
    },

    // ========================================================================
    // 注文確定
    // ========================================================================
    createOrder: async function() {
      this.salesOrder.customer = this.user
      this.salesOrder.salesOrderDetails = []
      for (let cartItem of this.user.cartItems) {
        let salesOrderDetail = {}
        salesOrderDetail.item = cartItem.item
        salesOrderDetail.quantity = cartItem.quantity
        this.salesOrder.salesOrderDetails.push(salesOrderDetail)
      }

      await axios.post('/salesorders/', this.salesOrder)
      .then(() => {
        this.getUser()
        this.$router.push('/')
      })
      .catch(error => {
        this.errors = error.response.data.errors
      })
    },
  },
};
</script>
<style>
img.card-img {
  width: 17rem;
  height: 17rem;
  object-fit: contain;
}
</style>
