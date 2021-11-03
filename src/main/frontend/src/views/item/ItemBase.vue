<template>
  <div class="container">
    <h1>{{ title }}</h1>

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">名前 <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <input type="text" class="form-control" :class="{ 'is-invalid' : getErrorMessage('name') }" v-model="item.name">
        <div :class="{ 'invalid-feedback' : getErrorMessage('name') }">{{getErrorMessage('name')}}</div>
      </div>
    </div>
    <div class="form-group row">
      <label class="col-sm-2 col-form-label">商品種別 <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <select class="form-control" :class="{ 'is-invalid' : getErrorMessage('price') }" v-model="item.itemType.id">
          <option v-for = "(itemType, index) in this.itemTypes" :key = "index" :value = "itemType.id">
            {{itemType.name}}
          </option>
        </select>
        <div :class="{ 'invalid-feedback' : getErrorMessage('price') }">{{getErrorMessage('itemType')}}</div>
      </div>
    </div>
    <div class="form-group row">
      <label class="col-sm-2 col-form-label">画像 <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10 bg-light" @drop.prevent="dropImage($event)">
        <input type="file" class="w-100 h-100 position-absolute" style="opacity: 0" @change="changeImage($event)" :class="{ 'is-invalid' : getErrorMessage('image') }">

        <p>クリックでファイル選択</p>
        <p>または画像をドラッグ＆ドロップ</p>
        <img v-show="getImageSrc(item.image)" :src="getImageSrc(item.image)" class="w-25">
        <p v-show="file.name"> {{file.name}} </p>

        <div :class="{ 'invalid-feedback' : getErrorMessage('image') }">{{getErrorMessage('image')}}</div>
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
      itemTypes: [],
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
    item: Object,
    errors: Array
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
    // 画像ファイルドラッグ＆ドロップ
    // ========================================================================
    dropImage: function(event) {
      this.loadImage(event.dataTransfer.files);
    },

    // ========================================================================
    // 画像ファイル変更
    // ========================================================================
    changeImage: function(event) {
        this.loadImage(event.target.files);
    },

    // ========================================================================
    // 画像ファイルプレビュー表示
    // ========================================================================
    loadImage: function(files) {

      // 1ファイルのみ選択可能
      if (files.length !== 1 || files[0].type.indexOf("image") !== 0) {
        return;
      }

      const file = files[0];
      this.file.name = file.name;
      this.item.image = file
      this.item.imageType = file.type
    },
  }
};
</script>
