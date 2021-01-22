;
var demo_h5_upload_ops = {
    init:function(){
        this.eventBind();
    },
    eventBind:function(){
        var that = this;
        $("#upload").change(function(){
            var reader = new FileReader();
            reader.onload = function (e) {
                that.compress(this.result);
            };
            reader.readAsDataURL(this.files[0]);
        });
    },
    compress : function (res) {
        var that = this;
        var img = new Image(),
            maxH = 300;

        img.onload = function () {
            var cvs = document.createElement('canvas'),
                ctx = cvs.getContext('2d');

            if(img.height > maxH) {
                img.width *= maxH / img.height;
                img.height = maxH;
            }
            cvs.width = img.width;
            cvs.height = img.height;

            ctx.clearRect(0, 0, cvs.width, cvs.height);
            ctx.drawImage(img, 0, 0, img.width, img.height);
            var dataUrl = cvs.toDataURL('image/jpeg', 1);
            $(".img-responsive").attr("src",dataUrl);
            $(".img-responsive").show();
            // 上传代码
            //that.upload( dataUrl );
        };

        img.src = res;
    }
};


$(document).ready( function(){
    demo_h5_upload_ops.init();
} );