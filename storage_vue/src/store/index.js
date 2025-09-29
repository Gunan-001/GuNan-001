import Vue from 'vue'
import Vuex from 'vuex'
import request from "@/utils/request";
Vue.use(Vuex)

// 准备actions用于响应组件中的动作
const actions = {
    getShopNum(context){
        context.commit('getShopNum')
    },
    removeUserInfo(){
        sessionStorage.removeItem('account')
        sessionStorage.removeItem('token')
        sessionStorage.removeItem('role')
        sessionStorage.removeItem('userId')
        sessionStorage.removeItem('userName')
    }
}
// 准备mutations用于操作数据state
const mutations = {
    async getShopNum(state) {
        let userId = sessionStorage.getItem('userId')
        if (userId) {
            let res = await request.getShopCarDataForId(userId)
            if (res) {
                state.shopNum = res.data.trolleyList.length || 0
            }
        }
    }
}
// 准备state用于存储数据
const state = {
    loginDialogVisible: false,
    shopNum:0,
    routepath:null
}

export default new Vuex.Store({
    actions,
    mutations,
    state,
})
