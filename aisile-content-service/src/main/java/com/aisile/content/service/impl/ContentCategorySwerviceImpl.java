package com.aisile.content.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aisile.content.service.ContentCategoryService;
import com.aisile.mapper.TbContentCategoryMapper;
import com.aisile.pojo.TbContentCategory;
import com.aisile.pojo.TbContentCategoryExample;
import com.aisile.pojo.TbContentCategoryExample.Criteria;
import com.aisile.pojo.TbItemCat;
import com.aisile.pojo.entity.PageResult;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
@Service
public class ContentCategorySwerviceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	

	@Override
	public PageResult findSearch(int pageNum, int pageSize, TbContentCategory tbContentCategory) {
		 PageHelper.startPage(pageNum, pageSize);
		 TbContentCategoryExample example = new TbContentCategoryExample();
		 Criteria createCriteria = example.createCriteria();
		 if(tbContentCategory.getName() != null && !tbContentCategory.getName().equals("")) {
			createCriteria.andNameLike("%"+tbContentCategory.getName()+"%");
		 }
		 Page<TbContentCategory> pageinfo = (Page<TbContentCategory>) tbContentCategoryMapper.selectByExample(example);
		return new PageResult(pageinfo.getTotal(), pageinfo.getResult());
	}




	@Override
	public void add(TbItemCat tbItemCat) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void update(TbItemCat tbItemCat) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public TbItemCat findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public List<TbItemCat> findAllByParentId(Long parentId) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public List<TbItemCat> findOne1(Long id) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public List<TbContentCategory> findAll() {
		return tbContentCategoryMapper.selectByExample(null);
	}




	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
