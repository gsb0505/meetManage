define(function(require, exports, module) {
	var inputLists = document.getElementsByTagName("input");
	var inputType;
	for (var int = 0,inputListlength = inputLists.length; int < inputListlength; int++) {
		inputType = inputLists[int].getAttribute('type');
		if(inputType == null || inputType == "text"){
			var content = inputLists[int].getAttribute("maxlength");
			if(content==null || content==''){
				inputLists[int].setAttribute("maxlength",50);
			}
		}
	}
	
	var keyEvent;
	var doc_inputLists = document.querySelectorAll("input");
	for (var int = 0 , doc_inputListlength = doc_inputLists.length; int < doc_inputListlength; int++) {
		if(Object.prototype.toString.call(doc_inputLists[int]) === "[object HTMLInputElement]"){
			if (doc_inputLists[int].addEventListener) {
				doc_inputLists[int].addEventListener("keyup",limit_sign, false);
		    } else if (doc_inputLists[int].attachEvent) {
		    	doc_inputLists[int].attachEvent("onkeyup",limit_sign);
		    }
		}
	}
	
	function limit_sign(events){
		//keyEvent = doc_inputLists[int].onkeyup;
		//if(keyEvent == null || keyEvent==''){
			var val = this.value;
			if(val != undefined && val.indexOf("'")!=-1){
				this.value = val.replace(/[']+/,"");
			}
			if(val != undefined && val.indexOf('"')!=-1){
				this.value = val.replace(/["]+/,"");
			}
		//}
	}
	
});