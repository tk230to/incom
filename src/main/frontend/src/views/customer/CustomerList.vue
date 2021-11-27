<template>
  <div class="container">
    <div class="row">
      <div class="col">
        <h1>顧客管理</h1>
      </div>
    </div>

    <div class="row">
      <div class="col">
        <input type="radio" id="name" value="name" v-model="searchCondition.mode">
        <label for="name">顧客名で検索</label>
        <br>
        <input type="radio" id="tel" value="tel" v-model="searchCondition.mode">
        <label for="tel">電話番号で検索</label>
        <br>
        <input v-show="searchCondition.mode == 'name'" type="text" v-model="searchCondition.name">
        <input v-show="searchCondition.mode == 'tel'" type="text" v-model="searchCondition.tel">
        <button class="btn btn-primary" @click="search()">検索</button>
      </div>
    </div>

    <div class="row">
      <div class="col">
        <router-link class="btn btn-primary" to="/customer/create">新規登録</router-link>
      </div>
    </div>

    <div class="row">
      <div class="col">
        <table class="table table-bordered table-hover">
          <thead class="thead-dark">
            <tr>
              <th width="90%">名前</th>
              <th width="10%">特別価格設定</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(customer, index) in this.customers" :key="index">
              <td><router-link :to="{name: 'CustomerUpdate', params: {id: customer.id}}">{{customer.name}}</router-link></td>
              <td><router-link :to="{name: 'CustomerSpecialPrice', params: {id: customer.id}}">特別価格設定</router-link></td>
            </tr>
          </tbody>
        </table>
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
    this.getCustomers()
  },

  // **************************************************************************
  // * データ
  // **************************************************************************
  data: function() {
    return {
      searchCondition: {
        mode: "name",
      },
    }
  },

  // **************************************************************************
  // * 算出プロパティ
  // **************************************************************************
  computed: {

    // ========================================================================
    // 顧客リスト
    // ========================================================================
    customers: {
      get () {
        return this.$store.state.customers
      },
      set (value) {
        this.$store.commit('setCustomers', value)
      }
    },

  },

  // **************************************************************************
  // * メソッド
  // **************************************************************************
  methods: {

    // ========================================================================
    // 検索
    // ========================================================================
    search: async function() {

      await axios.get('customers/search', { params:this.searchCondition })
      .then(response => {
        this.customers = response.data
      })
    },
  },
};
</script>
