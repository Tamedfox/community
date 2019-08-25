//提交提问回复
function postComment(){
    var questionId = $("#question_id").val();
    var questionComment = $("#question_comment").val();
    comment2Target(questionId,1,questionComment);
}

//提交评论的评论
function postDoubleComment(e){
    var commentId = e.getAttribute("data-id");
    var comment = $("#input-"+commentId).val();
    comment2Target(commentId,2,comment);
}

function comment2Target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容~~~");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 1003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=611cf31e7505f47133f2&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

function showSelectTag(){
    $("#select-tag").show();
}

function selectTag(e){
    event.stopPropagation();
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();

    if(previous.indexOf(value) == -1){
        if(previous){
            $("#tag").val(previous + '-' + value);
        }else{
            $("#tag").val(value);
        }
    }


}

/*
展开评论
 */
function doubleComment(e){
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("comment-" + id);

        if(subCommentContainer.children)

        $.getJSON(("/comment/"+id,function(data){
            $.each(data.data, function (index, comment) {
                var c = $("<div>",{
                    "class":"col-lg-9 col-md-12 col-sm-12 col-xs-12 comments",
                    html:comment.content
                });
                subCommentContainer.prepend(c);
            })
        }));

        //展开二级评论
        comments.addClass("in");
        e.setAttribute("data-collapse", "in");
        e.classList.add("class");
    }

}


