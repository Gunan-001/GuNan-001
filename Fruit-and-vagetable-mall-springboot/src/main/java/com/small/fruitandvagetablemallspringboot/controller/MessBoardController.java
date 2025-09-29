package com.small.fruitandvagetablemallspringboot.controller;

import com.small.fruitandvagetablemallspringboot.pojo.MessageBoard;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.result.ResultCodeEnum;
import com.small.fruitandvagetablemallspringboot.service.MessageBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 留言板controller
 *
 * @author GH
 * @date 2022-04-13 20:36
 */
@RestController
@RequestMapping("/messBoard")

public class MessBoardController {
    //自动装配留言板service
    @Autowired
    MessageBoardService service;

    /**
     * 查询所有留言板信息
     *
     * @return result
     */
//    @ApiOperation("查询所有留言板信息")
    @GetMapping("/query-ForMessageBoard")
    public Result queryForMessageBoard() {
        return service.queryForMessageBoard();
    }

    /**
     * 根据id查询对应留言板信息
     *
     * @param id
     * @returnx
     */
//    @ApiOperation("根据ID查询留言板信息")
    @GetMapping("/query-ForMessageBoard-ById/{id}")
   // @ApiImplicitParam(value = "个人资料主键", name = "id", required = true, paramType = "path", dataType = "int")
    public Result queryForMessageBoardById(@PathVariable("id") Integer id) {
        return service.queryForMessageBoardById(id);
    }

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
//    @ApiOperation("分页查询留言板信息")
    @GetMapping("/query-ForMessageBoard-Limit")
    public Result queryForMessageBoardLimit(@RequestParam("currentPage") Integer currentPage,
                                            @RequestParam("pageSize") Integer pageSize) {
        if (null == currentPage || null == pageSize) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        return service.queryForMessageBoardLimit(currentPage, pageSize);
    }

    /**
     * 添加一条留言板信息
     *
     * @param messageBoard
     * @return
     */
//    @ApiOperation("添加一条留言板信息")
    @PostMapping("/add-MessageBoardInfo")
   // @ApiImplicitParam(value = "留言板信息实体", name = "MessageBoard", paramType = "body", dataType = "messageBoard")
    public Result addMessageBoardInfo(@RequestBody(required = false) MessageBoard messageBoard) {

        return service.addMessageBoardInfo(messageBoard);
    }

    /**
     * 修改对应留言板信息
     *
     * @param messageBoard
     * @return
     */
//    @ApiOperation("修改对应留言板信息")
    @PutMapping("/update-MessageBoardInfo-ById")
   // @ApiImplicitParam(value = "留言板信息实体", name = "MessageBoard", paramType = "body", required = true, dataType = "messageBoard")
    public Result updateMessageBoardInfoById(@RequestBody(required = false) MessageBoard messageBoard) {
        return service.updateMessageBoardInfoById(messageBoard);
    }


    /**
     * 删除对应留言板信息
     *
     * @param id
     * @return
     */
//    @ApiOperation("删除对应留言板信息")
    @DeleteMapping("/delete-MessageBoardInfo-ById/{id}")
   // @ApiImplicitParam(value = "留言板信息表主键", name = "id", required = true, paramType = "path", dataType = "int")
    public Result deleteCommodityInfoById(@PathVariable("id") Integer id) {
        return service.deleteMessageBoardInfoById(id);
    }

    /**
     * 批量删除对应留言板信息
     *
     * @param messageIds
     * @return
     */
//    @ApiOperation("删除对应留言板信息")
    @PostMapping("/delete-MessageBoardInfoBatch-ById")
   // @ApiImplicitParam(value = "留言板主键数组", name = "messageIds", paramType = "body", required = true, dataType = "int")
    public Result deleteMessageBoardInfoBatchById(@RequestBody(required = true) Integer[] messageIds) {
        return service.deleteMessageBoardInfoBatchById(messageIds);
    }

}
