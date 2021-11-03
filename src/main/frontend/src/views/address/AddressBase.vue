<template>
  <div class="container">
    <h1>{{ title }}</h1>

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">宛名 <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <input type="text" name="name" autocomplete="name" class="form-control" :class="{ 'is-invalid' : getErrorMessage('name') }" v-model="address.name" />
        <div :class="{ 'invalid-feedback' : getErrorMessage('name') }">{{getErrorMessage('name')}}</div>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">郵便番号 <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <input type="text" name="postal-code" autocomplete="postal-code" class="form-control" :class="{ 'is-invalid' : getErrorMessage('postalCode') }" v-model="address.postalCode" />
        <div :class="{ 'invalid-feedback' : getErrorMessage('postalCode') }">{{getErrorMessage('postalCode')}}</div>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">都道府県 <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <input type="text" name="level1" autocomplete="address-level1" class="form-control" :class="{ 'is-invalid' : getErrorMessage('level1') }" v-model="address.level1" />
        <div :class="{ 'invalid-feedback' : getErrorMessage('level1') }">{{getErrorMessage('level1')}}</div>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">市区町村 <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <input type="text" name="level2" autocomplete="address-level2" class="form-control" :class="{ 'is-invalid' : getErrorMessage('level2') }" v-model="address.level2" />
        <div :class="{ 'invalid-feedback' : getErrorMessage('level2') }">{{getErrorMessage('level2')}}</div>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">番地 <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <input type="text" name="line1" autocomplete="address-line1" class="form-control" :class="{ 'is-invalid' : getErrorMessage('line1') }" v-model="address.line1" />
        <div :class="{ 'invalid-feedback' : getErrorMessage('line1') }">{{getErrorMessage('line1')}}</div>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">マンション名</label>
      <div class="col-sm-10">
        <input type="text" name="line2" autocomplete="address-line2" class="form-control" :class="{ 'is-invalid' : getErrorMessage('line2') }" v-model="address.line2" />
        <div :class="{ 'invalid-feedback' : getErrorMessage('line2') }">{{getErrorMessage('line2')}}</div>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">電話番号</label>
      <div class="col-sm-10">
        <input type="text" name="tel" autocomplete="tel" class="form-control" :class="{ 'is-invalid' : getErrorMessage('tel') }" v-model="address.tel" />
        <div :class="{ 'invalid-feedback' : getErrorMessage('tel') }">{{getErrorMessage('tel')}}</div>
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
    address: Object,
    errors: Array
  },
};
</script>
