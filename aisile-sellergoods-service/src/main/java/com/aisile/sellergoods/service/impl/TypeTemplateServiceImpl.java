package com.aisile.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aisile.mapper.TbSpecificationOptionMapper;
import com.aisile.mapper.TbTypeTemplateMapper;
import com.aisile.pojo.TbSpecificationOption;
import com.aisile.pojo.TbSpecificationOptionExample;
import com.aisile.pojo.TbTypeTemplate;
import com.aisile.pojo.TbTypeTemplateExample;
import com.aisile.pojo.TbTypeTemplateExample.Criteria;
import com.aisile.pojo.entity.PageResult;
import com.aisile.sellergoods.service.TypeTemplateService;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
@Service
public class TypeTemplateServiceImpl implements TypeTemplateService{

	@Autowired
	private TbTypeTemplateMapper tbTypeTemplateMapper;
	@Autowired
	private TbSpecificationOptionMapper specOptionMapper;

	@Override
	public List<TbTypeTemplate> findAll() {
		return tbTypeTemplateMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbTypeTemplate> pageinfo = (Page<TbTypeTemplate>) tbTypeTemplateMapper.selectByExample(null);
		return new PageResult(pageinfo.getTotal(), pageinfo.getResult());
	}

	@Override
	public PageResult findSearch(int pageNum, int pageSize, TbTypeTemplate tbTypeTemplate) {
		PageHelper.startPage(pageNum, pageSize);
		//多条件查询
		TbTypeTemplateExample example = new TbTypeTemplateExample();
		//开始拼接条件
		Criteria criteria = example.createCriteria();
		if(tbTypeTemplate.getName() != null && !tbTypeTemplate.getName().equals("")) {
			criteria.andNameLike("%"+tbTypeTemplate.getName()+"%");
		}

		Page<TbTypeTemplate> pageinfo = (Page<TbTypeTemplate>) tbTypeTemplateMapper.selectByExample(example);
		return new PageResult(pageinfo.getTotal(), pageinfo.getResult());
	}

@Override
	public void add(TbTypeTemplate tbTypeTemplate) {
		//添加规格名称
		tbTypeTemplateMapper.insert(tbTypeTemplate);
	}

	@Override
	public void update(TbTypeTemplate tbTypeTemplate) {
 		tbTypeTemplateMapper.updateByPrimaryKey(tbTypeTemplate);
		
	}

	@Override
	public TbTypeTemplate findOne(Long id) {
		//规格实体
		return tbTypeTemplateMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			//循环删除规格名称
			tbTypeTemplateMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public List<Map> findOptionsList(Long id) {
		TbTypeTemplate template = tbTypeTemplateMapper.selectByPrimaryKey(id);
		List<Map> list = JSON.parseArray(template.getSpecIds(), Map.class);
		for (Map map : list) {
			TbSpecificationOptionExample example = new TbSpecificationOptionExample();
			example.createCriteria().andSpecIdEqualTo(new Long((Integer)map.get("id")));
			List<TbSpecificationOption> list2 = specOptionMapper.selectByExample(example);
			map.put("options", list2);
		}
		return list;
	}


}
