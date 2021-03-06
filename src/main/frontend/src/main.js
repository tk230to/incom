import '@babel/polyfill'
import 'mutationobserver-shim'
import Vue from 'vue'
import axios from './plugins/axios'
import './plugins/bootstrap-vue'
import App from './App.vue'
import router from './router'
import store from './store'
import util from './mixin/util'

axios()

// fontawesome
import '@fortawesome/fontawesome-free/js/fontawesome';
import '@fortawesome/fontawesome-free/js/solid';
import '@fortawesome/fontawesome-free/js/regular';

// Firebase
import firebase from 'firebase/app'
import 'firebase/app'
import 'firebase/firestore'
import 'firebase/auth'

Vue.config.productionTip = false

// Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyBDWCDPl3Hw8KVJYB4bD1LGodwxSYb1P0A",
  authDomain: "tk-income-360cd.firebaseapp.com",
  databaseURL: "https://tk-income-360cd.firebaseio.com",
  projectId: "tk-income-360cd",
  storageBucket: "tk-income-360cd.appspot.com",
  messagingSenderId: "725477132553",
  appId: "1:725477132553:web:73bb72a76fe194258cabd8",
  measurementId: "G-Z4H6WER6K3"
};

// Initialize Firebase
firebase.initializeApp(firebaseConfig);

// Mixin
Vue.mixin(util)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
