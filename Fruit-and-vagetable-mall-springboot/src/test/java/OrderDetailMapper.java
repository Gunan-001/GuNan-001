import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.small.fruitandvagetablemallspringboot.pojo.OrderDetail;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.service.Impl.OrderDetailServiceImpl;
import com.small.fruitandvagetablemallspringboot.service.OrderDetailService;
import com.small.fruitandvagetablemallspringboot.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Map;
@SpringBootTest
public class OrderDetailMapper {
    @Autowired
    static OrderDetailService service;
    // 将Map数据转换为OrderDetail实体
    public static OrderDetail mapToOrderDetail(Map<String, Object> data) {
        // 检查数据是否为空
        if (data == null || !data.containsKey("orderList")) {
            return null;
        }

        // 获取orderList并取第一个元素（通常订单列表只有一个订单）
        List<Map<String, Object>> orderList = (List<Map<String, Object>>) data.get("orderList");
        if (orderList == null || orderList.isEmpty()) {
            return null;
        }
        Map<String, Object> orderData = orderList.get(0);

        // 创建实体类对象
        OrderDetail orderDetail = new OrderDetail();

        // 为实体类字段赋值（只映射实体类中存在的字段）
        if (orderData.containsKey("id")) {
            orderDetail.setId(Integer.parseInt(orderData.get("id").toString()));
        }
        if (orderData.containsKey("addressId")) {
            orderDetail.setAddressId(Integer.parseInt(orderData.get("addressId").toString()));
        }
        if (orderData.containsKey("userId")) {
            orderDetail.setUserId(Integer.parseInt(orderData.get("userId").toString()));
        }
        if (orderData.containsKey("orderPay")) {
            orderDetail.setOrderPay(orderData.get("orderPay").toString());
        }
        if (orderData.containsKey("commodityPrice")) {
            orderDetail.setCommodityPrice(Double.parseDouble(orderData.get("commodityPrice").toString()));
        }
        if (orderData.containsKey("orderStatus")) {
            orderDetail.setOrderStatus(Integer.parseInt(orderData.get("orderStatus").toString()));
        }
        if (orderData.containsKey("orderNumber")) {
            orderDetail.setOrderNumber(orderData.get("orderNumber").toString());
        }

        // 处理orderDetail字段（JSON字符串转对象）
        if (orderData.containsKey("orderDetail")) {
            String orderDetailJson = orderData.get("orderDetail").toString();
            // 这里根据实际需要解析为对应的对象或保持JSON字符串
            // 示例：解析为List<Map>
            JSONArray detailList = JSON.parseArray(orderDetailJson);
            orderDetail.setOrderDetail(detailList);
        }

        // 处理日期字段
        if (orderData.containsKey("orderTime")) {
            try {
                // 转换时间格式（根据实际格式调整）
                String timeStr = orderData.get("orderTime").toString();
                // 这里假设使用Jackson或FastJSON的日期解析
                orderDetail.setOrderTime(JSON.parseObject("{\"time\":\"" + timeStr + "\"}",
                        TempTime.class).getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return orderDetail;
    }

    // 临时辅助类用于日期转换
    static class TempTime {
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+8")
        private Date time;

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }
    }

    // 使用示例
    public static void main(String[] args) {
        // 假设order.getData()返回你的Map数据
        // Map<String, Object> data = order.getData();
        // OrderDetail orderDetail = mapToOrderDetail(data);

        System.out.println(MD5Utils.md5(String.valueOf(123456)));
        //      e10adc3949ba59abbe56e057f20f883e
//        Result order = service.queryForOrderDetailInformationByOrderId(Integer.valueOf(34));
//        Map<String, Object> data = order.getData();
//        OrderDetail orderDetail = mapToOrderDetail(data);
//        System.out.println(orderDetail);
    }
}
