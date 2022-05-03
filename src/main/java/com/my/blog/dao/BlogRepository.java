package com.my.blog.dao;

import com.my.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
    List<Blog> findByTitleLike(String title);

    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findRecommendTop(Pageable pageable);
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);

    @Transactional
    @Modifying
    @Query(("update  Blog b set b.views = b.views+1 where b.id = ?1"))
    int updateViews(Long id);

    @Query("select function('date_format',b.updateTime,'%Y')as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year DESC ")
    List<String> findGroupYear();

    @Query("select b from Blog  b where function('date_format',b.updateTime,'%Y')= ?1")
    List<Blog> findByYear(String year);

    @Query(value = "SELECT tags_id FROM t_blog_tags   WHERE blogs_id = ?1",nativeQuery = true)
    List<String> findTagsByBlogId(Long id);

    @Query(value = "select * from t_blog where title = ?1",nativeQuery = true)
    Blog findBlogByTitle(String blogTitle);
}
