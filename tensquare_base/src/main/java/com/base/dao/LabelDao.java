package com.base.dao;

import com.base.domain.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Name: LabelDao
 * User: zhaocq
 * Date: 2019/5/5 0005
 * Time: 11:25
 * Description: 标签的持久层接口
 */
public interface LabelDao extends JpaRepository<Label,String>, JpaSpecificationExecutor<Label>{
}
