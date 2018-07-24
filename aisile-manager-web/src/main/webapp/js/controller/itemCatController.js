app.controller('itemCatController', function($scope,$http,itemCatService,$controller) {
	//继承baseController
		$controller('baseController',{$scope:$scope});
		
	$scope.list = [];
	$scope.grade = 1;//默认级别为1
	$scope.parentIdW = 0;
	
	$scope.setGrade = function(value){
		$scope.grade = value;
	}
	/*//确认删除
	$scope.del = function(){
					$scope.dele();
	}*/
	
	//定义面包屑使用方法
	$scope.findAllList = function(p_entity){
		if ($scope.grade == 1) {
			//把对象设为空值
			$scope.entity_1 = null;
			$scope.entity_2 = null;
			
		}
		if ($scope.grade == 2) {
			$scope.entity_1 = p_entity;
			$scope.entity_2 = null;
		}
		if ($scope.grade == 3) {
			$scope.entity_2 = p_entity;
		}
		
		$scope.findAllByParentId(p_entity.id);
	}
	
	
	//根据父亲id查询列表
	$scope.findAllByParentId = function(parentid){
		$scope.parentIdW = parentid;
		//读取列表数据绑定到表单中
		itemCatService.findAllByParentId(parentid).success(
				function(response){
					$scope.list = response;
				}
			)
	}
	
	
	
	
	
	
	
	//列表方法
	$scope.findAll = function(){
		//读取列表数据绑定到表单中
		itemCatService.findAll.success(
				function(response){
					$scope.list = response;
				}
		)
	}

	//分页
	$scope.findPage = function(page,rows,itemCatService){
		itemCatService.findPage.success(
				function(response){
					$scope.list = response.rows;
					$scope.paginationConf.totalItems = response.total;
				}
		)
	}
	//分页带查询数据
	$scope.searchEntity = {};
	$scope.findSearch = function(page,rows){
		itemCatService.findSearch(page,rows,$scope.searchEntity).success(
				function(response){
					$scope.list = response.rows;
					$scope.paginationConf.totalItems = response.total;
				}
		)
	}

	//添加方法+修改方法
	$scope.save = function( ){
		if($scope.entity.id != null){
			$scope.entity.parentId = $scope.parentIdW;
			itemCatService.update($scope.entity).success(function(response){
				if(response.success){
					$scope.findAllByParentId($scope.parentIdW);
					alert(response.message);
				}else{
					alert(response.message);
				}
			})
		}else{
			$scope.entity.parentId = $scope.parentIdW;
			itemCatService.add($scope.entity).success(function(response){
				if(response.success){
					//重新加载
					//$scope.reloadList();
					$scope.findAllByParentId($scope.parentIdW);
					alert(response.message);
				}else{
					alert(response.message);
				}
			})
		}
		
	}
	//查找对象
	$scope.findOne = function(id){
		itemCatService.findOne(id).success(function(response){
			$scope.entity = response;
		})
	}

	//确认删除
	$scope.dele = function(){
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
				itemCatService.dele($scope.selectIds).success(function(response){
					if(response.success){
						alert($scope.selectIds);
						$scope.findAllByParentId($scope.parentIdW);
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