import firebase from 'firebase/app';
import axios from 'axios';

export default function setup() {

  // ========================================================================
  // リクエスト時処理
  // ========================================================================
  axios.interceptors.request.use(
    async request => {

      request.data = await deepClone(request.data)
      await encodeImage(request.data)

      let token;
      if (firebase.auth().currentUser) {
        token = await firebase.auth().currentUser.getIdToken(true)
      }

      if (token) {
        request.headers.Authorization = `Bearer ${token}`;
      }

      return request;
    },

    error => {
      return Promise.reject(error);
    }
  );

  // ========================================================================
  // レスポンス時処理
  // ========================================================================
  axios.interceptors.response.use(
    async response => {
      await decodeImage(response.data)
      return response
    },

    error => {
      return Promise.reject(error);
    }
  );

  // ========================================================================
  // リクエスト先デフォルトURL
  // ========================================================================
  axios.defaults.baseURL = "http://localhost:8080"
}

// ========================================================================
// 画像エンコード
// ========================================================================
async function encodeImage(data) {
  if (typeof data === 'object') {
   if (Array.isArray(data)) {
     for (let element of data) {
       await encodeImage(element)
     }
   } else {
     for (let key in data) {
       if (key === "image") {
         data.image = await base64Encode(data.image)
       } else {
         await encodeImage(data[key])
       }
     }
   }
  }
}

// ========================================================================
// Base64エンコード
// ========================================================================
function base64Encode(...parts) {

  if (!parts) {
   return null
  }

  return new Promise(resolve => {
   const reader = new FileReader();
   reader.onload = () => {
     const offset = reader.result.indexOf(",") + 1;
     resolve(reader.result.slice(offset));
   };
   reader.readAsDataURL(new Blob(parts));
  });
}

// ========================================================================
// 画像デコード
// ========================================================================
async function decodeImage(data) {
  if (typeof data === 'object') {
    if (Array.isArray(data)) {
      for (let element of data) {
        await decodeImage(element)
      }
    } else {
      for (let key in data) {
        if (key === "image") {
          data.image = await base64DecodeAsBlob(data.image, data.imageType)
        } else {
          await decodeImage(data[key])
        }
      }
    }
  }
}

// ========================================================================
// Base64デコード
// ========================================================================
function base64DecodeAsBlob(text, type = "text/plain;charset=UTF-8") {
  return fetch(`data:${type};base64,` + text).then(response => response.blob());
}

// ========================================================================
// ディープコピー
// https://qiita.com/knhr__/items/d7de463bf9013d5d3dc0
// ========================================================================
async function deepClone(obj) {

  const channel = new MessageChannel()
  const inPort = channel.port1
  const outPort = channel.port2

  return new Promise(resolve => {
    inPort.onmessage = data => {
        resolve(data.data)
    }
    outPort.postMessage(obj)
  })
}
