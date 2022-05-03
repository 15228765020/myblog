package com.my.blog.vo.fileupload;

import com.alibaba.excel.EasyExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController {
    //    批量导入
    @Autowired
    private DemoService demoService;

    @PostMapping("upload")
    public String upload(@RequestParam("file") MultipartFile file,Integer classId) throws IOException {
//        MultipartFile file1 = file;
        //假设批量存入3班数据
//        Integer classId = 3;
        //使用EasyExcel解析file
        DemoDataListener demoDataListener = new DemoDataListener(demoService,classId);
        EasyExcel.read(file.getInputStream(), fileVo.class, demoDataListener).headRowNumber(1).sheet().doRead();

        return "导入成功!!";
    }
}
