package com.my.blog.service;

import com.my.blog.NotFoundException;
import com.my.blog.dao.BlogRepository;
import com.my.blog.po.Blog;
import com.my.blog.po.Type;
import com.my.blog.util.MarkdownUtils;
import com.my.blog.util.MyBeanUtils;
import com.my.blog.vo.BlogQuery;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public Blog addBlog(Blog blog) {

        //第一次保存时
        if (blog.getId() == null)
        {
            //保存博客时初始化一些数据
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            //浏览次数
            blog.setViews(0);
        }
        //非第一次保存
        else
            {
                blog.setUpdateTime(new Date());
            }

        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        blogRepository.deleteById(id);

    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blogUp = blogRepository.getOne(id);

        if (blogUp ==null)
        {
            throw new NotFoundException("该博客不存在!!");
        }
        //在此处进行修改操作 blog和blogUp的相同属性值之间传递，且blog赋值给blogUp      source, target
        //增加过滤掉blog属性值为空的值
        BeanUtils.copyProperties(blog,blogUp, MyBeanUtils.getNullPropertyNames(blog));

        //设置更新时间
        blogUp.setUpdateTime(new Date());

        //再保存，并返回
        return blogRepository.save(blogUp);
    }

    @Override
    public Blog findBlog(Long id) {
        List<String> tagIds = blogRepository.findTagsByBlogId(id);
        String join = String.join(",", tagIds);

        Blog blog = blogRepository.getOne(id);
        blog.setTagIds(join);
        return blog;
    }

    @Override
    public Blog findBlogByTitle(String blogTitle) {
        return this.blogRepository.findBlogByTitle(blogTitle);
    }

    //转化成html格式
    @Transactional
    @Override
    public Blog getOneConvert(Long id) {

        Blog blog = blogRepository.getOne(id);
        if (blog == null)
        {
            throw new NotFoundException("博客不存在");
        }
        //老师说这里这样做避免hibernate操作数据库
        Blog b = new Blog();
        //转化
        String content = blog.getContent();
        String htmlContent = MarkdownUtils.markdownToHtmlExtensions(content);
        blog.setContent(htmlContent);

        BeanUtils.copyProperties(blog,b);
        //更新浏览次数
         blogRepository.updateViews(id);
        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        Page<Blog> blogs = blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                //查询标题都不为空时查询方法
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                //类型不为空
                if (blog.getTypeId() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));

                }
                //是推荐时
                if (blog.getRecommend()!=null&&blog.getRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.getRecommend()));
                }
                //query.where(predicates.toArray(new Predicate[predicates.size()]));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
        return blogs;
    }

    //根据
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    public List<Blog> listRecommendTopBlogs(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable =  PageRequest.of(0,size,sort);
        return blogRepository.findRecommendTop(pageable);
    }

    @Override
    public Page<Blog> listLatestBlogs(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable =  PageRequest.of(0,size,sort);

        return blogRepository.findAll(pageable);
    }


    //模糊查询
    @Override
    public List<Blog> searchTitle(String title) {
        List<Blog> blogs = blogRepository.findByTitleLike("%"+title+"%");
        return blogs;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    //关联表，通过中间表查询 tag_blog
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> groupYear = blogRepository.findGroupYear();
        Map<String, List<Blog>> listMap = new HashMap<>();
        for (String year : groupYear) {
            listMap.put(year,blogRepository.findByYear(year));
        }
        return listMap;
    }

}
