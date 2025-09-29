/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : fruit_and_vegetable_shopping_mall

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 30/09/2025 02:23:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for commodity_classify
-- ----------------------------
DROP TABLE IF EXISTS `commodity_classify`;
CREATE TABLE `commodity_classify`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `commoditTypeName` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '商品分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of commodity_classify
-- ----------------------------
INSERT INTO `commodity_classify` VALUES (1, '水果');
INSERT INTO `commodity_classify` VALUES (2, '蔬菜');

-- ----------------------------
-- Table structure for commodity_information
-- ----------------------------
DROP TABLE IF EXISTS `commodity_information`;
CREATE TABLE `commodity_information`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键(商品编号)',
  `commodityInformation` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '商品信息',
  `commodityName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商品名字',
  `commodityHot` int NOT NULL DEFAULT 0 COMMENT '是否为热门推荐',
  `commodityFreight` decimal(10, 0) NULL DEFAULT NULL COMMENT '运费',
  `commodityPrice` decimal(10, 0) NOT NULL COMMENT '商品价格',
  `commodityImage` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `commodityNumber` int NOT NULL COMMENT '库存数量',
  `commodityClassId` int NOT NULL COMMENT '商品分类id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '商品信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of commodity_information
-- ----------------------------
INSERT INTO `commodity_information` VALUES (28, '苹果很好吃哦', '苹果', 0, 0, 3, 'upload/1650550937901.png', 10, 1);
INSERT INTO `commodity_information` VALUES (29, '菠萝贼好吃', '菠萝', 0, 1, 1, 'upload/1650554931413.png', 1, 1);
INSERT INTO `commodity_information` VALUES (30, '草莓也好吃', '草莓', 0, 1, 12, 'upload/1650554950112.png', 12, 1);
INSERT INTO `commodity_information` VALUES (31, '蓝莓', '蓝莓', 0, 1, 12, 'upload/1650554981978.png', 1, 1);
INSERT INTO `commodity_information` VALUES (32, '青柠檬', '柠檬', 0, 1, 12, 'upload/1650554999802.png', 1, 1);
INSERT INTO `commodity_information` VALUES (33, '1', '葡萄', 0, 12, 12, 'upload/1650555023906.png', 12, 1);
INSERT INTO `commodity_information` VALUES (34, '这种更好吃', '柠檬', 0, 1, 14, 'upload/1650555045291.png', 12, 1);
INSERT INTO `commodity_information` VALUES (35, '好吃！！！', '西瓜', 0, 1, 10, 'upload/1650555066271.png', 100, 1);
INSERT INTO `commodity_information` VALUES (36, '好吃', '樱桃', 0, 12, 18, 'upload/1650555087891.png', 20, 1);
INSERT INTO `commodity_information` VALUES (37, '大葱贼好吃', '大葱', 0, 0, 2, 'upload/1650555111588.png', 100, 2);
INSERT INTO `commodity_information` VALUES (38, '绿色食品', '花菜', 0, 0, 5, 'upload/1650555142393.png', 25, 2);
INSERT INTO `commodity_information` VALUES (39, '黄瓜很新鲜', '黄瓜', 0, 0, 6, 'upload/1650555169778.png', 6, 2);
INSERT INTO `commodity_information` VALUES (41, '好吃的茄子', '茄子', 0, 0, 14, 'upload/1650555216189.png', 70, 2);
INSERT INTO `commodity_information` VALUES (42, '好吃了', '豌豆', 0, 0, 14, 'upload/1650555243432.png', 12, 2);
INSERT INTO `commodity_information` VALUES (43, '可以烧菜', '小葱', 0, 0, 12, 'upload/1650555264068.png', 12, 2);
INSERT INTO `commodity_information` VALUES (44, '好吃', '西红柿', 0, 0, 12, 'upload/1650555283751.png', 7, 2);
INSERT INTO `commodity_information` VALUES (45, '又大又好吃', '洋葱', 0, 0, 5, 'upload/1650555300028.png', 188, 2);
INSERT INTO `commodity_information` VALUES (46, '测试', '测试', 0, 1, 9999999, '/upload/a2c8f0792efc408d89da3682e0c7dc5a图片.jpg', 1, 1);

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `account` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '管理员' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES (1, '贾斯汀', 'admin', '123456');
INSERT INTO `manager` VALUES (2, '梅西', '645613546', '123456');
INSERT INTO `manager` VALUES (3, '奥德赛', '515415', '123456');

-- ----------------------------
-- Table structure for message_board
-- ----------------------------
DROP TABLE IF EXISTS `message_board`;
CREATE TABLE `message_board`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` int NOT NULL COMMENT '用户id',
  `messageInfo` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '留言信息',
  `shopBack` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '商家回复',
  `messageTime` datetime NOT NULL COMMENT '留言时间',
  `backTime` datetime NULL DEFAULT NULL COMMENT '商家回复时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '留言板' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of message_board
