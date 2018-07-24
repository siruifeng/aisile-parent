app.controller('goodsController', function($scope,$location,$http,goodsService,$controller,itemCatService) {
	//继承baseController
		$controller('baseController',{$scope:$scope});
		//定义初始数组
		$scope.list = [];
	
	$scope.findAll = function(){
		//读取列表数据绑定到表单中
		goodsService.findAll.success(
				function(response){
					$scope.list = response;
				}
		)
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
					console.log(response);
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
	//审核通过
	$scope.openGo = function(){
		goodsService.openGo($scope.selectIds).success(function(response){
			if(response.success){
				alert(response.message);
				$scope.reloadList();//重新刷新数据
			}else{
				alert(response.message);
			}
		})
	}
	//驳回
	$scope.turnDown = function(){
		goodsService.turnDown($scope.selectIds).success(function(response){
			if(response.success){
				alert(response.message);
				$scope.reloadList();//重新刷新数据
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