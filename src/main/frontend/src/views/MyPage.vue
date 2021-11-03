<template>
  <div class="container">
    <h1>マイページ</h1>

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">ユーザ名 <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <input type="text" class="form-control" :class="{ 'is-invalid' : getErrorMessage('name') }" v-model="user.name">
        <div :class="{ 'invalid-feedback' : getErrorMessage('name') }">{{getErrorMessage('name')}}</div>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">メールアドレス <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <input type="email" class="form-control" :class="{ 'is-invalid' : getErrorMessage('email') }" v-model="user.email">
        <div :class="{ 'invalid-feedback' : getErrorMessage('email') }">{{getErrorMessage('email')}}</div>
      </div>
    </div>

    <h1>納品先</h1>

    <div class="row">
      <div v-for="(address, index) in this.user.addresses" class="col-lg-4 col-md-6" :key="index">
        <div class="card" style="width: 18rem;">
          <div class="card-header">
            <h5 class="card-title">{{address.name}}</h5>
          </div>
          <div class="card-body">
            <p class="card-text">〒{{address.postalCode}}</p>
            <p class="card-text">{{address.level1}} {{address.level2}}</p>
            <p class="card-text">{{address.line1}}</p>
            <p class="card-text">{{address.line2}}</p>
            <p class="card-text"><router-link class="btn btn-primary" :to="{name: 'AddressUpdate', params: {id: address.id}}">編集</router-link></p>
            <p class="card-text"><button @click="deleteAddress(address.id)" class="btn btn-secondary d-block">削除</button></p>
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-sm-12">
        <router-link class="btn btn-primary" to="/address/create">納品先を追加</router-link>
      </div>
    </div>

    <div class="row">
      <div class="col-sm-6">
        <button class="btn btn-primary" @click="cancel()">解約</button>
      </div>
      <div class="col-sm-6 text-right">
        <button class="btn btn-primary" @click="update()">更新</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import firebase from "firebase/app";

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
      errors: null
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
  },

  // **************************************************************************
  // * メソッド
  // **************************************************************************
  methods: {

    // ========================================================================
    // 納品先削除
    // ========================================================================
    deleteAddress: async function(id) {

      await axios.delete('/addresses/' + id)
      .then(response => {
        console.log(response)
        this.getUser()
      })

      .catch(error => {
        this.errors = error.response.data.errors
      })
    },

    // ========================================================================
    // 更新
    // ========================================================================
    update: async function () {
      await axios.put('/customers/' + this.user.id, this.user)
      .then(response => {
        this.user = response.data
        this.$router.push('/')
      })

      .catch(error => {
        this.errors = error.response.data.errors
      })
    },

    // ========================================================================
    // 解約
    // ========================================================================
    cancel: async function () {

      firebase.auth().currentUser.delete().then(() => {
        // User deleted.
      }).catch(error => {
        console.log(error)
      });
    },
  }
};
</script>