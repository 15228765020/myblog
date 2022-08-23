package com.my.blog.dao;

import com.my.blog.po.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo,Integer>, JpaSpecificationExecutor<Photo> {

    @Query(nativeQuery = true,value = "select distinct city_name from t_photo_album where is_del = 0")
    List<String> findAllCityName();

    @Query(nativeQuery = true,value = "SELECT * FROM t_photo_album WHERE city_name = ?1 and is_del = 0")
    List<Photo> findAlbumByCityName(String cityName);

//    Page<Photo> findPageByCondition(Pageable pageable);
}
