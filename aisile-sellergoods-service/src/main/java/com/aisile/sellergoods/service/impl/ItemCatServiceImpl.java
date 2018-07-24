package com.aisile.sellergoods.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aisile.mapper.TbItemCatMapper;
import com.aisile.pojo.TbItemCat;
import com.aisile.pojo.TbItemCatExample;
import com.aisile.pojo.TbItemCatExample.Criteria;
import com.aisile.pojo.entity.PageResult;
import com.aisile.sellergoods.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Service;
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public List<TbItemCat> findAll() {
		return	itemCatMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResult findSearch(int pageNum, int pageSize, TbItemCat tbItemCat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(TbItemCat tbItemCat) {
		itemCatMapper.insert(tbItemCat);
	}

	@Override
	public void update(TbItemCat tbItemCat) {
		itemCatMapper.updateByPrimaryKey(tbItemCat);
		
	}

	@Override
	public TbItemCat findOne(Long id) {
		return itemCatMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<TbItemCat> findAllByParentId(Long parentId) {
		TbItemCatExample catExample = new TbItemCatExample();
		catExample.createCriteria().andParentIdEqualTo(parentId);
		return itemCatMapper.selectByExample(catExample);
	}

	@Override
	public List<TbItemCat> findOne1(Long id) {
		TbItemCatExample catExample = new TbItemCatExample();
		catExample.createCriteria().andParentIdEqualTo(id);
		return itemCatMapper.selectByExample(catExample);
	}

	@Override
	public boolean delete(Long id) {
		//根据规格id,删除规格
		TbItemCatExample catExample  = new TbItemCatExample();//新建对象
		Criteria criteria = catExample.createCriteria();//开始拼接条件
		criteria.andParentIdEqualTo(id);
		int count = itemCatMapper.countByExample(catExample);
		if(count == 0) {
			//查询count 等于0表示 是可以删除的,但是不进行return,进行接着循环.
			itemCatMapper.deleteByPrimaryKey(id);
		}else if(count != 0){
			//判断count!=0表示不可以进行删除  需要直接return结果,提示不可以删除
			return false;
		}
			//最后删除完成 fore循环 读取return  true   返回删除成功
		return true;
	}

	

}
