//封装所有连接后端代码    需要$http
app.service('sellerService',function($http){
	//查询全部的数据
	this.findAll = function(){
		return $http.get('../seller/findAll.do');
	}
	//分页
	this.findPage = function(page,rows){
		return $http.get('../seller/findPage.do?page='+page+"&rows="+rows);
	}
	//分页带数据查询
	this.findSearch = function(page,rows,searchEntity){
		return $http.post('../seller/findSearch.do?page='+page+"&rows="+rows,searchEntity);
	}
	//修改
	this.update = function(entity){
		return $http.post('../seller/update.do',entity);
	}
	//修改(针对审核使用)
	this.updateStatus = function(entity,status){
		return $http.post('../seller/updateStatus.do?status='+status,entity);
	}
	//添加
	this.add = function(entity){
		return $http.post('../seller/add.do',entity);
	}
	//根据id查询对象
	this.findOne = function(id){
		return $http.post('../seller/findOne.do?id='+id);
	}
	//删除
	this.dele = function(selectIds){
		return $http.get('../seller/dele.do?ids='+selectIds);
	}
	//下拉框
	this.selectOptionList = function(){
		return $http.get('../seller/selectOptionList.do');
	}
})