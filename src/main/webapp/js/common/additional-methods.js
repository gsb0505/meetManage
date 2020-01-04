jQuery.validator.addMethod("isPhoto", function (value, element) {
    var photo = true;
    var photoExt = value.substr(value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
    if (photoExt != ".png" && photoExt != ".jpg" && photoExt != ".svg" && photoExt != ".jpeg" ) {
        //alert("请上传后缀名为jpg或png的照片!");
        photo = (false);
    }
    // var fileSize = 0;
    // var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
    // if (isIE && !obj.files) {
    //     var filePath = obj.value;
    //     var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
    //     var file = fileSystem.GetFile (filePath);
    //     fileSize = file.Size;
    // }else {
    //     fileSize = obj.files[0].size;
    // }
    // fileSize=Math.round(fileSize/1024*100)/100; //单位为KB
    // if(fileSize>=100){
    //     alert("照片最大尺寸为100KB，请重新上传!");
    //     return (false);
    // }
    return this.optional(element) || photo;
}, "请上传后缀名为jpg或png的照片!");