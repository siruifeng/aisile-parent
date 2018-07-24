//父类公共的controller
app.controller('baseController', function($scope) {
//	重新加载方法
	$scope.reloadList = function(){
		//angularjs中的有参数方法调用
		/* $scope.findPage($scope.paginationConf.currentPage,
						$scope.paginationConf.itemsPerPage) */ 
		$scope.findSearch($scope.paginationConf.currentPage,
				$scope.paginationConf.itemsPerPage)
	}
//	分页渲染控件方法
	$scope.paginationConf = {
			currentPage: 1,
			totalItems: 10,
			itemsPerPage: 10,
			perPageOptions: [1,2,10, 20, 30, 40, 50],
			onChange: function(){
				$scope.reloadList();//重新加载
			}

	}
//	更改选中id(批量删除)
	$scope.selectIds = [];//选中的id集合

	$scope.updateSelections = function($event,id){//$event能够获得对象本身
		//判断是否选中   如果被选中,就直接push进去,如果没有选中就根据下标移除
		if($event.target.checked){//如果被选中,则增加到数组中
			$scope.selectIds.push(id);
		}else{//如果没有选中,则移除
			var idx = $scpoe.selectIds.indexOf(id);
			$scope.selectIds.splice(idx,1);
		}
	}
	//提取json字符串数据中某个属性，返回拼接字符串 逗号分隔
	$scope.jsonToString=function(jsonString,key){
		var json=JSON.parse(jsonString);//将json字符串转换为json对象
		var value="";
		for(var i=0;i<json.length;i++){		
			if(i>0){
				value+=","
			}
			value+=json[i][key];			
		}
		return value;
	}

});