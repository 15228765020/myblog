package com.my.blog.web.admin;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.my.blog.service.VisitorRecordsService;
import com.my.blog.vo.VisitorRecordsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class VisitorInfoController {

    @Autowired
    private VisitorRecordsService recordsService;
    /**
     * 根据条件查询访客记录 并分页 /admin/visitorinfo
     */
    @RequestMapping("/visitorinfo")
    public String getVisitorInfoByCondotion(@PageableDefault(sort = {"lastVisitTime"},size = 8,direction = Sort.Direction.DESC) Pageable pageable, String keyWord, Model model){

        Page<VisitorRecordsVo> resPage = this.recordsService.findByConditon(pageable,keyWord);


        if (CollUtil.isEmpty(resPage.getContent()))
        {
            model.addAttribute("yes","访客记录列表为空~~");
        }

        //如果在前端点击搜索，那么只刷新表格内容

        if (StrUtil.isNotBlank(keyWord))
        {
            model.addAttribute("visitorPage",resPage);
            return "admin/visitor-info:: visitorList";
        }
        else
        {
            model.addAttribute("visitorPage",resPage);
            return "admin/visitor-info";

        }
    }
}
