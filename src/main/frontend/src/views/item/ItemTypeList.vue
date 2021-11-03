<template>
  <div class="container">
    <div class="row">
      <div class="col">
        <h1>商品種別管理</h1>

        <router-link class="btn btn-primary" to="/itemType/create">新規登録</router-link>

        <table class="table table-bordered table-hover">
          <thead class="thead-dark">
            <tr>
              <th width="10%">ID</th>
              <th width="80%">名前</th>
              <th width="10%">削除</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(itemType, index) in this.itemTypes" :key="index">
              <td>{{itemType.id}}</td>
              <td><router-link :to="{name: 'ItemTypeUpdate', params: {id: itemType.id}}">{{itemType.name}}</router-link></td>
              <td><a @click="deleteItemType(itemType.id)"><i class="far fa-trash-alt fa-lg" aria-hidden="true"></i></a></td>
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
    this.getItemTypes()
  },

  // **************************************************************************
  // * データ
  // **************************************************************************
  data: function() {
    return {
      itemTypes: []
    }
  },

  // **************************************************************************
  // * メソッド
  // **************************************************************************
  methods: {

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
    // 商品種別削除
    // ========================================================================
    deleteItemType: async function(id) {

      await axios.delete('/itemTypes/' + id)
      .then(response => {
        console.log(response)
        this.getItemTypes()
      })

      .catch(error => {
        this.errors = error.response.data.errors
      })
    }
  }
};
</script>
