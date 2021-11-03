<template>
  <div class="container">
    <div class="row">
      <div class="col">
        <h1>顧客管理</h1>

        <router-link class="btn btn-primary" to="/customer/create">新規登録</router-link>

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
export default {

  // **************************************************************************
  // * 表示前処理
  // **************************************************************************
  mounted: function() {
    this.getCustomers()
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
};
</script>
