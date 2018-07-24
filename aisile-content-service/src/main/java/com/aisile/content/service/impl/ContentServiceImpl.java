package com.aisile.content.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;

import com.aisile.content.service.ContentService;
import com.aisile.mapper.TbContentMapper;
import com.aisile.pojo.TbContent;
import com.aisile.pojo.TbContentExample;
import com.aisile.pojo.TbContentExample.Criteria;
import com.aisile.pojo.TbSeller;
import com.aisile.pojo.entity.PageResult;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
@Service
@ContextConfiguration(locations="classpath:spring/applicationContext-redis.xml")
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper tbContentMapper;
	//注入Spring Data Redis
	@Autowired
	private RedisTemplate redisTemplate;
	
	
	
	@Override
	public List<TbContent> findAll() {
		return tbContentMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int page, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResult findSearch(int page, int rows, TbContent tbContent) {
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		Page<TbContent> pageinof = (Page<TbContent>) tbContentMapper.selectByExample(example);
		return new PageResult(pageinof.getTotal(), pageinof.getResult());
	}

	@Override
	public void add(TbContent tbContent) {
		if(tbContent.getStatus() != null) {
			tbContentMapper.insert(tbContent);
		}else {
			tbContent.setStatus("0");
			tbContentMapper.insert(tbContent);
		}
		//清理缓存
		redisTemplate.boundHashOps("content").delete(tbContent.getCategoryId());
		
	}

	@Override
	public void update(TbContent tbContent) {
		//查询原来分组ID
		Long categoryId = tbContentMapper.selectByPrimaryKey(tbContent.getId()).getCategoryId();
		//清除原来的分组缓存
		redisTemplate.boundHashOps("content").delete(categoryId);

		tbContentMapper.updateByPrimaryKey(tbContent);
		//清除新分组缓存
		if(categoryId.longValue()!=tbContent.getCategoryId().longValue()){
			redisTemplate.boundHashOps("content").delete(tbContent.getCategoryId());
		}

	}	

	@Override
	public TbContent findOne(Long id) {
		return tbContentMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			//查询原来分组ID
			Long categoryId = tbContentMapper.selectByPrimaryKey(id).getCategoryId();
			//清除原来的分组缓存
			redisTemplate.boundHashOps("content").delete(categoryId);
			tbContentMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public void updateStatus(TbSeller tbSeller, String status) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * 根据广告分类ID查询列表
	 */
	@Override
	public List<TbContent> findByCategoryId(Long categoryId) {
		//先去查询缓存 如果有的话就直接return     
		//如果没有的话,先去数据库里面去拿一下,然后在放在缓存中,在return
		List<TbContent> contentList = (List<TbContent>) redisTemplate.boundHashOps("content").get(categoryId);		
		if(contentList != null) {
			System.out.println("进来了!!!");
			return contentList;
		}else {
			TbContentExample example = new TbContentExample();
			Criteria criteria = example.createCriteria();
			criteria.andCategoryIdEqualTo(categoryId);
			criteria.andStatusEqualTo("1");//开启默认
			example.setOrderByClause("sort_order");//排序
			contentList = tbContentMapper.selectByExample(example);
			redisTemplate.boundHashOps("content").put(categoryId, contentList);//加入缓存   缓存同步
		}
		return contentList;
	}

	@Override
	public void shield(Long[] ids) {
		for (Long id : ids) {
			TbContent content = tbContentMapper.selectByPrimaryKey(id);
			content.setStatus("0");
			tbContentMapper.updateByPrimaryKey(content);
		}
	}
	@Override
	public void openq(Long[] ids) {
		for (Long id : ids) {
			TbContent content = tbContentMapper.selectByPrimaryKey(id);
			content.setStatus("1");
			tbContentMapper.updateByPrimaryKey(content);
		}
	}


}
