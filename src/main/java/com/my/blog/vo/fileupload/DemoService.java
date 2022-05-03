package com.my.blog.vo.fileupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 假设这个是你的DAO存储。当然还要这个类让spring管理，当然你不用需要存储，也不需要这个类。
 **/
@Service
public class DemoService {

    @Autowired
    private DemoDao demoDao;

    public Boolean save(List<fileVo> list,Integer classId) {

        FileEntity save =null;
        for (fileVo fileVo : list) {
            //vo转实体
            FileEntity fileEntity = new FileEntity();
            fileEntity.setName(fileVo.getName());
            fileEntity.setAge(fileVo.getAge());
            fileEntity.setSex(fileVo.getSex());
            fileEntity.setClassId(classId);
             save = this.demoDao.save(fileEntity);

        }
      return save!=null;
        // 如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
    }
}