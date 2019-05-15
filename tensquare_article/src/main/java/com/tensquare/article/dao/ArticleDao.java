package com.tensquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    /* *
     * @Author zhaocq
     * @Description //TODO 点赞
     * @Date 11:46 2019/5/6 0006
     * @Param [articleId]
     * @return void
     **/
    @Modifying
    @Query(value = "update tb_article SET thumbup = thumbup + 1 where id = ?",nativeQuery = true)
    void thumbup(String articleId);

    /* *
     * @Author zhaocq
     * @Description //TODO 文章审核
     * @Date 11:50 2019/5/6 0006
     * @Param [articleId]
     * @return void
     **/
    @Modifying
    @Query(value = "update tb_article SET state = 1 where id = ?",nativeQuery = true)
    void examine(String articleId);
}
