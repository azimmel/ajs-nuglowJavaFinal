$(function(){
    $('.collapse').collapse();
    $("#checkbox").change(function(){
       if(this.checked){
           $("#checkbox").val("0");
           $(".billing").hide();
       } else{
           $(".billing").show();
           $("#checkbox").val("1");
       }
    });
});


