package com.small.fruitandvagetablemallspringboot.service.Impl;

import com.small.fruitandvagetablemallspringboot.dao.CommodityInformationMpper;
import com.small.fruitandvagetablemallspringboot.pojo.CommodityInformation;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.result.ResultCodeEnum;
import com.small.fruitandvagetablemallspringboot.service.CommoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品信息服务层
 *
 * @author GH
 * @date 2022-04-12 15:52
 */
@Service
public class CommoInfoServiceImpl implements CommoInfoService {
    //自动装配commodityInfoMapper
    @Autowired
    CommodityInformationMpper commodityInfoMapper;

    /**
     * 查询所有商品信息
     *
     * @return result
     */
    @Override
    public Result queryForCommodityInfoAndClass(Integer commodityClassId, String search,
                                                Integer currentPage, Integer pageSize) {
        if (null != search && !"".equals(search.trim())) {
            search = "%" + search + "%";
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Integer start = (currentPage - 1) * pageSize;
        List<Map<String, Object>> maps = commodityInfoMapper.queryForCommodityInfoAndClass(commodityClassId, search, start, pageSize);
        Integer totalPage = commodityInfoMapper.queryForCommodityCount(commodityClassId, search);
        resultMap.put("totalPage", totalPage);
        resultMap.put("commodityList", maps);

        return Result.ok().data(resultMap);

    }

    /**
     * 查询所有商品信息
     * @return
     */
    @Override
    public Result queryForCommodityInfo() {
        List<Map<String, Object>> maps = commodityInfoMapper.queryForCommodityInfo();

        return Result.ok().data("commodityList", maps);
    }


    /**
     * 根据id查询对应商品信息
     *
     * @param id
     * @return
     */
    @Override
    public Result queryForCommodityInformationById(Integer id) {
        if (null == id) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        List<Map<String, Object>> maps = commodityInfoMapper.queryForCommodityInformationById(id);

        return Result.ok().data("commoInfo", maps);

    }

    /**
     * 添加一条商品信息
     *
     * @param commodityInformation
     * @return
     */
    @Override
    public Result addCommodityInfo(CommodityInformation commodityInformation) {
        if (null == commodityInformation.getCommodityName() ||
                "" == commodityInformation.getCommodityName().trim()
                || null == commodityInformation.getCommodityPrice() ||
                null == commodityInformation.getCommodityClassId() ||
                null == commodityInformation.getCommodityNumber()) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = commodityInfoMapper.addCommodityInfo(commodityInformation);
        if (integer < 1) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

    /**
     * 修改对应商品信息
     *
     * @param commodityInformation
     * @return
     */
    @Override
    public Result updateCommodityInfoById(CommodityInformation commodityInformation) {
        if (null == commodityInformation.getCommodityClassId()) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = commodityInfoMapper.updateCommodityInfoById(commodityInformation);
        if (integer > 0) {
            return Result.ok(ResultCodeEnum.SUCCESS);
        } else {
            return Result.error(ResultCodeEnum.FAIL);
        }
    }

    /**
     * 删除对应商品信息
     *
     * @param id
     * @return
     */
    @Override
    public Result deleteCommodityInfoById(Integer id) {
        if (null == id) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = commodityInfoMapper.deleteCommodityInfoById(id);
        if (integer > 0) {
            return Result.ok(ResultCodeEnum.SUCCESS);
        } else {
            return Result.error(ResultCodeEnum.FAIL);
        }
    }

    /**
     * 批量删除商品信息
     *
     * @param commodityIds
     * @return
     */
    @Override
    public Result deleteCommodityInfoBatchById(Integer[] commodityIds) {
        if (null == commodityIds) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        Integer integer = commodityInfoMapper.deleteCommodityInfoBatchById(commodityIds);
        return Result.ok();
    }
}
