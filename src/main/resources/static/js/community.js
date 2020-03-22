/**
 * 提交回复
 */
function post() {
    //获取input_hidden中的值
    var parentId = $('#question_input').val();
    var content = $('#textarea_input').val();
    comment2target(parentId,1,content);


}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("请输入回复内容");
        return;
    }
    $.ajax({
        type: "post",
        url: "/comment",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({
            "content": content,
            "parentId": targetId,
            "type": type
        }),
        success: function (response) {
            console.log(response);
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2002) {
                    //当没有登录时,实现不刷新页面登录
                    var isAccepted = window.confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=bedef8cad503139674a5&redirect_uri=http://localhost:8087/callback&scope=user&state=1");
                        //通过localStorage
                        window.localStorage.setItem("closeable", "true");
                    }
                } else {
                    alert(response.message);
                }
            }

        },
        dataType: "json"

    });

}

//评论
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId,2,content);

}

/**
 * 展开二级评论
 */
function collapseComments(e) {
    // debugger;
    console.log(e);
    //获取当前评论的id
    var id = e.getAttribute("data-id");
    //获取二级评论的div标签
    var comments = $("#comment-" + id);
    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //如果已经展开 就折叠
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        //如果二级评论的已经标签不等于1就直接展开
        var subCommentContainer = $("#comment-" + id);
        console.log(subCommentContainer.children().length);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");

        } else {
            //从后台获取数据
            $.getJSON("/comment/" + id, function (data) {
                console.log(data);
                $.each(data.data.reverse(),function (index,comment) {
                    //头像
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));
                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));
                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);

                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });


        }
    }


}