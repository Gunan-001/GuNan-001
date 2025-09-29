import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueRouter from 'vue-router'
import router from './router/index.js'
import store from './store/index.js'
import { Button, Dialog } from 'vant';

Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(Button).use(Dialog)

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router:router,
  store:store,
}).$mount('#app')
