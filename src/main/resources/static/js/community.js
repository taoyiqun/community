function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,1,content);
}
function comment(e){
    let commentId = e.getAttribute("data-id");
    let content = $("#reply_content_"+commentId).val();
    comment2target(commentId,2,content);
}
function collapseComments(e){
    let id = e.getAttribute("data-id");
    let comment = $("#comment-"+id);
    if(comment.hasClass("in")){
        e.classList.remove("active");
        comment.removeClass("in");
    }else {
        $.ajax({
            url: "/comment/"+id,
            type: "GET",
            success:function (data){
                comment.html(data);
                e.classList.add("active");
                comment.addClass("in");
            }
        });
    }
}
function comment2target(targetId,type,content){
    if(!content){
        alert("不能回复空内容~");
        return ;
    }
    console.log(targetId);
    $.ajax({
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type,
        }),
        success: function (response) {
            if(response.code === 200){
                window.location.reload();
            }else {
                if(response.code === 2003){
                    var isAppcepted = confirm(response.message);
                    if(isAppcepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=da7e5aa6cd6b9c3de26b&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable","true");
                    }else {
                        alert(response.message);
                    }
                }
            }
        },
        contentType: "application/json",
        dataType: "json"
    });
}
function selectTag(e){
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if(previous.indexOf(value) !== -1){
        if(previous){
            $("#tag").val(previous + ',' + value);
        }else {
            $("#tag").val(value);
        }
    }
}
function showSelectTag(){
    $("#select-tag").show();
}
