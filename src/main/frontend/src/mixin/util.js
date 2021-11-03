import axios from 'axios';
import firebase from "firebase/app";

export default {

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

    // ========================================================================
    // 顧客リスト
    // ========================================================================
    customers: {
      get () {
        return this.$store.state.customers
      },
      set (value) {
        this.$store.commit('setCustomers', value)
      }
    },
  },

  // **************************************************************************
  // * メソッド
  // **************************************************************************
  methods: {

    // ========================================================================
    // 画像ファイルソース取得
    // ========================================================================
    getImageSrc: function(image) {

      if (!image || !image.type) {
        return ""
      }

      return URL.createObjectURL(image)
    },

    // ========================================================================
    // エラーメッセージ取得
    // ========================================================================
    getErrorMessage: function(fieldName) {

      // エラーが無い場合
      if (!this.errors) {
        return ""
      }

      for (let error of this.errors) {
        if (error.field === fieldName) {
          return error.defaultMessage
        }
      }
    },

    // ========================================================================
    // ユーザ取得
    // ========================================================================
    getUser: async function() {

      let user = {}
      let id

      // 未ログイン
      if (!firebase.auth().currentUser) {
        this.user = user
        return this.user
      }

      id = firebase.auth().currentUser.uid

      await axios.get('/customers/' + id)
      .then(function (response) {
        user = response.data
      })

      .catch((error) => {
        if (error.response.status == 404) {
          user = {}
        }
      });

      this.user = user
      this.user.token = await firebase.auth().currentUser.getIdToken(true)

      return this.user
    },

    // ========================================================================
    // ユーザ登録入力チェック
    // ========================================================================
    validateUser: async function(user) {

      // HTTPリクエスト送信
      await axios.post('/customers/validate', user)
    },

    // ========================================================================
    // Firebaseユーザ登録
    // ========================================================================
    createFirebaseUser: function(user) {

      // 現在のログインユーザを保持
      let currentUser = firebase.auth().currentUser

      firebase.auth().createUserWithEmailAndPassword(user.email, user.password)
      .then((userCredential) => {
        user.id = userCredential.user.uid
        this.updateDisplayName(user)

        // 作成したユーザにログインしている状態を、元のログイン状態に戻す
        if (currentUser) {
          firebase.auth().updateCurrentUser(currentUser)
        }
      })

      .catch((error) => {
        alert(error.message);
      });
    },

    // ========================================================================
    // firebaseユーザ名設定
    // ========================================================================
    updateDisplayName: async function(user) {

      firebase.auth().currentUser.updateProfile({
        displayName: user.name
      }).then(() => {
        this.createUser(user)
      }).catch((error) => {
        alert(error.message);
      });
    },

    // ========================================================================
    // ユーザ登録
    // ========================================================================
    createUser: async function(user) {

      // HTTPリクエスト送信
      await axios.post('/customers/', user)
      .then(response => {
        user = response.data
      })

      .catch(error => {
        this.errors = error.response.data.errors
      })

      await this.getCustomers()
    },

    // ========================================================================
    // 顧客取得
    // ========================================================================
    getCustomers: async function() {
      let customers
      await axios.get('/customers/')
      .then(function (response) {
        customers = response.data
      })
      this.customers = customers
    },
  }
}
