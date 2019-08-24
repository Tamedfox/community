//提交回复
function postComment(){
    var questionId = $("#question_id").val();
    var questionComment = $("#question_comment").val();
    if(!questionComment){
        alert("不能回复空内容！");
        return ;
    }
    $.ajax({
        type:"post",
        contentType:"application/json",
        url:"/comment",
        data:JSON.stringify({
            "parentId":questionId,
            "content":questionComment,
            "type":1
        }),
        success:function(response){
            if(response.code == 200){
                window.location.reload();
            }else{
                if(response.code == 2003){
                    var isAccepted = confirm(response.message);;
                    if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=611cf31e7505f47133f2&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable","true");
                    }
                }else{
                    alert(response.message);
                }
            }

        },
        dataType:"json"
    });
}

/*
展开评论
 */
function doubleComment(e){
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);

    var close = e.getAttribute("data-close");

    if(close){
        comments.removeClass("in");
        e.removeAttribute("data-close");
        e.classList.remove("active");
    }else{
        comments.addClass("in")
        e.setAttribute("data-close","in");
        e.classList.add("active");
    }
}
