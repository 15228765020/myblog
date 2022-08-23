package com.my.blog.web;

import com.my.blog.service.PhotoService;
import com.my.blog.vo.AblumVo;
import com.my.blog.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("ablum/")
public class MyPhotoShowController {

    @Autowired
    private PhotoService photoService;

    @RequestMapping("index")
    @ResponseBody
//    ResultVo<List<AblumVo>>
    public ResultVo<List<AblumVo>> getAblums(){

        ResultVo<List<AblumVo>> resultVo =  photoService.findAblums();

        return resultVo;
    }
}
