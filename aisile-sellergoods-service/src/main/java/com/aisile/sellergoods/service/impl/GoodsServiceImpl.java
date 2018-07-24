package com.aisile.sellergoods.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aisile.mapper.TbBrandMapper;
import com.aisile.mapper.TbGoodsDescMapper;
import com.aisile.mapper.TbGoodsMapper;
import com.aisile.mapper.TbItemCatMapper;
import com.aisile.mapper.TbItemMapper;
import com.aisile.mapper.TbSellerMapper;
import com.aisile.pojo.TbGoods;
import com.aisile.pojo.TbGoodsDesc;
import com.aisile.pojo.TbGoodsExample;
import com.aisile.pojo.TbGoodsExample.Criteria;
import com.aisile.pojo.TbItem;
import com.aisile.pojo.TbItemExample;
import com.aisile.pojo.TbSeller;
import com.aisile.pojo.entity.PageResult;
import com.aisile.pojo.entity.group.Goods;
import com.aisile.sellergoods.service.GoodsService;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper tbGoodsMapper;
	@Autowired
	private TbGoodsDescMapper tbGoodsDescMapper;
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbBrandMapper tbBrandMapper;
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	@Autowired
	private TbSellerMapper tbSellerMapper;
	
	
	private void setItemValue(Goods goods,TbItem tbItem) {
		tbItem.setCategoryid(goods.getGoods().getCategory3Id());//商品分类编号3级
		tbItem.setCreateTime(new Date());
		tbItem.setUpdateTime(new Date());
		tbItem.setGoodsId(goods.getGoods().getId());//商家supId
		TbSeller seller = tbSellerMapper.selectByPrimaryKey(goods.getGoods().getSellerId());
		tbItem.setSeller(seller.getNickName());//商家名称
		tbItem.setSellerId(seller.getSellerId());//商家ID
		tbItem.setBrand(tbBrandMapper.selectByPrimaryKey(goods.getGoods().getBrandId()).getName());
		tbItem.setCategory(tbItemCatMapper.selectByPrimaryKey(goods.getGoods().getCategory3Id()).getName());
		//加载图片
		List<Map> imgs = JSON.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
		if (imgs != null) {
			if (imgs.size()>0) {
				tbItem.setImage((String)imgs.get(0).get("url"));
			}
		}
	
	}
	
	
	
	
	@Override
	public List<TbGoods> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResult findSearch(int pageNum, int pageSize, TbGoods tbGoods) {
		//分页
		PageHelper.startPage(pageNum, pageSize);
		TbGoodsExample example = new TbGoodsExample();
		Criteria criteria = example.createCriteria();
		/*//根据商家的ID查询商家的商品
		if (tbGoods.getSellerId() != null && tbGoods.getSellerId().length()>0) {
			criteria.andSellerIdEqualTo(tbGoods.getSellerId());
		}*/
		if (tbGoods.getAuditStatus() != null && tbGoods.getAuditStatus().length()>0) {
			criteria.andAuditStatusEqualTo(tbGoods.getAuditStatus());
		}
		if (tbGoods.getGoodsName() != null && tbGoods.getGoodsName().length()>0) {
			criteria.andGoodsNameLike("%"+tbGoods.getGoodsName()+"%");
		}
		Page<TbGoods> pageinfo = (Page<TbGoods>) tbGoodsMapper.selectByExample(example);
		
		return new PageResult(pageinfo.getTotal(), pageinfo.getResult());
	}

	@Override
	public void add(Goods goods) {
		//保存的是sup
		goods.getGoods().setAuditStatus("0");//未审核状态
		goods.getGoods().setIsMarketable("0");//下架
		tbGoodsMapper.insert(goods.getGoods());
		//可以获得goods插入的id
		//保存的是sup_desc
		goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());
		tbGoodsDescMapper.insert(goods.getGoodsDesc());
		//保存的是suk       省略(以后再做)
		
		saveItemList(goods);
	}
	
	
	private void saveItemList(Goods goods) {
		List<TbItem> list = goods.getItemList();
		if("1".equals(goods.getGoods().getIsEnableSpec())) {//如果启动了规格
			for (TbItem tbItem : list) {
				//需要哪些属性
				//商品品牌. 根据规格拼接
				String title = goods.getGoods().getGoodsName();
				Map<String, Object> specmap = JSON.parseObject(tbItem.getSpec());
				for (String maps : specmap.keySet()) {
					title+=","+specmap.get(maps);
				}
				tbItem.setTitle(title);
				setItemValue(goods, tbItem);
				tbItemMapper.insert(tbItem);
			}
		}else {//没有启动规格     但是有默认的一个sku
			TbItem tbItem = new TbItem();
			//需要哪些属性
			//商品品牌. 根据规格拼接
			String title = goods.getGoods().getGoodsName();
			tbItem.setTitle(title);//标题
			tbItem.setPrice(goods.getGoods().getPrice());//价格
			tbItem.setStatus("1");//状态
			tbItem.setIsDefault("1");//默认
			tbItem.setNum(9999);//库存
			tbItem.setSpec("{}");//表中表的默认规范
			
			setItemValue(goods, tbItem);
			tbItemMapper.insert(tbItem);
		}
	}

	@Override
	public void update(Goods goods) {
		goods.getGoods().setAuditStatus("0");//设置未申请状态:如果经过修改的商品,需要重新审核.
		tbGoodsMapper.updateByPrimaryKey(goods.getGoods());//保存商品表
		tbGoodsDescMapper.updateByPrimaryKey(goods.getGoodsDesc());//保存商品扩展
		//删除原有的sku列表数据
		TbItemExample example = new TbItemExample();
		com.aisile.pojo.TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andGoodsIdEqualTo(goods.getGoods().getId());
		tbItemMapper.deleteByExample(example);
		//添加新的sku列表
		saveItemList(goods);
		
	}

	@Override
	public Goods findOne(Long id) {
		Goods goods = new Goods();
		//查询商品的基本信息(SPU)
		TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(id);
		goods.setGoods(tbGoods);
		//商品的详细信息(SPUdesc)
		TbGoodsDesc tbGoodsDesc = tbGoodsDescMapper.selectByPrimaryKey(id);
		goods.setGoodsDesc(tbGoodsDesc);
		//商品的sku列表
		TbItemExample example = new TbItemExample();
		example.createCriteria().andGoodsIdEqualTo(id);
		List<TbItem> list = tbItemMapper.selectByExample(example);
		goods.setItemList(list);
		
		return goods;
	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			tbGoodsMapper.deleteByPrimaryKey(id);
		}
		
	}

	@Override
	public void turnDown(Long[] ids) {
		for (Long id : ids) {
			TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(id);
			tbGoods.setAuditStatus("3");
			tbGoodsMapper.updateByPrimaryKey(tbGoods);
		}
	}

	@Override
	public void openGo(Long[] ids) {
		for (Long id : ids) {
			TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(id);
			tbGoods.setAuditStatus("2");
			tbGoodsMapper.updateByPrimaryKey(tbGoods);
		}
		
	}

	@Override
	public void upDownFrame(Long [] ids,String status ) {
		for (Long id : ids) {
			TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(id);
			tbGoods.setIsMarketable(status);
			tbGoodsMapper.updateByPrimaryKey(tbGoods);
		}
		
	}

}
