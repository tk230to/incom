<template>
  <div>
    <header>
      <b-navbar toggleable="lg" type="dark" class="indigo fixed-top">
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-navbar-brand to="/">ロゴ</b-navbar-brand>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item active-class="active" to="/about">初めての方へ</b-nav-item>
            <b-nav-item active-class="active" to="/rental">レンタルする</b-nav-item>
            <b-nav-item active-class="active" to="/buy">購入する</b-nav-item>
            <b-nav-item v-if="isAdmin()" active-class="active" to="/item">商品管理</b-nav-item>
            <b-nav-item v-if="isAdmin() || isSales()" active-class="active" to="/customer">顧客管理</b-nav-item>
          </b-navbar-nav>

          <b-navbar-nav class="ml-auto">
            <b-nav-item v-if="!isLogin()" to="/signup">
              <i class="fas fa-user-plus"></i>新規登録
            </b-nav-item>
            <b-nav-item v-if="!isLogin()" to="/login">
              <i class="fas fa-sign-in-alt"></i> ログイン
            </b-nav-item>
            <b-nav-item v-else @click="logoff">
              <i class="fas fa-sign-out-alt"></i> ログオフ
            </b-nav-item>
            <b-nav-item active-class="active" to="/mypage">
              <i class="fas fa-user-cog"></i> マイページ
            </b-nav-item>
            <b-nav-item active-class="active" to="/cart">
              <i class="fas fa-shopping-cart"></i>
              <span v-if="this.getCartItemCount !== 0" class="badge badge-danger rounded-circle ml-1">{{this.getCartItemCount}}</span>
            </b-nav-item>
            <b-nav-item active-class="active" to="/contact">
              <i class="fas fa-question-circle"></i> 問合せ
            </b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </header>

    <main>
      <router-view />
    </main>

    <footer class="indigo">
      <div class="container">
        <div class="row d-flex justify-content-between">
          <div>
            <router-link class="text-white p-3" to="/company">会社紹介</router-link>
            <router-link class="text-white p-3" to="/privacy">プライバシーポリシー</router-link>
          </div>
          <div>© 2020 Copyright income.com</div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
import firebase from "firebase/app";
import "./assets/main.css";

export default {

  // **************************************************************************
  // * メソッド
  // **************************************************************************
  methods: {

    // ========================================================================
    // ログオフ
    // ========================================================================
    logoff: function() {
      firebase.auth().signOut();
      this.user = {}
      this.$router.push("/login");
    },

    // ========================================================================
    // ログイン有無取得
    // ========================================================================
    isLogin: function() {
      if (!firebase.auth().currentUser) {
        return false
      }
      if (firebase.auth().currentUser.isAnonymous) {
        return false
      }
      return true;
    },
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

    // ========================================================================
    // カート内商品数取得
    // ========================================================================
    getCartItemCount: function() {
      if (this.user.cartItems) {
        return this.user.cartItems.length;
      }
      return 0
    },
  },
};
</script>
<style>
main {
  margin-top: 60px;
}
</style>
