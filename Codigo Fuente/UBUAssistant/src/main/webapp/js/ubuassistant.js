
function hideAndSubmit(param){
	param.style.display = 'none';
	var buttonDiv = document.getElementById("buttonPanel").innerHTML;
	
	var num = document.getElementsByName("buttonDiv").length;
	var x = document.getElementsByName("buttonDiv")
	for (var i=0; i < num; i++) {
		x[i].value=buttonDiv;
	}
	
	var form = param.form;
	form.submit();
}
