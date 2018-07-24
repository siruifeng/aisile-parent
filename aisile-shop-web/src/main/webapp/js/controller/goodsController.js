app.controller('goodsController', function($scope,$location,$http,goodsService,$controller,uploadService,itemCatService,typeTemplateService) {
	//继承baseController
		$controller('baseController',{$scope:$scope});
		//定义初始数组
		$scope.entity = {goodsDesc:{itemImages:[],specificationItems:[]}};
		$scope.list = [];
	

	$scope.findAll = function(){
		//读取列表数据绑定到表单中
		goodsService.findAll.success(
				function(response){
					$scope.list = response;
				}
		)
	}
	
	//上下架(这个地方,还可以在延伸业务,比如说上下架的时候,必须判断是否是审核通过或者是未上架)
	$scope.upDownFrame = function(status){
		goodsService.upDownFrame($scope.selectIds,status).success(function(response){
			if(response.success){
				alert(response.message);
				$scope.reloadList();
				$scope.selectIds = [];
			}else{
				alert(response.message);
			}
		})
	}
	
	
	//上传图片
	$scope.uploadFile = function(){
		uploadService.uploadFile().success(
				function(response){
					if(response.success){//如果上传成功,则取出url
						//把这个url给entity.goods
						//把图片回显出来
						$scope.image_entity.url=response.message;
					}else{
						alert(response.message);
					}
				}
		)
	}
	//定义页面实体结构
	//$scope.entity = {goods:{},goodsDesc:{itemImages:[]}};
	//添加图片到列表
	$scope.add_image_entity = function(){
		$scope.entity.goodsDesc.itemImages.push($scope.image_entity);
	}
	//删除图片列表根据对应的下标
	$scope.remove_image_entity = function(index){
		$scope.entity.goodsDesc.itemImages.splice(index,1);
	}
	
	//获取一级分类
	$scope.selectItemCat1List = function(){
		itemCatService.findAllByParentId(0).success(
				function(response){
					$scope.itemCat1List = response;
				})
	};
	//获取二级分类   第一个参数是:监控水谁       第二个参数:监控entity.goods.category1Id0.
	$scope.$watch('entity.goods.category1Id',function(newVal,oldVal){
		itemCatService.findAllByParentId(newVal).success(
				function(response){
					$scope.itemCat2List = response;
				})
	})
	//获取三级分类
	$scope.$watch('entity.goods.category2Id',function(newVal,oldVal){
		itemCatService.findAllByParentId(newVal).success(
				function(response){
					$scope.itemCat3List = response;
				})
	})
	//监控三级分类获取模板ID
	$scope.$watch('entity.goods.category3Id',function(newVal,oldVal){
		itemCatService.findOne(newVal).success(
				function(response){
					//更换模板ID
					$scope.entity.goods.typeTemplateId = response.typeId;//更新模板ID
				})
	})
	//获取品牌列表,同时获取拓展属性,同时获取规格列表
	//模板ID选择后,更新品牌列表
	$scope.$watch('entity.goods.typeTemplateId',function(newVal,oldVal){
		typeTemplateService.findOne(newVal).success(function(response){
			//获取的结果是一个对象  需要把品牌的列表取出来
			$scope.typeTemplate = response;//获取模板类型
			$scope.typeTemplate.brandIds = JSON.parse(response.brandIds);
			if($location.search()['id'] == null){
				$scope.entity.goodsDesc.customAttributeItems = JSON.parse(response.customAttributeItems);
			}
		})
		//获取规格列表
		typeTemplateService.findSpecList(newVal).success(function(response){
			$scope.specList = response;
		})
	})
	//保存规格拼接json对象使用
	$scope.updateSpecAttribute = function($event,name,value){
		//下面这个方法的三个参数:第一个参数:要查询谁的集合   第二个:查询那个属性   第三个:关键字
		var object = $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,'attributeName',name);
		if(object!=null){
			//判断选中还是没有选中
			if($event.target.checked){
				//判断是谁的?
				//如果没有就加进去
				object.attributeValue.push(value);
			}else{//如果有就删除
				object.attributeValue.splice(object.attributeValue.indexOf(value),1);
				//如果选项都取消了,将此条记录移除
				if(object.attributeValue.length == 0){
					$scope.entity.goodsDesc.specificationItems.splice($scope.entity.goodsDesc.specificationItems.indexOf(object),1);
				}
			}
		}else{
			//新增
			$scope.entity.goodsDesc.specificationItems.push(
					{"attributeName":name,"attributeValue":[value]});

		}
	}
	//生成sku列表
	$scope.createItemlist = function(){
		//初始化一个列表
		$scope.entity.itemList=[{spec:{},price:0,num:99999,status:'0',isDefault:'0' } ]
		//获取用户选中的规格列表
		var items=  $scope.entity.goodsDesc.specificationItems;	
		//循环
		for (var i = 0; i < items.length; i++) {
			$scope.entity.itemList = addColumn($scope.entity.itemList,items[i].attributeName,items[i].attributeValue);
		}
	}
	//添加列
	addColumn=function(list,columnName,conlumnValues){
		var newList=[];//新的集合
		for(var i=0;i<list.length;i++){
			var oldRow= list[i];
			for(var j=0;j<conlumnValues.length;j++){
				var newRow= JSON.parse( JSON.stringify( oldRow )  );//深克隆
				newRow.spec[columnName]=conlumnValues[j];
				newList.push(newRow);
			}    		 
		} 		
		return newList;
	}

	
	
	//分页
	$scope.findPage = function(page,rows,goodsService){
		goodsService.findPage.success(
				function(response){
					$scope.list = response.rows;
					$scope.paginationConf.totalItems = response.total;
				}
		)
	}
	//分页带查询数据
	$scope.searchEntity = {};
	$scope.findSearch = function(page,rows){
		goodsService.findSearch(page,rows,$scope.searchEntity).success(
				function(response){
					$scope.list = response.rows;
					$scope.paginationConf.totalItems = response.total;
				}
		)
	}
	$scope.status=['未申请','申请中','审核通过','已驳回'];
	/**
	 * 如何才能显示分类分类名称?
	 * 方法一:在后台代码中写关联查询语句,返回数据中直接有分类名称
	 * 方法二:在前段代码用ID去查询后端,异步返回商品分类名称
	 */
	//查询商品分类名称方法
	$scope.itemCatList = [];
	$scope.findItemCatList = function(){
		itemCatService.findAll().success(function(response){
			for (var i = 0; i < response.length; i++) {
				$scope.itemCatList[response[i].id] = response[i].name;
			}
		})
	}

	//添加方法+修改方法
	$scope.save = function(){
		//把数据加载进来,描述数据
		$scope.entity.goodsDesc.introduction = editor.html();
		var serviceObject = {};
		if($scope.entity.goods.id !=null){
			serviceObject = goodsService.update($scope.entity);
		}else{
			serviceObject =  goodsService.add($scope.entity)
		}
		serviceObject.success(function(response){
				if(response.success){
					$scope.entity={};
					editor.html("");
					swal({
						title:response.message,
						text:'添加成功(2秒后自动关闭)!',
						timer:2000,
						type:'success'
					});
				}else{
					swal({
						title:response.message,
						text:'哈哈(2秒后自动关闭)!',
						timer:2000,
						type:'error'
					});
				}
			})
		}
	//注册
	$scope.add = function(){
		goodsService.add($scope.entity).success(function(response){
			if(response.success){
				//如果登录成功,则跳转登录页面
				location.href="shoplogin.html";
			}else{
				alert(response.message);
			}
		})
	}
	//查找对象
	$scope.findOne = function(){
		var id = $location.search()['id'];//获取参数值
		console.log(id);
		if(id == null){
			return ;
		}
		goodsService.findOne(id).success(function(response){
			$scope.entity = response;	
			//富文本编辑器  赋值
			editor.html($scope.entity.goodsDesc.introduction);
			//商品图片的回显
			$scope.entity.goodsDesc.itemImages = JSON.parse($scope.entity.goodsDesc.itemImages);
			//商品扩展属性展示
			$scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.entity.goodsDesc.customAttributeItems);
			//商品的规格明细扩展
			$scope.entity.goodsDesc.specificationItems = JSON.parse($scope.entity.goodsDesc.specificationItems);
			//商品sku列表
			for (var i = 0; i < $scope.entity.itemList.length; i++) {
				$scope.entity.itemList[i].spec = JSON.parse($scope.entity.itemList[i].spec);
			}
		})
	}
	//判断是否被选中
	$scope.checkAttributeValue = function(specName,optionName){
		var items = $scope.entity.goodsDesc.specificationItems;
		var object = $scope.searchObjectByKey(items,'attributeName',specName);
		if(object == null){
			return false;
		}else{
			if(object.attributeValue.indexOf(optionName)>0){
				return true;
			}else{
				return false;
			}
		}
	}

	//确认删除
	$scope.del = function(){
		swal({
			title : '确定删除吗？',
			text : '你将无法恢复它！',
			type : 'warning',
			showCancelButton : true,
			confirmButtonColor : '#3085d6',
			cancelButtonColor : '#d33',
			confirmButtonText : '确定！',
			cancelButtonText : '取消！',
			confirmButtonClass : 'btn btn-success',
			cancelButtonClass : 'btn btn-danger'
		}).then(function(isConfirm){
			if(isConfirm.value == true) {
				goodsService.dele($scope.selectIds).success(function(response){
					if(response.success){
						$scope.reloadList();//重新加载
						swal({
							title:response.message,
							text:'哈哈(2秒后自动关闭)!',
							timer:2000,
							type:'success'
						});
					}else{
						swal({
							title:response.message,
							text:'哈哈(2秒后自动关闭)!',
							timer:2000,
							type:'error'
						});
					}
				})
			}else{
				swal({
					title:'错误',
					text:'请至少选中一条数据进行删除,(2秒后自动关闭)!',
					timer:2000,
					type:'error'
				});
			}
		})

	}
});