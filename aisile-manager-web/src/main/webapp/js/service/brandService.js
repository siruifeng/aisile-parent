//封装所有连接后端代码    需要$http
app.service('brandService',function($http){
	//查询全部的数据
	this.findAll = function(){
		return $http.get('../brand/findAll.do');
	}
	//分页
	this.findPage = function(page,rows){
		return $http.get('../brand/findPage.do?page='+page+"&rows="+rows);
	}
	//分页带数据查询
	this.findSearch = function(page,rows,searchEntity){
		return $http.post('../brand/findSearch.do?page='+page+"&rows="+rows,searchEntity);
	}
	//修改
	this.update = function(entity){
		return $http.post('../brand/update.do',entity);
	}
	//添加
	this.add = function(entity){
		return $http.post('../brand/add.do',entity);
	}
	//根据id查询对象
	this.findOne = function(id){
		return $http.post('../brand/findOne.do?id='+id);
	}
	//删除
	this.dele = function(selectIds){
		return $http.get('../brand/dele.do?ids='+selectIds);
	}
	//下拉框
	this.selectOptionList = function(){
		return $http.get('../brand/selectOptionList.do');
	}
})