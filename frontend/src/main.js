import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from './http'
import Qs from 'qs'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from './store'

Vue.use(ElementUI)
Vue.prototype.$axios = axios
Vue.prototype.$Qs = Qs

Vue.config.productionTip = false

new Vue({
  store: store,
  router,
  render: h => h(App)
}).$mount('#app')
