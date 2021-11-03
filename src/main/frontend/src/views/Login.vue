<template>
  <div class="container signup">
    <h1>ログイン</h1>

    <div v-if="error" class="alert alert-danger">
      メールアドレスまたはパスワードが誤っています。
    </div>

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">メールアドレス <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <input type="email" class="form-control" :class="{ 'is-invalid' : getErrorMessage('email') }" v-model="user.email">
        <div :class="{ 'invalid-feedback' : getErrorMessage('email') }">{{getErrorMessage('email')}}</div>
      </div>
    </div>

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">パスワード <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <input type="password" class="form-control" :class="{ 'is-invalid' : getErrorMessage('password') }" v-model="user.password">
        <div :class="{ 'invalid-feedback' : getErrorMessage('password') }">{{getErrorMessage('password')}}</div>
      </div>
    </div>

    <ul class="firebaseui-idp-list">
      <li class="firebaseui-list-item">
        <button  @click="login" class="firebaseui-idp-button mdl-button mdl-js-button mdl-button--raised firebaseui-idp-password firebaseui-id-idp-button" data-provider-id="password" data-upgraded=",MaterialButton">
          <span class="firebaseui-idp-icon-wrapper"><img class="firebaseui-idp-icon" alt="" src="https://www.gstatic.com/firebasejs/ui/2.0.0/images/auth/mail.svg"></span>
          <span class="firebaseui-idp-text firebaseui-idp-text-long">メールでログイン</span>
          <span class="firebaseui-idp-text firebaseui-idp-text-short">メールアドレス</span>
        </button>
      </li>
      <li class="firebaseui-list-item">
        <button @click="glogin" class="firebaseui-idp-button mdl-button mdl-js-button mdl-button--raised firebaseui-idp-google firebaseui-id-idp-button" data-provider-id="google.com" data-upgraded=",MaterialButton">
          <span class="firebaseui-idp-icon-wrapper"><img class="firebaseui-idp-icon" alt="" src="https://www.gstatic.com/firebasejs/ui/2.0.0/images/auth/google.svg"></span>
          <span class="firebaseui-idp-text firebaseui-idp-text-long">Google でログイン</span>
          <span class="firebaseui-idp-text firebaseui-idp-text-short">Google</span>
        </button>
      </li>
    </ul>

    <hr>

    <ul class="firebaseui-idp-list">
      <li class="firebaseui-list-item">
        初めてのご利用ですか？
      </li>
      <li class="firebaseui-list-item">
        <router-link to="/signup">
          アカウントを作成する
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
import firebase from 'firebase/app';
require("firebaseui-ja/dist/firebaseui.css");

export default {

  // **************************************************************************
  // * データ
  // **************************************************************************
  data: function() {
    return {
      error: null
    }
  },

  // **************************************************************************
  // * 算出プロパティ
  // **************************************************************************
  computed: {

    // ========================================================================
    // ユーザ
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
    // ログイン
    // ========================================================================
    login: async function() {

      // メールアドレスとパスワードを使って認証
      firebase.auth().signInWithEmailAndPassword(this.user.email, this.user.password)
      .then(async () => {
        await this.getUser()
        this.$router.push('/')
      })

      .catch((error) => {
        this.error = error
        console.log(error.message);
      });
    },

    // ========================================================================
    // Googleログイン
    // ========================================================================
    glogin: async function() {

      const provider = new firebase.auth.GoogleAuthProvider();
      firebase.auth().signInWithPopup(provider)
      .then(async () => {
        await this.getUser()
        this.$router.push('/')
      })

      .catch((error) => {
        this.error = error
        console.log(error.message);
      });
    },
  },
};
</script>
