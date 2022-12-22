package com.my.blog.web.admin;

import com.my.blog.enums.ResultCode;
import com.my.blog.po.Photo;
import com.my.blog.service.PhotoService;
import com.my.blog.vo.PageVo;
import com.my.blog.vo.PhotoVo;
import com.my.blog.vo.ResultVo;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("admin/album/")
//admin/album/albumAdd
public class MyPhotoAlbumController {

    @Autowired
    private PhotoService photoService;

    //增

    @RequestMapping(value = "albumAdd",method = RequestMethod.POST )
    @ResponseBody
//    Photo photo, MultipartFile files
    public ResultVo addPhoto(@RequestBody PhotoVo[] photoVos) throws IOException {


        photoService.addPhoto(photoVos);

        System.out.println(photoVos);

        return new ResultVo(ResultCode.SUCCESS);
    }

    //删

    @RequestMapping("delPhoto/{photoId}")
    public String delPhoto( @PathVariable("photoId") Integer photoId){
        photoService.delPhotoById(photoId);
        //TODO 测试修改4
        return "redirect:http://localhost:8081/admin/album/photoAlbumIndex";
//        return "redirect:http://www.blog4cl.top/admin/album/photoAlbumIndex";
    }

    //改

    //查  管理首页

    @RequestMapping("photoAlbumIndex")
    public String getPhotoPage(@PageableDefault(sort = {"id"},
            page = 0,
            size = 8,
            direction = Sort.Direction.DESC)
                                           Pageable pageable, Model model){
        PageVo page = photoService.getPage(pageable);
        model.addAttribute("page",page);

        return "admin/photo_album";
    }

    //根据id查一个

    @RequestMapping("findOne/{id}")
    public String showDetail(@PathVariable("id") Integer id,Model model){

        Photo photo = photoService.findOne(id);

        model.addAttribute("photo",photo);

        return "admin/photo_album_add";
    }



}
