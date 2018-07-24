package com.aisile.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aisile.mapper.TbBrandMapper;
import com.aisile.pojo.TbBrand;
import com.aisile.pojo.TbBrandExample;
import com.aisile.pojo.TbBrandExample.Criteria;
import com.aisile.pojo.entity.PageResult;
import com.aisile.sellergoods.service.BrandService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private TbBrandMapper tbBrandMapper;

	@Override
	public List<TbBrand> geTbBrands() {
		return tbBrandMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbBrand> pageinfo = (Page<TbBrand>) tbBrandMapper.selectByExample(null);
		return new PageResult(pageinfo.getTotal(), pageinfo.getResult());
	}

	@Override
	public void add(TbBrand tbBrand) {
		tbBrandMapper.insert(tbBrand);
	}

	@Override
	public void update(TbBrand tbBrand) {
		tbBrandMapper.updateByPrimaryKeySelective(tbBrand);
		
	}

	@Override
	public TbBrand findOne(Long id) {
		return tbBrandMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			tbBrandMapper.deleteByPrimaryKey(id);
		}
		
	}

	@Override
	public PageResult findSearch(int pageNum, int pageSize, TbBrand tbBrand) {
		PageHelper.startPage(pageNum, pageSize);
		//多条件查询
		TbBrandExample brandExample = new TbBrandExample();
		//开始拼接条件
		Criteria criteria = brandExample.createCriteria();
		if(tbBrand.getName() != null && !tbBrand.getName().equals("")) {
			criteria.andNameLike("%"+tbBrand.getName()+"%");
		}
		if(tbBrand.getFirstChar() != null && !tbBrand.getFirstChar().equals("")) {
			criteria.andFirstCharEqualTo(tbBrand.getFirstChar());
		}
		Page<TbBrand> pageinfo = (Page<TbBrand>) tbBrandMapper.selectByExample(brandExample);
		return new PageResult(pageinfo.getTotal(), pageinfo.getResult());
	}

	@Override
	public List<Map> selectOptionList() {
		return tbBrandMapper.selectOptionList();
	}
	
	

}
