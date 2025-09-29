package com.small.fruitandvagetablemallspringboot.util;

import com.small.fruitandvagetablemallspringboot.dao.OrderDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * 生成随机字符串
 * @author GH
 * @date 2022-04-14 16:42
 */
@Component
public class RandomString {
    @Autowired
    OrderDetailMapper orderDetailMapper;

    /**
     * 生成与数组中的字符串不重复的字符串返回
     * @param
     * @return
     */
    public  String getRandomString() {
        List<String> macTypeIdList = orderDetailMapper.queryAllDetailNum();
        Random random = new Random();
        long typeId = 0;
        int supplierId = (int) (random.nextInt(5000) +Math.random()*500);
        String result="";
        while (true){
            int serialNum = random.nextInt(900) + 10000;
            typeId = Long.parseLong (String.valueOf(supplierId)+String.valueOf(serialNum));
            result= String.valueOf(typeId);
            if (!macTypeIdList.contains(typeId)){
                break;
            }
        }

        return result;
    }


}
