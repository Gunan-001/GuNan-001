package com.small.fruitandvagetablemallspringboot.service;

import com.small.fruitandvagetablemallspringboot.pojo.MessageBoard;
import com.small.fruitandvagetablemallspringboot.result.Result;
import org.apache.ibatis.annotations.Param;

/**
 * 留言板service接口
 */
public interface MessageBoardService {

    /**
     * 关联查询所有留言板信息 对应用户信息,返回留言板信息集合
     * @return
     */
    Result queryForMessageBoard();


    /**
     * 根据id关联查询对应留言板信息和用户信息
     * @return
     */
    Result queryForMessageBoardById(Integer id);

    /**
     * 分页查询留言板信息
     * @return
     */
    Result queryForMessageBoardLimit(Integer currentPage, Integer pageSize);

    /**
     * 添加留言板信息
     * @param
     * @return
     */
    Result addMessageBoardInfo(MessageBoard messageBoard);

    /**
     * 根据id更新留言板信息
     * @param
     * @return
     */
    Result updateMessageBoardInfoById(MessageBoard messageBoard);

    /**
     * 根据id删除留言板信息
     * @param id
     * @return
     */
    Result deleteMessageBoardInfoById( Integer id);


    /**
     * 批量删除留言板信息
     * @param messageIds
     * @return
     */
    Result deleteMessageBoardInfoBatchById(@Param("messageIds") Integer[] messageIds);
}
