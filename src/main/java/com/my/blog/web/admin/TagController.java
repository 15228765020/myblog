package com.my.blog.web.admin;


import com.my.blog.po.Tag;
import com.my.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tagsList(@PageableDefault(sort = {"id"},
                                            size = 8,
                                            direction = Sort.Direction.DESC)
                                            Pageable pageable, Model model){
        Page<Tag> tags = tagService.listTag(pageable);
        model.addAttribute("page",tags);

        return "admin/tags";
    }
    //新增保存
    @PostMapping("/tags")
    public String addSave(@Valid Tag tag,BindingResult result,RedirectAttributes attributes,HttpSession session){
        //不能添加重复的
        Tag byName = tagService.findByName(tag.getName());
        if (byName!=null)
        {
            result.rejectValue("name","nameError","不能添加重复的标签!!");
            //然后回到之前的页面
            if (result.hasErrors())
            {
                return "admin/tags-input";
            }
        }
        else
        {
            //修改操作
            Tag tag1 = tagService.saveTag(tag);
            msgBack(tag1,attributes,session);
        }
        return "redirect:/admin/tags";
    }
    //修改保存
    @PostMapping("/tags/{id}")
    public String updateSave(@Valid Tag tag,BindingResult result, @PathVariable Long id,RedirectAttributes attributes,HttpSession session){

        //修改操作 不能添加重复的
        Tag byName = tagService.findByName(tag.getName());
        if (byName!=null)
        {
            result.rejectValue("name","nameError","不能添加重复的标签!!");
            //然后回到之前的页面
            if (result.hasErrors())
            {
                return "admin/tags-input";
            }
        }
        else
        {
            //修改操作
            Tag tag1 = tagService.updateTag(id, tag);
            msgBack(tag1,attributes,session);
        }
        return "redirect:/admin/tags";
    }
    //跳到新增
    @GetMapping("/tags/input")
    public String addInput(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    //跳到编辑
    @GetMapping("/tags/{id}/input")
    public String tagsInput(@PathVariable Long id,Model model){
        Tag tag2 = tagService.getTag(id);
        model.addAttribute("tag",tag2);
        return "admin/tags-input";
    }


    //删除
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("yes","删除成功!!");
        return "redirect:/admin/tags";
    }

    //操作结果反馈
    private void msgBack(Tag tag, RedirectAttributes attributes, HttpSession session){
        //反馈
        if (tag!=null)
        {
            attributes.addFlashAttribute("yes","操作成功!!");
            session.setAttribute("tag1",tag);
        }
        else
        {
            attributes.addFlashAttribute("no","操作失败!!");
        }
    }


}
