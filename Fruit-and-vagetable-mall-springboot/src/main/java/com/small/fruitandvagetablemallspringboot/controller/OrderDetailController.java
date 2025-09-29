package com.small.fruitandvagetablemallspringboot.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.druid.util.StringUtils;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.small.fruitandvagetablemallspringboot.pojo.OrderDetail;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.service.OrderDetailService;
import com.small.fruitandvagetablemallspringboot.util.AliPayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单controller
 *
 * @author GH
 * @date 2022-04-14 16:07
 */
@RestController
@RequestMapping("/orderDetail")
//@Api(tags = "订单信息接口")
public class OrderDetailController {
    //自动装配订单service
    @Autowired
    OrderDetailService service;

    @Autowired
    private AliPayProperties aliPayProperties;


    /**
     * 支付宝沙箱通知接口
     *
     * @author 谭颖
     * @date 2025-05-21
     */

    @PostMapping("/notify")
    public void payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {

            System.out.println("=========支付宝异步回调========");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }
            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, aliPayProperties.getAlipayPublicKey(), "UTF-8"); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));

                // 从回调参数中获取订单号（去掉后面的随机字符串）
                String outTradeNo = params.get("out_trade_no");
                String orderIdStr = outTradeNo.substring(0, outTradeNo.length() - 5);

                // 更新订单状态为已支付
                OrderDetail orders = service.queryForOrderDetailInfoByOrderNumber(orderIdStr);
                if (orders != null) {
                    orders.setOrderStatus(1);
                    int i = service.updateOrderDetail(orders);
                    if (i !=0) {
                        System.out.println("订单状态修改成功");
                    } else {
                        System.out.println("订单状态修改失败");
                    }
                } else {
                    System.out.println("未找到对应订单ID: " + orders.getId());
                }
            }
        }
    }

    /**
     * 支付宝沙箱支付页面
     *
     * @author 谭颖
     * @date 2025-05-21
     */
    private static final String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";
    private static final String CHARSET = "UTF-8";
    //签名方式
    private static final String SIGN_TYPE = "RSA2";
    @GetMapping("/pay") // &subject=xxx&traceNo=xxx&totalAmount=xxx
    public void pay(String id , HttpServletResponse httpResponse) throws Exception {
        if (StringUtils.isEmpty(id)) {
            throw new Exception("订单id为空");
        }

        OrderDetail orders = service.queryForOrderDetailById(Long.valueOf(id));

        /********************************************************/
//        System.out.println(id);
//        Result order = service.queryForOrderDetailInformationByOrderId(Integer.valueOf(id));
//        Map<String, Object> data = order.getData();
//
//        // 获取orderList并取第一个元素（通常订单列表只有一个订单）
//        List<Map<String, Object>> orderList = (List<Map<String, Object>>) data.get("orderList");
//
//        Map<String, Object> orderData = orderList.get(0);
//
//        // 创建实体类对象
//        OrderDetail orders = new OrderDetail();
//
//        // 为实体类字段赋值（只映射实体类中存在的字段）
//        if (orderData.containsKey("id")) {
//            orders.setId(Integer.parseInt(orderData.get("id").toString()));
//        }
//        if (orderData.containsKey("addressId")) {
//            orders.setAddressId(Integer.parseInt(orderData.get("addressId").toString()));
//        }
//        if (orderData.containsKey("userId")) {
//            orders.setUserId(Integer.parseInt(orderData.get("userId").toString()));
//        }
//        if (orderData.containsKey("orderPay")) {
//            orders.setOrderPay(orderData.get("orderPay").toString());
//        }
//        if (orderData.containsKey("commodityPrice")) {
//            orders.setCommodityPrice(Double.parseDouble(orderData.get("commodityPrice").toString()));
//        }
//        if (orderData.containsKey("orderStatus")) {
//            orders.setOrderStatus(Integer.parseInt(orderData.get("orderStatus").toString()));
//        }
//        if (orderData.containsKey("orderNumber")) {
//            orders.setOrderNumber(orderData.get("orderNumber").toString());
//        }

/****************************************************************************/

        if (orders == null) {
            throw new Exception("订单不存在");
        }

        if (orders.getOrderStatus() != 0) {
            throw new Exception("订单已支付,请勿重复支付");
        }
        // 随机字符串
        String traceNo = RandomUtil.randomString(5);
        // 订单号规则
        String outTradeNo = orders.getOrderNumber() + traceNo ;
        AlipayClient alipayClient = new DefaultAlipayClient(
                GATEWAY_URL, aliPayProperties.getAppId(),
                aliPayProperties.getAppPrivateKey(), FORMAT, CHARSET, aliPayProperties.getAlipayPublicKey(), SIGN_TYPE);

        // 创建 Request并设置Request参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();  // 发送请求的 Request类
        request.setNotifyUrl(aliPayProperties.getNotifyUrl());
        JSONObject bizContent = new JSONObject();
        bizContent.set("out_trade_no", outTradeNo);  // 我们自己生成的订单编号
        bizContent.set("total_amount", orders.getCommodityPrice()); // 订单的总金额
//        bizContent.set("subject",  "2024021865"+"+"+"谭颖"+"+"+orders.getName());   // 支付的名称
        bizContent.set("subject",  orders.getOrderNumber()); // 支付的订单号
        bizContent.set("product_code", "FAST_INSTANT_TRADE_PAY");  // 固定配置
        request.setBizContent(bizContent.toString());

        // 执行请求，拿到响应的结果，返回给浏览器
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        // 直接将完整的表单html输出到页面
        httpResponse.getWriter().write(form);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();

    }


    /**
     * 查询所有订单信息
     *
     * @return result
     */
  //  @ApiOperation("查询所有订单信息")
    @GetMapping("/query-ForOrderInfoAnd-PersonInfo")
    public Result queryForOrderDetailInfoAndPersonInfo() {
        return service.queryForOrderDetailInfoAndPersonInfo();
    }

    /**
     * 根据用户id查询对应订单信息
     *
     * @param uid
     * @return
     */
  //  @ApiOperation("根据用户id查询订单信息")
    @GetMapping("/query-ForOrderDetailInformation-ById/{uid}")
   // @ApiImplicitParam(value = "个人资料表主键",name = "uid",required = true,paramType = "path",dataType = "int")
    public Result queryForOrderDetailInformationById(@PathVariable("uid") Integer uid) {
        return service.queryForOrderDetailInformationById(uid);
    }

    /**
     * 根据id查询对应订单信息
     *
     * @param id
     * @return
     */
  //  @ApiOperation("根据订单id查询订单信息")
    @GetMapping("/query-ForOrderDetailInformation-ByOrderId/{id}")
   // @ApiImplicitParam(value = "订单表主键",name = "id",required = true,paramType = "path",dataType = "int")
    public Result queryForOrderDetailInformationByOrderId(@PathVariable("id") Integer id) {
        return service.queryForOrderDetailInformationByOrderId(id);
    }

    /**
     * 添加一条订单信息
     *
     * @param orderDetail
     * @return
     */
  //  @ApiOperation("添加一条订单信息")
    @PostMapping("/add-OrderDetailInfo")
   // @ApiImplicitParam(value = "订单信息实体", name = "OrderDetail", paramType = "body",required = true,dataType = "orderDetail")
    public Result addMessageBoardInfo(@RequestBody(required = true) OrderDetail orderDetail) {
        System.out.println("=====>"+orderDetail);

        return service.addOrderDetailInfo(orderDetail);
    }

    /**
     * 修改对应订单信息
     *
     * @param orderDetails
     * @return
     */
  //  @ApiOperation("修改对应订单信息")
    @PutMapping("/update-OrderDetailInfo-ById")
   // @ApiImplicitParam(value = "订单信息实体", name = "orderDetails", paramType = "body",required = false,dataType = "orderDetail")
    public Result updateOrderDetailInfoById(@RequestBody(required = false) List<OrderDetail> orderDetails) {
        //System.out.println(orderDetail);
        return service.updateOrderDetailInfoById(orderDetails);
    }


    /**
     * 删除对应订单信息
     *
     * @param id
     * @return
     */
  //  @ApiOperation("删除对应订单信息")
    @DeleteMapping("/delete-OrderDetailInfo-ById/{id}")
   // @ApiImplicitParam(value = "订单信息表主键",name = "id",required = true,paramType = "path",dataType = "int")
    public Result deleteCommodityInfoById(@PathVariable("id") Integer id) {
        return service.deleteOrderDetailInfoById(id);
    }
}
