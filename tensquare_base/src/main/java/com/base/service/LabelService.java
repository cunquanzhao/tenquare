package com.base.service;

import com.base.domain.Label;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Name: LabelService
 * User: zhaocq
 * Date: 2019/5/5 0005
 * Time: 11:26
 * Description: 标签的业务层接口
 */
public interface LabelService {

    /* *
     * @Author zhaocq
     * @Description //TODO 保存
     * @Date 11:27 2019/5/5 0005
     * @Param [label]
     * @return void
     **/
    void save(Label label);

    /* *
     * @Author zhaocq
     * @Description //TODO 根据Id查询
     * @Date 11:27 2019/5/5 0005
     * @Param [id]
     * @return com.base.domain.Label
     **/
    Label findById(String id);

    /* *
     * @Author zhaocq
     * @Description //TODO 更新
     * @Date 11:28 2019/5/5 0005
     * @Param [label]
     * @return void
     **/
    void update(Label label);

    /* *
     * @Author zhaocq
     * @Description //TODO 根据Id删除
     * @Date 11:28 2019/5/5 0005
     * @Param [id]
     * @return void
     **/
    void deleteById(String id);

    /* *
     * @Author zhaocq
     * @Description //TODO 查询所有
     * @Date 11:29 2019/5/5 0005
     * @Param []
     * @return java.util.List<com.base.domain.Label>
     **/
    List<Label> findAll();

    /* *
     * @Author zhaocq
     * @Description //TODO 按条件查询
     * @Date 8:49 2019/5/6 0006
     * @Param [label]
     * @return java.util.List<com.base.domain.Label>
     **/
    List<Label> search(Label label);

    /* *
     * @Author zhaocq
     * @Description //TODO 按条件查询(分页)
     * @Date 9:13 2019/5/6 0006
     * @Param [label, page, size]
     * @return org.springframework.data.domain.Page<com.base.domain.Label>
     **/
    Page<Label> search(Label label, int page, int size);
}
