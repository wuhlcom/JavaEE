package fish.yu.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import fish.yu.dao.BeautifulPicturesMapper;
import fish.yu.entity.BeautifulPictures;

/**
 * 
 *  @author yuliang-ds1
 *  服务实现类
 *  这个类在继承了ServiceImpl之后，会有相应的增删改查以及分页的相关方法
 *  敏捷开发
 */
@Service
public class BeautifulPicturesService extends ServiceImpl<BeautifulPicturesMapper, BeautifulPictures> {
	
}
