/**
 * 跨浏览器支持event 
 * element代表元素，type代表事件类型，handler代表事件被触发时调用的方法
 * */
var EventUtil = {
	addHandler: function (element, type, handler) {
		if(Object.prototype.toString.call(element) === '[object NodeList]'){
			for (var int = 0; int < element.length; int++) {
				ext(element[int]);
			}
		}else{
			ext(element);
		}
			
        function ext(element2){
        	if (element2.addEventListener) {
                element2.addEventListener(type, handler, false);
            } else if (element2.attachEvent) {
                element2.attachEvent("on" + type, handler);
            } else {
                element2["on" + type] = handler;
            }
        	element2.onpaste=noonpaste;
        }
        function noonpaste(){
        	return false;
        }
    },
    preventDefault: function (event) {
        if (event.preventDefault) {
            event.preventDefault();
        } else {
            event.returnValue = false;
        }
    },
    getEvent: function(event){
    	return event ? event : window.event;
    },
    getTarget: function(event){
    	return event.target || event.srcElement;
    },
    removeEventHandler: function (oTarget, sEventType, fnHandler) {
        if (oTarget.removeEventListener) {
            oTarget.removeEventListener(sEventType, fnHandler, false);
        } else if (oTarget.detachEvent) {
            oTarget.detachEvent("on" + sEventType, fnHandler);
        } else { 
            oTarget["on" + sEventType] = null;
        }
    },
    getCharCode:function(event){
		if(typeof event.charCode == "number"){
			return event.charCode;
		}else{
			return event.keyCode;
		}
	},
	stopPropagation:function(event){
		if(event.stopPropagation){
			event.stopPropagation();
		}else{
			event.cancleBubble = true;
		}
	},
	getButton:function(event){
		if(document.implementaion.hasFeaturn('MouseEvent', '2.0')){
			return event.button;
		}else{
			switch(event.button){
				case 0:
				case 1:
				case 3:
				case 5:
				case 7:
					return 0;
				case 2:
				case 6:
					return 3;
				case 4:
					return 1
			}
		}
	},
	getWheelDelta:function(event){
		if(event.wheelDelta){
			return (client.engine.opera && client.engine.opera < 9.5 ? -event.wheelDelta : enent.wheelDelta);
		}else{
			return -(event.detail = 40);
		}
	}
};

if(typeof EventUtil !='undefined'){
	EventUtil.addHandler(document.querySelectorAll(".number-limit"),"keypress",function(event){
		event = EventUtil.getEvent(event);
		var target = EventUtil.getTarget(event);
		var charCode = EventUtil.getCharCode(event);
		
		if(!/\d/.test(String.fromCharCode(charCode)) && charCode > 9 && !event.ctrlKey){
			EventUtil.preventDefault(event);		
		}
	});
}

function callEventUtil(){
	EventUtil.addHandler(document.querySelectorAll(".number-limit"),"keypress",function(event){
		event = EventUtil.getEvent(event);
		var target = EventUtil.getTarget(event);
		var charCode = EventUtil.getCharCode(event);
		
		if(!/\d/.test(String.fromCharCode(charCode)) && charCode > 9 && !event.ctrlKey){
			EventUtil.preventDefault(event);		
		}
	});
}