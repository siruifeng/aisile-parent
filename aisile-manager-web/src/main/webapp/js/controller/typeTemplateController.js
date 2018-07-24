app.controller('typeTemplateController', function($scope,typeTemplateService,brandService,specificationService,$controller) {
	//继承baseController
	$controller('baseController',{$scope:$scope});
	$scope.list = [];
	//brand列表方法
	$scope.findAll = function(){
		//读取列表数据绑定到表单中
		typeTemplateService.findAll().success(
				function(response){
					$scope.list = response;
				}
		)
	}
	//新增行数
	$scope.addTableRow = function(){
		//添加一行
		$scope.entity.customAttributeItems.push({});
	}
	//删除行
	$scope.deleteTableRow = function(index){
		//splice有两个参数    第一个参数是从谁开始      第二个参数是几个
		$scope.entity.customAttributeItems.splice(index,1);
	}

	//分页
	$scope.findPage = function(page,rows){
		typeTemplateService.findPage.success(
				function(response){
					$scope.list = response.rows;
					$scope.paginationConf.totalItems = response.total;
				}
		)
	}
	//分页带查询数据
	$scope.searchEntity = {};
	$scope.findSearch = function(page,rows){
		typeTemplateService.findSearch(page,rows,$scope.searchEntity).success(
				function(response){
					$scope.list = response.rows;
					$scope.paginationConf.totalItems = response.total;
				}
		)
	}

	//添加方法+修改方法
	$scope.save = function(){
		if($scope.entity.id != null){
			typeTemplateService.update($scope.entity).success(function(response){
				if(response.success){
					$scope.reloadList();//重新加载
					alert(response.message);
				}else{
					alert(response.message);
				}
			})
		}else{
			typeTemplateService.add($scope.entity).success(function(response){
				if(response.success){
					$scope.reloadList();//重新加载
					alert(response.message);
				}else{
					alert(response.message);
				}
			})
		}
		
	}
	//查找对象
	$scope.findOne = function(id){
		typeTemplateService.findOne(id).success(function(response){
			$scope.entity = response;
			//select2控件需要回显数据,使用是json格式的对象  所以我们需要把属性值取到  然后在使用     
			//json.parse是js提供的
			//有三种方法    eval() $parseJson() JSON.parse()都可以转换成json对象
			$scope.entity.brandIds = JSON.parse($scope.entity.brandIds);//转换品牌列表
			$scope.entity.specIds = JSON.parse($scope.entity.specIds);//转换规格列表
			$scope.entity.customAttributeItems = JSON.parse($scope.entity.customAttributeItems);//转换扩展属性

		})
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
				typeTemplateService.dele($scope.selectIds).success(function(response){
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
	//定义brandList列表
	$scope.brandList = {data:[]};
	//品牌列表
	$scope.findBrandList= function(){
		brandService.selectOptionList().success(function(response){
			$scope.brandList = {data:response};
		})
	}
	//定义specList列表
	$scope.specList = {data:[]};
	//规格明细列表
	$scope.findSpecList= function(){
		specificationService.selectOptionList().success(function(response){
			$scope.specList = {data:response};
		})
	}
	
	
	
});