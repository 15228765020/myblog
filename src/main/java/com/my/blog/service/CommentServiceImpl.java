package com.my.blog.service;

import com.my.blog.dao.CommentRepository;
import com.my.blog.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
    }
    /*
    * 循环每个顶级评论的节点
    * */

    private  List<Comment> eachComment(List<Comment> comments){
        //避免由于数据修改造成数据在数据库的变化
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中 --其实这一步最主要
        combineChildren(commentsView);

        return commentsView;
    }
    /*
    *
    *
    * */
    private void combineChildren(List<Comment> commentsView){

        for (Comment comment : commentsView) {
            //每一层 以及评论下面的子评论
            List<Comment> replyComments = comment.getReplyComments();
            for (Comment replyComment : replyComments) {
                //调用递归的方法 递归遍历子评论下面的评论
                recursively(replyComment);

            }

            //将后面的所有评论对象放在顶级节点的集合变量
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    List<Comment> tempReplys =  new ArrayList<Comment>();

    /*
    *  迭代递归，直到后继节点再无回复内容
    * */

    private void recursively(Comment comment) {

        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0)
        {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0)
                {
                    //递归
                    recursively(reply);
                }
            }
        }
    }


    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentId = comment.getParentComment().getId();
        if (parentId!=-1){
            comment.setParentComment(commentRepository.getOne(parentId));
        }
        else
        {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
}
