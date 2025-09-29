import axios from 'axios'
import {Message} from 'element-ui'
import router from '@/router/index.js'
import store from "@/store";

// axios.defaults.baseURL = 'http://192.168.31.107:8000/';
axios.defaults.baseURL = 'http://127.0.0.1:8000/';
// 配置请求头携带token
axios.interceptors.request.use( config => {
  // console.log("config:", config);
  config.headers.Authorization = window.sessionStorage.getItem("token")||null;
  return config;
});

axios.interceptors.response.use(response=>{
	if(200<=response.data.code && response.data.code<300){
		return response.data;
	}else{
		if(response.data.code === 403){
			store.dispatch('removeUserInfo')
			Message.error('访问受限,权限到期 请重新登入')
			if (store.state.routepath.indexOf('mall')>=0){
				router.push('/mall/index')
				window.setTimeout(()=>{
					store.state.loginDialogVisible = true
				},2000)
			}else if (store.state.routepath.indexOf('main')>=0){
				window.setTimeout(()=>{
					router.push('/login')
				},2000)
			}
		}else{
			console.log(response.data.message||response.data.data)
			Message.error(response.data.message||response.data.data)
			return null
		}
	}
},error=>{
	let {response} = error;
	if(response){
		switch(response.status){
			case 400:
				Message.error(response.data.message)
				break
			case 401:
				Message.error(response.data.message)
				break
			case 403:
				Message.error(response.data.message)
				break
			case 404:
				Message.error(response.data.message)
				break
		}
	} else {
		Message.error('网络走丢了')
	}
})

export default axios
