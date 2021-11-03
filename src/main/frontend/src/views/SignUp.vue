<template>
  <div class="container">
    <h1>アカウントを作成</h1>

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

    <div class="form-group row">
      <label class="col-sm-2 col-form-label">パスワード <span class="badge badge-danger">必須</span></label>
      <div class="col-sm-10">
        <input type="password" class="form-control" :class="{ 'is-invalid' : getErrorMessage('password') }" v-model="user.password">
        <div :class="{ 'invalid-feedback' : getErrorMessage('password') }">{{getErrorMessage('password')}}</div>
      </div>
    </div>

    <ul class="firebaseui-idp-list">
      <li class="firebaseui-list-item">
        <button  @click="signUp" class="firebaseui-idp-button mdl-button mdl-js-button mdl-button--raised firebaseui-idp-password firebaseui-id-idp-button" data-provider-id="password" data-upgraded=",MaterialButton">
          <span class="firebaseui-idp-icon-wrapper"><img class="firebaseui-idp-icon" alt="" src="https://www.gstatic.com/firebasejs/ui/2.0.0/images/auth/mail.svg"></span>
          <span class="firebaseui-idp-text firebaseui-idp-text-long">メールで登録</span>
          <span class="firebaseui-idp-text firebaseui-idp-text-short">メールアドレス</span>
        </button>
      </li>
      <li class="firebaseui-list-item">
        <button @click="gSignUp" class="firebaseui-idp-button mdl-button mdl-js-button mdl-button--raised firebaseui-idp-google firebaseui-id-idp-button" data-provider-id="google.com" data-upgraded=",MaterialButton">
          <span class="firebaseui-idp-icon-wrapper"><img class="firebaseui-idp-icon" alt="" src="https://www.gstatic.com/firebasejs/ui/2.0.0/images/auth/google.svg"></span>
          <span class="firebaseui-idp-text firebaseui-idp-text-long">Google で登録</span>
          <span class="firebaseui-idp-text firebaseui-idp-text-short">Google</span>
        </button>
      </li>
    </ul>

    <hr>

    <ul class="firebaseui-idp-list">
      <li class="firebaseui-list-item">
        アカウントをお持ちですか？
      </li>
      <li class="firebaseui-list-item">
        <router-link to="/login">
          既にあるアカウントでログインする
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
import firebase from 'firebase/app';

export default {

  // **************************************************************************
  // * データ
  // **************************************************************************
  data: function() {
    return {
      error: null,
      errors: null
    };
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
    // サインアップ
    // ========================================================================
    signUp: function() {

      this.validateUser(this.user)
      .then(() => {
        this.createFirebaseUser(this.user)
        this.$router.push('/')
      })

      .catch((error) => {
        this.errors = error.response.data.errors
      });
    },

    // ========================================================================
    // Googleサインアップ
    // ========================================================================
    gSignUp: function() {

      const provider = new firebase.auth.GoogleAuthProvider();

      firebase.auth().signInWithPopup(provider)
      .then((userCredential) => {
        this.user.id = userCredential.user.uid
        this.user.name = userCredential.user.displayName
        this.user.email = userCredential.user.email
        this.createUser()
      })

      .catch((error) => {
        this.error = error
      });
    },
  }
};
</script>
