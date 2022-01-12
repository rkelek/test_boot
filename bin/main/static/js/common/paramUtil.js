function locationKeepValue(url, param, dataNm){
	if(param != '' && JSON.stringify(param).substring(0,1) == '['){
		param = JSON.stringify(param);
		param = JSON.parse(param);
	} else if(param != '' && JSON.stringify(param).substring(0,1) != '{'){
		param = QueryStringToJSON(param);
	} 	
	/*localStorage.param = JSON.stringify(param);
	localStorage.keepYn = 'Y'*/
	location.href = url+"?param=" + encodeURIComponent(JSON.stringify(param))+"&dataNm="+dataNm;
}

function redisSaveData(dataNm, data){
	
	RequestUtil.post("/api/common/redis", { data : { dataNm : dataNm, data : data } }, function(d){
		if(d.result){
			
		}
	});	
	
}



//안씀
function getLocalParam(){
	if(typeof globalHeader != "undefined"){
		if(typeof localStorage.param == "undefined"){
			globalHeader.paramJson = '';
			localStorage.removeItem('keepYn');
		}else{
			var json = JSON.parse(localStorage.param);
			localStorage.removeItem('param');
			localStorage.removeItem('keepYn');
			globalHeader.paramJson = json;
		}
	}
		
}
