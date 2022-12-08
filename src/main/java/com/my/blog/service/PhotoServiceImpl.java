package com.my.blog.service;

import com.my.blog.constants.CommonConstants;
import com.my.blog.dao.PhotoRepository;
import com.my.blog.po.Photo;
import com.my.blog.vo.AblumVo;
import com.my.blog.vo.PageVo;
import com.my.blog.vo.PhotoVo;
import com.my.blog.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    //本地缓存  -- 事实证明在我这种情形下本地缓存更快
    private Map<String, List<AblumVo>> localCache = new HashMap<>();


    @Override
    public PageVo getPage(Pageable pageable) {

        Page<Photo> photos = photoRepository.findAll(new Specification<Photo>() {
            @Override
            public Predicate toPredicate(Root<Photo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Predicate predicate = criteriaBuilder.equal(root.get("isDel"), 0);

                return criteriaQuery.where(predicate).getRestriction();
            }
        }, pageable);
//        Page<Photo> photos = photoRepository.findAll(pageable);

        return new PageVo(photos);
    }

    @Transactional
    @Override
    public void addPhoto(String[] imgs) {

        for (String img : imgs) {

            Photo photo = new Photo();

            photo.setType("1");

            photo.setImgBase64(img);

            photo.setUploadDate(new Date());

            //0 未删除
            photo.setIsDel(0);

            photoRepository.save(photo);
        }


    }

    @Transactional
    @Override
    public void delPhotoById(Integer photoId) {

        Photo one = photoRepository.getOne(photoId);

        //逻辑删除
        one.setIsDel(1);

        photoRepository.save(one);

    }

    //根据id查一个
    @Override
    public Photo findOne(Integer id) {
        return photoRepository.getOne(id);
    }

//    @CacheEvict(cacheNames = {"Ablums"},key = "'findAblums'")
    @Transactional
    @Override
    public void addPhoto(PhotoVo[] photoVos) {

        //添加数据时候先使缓存失效
        if (!localCache.isEmpty()){
            localCache.remove(CommonConstants.LOCAL_CACHE_ABLUMS_KEY);
        }

        Arrays.stream(photoVos).forEach(photoVo -> {

            Photo photo = new Photo();
            photo.setType("1");
            //0 未删除
            photo.setIsDel(0);

            photo.setUploadDate(new Date());

            photo.setImgBase64(photoVo.getImgBase64());

            photo.setCityId(photoVo.getCityId());

            photo.setCityName(photoVo.getCityName());

            photo.setTitle(photoVo.getTitle());

            photoRepository.save(photo);
        });

    }

//    本地缓存
//    @Cacheable(cacheNames = {"Ablums"},key = "#root.methodName")
    @Override
    public ResultVo<List<AblumVo>> findAblums() {
//        localCache.remove(CommonConstants.LOCAL_CACHE_ABLUMS_KEY);
        if (!localCache.isEmpty()){

            List<AblumVo> ablumVos = localCache.get(CommonConstants.LOCAL_CACHE_ABLUMS_KEY);

            return new ResultVo<List<AblumVo>>(ablumVos);
        }

        //photoRepository.

        //找到所有城市名称
        List<String> cityNames = photoRepository.findAllCityName();

        List<AblumVo> ablumVos = new ArrayList<>();

        for (String cityName : cityNames) {

            AblumVo ablumVo = new AblumVo();

            List<Photo> photos =   photoRepository.findAlbumByCityName(cityName);

            ablumVo.setCityName(cityName);

            ablumVo.setPhotos(photos);

            ablumVos.add(ablumVo);
        }

        localCache.put("cache",ablumVos);

        ResultVo<List<AblumVo>> resultVo = new ResultVo<>(ablumVos);

        return resultVo;
    }
}
