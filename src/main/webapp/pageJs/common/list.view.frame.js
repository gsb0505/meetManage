jQuery(function($) {
    if(window.top!=window.self){
        //确定按钮事件
        if(jQuery("#okclick")){
            jQuery("#okclick").click(function () {
                var id = getChecked();
                var row = jQuery("#tabGrid").jqGrid('getRowData', id);
                var param = [];
                param.push(row);
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