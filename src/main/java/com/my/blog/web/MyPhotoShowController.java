package com.my.blog.web;

import com.my.blog.service.PhotoService;
import com.my.blog.vo.AblumVo;
import com.my.blog.vo.ResultVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    private Log log = LogFactory.getLog(MyPhotoShowController.class);

    @RequestMapping("index")
    @ResponseBody
//    ResultVo<List<AblumVo>>
    public ResultVo<List<AblumVo>> getAblums() {
        long l1 = System.currentTimeMillis();

        ResultVo<List<AblumVo>> resultVo =  photoService.findAblums();

        long l2 = System.currentTimeMillis();

        log.info("getAblums()接口 总共运行时间为 = "+ (l2 -l1)/1000.0+"s");
        return resultVo;
    }
}
