//封装所有连接后端代码    需要$http
app.service('uploadService',function($http){
	this.uploadFile=function(){
		var formData=new FormData();
	    formData.append("file",file.files[0]);   
		return $http({
            method:'POST',
            url:"../upload.do",
            data: formData,
            headers: {'Content-Type':undefined},
            transformRequest: angular.identity
        });		
	}	

})