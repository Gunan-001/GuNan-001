package com.small.fruitandvagetablemallspringboot;

import com.small.fruitandvagetablemallspringboot.dao.*;
import com.small.fruitandvagetablemallspringboot.pojo.*;


import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.service.ManagerService;
import com.small.fruitandvagetablemallspringboot.service.ShoppingTrolleyService;
import org.apache.catalina.connector.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FruitAndVagetableMallSpringbootApplicationTests {
    @Autowired    //商品分类mapper
    CommodityClassifyMapper commodityClassifyMapper;

    @Autowired
    ShoppingTrolleyService trolleyService;
    @Autowired
    ManagerService managerService;
    @Autowired  //商品信息mapper
    CommodityInformationMpper commodityInformationMpper;

    @Autowired  //管理员信息mapper
    ManagerMapper managerMapper;

    @Autowired  //留言板信息mapper
    MessageBoardMapper messageBoardMapper;

    @Autowired  //订单信息mapper
    OrderDetailMapper orderDetailMapper;

    @Autowired //用户信息mapper
    PersonalInformationMapper personalInformationMapper;

    @Autowired  //收货信息mapper
    ShoppingAddressMapper shoppingAddressMapper;

    @Autowired  //收藏信息mapper
    ShoppingStarMapper shoppingStarMapper;

    @Autowired  //购物车信息mapper
    ShoppingTrolleyMapper shoppingTrolleyMapper;

    @Autowired //轮播图信息mapper
    SlideShowMapper slideshowMapper;

    @Test
    public  void testCommodityClassifyMapper(){
        CommodityClassify classify =  new CommodityClassify();
        classify.setId(3);
        classify.setCommoditTypeName("鱼类");
//        commodityClassifyMapper.insertClassify(classify);
//
//        CommodityClassify    commodityClassifies = commodityClassifyMapper.queryForComClassfyById(1);
//        System.out.println(commodityClassifies);
//        commodityClassifyMapper.updateClassById(classify);
        commodityClassifyMapper.deleteClassById(3);
    }

    @Test
    public  void testCommodityInformationMpper(){
//        CommodityInformation comm  = new CommodityInformation();
//        comm.setId(4);
//        comm.setCommodityInformation("xixixi");
//        comm.setCommodityHot(1);
//        comm.setCommodityName("草莓");
//        comm.setCommodityFreight(3);
//        comm.setCommodityPrice(10);
//        comm.setCommodityImage("img/shanzh22u");
//        comm.setCommodityNumber(11);
//        comm.setCommodityClassId(1);
//        commodityInformationMpper.deleteCommodityInfoById(4);
//        String path = request.getServletContext().getRealPath("/upload");
//        System.out.println("path======>"+path);
//        List<Map<String, Object>> maps = commodityInformationMpper.queryForCommodityInfoLimit(0,10);
//        for (Map<String, Object> map : maps) {
//            System.out.println(map);
//        }
        //System.out.println();
        Integer[] arr = new Integer[]{7,8,9};
//        Integer integer = commodityInformationMpper.deleteCommodityInfoBatchById(arr);
//        System.out.println("integer====>"+integer);
    }
    @Test
    public  void testManagerMapper(){


       Result managers = managerService.queryForManager();
            System.out.println(managers);
//        Manager manager = managerMapper.queryForManagerById(2);
//        System.out.println(manager);
//        Manager manager  = new Manager();
//        manager.setId(4);
//        manager.setAccount("523525");
//        manager.setName("eeeeee");
//        manager.setPassword("432442");
//        managerMapper.updateManagerInfoById(manager);
//        managerMapper.deleteManagerInfoById(4);
    }
    @Test
    public  void testMessageBoardMapper(){
//        List<Map<String, Object>> maps = messageBoardMapper.queryForMessageBoardById(1);
//        System.out.println(maps);
        MessageBoard mb  = new MessageBoard();
        mb.setMessageInfo("adwadwdawd");
//        mb.setId(1);
        mb.setShopBack("无语了 ");
        mb.setUserId(1);
        messageBoardMapper.addMessageBoardInfo(mb);
//        messageBoardMapper.deleteMessageBoardInfoById(5);
    }
    @Test
    public  void testOrderDetailMapper(){
//        OrderDetail orderDetail  =  new OrderDetail();
//        orderDetail.setId(7);
//        orderDetail.setOrderDetail("{name:山竹,count:30}");
//        orderDetail.setOrderPay("支付宝");
//        orderDetail.setOrderNumber("6666666666666666666");
//        orderDetail.setOrderStatus(2);
//        orderDetail.setUserId(1);
//        orderDetail.setAddressId(1);
//        orderDetail.setCommodityPrice(1);
        orderDetailMapper.deleteOrderDetailInfoById(7);
    }
    @Test
    public  void testPersonalInformationMapper(){
//        PersonalInformation per = new PersonalInformation();
//        per.setNickName("妞妞");
//        per.setUserName("99999");
//        per.setUserPassword("351463416");
//        per.setUserSex("男");
//        per.setUserPhone("17779255655");
//        per.setUserStatus(0);
//        per.setId(4);
//        personalInformationMapper.updatePersonalInfoById(per);
        PersonalInformation personInfo = personalInformationMapper.queryForPersonByUserNameAndPassword("666", "123456");
        if(null==personInfo){
            System.out.println("登录失败 账户或密码错误");
        }else {
            System.out.println("登录成功!");
        }
    }
    @Test
    public  void testShoppingStarMapper(){
//        List<Map<String, Object>> maps = shoppingAddressMapper.queryForShoppingAddressById(2);
//        System.out.println(maps);
//        ShoppingAddress address = new ShoppingAddress();
//        address.setShoppingAddress("广东深圳");
//        address.setUserId(2);
//        address.setShoppingPhone("815615");
//        address.setShoppingName("团子");
//        address.setShoppingDefault(0);
//        address.setId(4);
        shoppingAddressMapper.deleteShoppingAddressById(4);
    }
    @Test
    public  void testShoppingTrolleyMapper(){
//        List<Map<String, Object>> maps = shoppingStarMapper.queryForShoppingStarAndPersonInfo();
//        for (Map<String, Object> map : maps) {
//            System.out.println(map);
//        }
        ShoppingTrolley shop = new ShoppingTrolley();
        shop.setCommodityId(1);
        shop.setCommodityNumber(1);
        shop.setUserId(1);
        //ShoppingTrolley shoppingTrolley = shoppingTrolleyMapper.queryForShopTrolleyByShopId(shop.getCommodityId(), shop.getUserId());

//        shop.setId(4);
        Result result = trolleyService.addShoppingTrolley(shop);

        System.out.println(result);

//        ShoppingStar star = new ShoppingStar();
//        star.setId(4);
//        star.setCommodityId(1);
//        star.setUserId(1);
//        shoppingStarMapper.updateShoppingStarById(star);
       // shoppingStarMapper.deleteShoppingStarById(4);
    }
    @Test
    public  void testShoppingAddressMapper(){
        List<Map<String, Object>> maps = shoppingTrolleyMapper.queryForShopTrolleyAndPersonCommodityInfo();
        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
//        ShoppingTrolley shop = new ShoppingTrolley();
//        shop.setCommodityId(1);
//        shop.setCommodityNumber(50);
//        shop.setUserId(2);
//        shop.setId(4);
//        shoppingTrolleyMapper.deleteShoppingTrolleyById(4);
    }
    @Test
    public  void testSlideshowMapper(){
//        Slideshow slideshows = slideshowMapper.queryForSlideshowAndCommodityInfoById(1);
//        System.out.println(slideshows);
//        for (Slideshow slideshow : slideshows) {
//            System.out.println(slideshow);
//        }
//        Slideshow slideshow  = new Slideshow();
//        slideshow.setName("wa");
//        slideshow.setPath("88888");
//        slideshow.setStatus(1);
//        slideshow.setId(4);
        slideshowMapper.deleteSlideshowById(4);

    }
}
