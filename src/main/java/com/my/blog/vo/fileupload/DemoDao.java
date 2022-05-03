package com.my.blog.vo.fileupload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DemoDao extends JpaRepository<FileEntity,Integer>, JpaSpecificationExecutor<FileEntity> {
}
