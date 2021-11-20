<template>
  <div class="container">
    <h1>{{ title }}</h1>

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">名前 <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <input type="text" class="form-control" :class="{ 'is-invalid' : getErrorMessage('name') }" v-model="customer.name">
        <div :class="{ 'invalid-feedback' : getErrorMessage('name') }">{{getErrorMessage('name')}}</div>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">メールアドレス <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <input type="text" class="form-control" :class="{ 'is-invalid' : getErrorMessage('email') }" v-model="customer.email">
        <div :class="{ 'invalid-feedback' : getErrorMessage('email') }">{{getErrorMessage('email')}}</div>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">ロール <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <select class="form-control" :class="{ 'is-invalid' : getErrorMessage('role') }" v-model="customer.role.id">
          <option v-for = "(role, index) in this.roles" :key = "index" :value = "role.id">
            {{role.name}}
          </option>
        </select>
        <div :class="{ 'invalid-feedback' : getErrorMessage('price') }">{{getErrorMessage('itemType')}}</div>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">パスワード <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <input type="password" class="form-control" :class="{ 'is-invalid' : getErrorMessage('password') }" v-model="customer.password">
        <div :class="{ 'invalid-feedback' : getErrorMessage('password') }">{{getErrorMessage('password')}}</div>
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
    this.getRoles()
  },

  // **************************************************************************
  // * データ
  // **************************************************************************
  data: function() {
    return {
      roles: [],
    }
  },

  // **************************************************************************
  // * 引数
  // **************************************************************************
  props: {
    title: String,
    customer: Object,
    errors: Array
  },

  // **************************************************************************
  // * メソッド
  // **************************************************************************
  methods: {

    // ========================================================================
    // ロール取得
    // ========================================================================
    getRoles: async function() {
      let roles
      await axios.get('/roles/')
      .then(function (response) {
        roles = response.data
      })
      this.roles = roles
    },
  },
};
</script>
