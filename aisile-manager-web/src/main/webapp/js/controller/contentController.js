app.controller('contentController', function($scope,$http,contentCategoryService,$controller,contentService,uploadService) {
	//继承baseController
		$controller('baseController',{$scope:$scope});
	$scope.list = [];
	//brand列表方法
	$scope.findAll = function(){
		//读取列表数据绑定到表单中
		contentService.findAll.success(
				function(response){
					$scope.list = response;
				}
		)
	}
	
	$scope.entity = {};
	//上传图片
	$scope.uploadFile = function(){
		uploadService.uploadFile().success(
				function(response){
					if(response.success){
						$scope.entity.pic = response.message;
					}else{
						alert(response.message);
					}
				}).error(function(){
					alert("上传错误!");
				})
	}
	
	
	
	
	

	//分页
	$scope.findPage = function(page,rows,contentService){
		contentService.findPage.success(
				function(response){
					$scope.list = response.rows;
					$scope.paginationConf.totalItems = response.total;
				}
		)
	}
	
	//分页带查询数据
	$scope.searchEntity = {};
	$scope.findSearch = function(page,rows){
		contentService.findSearch(page,rows,$scope.searchEntity).success(
				function(response){
					$scope.list = response.rows;
					$scope.paginationConf.totalItems = response.total;
				}
		)
	}

	//添加方法+修改方法
	$scope.save = function(){
		if($scope.entity.id != null){
			contentService.update($scope.entity).success(function(response){
				if(response.success){
					$scope.reloadList();//重新加载
					alert(response.message);
				}else{
					alert(response.message);
				}
			})
		}else{
			contentService.add($scope.entity).success(function(response){
				if(response.success){
					$scope.reloadList();//重新加载
					alert(response.message);
				}else{
					alert(response.message);
				}
			})
		}
		
	}
	//注册
	$scope.add = function(){
		contentService.add($scope.entity).success(function(response){
			if(response.success){
				//如果登录成功,则跳转登录页面
				location.href="shoplogin.html";
			}else{
				alert(response.message);
			}
		})
	}
	//查找对象
	$scope.findOne = function(id){
		contentService.findOne(id).success(function(response){
			$scope.entity = response;
		})
	}
	//审核商家用户
	$scope.updateStatus = function(status){
		contentService.updateStatus($scope.entity,status).success(
				function(response){
					if(response.success){
						//重新加载
						$scope.reloadList();
					}else{
						alert(response.message);
					}
				}
		) 
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
				contentService.dele($scope.selectIds).success(function(response){
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
	//查询广告分类的列表
	$scope.findContentCategoryList = function(){
		contentCategoryService.findAll().success(function(response){
			$scope.contentCategoryList = response;
		})
	}
	//屏蔽
	$scope.shield = function(){
		contentService.shield($scope.selectIds).success(function(response){
			if(response.success){
				alert(response.message);
				$scope.reloadList();//重新加载
			}else{
				alert(response.message);
			}
		})
	}
	//开启(当有效的时候,需要判断)
	$scope.openq = function(){
		contentService.openq($scope.selectIds).success(function(response){
			if(response.success){
				alert(response.message);
				$scope.reloadList();//重新加载
			}else{
				alert(response.message);
			}
		})
	}
	
	
});