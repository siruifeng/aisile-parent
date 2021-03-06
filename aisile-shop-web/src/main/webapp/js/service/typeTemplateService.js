//封装所有连接后端代码    需要$http
app.service('typeTemplateService',function($http){
	//查询全部的数据
	this.findAll = function(){
		return $http.get('../typeTemplate/findAll.do');
	}
	//分页
	this.findPage = function(page,rows){
		return $http.get('../typeTemplate/findPage.do?page='+page+"&rows="+rows);
	}
	//分页带数据查询
	this.findSearch = function(page,rows,searchEntity){
		return $http.post('../typeTemplate/findSearch.do?page='+page+"&rows="+rows,searchEntity);
	}
	//修改
	this.update = function(entity){
		return $http.post('../typeTemplate/update.do',entity);
	}
	//添加
	this.add = function(entity){
		return $http.post('../typeTemplate/add.do',entity);
	}
	//根据id查询对象
	this.findOne = function(id){
		return $http.post('../typeTemplate/findOne.do?id='+id);
	}
	//删除
	this.dele = function(selectIds){
		return $http.get('../typeTemplate/dele.do?ids='+selectIds);
	}
	//查询规格列表
	this.findSpecList = function(id){
		return $http.get('../typeTemplate/findSpecList.do?id='+id);
	}
})