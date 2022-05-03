package com.my.blog.web.admin;

import com.my.blog.po.Type;
import com.my.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    //测试window带参跳转

    @RequestMapping("/test")
    public String test(String my, Model model, HttpServletRequest request){

        model.addAttribute("my",my);
        return "admin/test";
    }
    //查询全部分类
    @GetMapping("types")
    public String list(@PageableDefault(size = 10,sort = {"id"}
                                    ,direction = Sort.Direction.DESC
                                        )Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }
    //跳到新增
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    //跳到编辑页面（之前的新增编辑页面复用）
    @GetMapping("/types/{id}/input")
    public String editPost(@PathVariable Long id, Model model){
        //根据id查分类
        Type type = typeService.getType(id);
        model.addAttribute("type",type);
        return "admin/types-input";
    }

    //保存  注意此路径和前面的查询所有分类 post和get提交方式的不同
    //新增后提交
    @PostMapping("types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes
            , HttpSession session){

            //先校验再保存
            Type tn = typeService.findByName(type.getName());
            //System.out.println("之前有过此类型为:"+tn.getName());
            if (tn!=null)
            {
                //数据库保存过此类型 BindingResult提醒
                result.rejectValue("name","nameError","不能添加重复的分类!!");
                //下面细节!!务必写在后面
                if (result.hasErrors()){
                    return "admin/types-input";
                }
            }else {
            //保存
            Type type1 = typeService.saveType(type);

            if (type1!=null)
            {
                 //响应数据1
                attributes.addFlashAttribute("yes","保存成功!!");
                session.setAttribute("type1",type1);
            }
            else
            {
               //响应数据2
                attributes.addFlashAttribute("no","保存失败!!请联系开发人员!!");
            }
        }
        //无论是否保存成功，都会跳转分类页
      return "redirect:/admin/types";
    }

    //删除
    @GetMapping("types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        System.out.println("===="+id+"===");
        typeService.deleteType(id);
        attributes.addFlashAttribute("yes","删除成功!!");
        return "redirect:/admin/types";
    }

    //修改过后的提交，到此修改方法(不再共用之前的保存方法)
    @PostMapping("types/{id}")
    public String editPost(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes
            , HttpSession session){

        //类型不能重复
        Type tn = typeService.findByName(type.getName());
        if (tn!=null)
        {
            //数据库保存过此类型 BindingResult提醒
            result.rejectValue("name","nameError","不能添加重复的分类!!");
            //下面细节!!务必写在后面
            if (result.hasErrors()){
                return "admin/types-input";
            }

        }
        else
         {
            //保存
            Type type1 = typeService.updateType(id,type);

            if (type1!=null)
            {
                //响应数据1
                attributes.addFlashAttribute("yes","保存成功!!");
                session.setAttribute("type1",type1);
            }
            else
            {
                //响应数据2
                attributes.addFlashAttribute("no","保存失败!!请联系开发人员!!");
            }
        }
        //无论是否保存成功，都会跳转分类页
        return "redirect:/admin/types";
    }


}
