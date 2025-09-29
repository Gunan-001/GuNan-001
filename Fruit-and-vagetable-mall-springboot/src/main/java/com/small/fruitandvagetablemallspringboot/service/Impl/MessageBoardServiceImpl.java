package com.small.fruitandvagetablemallspringboot.service.Impl;

import com.small.fruitandvagetablemallspringboot.dao.MessageBoardMapper;
import com.small.fruitandvagetablemallspringboot.pojo.MessageBoard;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.result.ResultCodeEnum;
import com.small.fruitandvagetablemallspringboot.service.MessageBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 留言板信息service实现类
 *
 * @author GH
 * @date 2022-04-12 15:52
 */
@Service
public class MessageBoardServiceImpl implements MessageBoardService {
    //自动装配留言板mapper
    @Autowired
    MessageBoardMapper boardMapper;


    /**
     * 查询所有留言板信息
     *
     * @return
     */
    @Override
    public Result queryForMessageBoard() {
        List<Map<String, Object>> maps = boardMapper.queryForMessageBoard();
        return Result.ok().data("boardList", maps);
    }

    /**
     * 根据用户id查询对应用户对应的留言板信息
     *
     * @return
     */
    @Override
    public Result queryForMessageBoardById(Integer id) {
        if (null == id) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        List<Map<String, Object>> maps = boardMapper.queryForMessageBoardById(id);
        return Result.ok().data("boardList", maps);
    }

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Result queryForMessageBoardLimit(Integer currentPage, Integer pageSize) {
        Integer start = (currentPage - 1) * pageSize;

        List<Map<String, Object>> maps = boardMapper.queryForMessageBoardLimit(start, pageSize);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Integer totalPage = boardMapper.queryForMessageBoardTotalCount();
        resultMap.put("commodityList", maps);
        resultMap.put("totalPage", totalPage);
        return  Result.ok().data(resultMap);
    }

    /**
     * 新增一条留言板信息
     *
     * @return
     */
    @Override
    public Result addMessageBoardInfo(MessageBoard messageBoard) {
        System.out.println("8468s4ad5465s4d654d6as54d65sa4d6sa54d6sa874d68sa574dsa654d6a5s4d6asd6sa5d6sa5d"+messageBoard);
        if (null == messageBoard.getUserId() || null == messageBoard.getMessageInfo() ||
                "".equals(messageBoard.getMessageInfo().trim())
        ) {
            //参数有误
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = boardMapper.addMessageBoardInfo(messageBoard);
        if (integer < 1) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

    /**
     * 修改对应留言板信息
     *
     * @return
     */
    @Override
    public Result updateMessageBoardInfoById(MessageBoard messageBoard) {
        if (null == messageBoard.getId()) {
            //参数有误
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = boardMapper.updateMessageBoardInfoById(messageBoard);
        if (integer < 1) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }

    /**
     * 删除对应留言板信息
     *
     * @return
     */
    @Override
    public Result deleteMessageBoardInfoById(Integer id) {
        if (null == id) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        Integer integer = boardMapper.deleteMessageBoardInfoById(id);
        if (integer < 1) {
            return Result.error(ResultCodeEnum.FAIL);
        }
        return Result.ok();
    }
    /**
     * 批量删除对应留言板信息
     *
     * @return
     */
    @Override
    public Result deleteMessageBoardInfoBatchById(Integer[] messageIds) {
        if(null==messageIds){
            return Result.error(ResultCodeEnum.FAIL);
        }
        boardMapper.deleteMessageBoardInfoBatchById(messageIds);
        return Result.ok();
    }
}
