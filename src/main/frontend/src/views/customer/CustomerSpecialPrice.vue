<template>
  <div class="container">
    <div class="row">
      <div class="col">
        <h1>特別価格 顧客名：{{customer.name}}</h1>

        <table class="table table-bordered table-hover">
          <thead class="thead-dark">
            <tr>
              <th width="80%">名前</th>
              <th width="10%">標準価格</th>
              <th width="10%">特別価格</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(specialPrice, index) in customer.specialPrices" :key="index">
              <td>{{specialPrice.itemType.name}}</td>
              <td>{{specialPrice.itemType.price}}</td>
              <td>
                <input type="number" class="form-control" :class="{ 'is-invalid' : getErrorMessage('price') }" v-model="specialPrice.price">
                <div :class="{ 'invalid-feedback' : getErrorMessage('price') }">{{getErrorMessage('price')}}</div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="row mt-5">
      <div class="col-sm-6">
        <button class="btn btn-primary" @click="$router.back()">戻る</button>
      </div>
      <div class="col-sm-6 text-right">
        <button @click="update()" class="btn btn-warning">更新</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {

  // **************************************************************************
  // * 表示前処理
  // **************************************************************************
  mounted: function() {
    this.getSpecialPrices()
  },

  // **************************************************************************
  // * データ
  // **************************************************************************
  data: function() {
    return {
      customer: {},
      itemTypes: [],
    }
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
    // 商品種別取得
    // ========================================================================
    getItemTypes: async function() {
      let itemTypes
      await axios.get('/itemTypes/')
      .then(function (response) {
        itemTypes = response.data
      })
      this.itemTypes = itemTypes
    },

    // ========================================================================
    // 特別価格取得
    // ========================================================================
    getSpecialPrices: async function() {
      await this.getItemTypes()
      await this.getCustomer()

      if (this.customer.specialPrices.length === this.itemTypes.length) {
        return
      }

      for (let itemType of this.itemTypes) {
        if (!this.existsItemType(itemType)) {
          let specialPrice = {}
          specialPrice.itemType = itemType
          specialPrice.price = itemType.price
          this.customer.specialPrices.push(specialPrice)
        }
      }
    },

    // ========================================================================
    // 商品種別の存在チェック
    // ========================================================================
    existsItemType: function(itemType) {
      for (let specialPrice of this.customer.specialPrices) {
        if (specialPrice.itemType.id === itemType.id) {
          return true
        }
      }
      return false
    },

    // ========================================================================
    // 更新
    // ========================================================================
    update: async function () {
      await axios.put('/customers/' + this.customer.id, this.customer)
      .then(response => {
        this.customer = response.data
        this.$router.push('/')
      })

      .catch(error => {
        this.errors = error.response.data.errors
      })
    },
  }
};
</script>
