package com.small.fruitandvagetablemallspringboot.dao;

import com.small.fruitandvagetablemallspringboot.pojo.MessageBoard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 留言板mapper
 */
@Mapper
@Repository
public interface MessageBoardMapper {
    /**
     * 关联查询所有留言板信息 对应用户信息,返回留言板信息集合
     * @return
     */
    List<Map<String,Object>> queryForMessageBoard();


    /**
     * 根据id关联查询对应留言板信息和用户信息
     * @return
     */
    List<Map<String,Object>> queryForMessageBoardById(@Param("id") Integer id);

    /**
     * 分页查询留言板信息
     * @param start
     * @param pageSize
     * @return
     */
    List<Map<String,Object>> queryForMessageBoardLimit(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    /**
     * 查询留言板信息总条数
     * @return
     */
    Integer queryForMessageBoardTotalCount();

    /**
     * 添加留言板信息
     * @param
     * @return
     */
    Integer addMessageBoardInfo(MessageBoard messageBoard);

    /**
     * 根据id更新留言板信息
     * @param
     * @return
     */
    Integer updateMessageBoardInfoById(MessageBoard messageBoard);

    /**
     * 根据id删除留言板信息
     * @param id
     * @return
     */
    Integer deleteMessageBoardInfoById(@Param("id") Integer id);

    /**
     * 批量删除留言板信息
     * @param messageIds
     * @return
     */
    Integer deleteMessageBoardInfoBatchById(@Param("messageIds") Integer[] messageIds);
}
