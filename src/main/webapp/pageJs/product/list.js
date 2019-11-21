jQuery(function ($) {
    var jGrid = new jqGrid();
    loadJqGrid("#tabGrid", "#pager", jGrid);
    var btnzj = jQuery("#insert");
    var btnbj = jQuery("#modify");
    var btnund = jQuery("#undercarriage");
    var btnshelf = jQuery("#shelf");
    var btnsc = jQuery("#delete");
    // 绑定增加点击事件
    if (btnzj != null) {
        btnzj.click(function () {
            showWindow('添加商品', 640, 580,
                _path + 'product/addView.do');
        });
    }

    if (btnbj != null) {
        btnbj.click(function () {
            var id = getChecked();
            if (id.length != 1) {
                alert('请选定一条记录!');
                return;
            }
            showWindow('更新商品', 660, 580,
                _path + 'product/modifyView.do?id='
                + id);
        });
    }

    // 绑定增加点击事件
    if (btnund != null) {
        btnund.click(function () {
            var id = getChecked();
            if (id.length != 1) {
                alert('请选定一条记录!');
                return;
            }
            var statu = jQuery("#tabGrid").getCell(id, 'status');
            if (statu == "2") {
                alert("选择的商品已下架!!!");
                return;
            }
            var r = confirm("确定下架该商品吗?");
            var row = jQuery("#tabGrid").jqGrid('getRowData', id);
            if (r) {
                jQuery.ajax({
                    url: _path + 'product/lowerShelf.do?id=' + id,
                    type: "post",
                    async: false,
                    success: function (data) {
                        if (data == "success") {
                            alert_auto("下架成功");
                        } else if (data == "fail") {
                            alert("下架失败");
                        } else {
                            alert("异常！！请联系管理员");
                        }
                    }
                });
                jQuery(window.document).find('#searchResult').click();
            }

        });
    }
    if (btnshelf != null) {
        btnshelf.click(function () {
            var id = getChecked();
            if (id.length != 1) {
                alert('请选定一条记录!');
                return;
            }
            var statu = jQuery("#tabGrid").getCell(id, 'status');
            if (statu == "1") {
                alert("选择的商品已上架!!!");
                return;
            }
            var row = jQuery("#tabGrid").jqGrid('getRowData', id);
            jQuery.ajax({
                url: _path + 'product/shelf.do?id=' + id,
                type: "post",
                async: false,
                success: function (data) {
                    if (data == "success") {
                        alert_auto("上架成功");
                    } else if (data == "fail") {
                        alert("上架失败");
                    } else {
                        alert("异常！！请联系管理员");
                    }
                }
            });
            jQuery(window.document).find('#searchResult').click();

        });
    }

    if (btnsc != null) {
        btnsc.click(function () {
            var id = getChecked();
            if (id.length != 1) {
                alert('请选定一条记录!');
                return;
            }
            //删除前的判断
            jQuery.ajax({
                url: _path + 'product/confirmOrder.do?id=' + id,
                type: "post",
                async: false,
                success: function (data) {
                    if (data == "true") {
                        alert('该商品已被预约（下单），无法被删除!');
                    }else if(data == "false"){
                        alert('删除失败!');
                    }else {
                        alert("异常！！请联系管理员");
                    }
                }
            });

            var row = jQuery("#tabGrid").jqGrid('getRowData', id);
            var r = confirm("删除后商品无法恢复，确定删除该商品吗?");
            if (r) {
                jQuery.ajax({
                    url: _path + 'product/delete.do?id=' + id,
                    type: "post",
                    async: false,
                    success: function (data) {
                        if (data == "success") {
                            alert_auto("删除成功");
                        } else if (data == "fail") {
                            alert("删除失败");
                        } else {
                            alert("异常！！请联系管理员");
                        }
                    }
                });
                jQuery(window.document).find('#searchResult').click();
            }
        });
    }


});