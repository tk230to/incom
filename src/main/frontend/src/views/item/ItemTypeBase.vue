<template>
  <div class="container">
    <h1>{{ title }}</h1>

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">名前 <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <input type="text" class="form-control" :class="{ 'is-invalid' : getErrorMessage('name') }" v-model="itemType.name">
        <div :class="{ 'invalid-feedback' : getErrorMessage('name') }">{{getErrorMessage('name')}}</div>
      </div>
    </div>
    <div class="form-group row">
      <label class="col-sm-2 col-form-label">金額 <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <input type="number" class="form-control" :class="{ 'is-invalid' : getErrorMessage('price') }" v-model="itemType.price">
        <div :class="{ 'invalid-feedback' : getErrorMessage('price') }">{{getErrorMessage('price')}}</div>
      </div>
    </div>
  </div>
</template>

<script>

export default {

  // **************************************************************************
  // * 引数
  // **************************************************************************
  props: {
    title: String,
    itemType: Object,
    errors: Array
  },
};
</script>
