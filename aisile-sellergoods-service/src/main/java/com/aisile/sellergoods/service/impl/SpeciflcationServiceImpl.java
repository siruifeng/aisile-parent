package com.aisile.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aisile.mapper.TbSpecificationMapper;
import com.aisile.mapper.TbSpecificationOptionMapper;
import com.aisile.pojo.TbSpecification;
import com.aisile.pojo.TbSpecificationExample;
import com.aisile.pojo.TbSpecificationExample.Criteria;
import com.aisile.pojo.TbSpecificationOption;
import com.aisile.pojo.TbSpecificationOptionExample;
import com.aisile.pojo.entity.PageResult;
import com.aisile.pojo.entity.group.Specification;
import com.aisile.sellergoods.service.SpecificationService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
@Service
public class SpeciflcationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper tbSpecificationMapper;
	@Autowired
	private TbSpecificationOptionMapper tbSpecificationOptionMapper;

	@Override
	public List<TbSpecification> findAll() {
		return tbSpecificationMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbSpecification> pageinfo = (Page<TbSpecification>) tbSpecificationMapper.selectByExample(null);
		return new PageResult(pageinfo.getTotal(), pageinfo.getResult());
	}

	@Override
	public PageResult findSearch(int pageNum, int pageSize, TbSpecification tbSpecification) {
		PageHelper.startPage(pageNum, pageSize);
		//多条件查询
		TbSpecificationExample example = new TbSpecificationExample();
		//开始拼接条件
		Criteria criteria = example.createCriteria();
		if(tbSpecification.getSpecName() != null && !tbSpecification.getSpecName().equals("")) {
			criteria.andSpecNameLike("%"+tbSpecification.getSpecName()+"%");
		}

		Page<TbSpecification> pageinfo = (Page<TbSpecification>) tbSpecificationMapper.selectByExample(example);
		return new PageResult(pageinfo.getTotal(), pageinfo.getResult());
	}

	@Override
	public void add(Specification specification) {
		//添加规格名称
		TbSpecification tbspe = specification.getSpecification();
		tbSpecificationMapper.insert(tbspe);

		//循环添加规格明细
		for (TbSpecificationOption option : specification.getSpecificationOptionList()) {
			option.setSpecId(tbspe.getId());
			tbSpecificationOptionMapper.insert(option);
		}
	}

	@Override
	public void update(Specification specification) {
		//修改规格名称
		TbSpecification tbspe = specification.getSpecification();
		tbSpecificationMapper.updateByPrimaryKey(tbspe);
		//根据相对应的规格明细进行删除
		TbSpecificationOptionExample optionExample = new TbSpecificationOptionExample();
		com.aisile.pojo.TbSpecificationOptionExample.Criteria criteria = optionExample.createCriteria();
		criteria.andSpecIdEqualTo(tbspe.getId());
		tbSpecificationOptionMapper.deleteByExample(optionExample);
		//循环添加规格明细
		for (TbSpecificationOption option : specification.getSpecificationOptionList()) {
			option.setSpecId(tbspe.getId());
			tbSpecificationOptionMapper.insert(option);
		}
	}

	@Override
	public Specification findOne(Long id) {
		//规格实体
		TbSpecification tbspe = tbSpecificationMapper.selectByPrimaryKey(id);
		//通过规格实体的id,查询明细的数据
		TbSpecificationOptionExample optionExample = new TbSpecificationOptionExample();
		com.aisile.pojo.TbSpecificationOptionExample.Criteria criteria = optionExample.createCriteria();
		criteria.andSpecIdEqualTo(tbspe.getId());
		List<TbSpecificationOption> list = tbSpecificationOptionMapper.selectByExample(optionExample);
		Specification specification = new Specification();
		specification.setSpecification(tbspe);
		specification.setSpecificationOptionList(list);
		return specification;
	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			//循环删除规格名称
			tbSpecificationMapper.deleteByPrimaryKey(id);
			//删除规格名称应对的规格明细
			TbSpecificationOptionExample optionExample = new TbSpecificationOptionExample();
			com.aisile.pojo.TbSpecificationOptionExample.Criteria criteria = optionExample.createCriteria();
			criteria.andSpecIdEqualTo(id);//指定规格id为条件
			tbSpecificationOptionMapper.deleteByExample(optionExample);
		}
	}

	@Override
	public List<Map> selectOptionList() {
		return tbSpecificationMapper.selectOptionList();
	}

}
