app.controller('specificationController', function($scope,specificationService,$controller) {
	//继承baseController
	$controller('baseController',{$scope:$scope});
	$scope.list = [];
	//brand列表方法
	$scope.findAll = function(){
		//读取列表数据绑定到表单中
		specificationService.findAll.success(
				function(response){
					$scope.list = response;
				}
		)
	}
	//新增行数
	$scope.addTableRow = function(){
		//添加一行
		$scope.entity.specificationOptionList.push({});
	}
	//删除行
	$scope.deleteTableRow = function(index){
		//splice有两个参数    第一个参数是从谁开始      第二个参数是几个
		$scope.entity.specificationOptionList.splice(index,1);
	}

	//分页
	$scope.findPage = function(page,rows,specificationService){
		specificationService.findPage.success(
				function(response){
					$scope.list = response.rows;
					$scope.paginationConf.totalItems = response.total;
				}
		)
	}
	//分页带查询数据
	$scope.searchEntity = {};
	$scope.findSearch = function(page,rows){
		specificationService.findSearch(page,rows,$scope.searchEntity).success(
				function(response){
					$scope.list = response.rows;
					$scope.paginationConf.totalItems = response.total;
				}
		)
	}

	//添加方法+修改方法
	$scope.save = function(){
		if($scope.entity.specification.id != null){
			specificationService.update($scope.entity).success(function(response){
				if(response.success){
					$scope.reloadList();//重新加载
					alert(response.message);
				}else{
					alert(response.message);
				}
			})
		}else{
			specificationService.add($scope.entity).success(function(response){
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
		specificationService.findOne(id).success(function(response){
			$scope.entity = response;
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
				specificationService.dele($scope.selectIds).success(function(response){
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