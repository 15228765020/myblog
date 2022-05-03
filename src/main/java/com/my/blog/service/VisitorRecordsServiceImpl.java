package com.my.blog.service;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.my.blog.dao.VisitorRecordsDao;
import com.my.blog.po.VisitorRecords;
import com.my.blog.vo.VisitorRecordsVo;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VisitorRecordsServiceImpl implements VisitorRecordsService {

    @Autowired
    private VisitorRecordsDao visitorRecordsDao;

    private Logger logger = LoggerFactory.getLogger(VisitorRecordsServiceImpl.class);

    @Override
    public VisitorRecords findByIp(String ip) {
        return this.visitorRecordsDao.findByVisitorIp(ip);
    }

    /**
     * 保存访问记录
     * @param visitorRecords
     * @return
     */
    @Override
    public VisitorRecords save(VisitorRecords visitorRecords) {

        return this.visitorRecordsDao.save(visitorRecords);
    }

    /***
     * 查询访客记录并分页
     *
     * @param pageable
     * @param keyWord
     * @return
     */
    @Override
    public Page<VisitorRecordsVo> findByConditon(Pageable pageable, String keyWord) {
        Page<VisitorRecords> recordsPage = this.visitorRecordsDao.findAll(new Specification<VisitorRecords>() {
            @Override
            public Predicate toPredicate(Root<VisitorRecords> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                //关键词
                if (StrUtil.isNotBlank(keyWord))
                   {
                       list.add(criteriaBuilder.like(root.get("visitorAddress"),'%'+keyWord+'%'));
                   }

                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        }, pageable);

        List<VisitorRecords> content = recordsPage.getContent();
        if (CollUtil.isEmpty(content))
        {
            logger.error("访客记录列表为空~~~");
        }


      Page<VisitorRecordsVo> voPage =   recordsPage.map(visitorRecords -> {
          return entityToVo(visitorRecords);
        });
        return voPage;
    }


    //实体转vo

    private VisitorRecordsVo entityToVo(VisitorRecords visitorRecords){
        VisitorRecordsVo recordsVo = new VisitorRecordsVo();
        //
        recordsVo.setIpAdr(visitorRecords.getVisitorIp());

        recordsVo.setAdreess(visitorRecords.getVisitorAddress());

        recordsVo.setVisitCount(visitorRecords.getTotalVisitNumber());

        return recordsVo;
    }
}
