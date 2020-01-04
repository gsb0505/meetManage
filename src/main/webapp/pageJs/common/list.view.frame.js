jQuery(function($) {
    if(window.top!=window.self){
        //确定按钮事件
        if(jQuery("#okclick")){
            jQuery("#okclick").click(function() {
                var ids = getChecked();
                if(!ids || ids.length == 0){
                    alert("请选择行数据！");
                    return;
                }
                var param = [];
                if(ids.length > 1) {
                    for (var i = 0; i < ids.length; i++) {
                        var row = jQuery("#tabGrid").jqGrid('getRowData', ids[i]);
                        console.log("勾选的内容：" + JSON.stringify(row));
                        param.push(row);
                    }
                }else{
                    var row = jQuery("#tabGrid").jqGrid('getRowData', ids);
                    param.push(row);
                }
                parent.rollbackOK(param);
                iFClose();
            });
        }
        if(jQuery("#closeclick")){
            jQuery("#closeclick").click(function () {
                iFClose();
            });
        }
    }
});