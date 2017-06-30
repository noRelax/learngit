  $(function(){

    // 弹框
    $('.open').on('click',function(){
        $('.dialog-main').show();
    });

    // 关闭弹框
    $('.dialog-foot-cancel,.dialog-close').on('click',function(){
        $('.dialog-main').hide();
    });


    // 预览图片删除
    $('.img_delete').on('click',function(){
        $(this).parent().find('img').remove();
        $('.form_icon').val('');
        $('.icon_body').hide();
        $('.icon_file').show();
        $('.form_icon').parent().parent().find('.lxl_text_error').html('请上传图标');
    });

    // 提交表单
    $('.dialog-foot-save').on('click', function(){
        if ($('.form_name').val() == '') {
            $('.form_name').addClass('lxl_input_error');
            $('.form_name').parent().find('.lxl_text_error').html('请输入服务名称');
            return false;
        }
        if ($('.form_icon').val() == '') {
            $('.form_icon').parent().parent().find('.lxl_text_error').html('请上传图标');
            return false;
        }
        if ($('.form_url').val() == '') {
            $('.form_url').addClass('lxl_input_error');
            $('.form_url').parent().find('.lxl_text_error').html('请输入URL');
            return false;
        }
        alert('成功');
    });


    // 表单输入时
    $('.form_name,.form_url').on('keyup',function(){
        if($(this).val().length){
            $(this).removeClass('lxl_input_error');
            $(this).parent().find('.lxl_text_error').html('');
        }
    });

 });





 // 文件预览
function fileChange() {
    var file = $('.icon_file input').get(0);
    var ext=file.value.substring(file.value.lastIndexOf(".")+1).toLowerCase(); //截取 . 最后出现的位置

     // gif在IE浏览器暂时无法显示
     if(ext!='png'&&ext!='jpg'&&ext!='jpeg'&&ext!='gif'){
         alert("图片的格式必须为png或者jpg或者jpeg格式或者gif格式！");
         return;
     }
    html5Reader(file);
}
function html5Reader(file){
    var file = file.files[0];
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function(e){
        $('.icon_body .img_wrap').append('<img src="'+this.result+'"/>');
        $('.icon_body .img_wrap img').get(0).onload = function(){
            if (this.width > 80 || this.height > 80) {
                alert('图片太大建议尺寸80*80');
                $('.icon_body .img_wrap img').remove();
                $('.form_icon').val('');
                return false;
            } else {
                $('.icon_body').css('display','inline-block');
                $('.form_icon').parent().parent().find('.lxl_text_error').html('');
                $('.icon_file').hide();
            }
        }
    }
}