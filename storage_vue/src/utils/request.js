import axios from '@/utils/axios.js'

const request = {

	baseUrl: axios.defaults.baseURL,
	upLoadUrl: axios.defaults.baseURL+'upload/image',
//	pageURL:`http:${axios.defaults.baseURL.split(':')[1]}:8080/`,
	pageURL:`http:${axios.defaults.baseURL.split(':')[1]}:8000/`,
	/* 前台 */
	// 登入
	login(data){
		return axios.post('/personInfo/login',data)
	},
	// 注册
	register(data){
		return axios.post('/personInfo/add-PersonalInfo',data)
	},
	// 获取轮播图数据
	getBannerData(param){
		return axios.get('/slideShow/query-ForSlideshowInfo',param)
	},
	// 获取商品->关键字查询+分页
	getCommodityDataForCondition(params){
		return axios.get('/commoInfo/query-ForCommodityInfoAndClass',{params:params})
	},
	// 获取商品详情
	getCommodityDataForId(id){
		return axios.get(`/commoInfo/query-ForCommodityInformation-ById/${id}`)
	},
	// 获取分类信息
	getCommodityClass(param){
		return axios.get('/commoClassify/query_ForComClassfy',param)
	},
	// 获取购物车信息
	getShopCarDataForId(id){
		return axios.get(`/trolley/query-ForShopTrolleyAnd-PersonCommodityInfo-ById/${id}`)
	},
	// 添加商品到购物车
	addcommodityToShopCar(params){
		return axios.post('/trolley/add-ShoppingTrolley',params)
	},
	// 添加商品数量
	addcommodityNumberToShopCar(params){
		return axios.put('/trolley/update-ShoppingTrolley-ById',params)
	},
	// 将商品从购物车移除
	delCommodityToShopCar(id){
		return axios.delete(`/trolley/delete-ShoppingTrolley-ById/${id}`)
	},
	// 删除购物车信息
	// delShopCar(params){
	// 	return axios.delete('/trolley/delete-ShoppingTrolley-ById',params)
	// },
	// 获取留言板信息
	getMessageData(params){
		return axios.get('/messBoard/query-ForMessageBoard-Limit',{params:params})
	},
	// 发送留言信息
	sendMessage(params){
		return axios.post('/messBoard/add-MessageBoardInfo',params)
	},
	// 获取收藏信息
	getCollectData(id){
		return axios.get(`/star/query-ForShoppingStarById/${id}`)
	},
	// 添加到收藏列表
	addCommodityToStar(params){
		return axios.post(`/star/add-ShoppingStar`,params)
	},
	// 删除收藏信息
	delCollectObj(id){
		return axios.delete(`/star/delete-ShoppingStar-ById/${id}`)
	},
	// 获取订单信息-根据用户id
	getOrderData(id){
		return axios.get(`/orderDetail/query-ForOrderDetailInformation-ById/${id}`)
	},
	// 获取前订单信息-根据订单id
	getOrderDataForOrderId(id){
		return axios.get(`/orderDetail/query-ForOrderDetailInformation-ByOrderId/${id}`)
	},
	// 修改订单信息-完成支付
	payMentOrder(params){
		return axios.put('/orderDetail/update-OrderDetailInfo-ById',params)
	},
	// 生成订单
	addOrderData(params){
		return axios.post('/orderDetail/add-OrderDetailInfo',params)
	},
	// 获取地址信息
	getAddressData(id){
		return axios.get(`/address/query-ForShoppingAddress-ById/${id}`)
	},
	// 删除地址信息
	delAddressData(id){
		return axios.delete(`/address/delete-ShoppingAddress-ById/${id}`)
	},
	// 添加地址
	addAddress(params){
		return axios.post('/address/add-ShoppingAddress',params)
	},
	// 修改地址信息
	editAddress(params){
		return axios.put('/address/update-ShoppingAddress-ById',params)
	},

	/* 后台 */
	/* 登入 */
	manageLogin(params){
		return axios.post('/managerInfo/login',params)
	},
	/* 获取所有商品 */
	getCommodityData(){
		return axios.get('/commoInfo/query-ForCommodityInfo')
	},
	/* 添加商品信息 */
	addCommodityInfo(params){
		return axios.post('/commoInfo/add-CommodityInfo',params)
	},
	/* 修改商品信息 */
	editCommodityInfo(params){
		return axios.put('/commoInfo/update-CommodityInfo-ById',params)
	},
	/* 删除商品信息 */
	delCommodityInfoById(id){
		return axios.delete(`/commoInfo/delete-CommodityInfo-ById/${id}`)
	},
	/* 批量删除商品信息 */
	delAllCommodityInfoById(params){
		return axios.post(`/commoInfo/delete-CommodityInfoBatch-ById`,params)
	},
	/* 添加商品分类信息 */
	addCommodityClass(params){
		return axios.post('/commoClassify/insert-Classify',params)
	},
	/* 修改商品分类信息 */
	editCommodityClass(params){
		return axios.put('/commoClassify/update-ClassById',params)
	},
	/* 删除商品分类信息 */
	delCommodityClassById(id){
		return axios.delete(`/commoClassify/delete-Class-ById/${id}`)
	},
	/* 批量删除商品分类信息 */
	delAllCommodityClassById(params){
		return axios.post(`/commoClassify/delete-ClassBy-BatchId`,params)
	},
	/* 查询订单信息 */
	getOrderAllData(){
		return axios.get('/orderDetail/query-ForOrderInfoAnd-PersonInfo')
	},
	/* 修改订单信息 */
	editOrderInfo(params){
		return axios.put('/orderDetail/update-OrderDetailInfo-ById',params)
	},
	/* 添加轮播图 */
	addBannerInfo(params){
		return axios.post('/slideShow/add-Slideshow',params)
	},
	/* 修改轮播图 */
	editBannerInfo(params){
		return axios.put('/slideShow/update-Slideshow-ById',params)
	},
	/* 批量删除轮播图 */
	delAllBannerInfo(parmas){
		return axios.post('/slideShow/delete-SlideshowBatch-ById',parmas)
	},
	/* 查询所有留言 */
	getMessageInfo(){
		return axios.get('/messBoard/query-ForMessageBoard')
	},
	/* 留言回复 */
	editMessageInfo(params){
		return axios.put('/messBoard/update-MessageBoardInfo-ById',params)
	},
	/* 留言删除 */
	delAllMessageInfo(params){
		return axios.post('/messBoard/delete-MessageBoardInfoBatch-ById',params)
	},
	/* 查询所有用户 */
	getUserData(){
		return axios.get('/personInfo/query-ForPersonalInformation')
	},
	/* 批量处理用户 */
	editUserInfo(params){
		return axios.put('/personInfo/update-PersonalInfo-ById',params)
	},
	/* 管理员修改用户密码 */
	updataPassword(params){
		return axios.put('/personInfo/update-userPassword',params)
	},
	/* 用户修改用户密码 */
	updataPasswordByNameAndPhone(params){
		return axios.put('/personInfo/update-userPassword-NameAndPhone',params)
	}
}

export default request
