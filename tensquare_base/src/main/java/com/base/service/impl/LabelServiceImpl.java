package com.base.service.impl;

import com.base.dao.LabelDao;
import com.base.domain.Label;
import com.base.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Name: LabelServiceImpl
 * User: zhaocq
 * Date: 2019/5/5 0005
 * Time: 11:29
 * Description:
 */
@Service
public class LabelServiceImpl implements LabelService{

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    @Override
    public void save(Label label) {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    @Override
    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    @Override
    public void update(Label label) {
        labelDao.save(label);
    }

    @Override
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    @Override
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    @Override
    public List<Label> search(Label label) {

        // 创建 Specification对象
        Specification specification = new Specification() {
            @Nullable
            @Override
            public Predicate toPredicate(
                    Root root, CriteriaQuery cq, CriteriaBuilder cb) {
                // 创建条件集合
                ArrayList<Predicate> predicates = new ArrayList<>();
                // 拼接条件
                if(label.getLabelname() != null && !"".equals(label.getLabelname())){
                    Predicate p1 = cb.like(root.get("labelname"), "%"+ label.getLabelname() + "%");
                    predicates.add(p1);
                }
                if(label.getRecommend() != null && !"".equals(label.getRecommend())){
                    Predicate p2 = cb.equal(root.get("recommend"), label.getRecommend());
                    predicates.add(p2);
                }
                if(label.getState() != null && !"".equals(label.getState())){
                    Predicate p3 = cb.equal(root.get("state"), label.getState());
                    predicates.add(p3);
                }

                // 创建条件数组
                Predicate[] predicateArr = {};
                return cb.and(predicates.toArray(predicateArr));
            }
        };

        return labelDao.findAll(specification);
    }

    @Override
    public Page<Label> search(Label label, int page, int size) {
        // 创建 Specification对象
        Specification specification = new Specification() {
            @Nullable
            @Override
            public Predicate toPredicate(
                    Root root, CriteriaQuery cq, CriteriaBuilder cb) {
                // 创建条件集合
                ArrayList<Predicate> predicates = new ArrayList<>();
                // 拼接条件
                if(label.getLabelname() != null && !"".equals(label.getLabelname())){
                    Predicate p1 = cb.like(root.get("labelname"), "%"+ label.getLabelname() + "%");
                    predicates.add(p1);
                }
                if(label.getRecommend() != null && !"".equals(label.getRecommend())){
                    Predicate p2 = cb.equal(root.get("recommend"), label.getRecommend());
                    predicates.add(p2);
                }
                if(label.getState() != null && !"".equals(label.getState())){
                    Predicate p3 = cb.equal(root.get("state"), label.getState());
                    predicates.add(p3);
                }

                // 创建条件数组
                Predicate[] predicateArr = {};
                return cb.and(predicates.toArray(predicateArr));
            }
        };

        // 创建分页对象
        Pageable pageable = PageRequest.of(page - 1,size);
        return labelDao.findAll(specification,pageable);
    }

}
