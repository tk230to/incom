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
      <label class="col-sm-2 col-form-label">パスワード <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <input type="password" class="form-control" :class="{ 'is-invalid' : getErrorMessage('password') }" v-model="customer.password">
        <div :class="{ 'invalid-feedback' : getErrorMessage('password') }">{{getErrorMessage('password')}}</div>
      </div>
    </div>

  </div>
</template>

<script>

export default {

  // **************************************************************************
  // * データ
  // **************************************************************************
  data: function() {
    return {
      file: {
        name: null
      },
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
};
</script>
