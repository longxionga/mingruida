<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="../../../common/common.jsp" %>
    <title>${applicationScope.T}</title>
    <style>
        body {
            background: url("static/images/loginbg03.jpg") no-repeat center bottom;
        }

        .login_main {
            position: relative;
            top: 24%;
            margin: 0 auto;
            width: 840px;
            height: 322px;
        }

        .login_main_left {
            float: left;
        }

        .login_logo {
            margin-top: 130px;
            padding-left: 106px;
            width: 245px;
            height: 54px;
            font-size: 54px;
            color: #fff;
            background: url("static/images/logo-5.png") no-repeat center right;
        }

        .login_main_right {
            width: 412px;
            float: right;
            background: url("static/images/loginborder.png") no-repeat left center;
        }

        .login_layout {
            float: right;
            padding: 38px 18px;
            width: 300px;
            height: 246px;
            border-radius: 5px;
            background-color: rgba(10, 17, 29, 0.2);
        }

        .login_row {
            position: relative;
            margin-bottom: 20px;
            padding: 0 15px 0 74px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #fff;
            overflow: hidden;
        }

        .login_row .i_name {
            position: absolute;
            top: 11px;
            left: 14px;
            color: #666;
        }

        .login_row .i_text {
            padding: 12.5px 0;
            width: 100%;
            border: 0;
            background-color: #fff;
        }

        .login_row .i_code {
            position: absolute;
            top: 0;
            right: 15px;
            width: 100px;
            height: 43px;
            line-height: 41px;
            color: #2ac7bf;
            text-decoration: underline;
            text-align: right;
            background-color: #fff;
        }

        .login_row .i_code_grey {
            color: #808080;
            cursor: default;
        }

        .login_row img {
            position: absolute;
            top: 8px;
            right: 15px;
        }

        .login_row_oper {
            margin: 0 -9px;
            height: 45px;
            line-height: 45px;
        }

        .login_row_oper .oper_width {
            float: left;
            width: 50%;
        }

        .login_row_oper span {
            display: block;
            padding: 0 9px;
        }

        .login_row_oper span input {
            display: block;
            width: 100%;
            height: 45px;
            line-height: 45px;
            font-size: 16px;
            border: 0;
            border-radius: 5px;
            cursor: pointer;
        }

        .login_row_oper span .i_submit {
            color: #fff;
            background-color: #50cbc5;
        }

        .login_row_oper span .i_submit:hover {
            background-color: #18a69f;
        }

        .login_row_oper span .i_submit:active {
            background-color: #047f79;
        }

        .login_row_oper span .i_reset {
            color: #999;
            background-color: #cdcfd0;
        }

        @media only screen and (max-width: 400px) {
        }

        @media only screen and (max-width: 1023px) {
            .login_layout {
                width: 266px;
            }
        }

        @media only screen and (max-width: 10230px) {
            .login_main {
                top: 13%;
                width: auto;
            }

            .login_logo {
                margin: 0 auto;
                width: 300px;
                padding-left: 0;
                background-position: center;
                background-size: auto 44px;
            }

            .login_main_left {
                float: none;
                margin-bottom: 28px;
            }

            .login_main_right {
                float: none;
                width: auto;
            }

            .login_layout {
                float: none;
                margin: 0 auto;
            }
        }
    </style>
    <script>
        if (location && location !== top.location && location.href.indexOf("/login") > -1) {
            top.location.href = location;
        }
    </script>
</head>
<body>
<div class="login_main">
    <div class="login_main_left">
        <div class="login_logo"></div>
    </div>
    <div class="login_main_right">
        <form id="login" method="post" autocomplete="off" action="${pageContext.request.contextPath}/loginAuth">
            <div class="login_layout">
                <div class="login_row">
                    <label class="i_name">账号：</label>
                    <input class="i_text easyui-validatebox" type="text" name="loginName"
                           data-options="required:true,validType:'length[2,20]',missingMessage:'请输入用户名'"/>
                </div>
                <div class="login_row">
                    <label class="i_name">密码：</label>
                    <input class="i_text easyui-validatebox" type="password" name="passWord"
                           data-options="required:true,validType:'length[5,20]',missingMessage:'请输入密码'"/>
                </div>
                <div class="login_row" id="j_key_img">
                    <label class="i_code" id="getcode">获取验证码</label>
                    <label class="i_name">验证码：</label>
                    <input id="js_web_code" class="i_text easyui-validatebox" type="text" name="web_code"
                           data-options="required:true,validType:'length[4,4]',missingMessage:'请输入4位验证码'"/>
                </div>

                <div class="login_row_oper">
                    <div class="oper_width"><span><input class="i_submit" type="submit" value="登录"/></span></div>
                    <div class="oper_width"><span><input class="i_reset" type="reset" value="重置"/></span></div>
                </div>
            </div>
        </form>
    </div>
</div>


<div id="codeModel" class="hide">
    <!-- 短信验证表单开始 -->
    <div id="code" class="easyui-window" title="" style="padding:15px 13px 15px 30px;width:350px;height:200px"
         data-options="iconCls:'icon-add',modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
        <form id="codeform" method="post" enctype="multipart/form-data" autocomplete="off" action-add="checkCode">
            <table class="tables">
                <tr>
                    <td>手机号：</td>
                    <td><input class="easyui-textbox" style="width:150px;" id="user_mobile" name="user_mobile"
                               data-options="required:true,validType:['length[2,100]']"></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <div class="pt15">
                            <a href="javascript:;" onclick="checkMobile()" class="easyui-linkbutton"
                               style="width:80px;">提 交 </a>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <!-- 短信验证表单结束 -->
