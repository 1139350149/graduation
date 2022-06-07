var set = new Set();

$(document).ready(function () {
    btnBind();
    removeBind();
});

function submitForm(event) {
    var tag = $("#tag");
    var temp = '';
    set.forEach(function (elemet, index, s) {
        temp += (elemet + " ");
    })
    tag.val(temp);
}

function btnBind() {
    var fileBtn = $("#file-btn");
    var uploadPic = $("#upload_file");
    fileBtn.on("click", function () {
        uploadPic.click();
    });
}

function tagParse() {
    var tags = $("#tag-surface");
    var tagsText = tags.val();
    if (tagsText.charAt(tagsText.length - 1) === ' ') {
        tags.val("");
        var tag = tagsText.trim().split(' ');
        if (tag[0] === '') {
            return;
        }
        if (set.size > 4) {
            toast("标签过多", "请删除一些标签", "error");
            return;
        }
        if (!set.has(tag[0])) {
            var temp = $("<div class='col-md-2'><span class='tag'>#" + tag[0] + "</span></div>");
            temp.on("click", function () {
                set.delete(tag[0]);
                temp.remove();
            })
            $("#tag-target").append(temp);
            set.add(tag[0]);
        }
    }
    console.log(set);
}

function removeBind() {
    var removeBtn = $("#remove_img");
    removeBtn.on("click", function () {
        var uploadPic = document.getElementById("upload_file");
        uploadPic.value = '';
        $("#img").attr("src", null);
        $("#img_show").css("display", "none");
        $("#remove_img").css("display", "none");
    })
}


function changeImg(e) {
    for (var i = 0; i < e.target.files.length; i++) {
        var file = e.target.files.item(i);
        if (!(/^image\/.*$/i.test(file.type))) {
            continue; //不是图片 就跳出这一次循环
        }
        //实例化FileReader API
        var freader = new FileReader();
        freader.readAsDataURL(file);
        freader.onload = function (e) {
            $("#img").attr("src", e.target.result);
            $("#img_show").css("display", "block");
            $("#remove_img").css("display", "block");
        }
    }
}