-- ----------------------------

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addressId` int NOT NULL COMMENT '收货地址id',
  `userId` int NOT NULL COMMENT '个人资料主键',
  `orderPay` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '支付方式',
  `commodityPrice` decimal(10, 0) NOT NULL COMMENT '订单金额',
  `orderStatus` int NOT NULL COMMENT '订单状况',
  `orderNumber` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '订单编号',
  `orderDetail` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'json型信息包含商品名称数量',
  `orderTime` datetime NOT NULL COMMENT '订单时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '订单信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (26, 1, 1, '支付宝支付', 470, 0, '137810659', '[{\"id\":28,\"name\":\"苹果\",\"number\":10,\"price\":3,\"image\":\"upload/1650550937901.png\"},{\"id\":29,\"name\":\"菠萝\",\"number\":20,\"price\":1,\"image\":\"upload/1650554931413.png\"},{\"id\":41,\"name\":\"茄子\",\"number\":30,\"price\":14,\"image\":\"upload/1650555216189.png\"}]', '2025-09-13 02:52:52');
INSERT INTO `order_detail` VALUES (33, 1, 1, '支付宝支付', 9, 0, '436810437', '[{\"id\":28,\"name\":\"苹果\",\"number\":3,\"price\":3,\"image\":\"upload/1650550937901.png\"}]', '2025-09-13 03:21:50');
INSERT INTO `order_detail` VALUES (44, 1, 1, '微信支付', 1, 2, '34210283', '[{\"id\":29,\"name\":\"菠萝\",\"number\":1,\"price\":1,\"image\":\"upload/1650554931413.png\"}]', '2025-09-15 21:31:41');
INSERT INTO `order_detail` VALUES (45, 1, 1, '微信支付', 12, 2, '400710388', '[{\"id\":43,\"name\":\"小葱\",\"number\":1,\"price\":12,\"image\":\"upload/1650555264068.png\"}]', '2025-09-15 21:38:46');
INSERT INTO `order_detail` VALUES (46, 9, 6, '支付宝支付', 3, 0, '419810113', '[{\"id\":28,\"name\":\"苹果\",\"number\":1,\"price\":3,\"image\":\"upload/1650550937901.png\"}]', '2025-09-26 00:34:29');
INSERT INTO `order_detail` VALUES (47, 9, 6, '支付宝支付', 14, 1, '476710703', '[{\"id\":41,\"name\":\"茄子\",\"number\":1,\"price\":14,\"image\":\"upload/1650555216189.png\"}]', '2025-09-26 00:35:36');
INSERT INTO `order_detail` VALUES (48, 9, 6, '支付宝支付', 12, 1, '172810425', '[{\"id\":31,\"name\":\"蓝莓\",\"number\":1,\"price\":12,\"image\":\"upload/1650554981978.png\"}]', '2025-09-26 00:37:36');
INSERT INTO `order_detail` VALUES (49, 10, 7, '支付宝支付', 15, 0, '370010306', '[{\"id\":29,\"name\":\"菠萝\",\"number\":1,\"price\":1,\"image\":\"upload/1650554931413.png\"},{\"id\":41,\"name\":\"茄子\",\"number\":1,\"price\":14,\"image\":\"upload/1650555216189.png\"}]', '2025-09-29 22:02:37');
INSERT INTO `order_detail` VALUES (50, 10, 7, '支付宝支付', 15, 1, '34010592', '[{\"id\":29,\"name\":\"菠萝\",\"number\":1,\"price\":1,\"image\":\"upload/1650554931413.png\"},{\"id\":41,\"name\":\"茄子\",\"number\":1,\"price\":14,\"image\":\"upload/1650555216189.png\"}]', '2025-09-29 22:03:26');

-- ----------------------------
-- Table structure for personal_information
-- ----------------------------
DROP TABLE IF EXISTS `personal_information`;
CREATE TABLE `personal_information`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `nickName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户昵称',
  `userSex` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '性别',
  `userName` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户账号',
  `userPassword` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户密码',
  `userPhone` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密保手机',
  `userStatus` tinyint(1) NOT NULL COMMENT '状态0为正常1为禁用',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `AK_Key_2`(`userName` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '个人资料' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of personal_information
-- ----------------------------
INSERT INTO `personal_information` VALUES (1, '小黑', '男', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '17376525222', 0);
INSERT INTO `personal_information` VALUES (6, 'qqqqq', '男', 'qqqqq', '437599f1ea3514f8969f161a6606ce18', 'qqqqq', 1);
INSERT INTO `personal_information` VALUES (7, '11111qwe', '男', '11111', 'a88de8848ad2f763136074eebea2641', '13512051235', 1);

-- ----------------------------
-- Table structure for shopping_address
-- ----------------------------
DROP TABLE IF EXISTS `shopping_address`;
CREATE TABLE `shopping_address`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` int NOT NULL COMMENT '个人资料主键\n   ',
  `shoppingName` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '收货名字',
  `shoppingPhone` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '收货电话',
  `shoppingAddress` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '收货地址',
  `shoppingDefault` int NOT NULL COMMENT '是否为默认地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '收货地址' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shopping_address
-- ----------------------------
INSERT INTO `shopping_address` VALUES (1, 1, '小黑', '17376525222', '湖南长沙中南街21号', 0);
INSERT INTO `shopping_address` VALUES (9, 6, '11111', '1111111', '11111', 0);
INSERT INTO `shopping_address` VALUES (10, 7, '121', '1212', '1212', 0);

-- ----------------------------
-- Table structure for shopping_star
-- ----------------------------
DROP TABLE IF EXISTS `shopping_star`;
CREATE TABLE `shopping_star`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `commodityId` int NOT NULL COMMENT '商品id',
  `userId` int NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '收藏' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shopping_star
-- ----------------------------
INSERT INTO `shopping_star` VALUES (1, 1, 2);
INSERT INTO `shopping_star` VALUES (15, 28, 1);
INSERT INTO `shopping_star` VALUES (16, 29, 7);

-- ----------------------------
-- Table structure for shopping_trolley
-- ----------------------------
DROP TABLE IF EXISTS `shopping_trolley`;
CREATE TABLE `shopping_trolley`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` int NOT NULL COMMENT '个人资料主键',
  `commodityId` int NOT NULL COMMENT '商品编号(商品信息id)',
  `commodityNumber` int NOT NULL COMMENT '商品数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '购物车管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shopping_trolley
-- ----------------------------
INSERT INTO `shopping_trolley` VALUES (3, 3, 1, 6);
INSERT INTO `shopping_trolley` VALUES (5, 2, 1, 12);
INSERT INTO `shopping_trolley` VALUES (60, 7, 32, 1);

-- ----------------------------
-- Table structure for slideshow
-- ----------------------------
DROP TABLE IF EXISTS `slideshow`;
CREATE TABLE `slideshow`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `path` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '轮播图路径',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '轮播图名字',
  `status` int NOT NULL DEFAULT 0 COMMENT '是否显示,0为不显示，1为显示',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '轮播图' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of slideshow
-- ----------------------------
INSERT INTO `slideshow` VALUES (3, 'upload/1650551904327.png', '果篮', 1);
INSERT INTO `slideshow` VALUES (6, 'upload/1650552042003.png', '蔬果1', 0);
INSERT INTO `slideshow` VALUES (7, 'upload/1650552052853.png', '2', 0);
INSERT INTO `slideshow` VALUES (8, 'upload/1650552061576.png', '南瓜', 0);
INSERT INTO `slideshow` VALUES (12, 'upload/1650552143377.png', '猕猴桃', 0);
INSERT INTO `slideshow` VALUES (13, 'upload/1650552156806.png', '草莓', 0);
INSERT INTO `slideshow` VALUES (14, 'upload/1650552163835.png', '西红柿', 0);

SET FOREIGN_KEY_CHECKS = 1;
