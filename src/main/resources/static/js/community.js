function post() {
    //获取input_hidden中的值
    var parentId = $('#question_input').val();
    var content = $('#textarea_input').val();
    $.ajax({
        type: "post",
        url: "/comment",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({
            "content":content,
            "parentId":parentId,
            "type":"1"
        }),
        success: function (response) {
            console.log(response);
            if (response.code==200){
                $('#section_div').hide();
            }else {
                if (response.code==2002) {
                    //当没有登录时,实现不刷新页面登录
                    var isAccepted= window.confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=bedef8cad503139674a5&redirect_uri=http://localhost:8087/callback&scope=user&state=1");
                        //通过localStorage
                        window.localStorage.setItem("closeable","true");
                    }
                }else {
                    alert(response.message);
                }
            }

        },
        dataType: "json"

    });
    console.log(parentId);
    console.log(content);


}