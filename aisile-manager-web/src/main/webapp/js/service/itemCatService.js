//封装所有连接后端代码    需要$http
app.service('itemCatService',function($http){
	//查询全部的数据
	this.findAll = function(){
		return $http.get('../itemCat/findAll.do');
	}
	//分页
	this.findPage = function(page,rows){
		return $http.get('../itemCat/findPage.do?page='+page+"&rows="+rows);
	}
	//分页带数据查询
	this.findSearch = function(page,rows,searchEntity){
		return $http.post('../itemCat/findSearch.do?page='+page+"&rows="+rows,searchEntity);
	}
	//修改
	this.update = function(entity){
		return $http.post('../itemCat/update.do',entity);
	}
	//添加
	this.add = function(entity){
		return $http.post('../itemCat/add.do',entity);
	}
	//根据id查询对象
	this.findOne = function(id){
		return $http.post('../itemCat/findOne.do?id='+id);
	}
	//删除
	this.dele = function(selectIds){
		return $http.get('../itemCat/dele.do?ids='+selectIds);
	}
	//根据父亲的id查询列表
	this.findAllByParentId = function(id){
		return $http.get('../itemCat/findAllByParentId.do?parentId='+id);
	}
})