$(document).ready(function () {
    window.flag = [-1, -1, -1, -1];
    inputAnimation();
    cometsShow();
    pageToggle();
    verifyingInfo();
    showRegisterHint();
    submitCheck();
});

$(window).resize(function () {
    clearInterval(document.cometTimer);
    cometsShow();
});

function verifyingInfo() {
    $("#username").on("change", function () {
        var target = $("#username-hint");
        userNameUniquenessVerification(this.value);
        // console.log(window.flag);
        switch (window.flag[0]) {
            case 0: {
                target.find(".hint-icon").attr("src", "/resource/correct.png");
                target.find(".hint-content").text("合格的用户名");
                break;
            }
            case -1: {
                target.find(".hint-icon").attr("src", "/resource/error.png");
                target.find(".hint-content").text("用户名不能为空");
                break;
            }
            case -2: {
                target.find(".hint-icon").attr("src", "/resource/error.png");
                target.find(".hint-content").text("用户名已被使用");
                break;
            }
            case -3: {
                target.find(".hint-icon").attr("src", "/resource/error.png");
                target.find(".hint-content").text("网络连接失败");
                break;
            }
        }
        $(target).removeClass("hide-condition");
        submitConditionChange();
    });

    $("#address").on("change", function () {
        if (this.value === null || this.value === "") {
            window.flag[1] = -1;
        }
        else{
            window.flag[1] = 0;
        }
        var target = $("#address-hint");
        switch (window.flag[1]) {
            case 0: {
                target.find(".hint-icon").attr("src", "/resource/correct.png");
                target.find(".hint-content").text("");
                break;
            }
            case -1: {
                target.find(".hint-icon").attr("src", "/resource/error.png");
                target.find(".hint-content").text("此项是必填项，不可以空缺");
                break;
            }
        }
        $(target).removeClass("hide-condition");
        submitConditionChange();
    });

    $("#password").on("change", function () {
        $("#confirm_password").change();
        var target = $("#password-hint");
        window.flag[2] = passwordFormatVerification(this.value);
        switch (window.flag[2]) {
            case 0: {
                target.find(".hint-icon").attr("src", "/resource/correct.png");
                target.find(".hint-content").text("密码有效");
                break;
            }
            case -1: {
                target.find(".hint-icon").attr("src", "/resource/error.png");
                target.find(".hint-content").text("密码不能为空");
                break;
            }
            case -2: {
                target.find(".hint-icon").attr("src", "/resource/error.png");
                target.find(".hint-content").text("密码过短");
                break;
            }
            case -3:{
                target.find(".hint-icon").attr("src", "/resource/error.png");
                target.find(".hint-content").text("密码过长");
                break;
            }
            case -4: {
                target.find(".hint-icon").attr("src", "/resource/error.png");
                target.find(".hint-content").text("密码格式有误");
                break;
            }
        }
        $(target).removeClass("hide-condition");
        submitConditionChange();
    });

    $("#confirm_password").on("change", function () {
        var target = $("#confirm-password-hint");
        if (this.value === null || this.value === "") {
            window.flag[3] = -1;
            target.find(".hint-icon").attr("src", "/resource/error.png");
            target.find(".hint-content").text("此项是必填项，不可以空缺");
        }
        if (this.value === $("#password").val()) {
            window.flag[3] = 0;
            target.find(".hint-icon").attr("src", "/resource/correct.png");
            target.find(".hint-content").text("密码匹配");
        } else {
            window.flag[3] = -2;
            target.find(".hint-icon").attr("src", "/resource/error.png");
            target.find(".hint-content").text("密码不匹配");
        }
        $(target).removeClass("hide-condition");
        submitConditionChange();
    });
}

function passwordFormatVerification(val) {
    if (val === null || val === "") {
        return -1;
    }
    if (val.length < 6) {
        return -2;
    }
    else if(val.length > 20){
        return -3;
    }
    else {
        var pattern = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;
        if (pattern.test(val)) {
            return 0;
        }
        return -4;
    }
}

function userNameUniquenessVerification(val) {
    // console.log(val);
    if (val === null || val === "") {
        window.flag[0] = -1;
        return;
    }
    // var tg = TGTool();
    $.ajax({
        url: "usernameExistence",
        type: "post",
        data: "name=" + val,
        dataType: "json",
        async: false,
        error: function (data) {
            // console.log("Error! Can not send request");
            // tg.error("Error! Can not send request");
            window.flag[0] = -3;
        },
        success: function (data) {
            window.flag[0] = data.flag;
        }
    });
}

function allClear(val) {
    return (val[0] + val[1] + val[2] + val[3] === 0);
}

function showRegisterHint() {
    var submitBtn = $("#register-submit");
    var submitHint = $("#submit-hint");
    // positionFollowing(submitHint, this, 20);
    submitBtn.on("mouseenter", function () {
        positionFollowing(submitHint, this, 20);
        submitHint.fadeIn();
    });

    submitBtn.on("mouseleave", function () {
        positionFollowing(submitHint, this, 20);
        submitHint.fadeOut();
    });
}

function submitCheck() {
    var submitBtn = $("#register-submit");
    submitBtn.on("mouseenter", function () {
        $(this).css("background-color", "grey");
    });
    submitBtn.on("mouseleave", function () {
        $(this).css("background-color", "rgb(106, 157, 103)");
    });
    submitBtn.on("click", function () {
        if (!allClear(window.flag)) {
            return false;
        }
    })
}

function submitConditionChange() {
    var registerSubmit = $("#register-submit");
    if (allClear(flag)) {
        registerSubmit.css("cursor", "pointer");
        registerSubmit.off("mouseenter", changeDeepSkyBlue);
        registerSubmit.off("mouseenter", changeGray);
        registerSubmit.on("mouseenter", function () {
            changeDeepSkyBlue(this);
        });
        $("#submit-hint").text("已满足注册条件，可以注册");
    } else {
        registerSubmit.css("mouseenter", "not-allowed");
        registerSubmit.off("mouseenter", changeDeepSkyBlue);
        registerSubmit.off("mouseenter", changeGray);
        registerSubmit.on("mouseenter", function () {
            changeGray(this);
        });
        $("#submit-hint").text("当前不满足注册条件!");
    }
}

function changeDeepSkyBlue(val) {
    $(val).css("background-color", "deepskyblue");
}

function changeGray(val) {
    $(val).css("background-color", "grey");
}
