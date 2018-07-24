app.controller('contentController',function($scope,contentService){
	$scope.contentList = [];//定义广告集合
	$scope.findByCategoryId = function(categoryId){
		contentService.findByCategoryId(categoryId).success(function(response){
			$scope.contentList[categoryId] = response;
		})
	}
});

//[[轮播图],[大广告],[小广告]]