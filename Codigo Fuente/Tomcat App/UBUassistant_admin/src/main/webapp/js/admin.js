function checkInput(){
	var k1=document.getElementById('keyWord1');
	var c =document.getElementById('categoria');
	var r =document.getElementById('respuesta');
	
	if(k1.value!="" && c.value!="" && r.value!=""){
		return true;
	}else{

		document.getElementById('error').style.display = "block";
		return false;
	}	
}


function showErrorIfExists(param){
	var user = document.getElementById("user").value;
	var password = document.getElementById("password").value;
	
	if(user == "" || password==""){
		document.getElementById("error").innerHTML="* Debe rellenar todos los campos";
		return false;
	}else{
		param.submit();
	}
}