</div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
<script type="text/javascript">
    var kflag = false;
    var webCodeVal, webCodeType;
    //点击发送短信验证码
    $(document).on("click", "#j_account_getsms", function () {
        var $this = $(this);
        if (!$('[name="loginName"]').validatebox("isValid")) {
            $('[name="loginName"]').focus();
            return false;
        }

        var $this = $('#code');
        var $form = $this.find("form");
        $('#codeform').form('clear');
        $("#reset").linkbutton('enable');
        $this.window({title: '请输入手机号'});
        $form.attr("action", $form.attr("action-code"));
        $this.window({
            top: ($(window).height() - 200) * 0.5 + $(document).scrollTop(),
            left: ($(window).width() - 350) * 0.5
        });
        $this.window('open');


    });

    function checkMobile() {
        $("body").loading();
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/getVerifyCodeText",
            data: {
                loginName: $.trim($('[name="loginName"]').val()),
                user_mobile: $.trim($('[name="user_mobile"]').val())
            },
            dataType: 'json',
            success: function (data) {
                $("body").loading("close");
                if (data.success == true) {
                    $.messager.alert('提示信息', data.msg, 'info', function () {
                        $("#code").window("close");
                    });
                    $(".messager-window .panel-tool-close").remove();

                    var setTime;
                    var elem = $("#j_account_repicksms").find("span");
                    var elemCount = $("#j_account_repicksms").find("span").html();
                    var hideObj = $("#j_account_repicksms").show();
                    setTime = setInterval(function () {
                        if (elem.html() == 0) {
                            hideObj.hide().parent().find("#j_account_getsms").show();
                            elem.html(60);
                            clearInterval(setTime);
                            return false;
                        }
                        elemCount--;
                        elem.html(elemCount);
                    }, 1000);
                } else {
                    $.messager.progress('close');
                    $.messager.alert('错误信息', data.msg, 'error');
                }
            }
        });
    }

    $("#getcode").click(function () {
        var $this = $(this);
        if (!$('[name="loginName"]').validatebox("isValid")) {
            $('[name="loginName"]').focus();
            return false;
        }
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/slide",
            data: {
                loginName: $.trim($('[name="loginName"]').val())
            },
            dataType: 'json',
            success: function (data) {
                if (data.success == true) {
                    if (data.login_count - 3 > 0) {
                        $this.replaceWith('<img id="verifycode" src="${pageContext.request.contextPath}/getVerifyCodeImage?" onclick="this.src = this.src.split(\'?\')[0] + \'?\' + Math.random()" />');
                        $("#web_code").textbox("enableValidation");
                        $("#text_code").textbox("disableValidation");
                        webCodeType = 0;
                    } else {
                        $this.replaceWith('<img id="verifycode" src="${pageContext.request.contextPath}/getVerifyCodeImage?" onclick="this.src = this.src.split(\'?\')[0] + \'?\' + Math.random()" />');
                        $("#web_code").textbox("enableValidation");
                        $("#text_code").textbox("disableValidation");
                        webCodeType = 0;
                    }
                } else {
                    $.messager.progress('close');
                    $.messager.alert('错误信息', data.msg, 'error');
                }
            }
        });
    });

    $("#login").submit(function () {
        var $this = $(this);
        if (!$this.form("validate")) {
            return false;
        }
        var param = getFormJson(this);//获取表单元素的值 json对象
        var action = $this.attr("action"); //获取表单提交接口
        /* $.messager.progress({
            text:'数据验证中,请稍后...',
            interval:1000
        }); */
        $("body").loading();
        $.ajax({
            url: action,
            type: "POST",
            data: param,
            success: function (data) {
                if (data.success == true) {
                    window.location.href = "${pageContext.request.contextPath}/main";
                } else {
                    $("body").loading("close");
                    //$.messager.progress('close');
                    $.messager.alert('错误信息', data.msg, 'error');
                    if (data.login_count - 3 <= 0) {
                        $('#verifycode').click();
                    } else {
                        window.location.href = "${pageContext.request.contextPath}/login";
                    }
                }
                return false;
            }
        });
        return false;
    });

    $("body").height($(document).height());
    $(window).resize(function () {
        $("body").height($(document).height());
    });

    $("input[name=web_code]").keydown(function () {
        if (kflag) {
            e.preventDefault();
        } else {
            webCodeVal = $(this).val();
            kflag = true;
        }
    }).keyup(function () {
        var $this = $(this);
        if (!$this.val().match(/^[A-Za-z0-9]+$/) && $this.val() != "") {
            $this.val(webCodeVal);
        }
        kflag = false;
    });

    $("#js_web_code").focus(function () {
        if ($("#getcode").length) {
            $("#getcode").click();
        }
        if (!$("#verifycode").length && $("#j_account_getsms").length && $("#j_account_repicksms").css("display") != "block") {
            $(this).blur();
        }
    });
</script>
</body>
</html>
