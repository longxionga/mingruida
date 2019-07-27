<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${applicationScope.T}</title>
    <%@include file="../../../common/common.jsp" %>
    <style>
        .tabs-container .panel-body {
            overflow: hidden;
        }

        .layout-body {
            background-color: #f2f2f2;
        }

        .lay_top {
            height: 30px;
        }

        .lay_top_left {
            color: #fff;
            float: left;
            line-height: 20px;
            padding: 0 0 0 20px;
        }

        .lay_top_right {
            float: right;
            font-size: 14px;
            padding: 0 10px 0 0;
        }

        .lay_top_right a:hover {
            color: #fff;
        }

        .lay_info_window {
            display: none;
            position: absolute;
            bottom: 45px;
            right: 23px;
            z-index: 101;
            padding-bottom: 3px;
            width: 228px;
            background-color: #fff;
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.36)
        }

        .lay_info_window .info_w_title {
            border-bottom: 1px solid #edf0f2;
            padding: 8px 15px;
            font-weight: bold;
            color: #333;
            background-color: #f2f2f2;
        }

        .lay_info_window .info_w_cont {
            padding: 8px 15px 6px;
            max-height: 116px;
            overflow: hidden;
        }

        .lay_info_window .info_w_cont a {
            display: block;
            margin-bottom: 2px;
            color: #333;
            white-space: nowrap;
            text-overflow: ellipsis;
            overflow: hidden;
        }

        .lay_info_window .info_w_cont a:hover {
            color: #19c8be;
            text-decoration: underline;
        }

        .lay_info_window_act {
            display: block;
        }

        .animated {
            animation-duration: 1s;
            animation-fill-mode: both;
            z-index: 100;
        }

        .fadeInRight {
            -webkit-animation-name: fadeInRight;
            animation-name: fadeInRight
        }

        @-webkit-keyframes fadeInRight {
            0% {
                opacity: 0;
                -webkit-transform: translateX(20px);
                transform: translateX(20px)
            }
            100% {
                opacity: 1;
                -webkit-transform: translateX(0);
                transform: translateX(0)
            }
        }

        @keyframes fadeInRight {
            0% {
                opacity: 0;
                -webkit-transform: translateX(40px);
                -ms-transform: translateX(40px);
                transform: translateX(40px)
            }
            100% {
                opacity: 1;
                -webkit-transform: translateX(0);
                -ms-transform: translateX(0);
                transform: translateX(0)
            }
        }

        .fadeInRight {
            -webkit-animation-name: fadeInRight;
            animation-name: fadeInRight
        }

        .top_class {
            display: inline-block;
            padding: 0 10px;
            height: 30px;
            line-height: 30px;
            color: #e0e0e0;
            text-decoration: none;
        }

        .top_class i {
            margin-right: 10px;
        }

        .top_user {
            display: inline-block;
            position: relative;
        }

        .top_user .top_user_class {
            display: inline-block;
            padding: 0 25px 0 20px;
            height: 30px;
            line-height: 30px;
            color: #e0e0e0;
        }

        .top_user .top_user_class:after {
            position: absolute;
            display: block;
            top: 14px;
            right: 4px;
            content: "";
            border: 4px solid transparent;
            border-top-color: #e0e0e0
        }

        .top_dialog {
            display: none;
            position: absolute;
            top: 26px;
            right: -9px;
            z-index: 101;
            padding-bottom: 3px;
            width: 200px;
            background-color: #fff;
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.36)
        }

        .top_dialog a {
            display: block;
            color: #0e2026;
            border-radius: 3px;
            padding: 8px 24px;
            transition: background 0.15s ease 0s, color 0.15s ease 0s, border 0.15s ease 0s;
        }

        .top_dialog a:hover {
            color: #000;
            background-color: #f7f9fa;
        }

        .top_dialog span {
            display: block;
            border-bottom: 1px solid #edf0f2;
            padding: 16px 24px;
            color: #a6aeb3;
        }

        .tabs-tool .l-btn-icon {
            left: 2px;
        }

        .left-menu b {
            padding: 10px 15px 10px 50px;
            display: block;
            color: #4c4c4c;
            position: relative;
            font-weight: normal;
            font-size: 14px;
            cursor: pointer;
        }

        .left-menu b.active {
            color: #fff;
            background-color: #60cec8;
        }

        /* .left-menu b:before{content:'';background: url('/static/easyui/themes/default/images/combo_arrow_white.png') no-repeat 0 center; width:16px;height:16px; display:block;position: absolute;right:16px; top:50%;margin-top:-8px;transition:all 0.5s ease 0s;} */
        .left-menu b:before {
            content: '';
            width: 20px;
            height: 20px;
            display: block;
            position: absolute;
            right: 16px;
            top: 30%;
            margin-top: -8px;
        }

        .left-menu b.active:before {
            background: url('<c:url value="/static/easyui/themes/icons/icon_v2_arrow.png?v=${applicationScope.v}"></c:url>') no-repeat 0 center;
            background-size: 16px auto;
        }

        /* .left-menu b:after{background: url('/static/easyui/themes/default/images/nav.png') no-repeat 0 center; width:18px;height:15px; display:block;position: absolute;left:13px; top:50%;margin-top:-8px;} */
        .left-menu ul {
            overflow: hidden;
            display: none;
        }

        .left-menu li {
            position: relative;
        }

        .left-menu li:before {
            display: block;
            position: absolute;
            width: 9px;
            left: 25px;
            top: 17px;
            border-top: 1px dashed rgba(255, 255, 255, 0.5);
        }

        .left-menu li:after {
            display: block;
            position: absolute;
            top: 0;
            bottom: 0;
            left: 22px;
            width: 0;
            border-left: 1px dashed rgba(255, 255, 255, 0.5);
        }

        .left-menu li a {
            text-decoration: none;
            display: block;
            padding: 11px 0 11px 72px;
            color: #808080;
            font-size: 12px;
        }

        .left-menu li a:hover {
            color: #333;
            background-color: #f7f9fa;
        }

        .left-menu li a.act {
            color: #000;
            background-color: #ffffff
        }

        .left-menu .lay_menu_home {
            padding: 0 15px 0 50px;
            height: 41px;
            line-height: 41px;
            color: #4c4c4c;
            position: relative;
            font-weight: normal;
            font-size: 14px;
            cursor: pointer;
            background: url('<c:url value="/static/easyui/themes/icons/icon_l_home.png?v=${applicationScope.v}"></c:url>') no-repeat 20px center;
        }

        .left-menu .lay_menu_home.active {
            color: #fff;
            background-color: #60cec8;
            background-image: url('<c:url value="/static/easyui/themes/icons/icon_l_home_on.png?v=${applicationScope.v}"></c:url>')
        }

        .left-menu b.lay_menu_xtgl {
            background: url('<c:url value="/static/easyui/themes/icons/icon_l_xtgl.png?v=${applicationScope.v}"></c:url>') no-repeat 20px center;
        }

        .left-menu b.lay_menu_xtgl.active {
            background-color: #60cec8;
            background-image: url('<c:url value="/static/easyui/themes/icons/icon_l_xtgl_on.png?v=${applicationScope.v}"></c:url>')
        }

        .left-menu b.lay_menu_sp {
            background: url('<c:url value="/static/easyui/themes/icons/icon_l_sp.png?v=${applicationScope.v}"></c:url>') no-repeat 20px center;
        }

        .left-menu b.lay_menu_sp.active {
            background-color: #60cec8;
            background-image: url('<c:url value="/static/easyui/themes/icons/icon_l_sp_on.png?v=${applicationScope.v}"></c:url>')
        }

        .left-menu b.lay_menu_ptgl {
            background: url('<c:url value="/static/easyui/themes/icons/icon_l_ptgl.png?v=${applicationScope.v}"></c:url>') no-repeat 20px center;
        }

        .left-menu b.lay_menu_ptgl.active {
            background-color: #60cec8;
            background-image: url('<c:url value="/static/easyui/themes/icons/icon_l_ptgl_on.png?v=${applicationScope.v}"></c:url>')
        }

        .left-menu b.lay_menu_txgl {
            background: url('<c:url value="/static/easyui/themes/icons/icon_l_txgl.png?v=${applicationScope.v}"></c:url>') no-repeat 20px center;
        }

        .left-menu b.lay_menu_txgl.active {
            background-color: #60cec8;
            background-image: url('<c:url value="/static/easyui/themes/icons/icon_l_txgl_on.png?v=${applicationScope.v}"></c:url>')
        }

        .left-menu b.lay_menu_yhbb {
            background: url('<c:url value="/static/easyui/themes/icons/icon_l_yhbb.png?v=${applicationScope.v}"></c:url>') no-repeat 20px center;
        }

        .left-menu b.lay_menu_yhbb.active {
            background-color: #60cec8;
            background-image: url('<c:url value="/static/easyui/themes/icons/icon_l_yhbb_on.png?v=${applicationScope.v}"></c:url>')
        }

        .left-menu b.lay_menu_txbb {
            background: url('<c:url value="/static/easyui/themes/icons/icon_l_txbb.png?v=${applicationScope.v}"></c:url>') no-repeat 20px center;
        }

        .left-menu b.lay_menu_txbb.active {
            background-color: #60cec8;
            background-image: url('<c:url value="/static/easyui/themes/icons/icon_l_txbb_on.png?v=${applicationScope.v}"></c:url>')
        }

        .left-menu b.lay_menu_czbb {
            background: url('<c:url value="/static/easyui/themes/icons/icon_l_czbb.png?v=${applicationScope.v}"></c:url>') no-repeat 20px center;
        }

        .left-menu b.lay_menu_czbb.active {
            background-color: #60cec8;
            background-image: url('<c:url value="/static/easyui/themes/icons/icon_l_czbb_on.png?v=${applicationScope.v}"></c:url>')
        }

        .left-menu b.lay_menu_jjrbb {
            background: url('<c:url value="/static/easyui/themes/icons/icon_l_jjrbb.png?v=${applicationScope.v}"></c:url>') no-repeat 20px center;
        }

        .left-menu b.lay_menu_jjrbb.active {
            background-color: #60cec8;
            background-image: url('<c:url value="/static/easyui/themes/icons/icon_l_jjrbb_on.png?v=${applicationScope.v}"></c:url>')
        }

        .left-menu b.lay_menu_ddgl {
            background: url('<c:url value="/static/easyui/themes/icons/icon_l_txbb.png?v=${applicationScope.v}"></c:url>') no-repeat 20px center;
        }

        .left-menu b.lay_menu_ddgl.active {
            background-color: #60cec8;
            background-image: url('<c:url value="/static/easyui/themes/icons/icon_l_txbb_on.png?v=${applicationScope.v}"></c:url>')
        }

        .left-menu b.lay_menu_bbgl {
            background: url('<c:url value="/static/easyui/themes/icons/icon_l_bbgl.png?v=${applicationScope.v}"></c:url>') no-repeat 20px center;
        }

        .left-menu b.lay_menu_bbgl.active {
            background-color: #60cec8;
            background-image: url('<c:url value="/static/easyui/themes/icons/icon_l_bbgl_on.png?v=${applicationScope.v}"></c:url>')
        }

        .table td {
            height: 20px;
            width: 50%;
        }

        h1, h2, h3 {
            font-size: 12px;
            margin: 0;
            padding: 0;
        }

        .table {
            color: #666;
            margin: 0 auto;
            empty-cells: show;
            border-collapse: collapse;
        }

        .table th {
            border: 1px solid #cad9ea;
            padding: 0 1em 0;
        }

        .table tr.alter {
            background-color: #f5fafe;
        }
    </style>
    <script type="text/javascript">
        function addt(c, t) {
            if ($('#tab').tabs("exists", t)) {
                $('#tab').tabs("select", t);
            } else {
                $('#tab').tabs('add', {
                    title: t
                    ,
                    content: '<iframe src="' + c + '" data-iframe="center" style="width: 100%; height: 100%; overflow:hidden;overflow-y: auto; border: 0;" />'
                    ,
                    closable: true
                });
            }
            ;
            initNavStyle();
            $(".tabs-header").show();
        };

        function initNavStyle() {
            $(".tabs").css({height: 30});
            $(".tabs li a.tabs-inner").css({height: 30, lineHeight: "30px"});
            $(".tabs-tool").css({height: 30});
        }

        function initNavShow() {
            $(".tabs-header").hide();
            $(".tabs-panels").css({height: $(".tabs-panels").height() + $(".tabs-header").height()});
            $(".tabs-container .panel-body").css({height: $(".tabs-panels").height()});
        }

        function listMsg(msg) {
            var str = "";
            for (var i = 0; i < msg.length; i++) {
                str += '<a href="javascript:;" item-oid="' + msg[i].center_id + '" item-title="' + msg[i].center_title + '" item-date="' + msg[i].create_date + '" item-msg="' + msg[i].is_use + '">' + msg[i].center_abstract + '</a'
            }
            return str;
        }

        $(function () {
            //顶部菜单操作
            var setTopDialog;
            $("body").click(function () {
                setTopDialog = setTimeout(function () {
                    $(".top_dialog").hide();
                }, 200);
            });
            $(".j_top_user_class").click(function () {
                setTimeout(function () {
                    clearTimeout(setTopDialog);
                }, 100);
                var dialog = $(".top_dialog");
                if (dialog.is(":hidden")) {
                    dialog.show();
                } else {
                    dialog.hide();
                }
            });

            //左边菜单操作
            $.extend($.fn.tabs.methods, {
                allTabs: function (jq) {
                    var tabs = $(jq).tabs('tabs');
                    var all = [];
                    all = $.map(tabs, function (n, i) {
                        return $(n).panel('options')
                    });
                    return all;
                }
            });
            var $tab = $('#tab');
            $tab.tabs({
                border: true
                , tabHeight: '41px'
                , fit: true
                , tools: [{
                    text: '',
                    iconCls: 'icon-clear',
                    handler: function () {
                        $.each($tab.tabs('allTabs'), function (i, n) {
                            n.title == '主页' || $tab.tabs('close', n.title);
                        })
                    }
                }]
            })
                .tabs('add', {
                    title: '主页'
                    //, tabWidth: 104
                    ,
                    content: '<iframe src="<c:url value='/home'/>" data-iframe="center" style="width: 100%; height: 100%; overflow:hidden;overflow-y: auto; border: 0;" />'
                    /* , content: '<iframe src="home.html" data-iframe="center" style="width: 100%; height: 100%; overflow:hidden;overflow-y: auto; border: 0;" />' */
                })
                .tabs({
                    onClose: function () {
                        if ($(".tabs li").length == 1) {
                            $(".lay_menu_home").click();
                            initNavShow();
                        }
                    }
                });
            initNavShow();
            initNavStyle();

            $.ajax({
                type: "POST",
                url: '${pageContext.request.contextPath}' + "/sys/queryMsg",
                dataType: 'json',
                success: function (msg) {
                    if (msg.length == 0) return;
                    context = msg[0].center_text;
                    title = msg[0].center_title;
                    create_date = msg[0].create_date;
                    $(".lay_info_window .info_w_cont").html(listMsg(msg));
                    setTimeout(function () {
                        $(".lay_info_window").addClass("lay_info_window_act");
                    }, 1000);
                    setTimeout(function () {
                        $(".lay_info_window").removeClass("lay_info_window_act");
                    }, 6000);
                }
            });

            $.ajax({
                type: "POST",
                url: '${pageContext.request.contextPath}' + "/queryRoleMenuBase",
                dataType: 'json',
                success: function (msg) {
                    /* console.log(msg); */
                    var html = "", html2 = "";
                    $.each(msg, function (v, k) {
                        if (k.menu_parent_id === "-1") {
                            html += '<b class="lay_menu_' + k.menu_icon + '">' + k.menu_name + '</b>';
                            html2 = "";
                            $.each(msg, function (m, n) {
                                if (k.menu_id === n.menu_parent_id) {
                                    html2 += '<li><a href="${pageContext.request.contextPath}' + n.menu_url + '" data-id="' + n.menu_id + '" style="background: url(\'<c:url value="/static/easyui/themes/icons/submenu/icon_'+n.menu_icon+'.png?v=${applicationScope.v} "></c:url>\') no-repeat 47px center">' + n.menu_name + '</a></li>';
                                }
                            });
                            if (html2 !== "") {
                                html += "<ul>" + html2 + "</ul>";
                            }
                        }
                    });
                    $(".left-menu > div").append(html);

                    $(".left-menu a").click(function () {
                        var $this = $(this);
                        var title = $this.text();
                        if ($tab.tabs("exists", title)) {
                            $tab.tabs("select", title);
                        } else {
                            $tab.tabs('add', {
                                title: title ,
                                content: '<iframe src="' + $this.attr("href") + '" data-iframe="center" style="width: 100%; height: 100%; overflow:hidden;overflow-y: auto; border: 0;" />'
                                ,
                                closable: true
                            });
                        }
                        ;
                        $(".left-menu a").removeClass("act");
                        $this.addClass("act");
                        initNavStyle();
                        $(".tabs-header").show();
                        return false;
                    });

                    $(".left-menu b").click(function () {
                        var $this = $(this);
                        if ($this.hasClass("active")) {
                            $this.removeClass("active");
                            $this.next().slideUp();
                        } else {
                            $this.addClass("active").siblings("b").removeClass("active");
                            $this.next().slideDown().siblings("ul").slideUp();
                        }
                        $(".lay_menu_home").removeClass("active");
                    });

                    $(".lay_menu_home").click(function () {
                        var $this = $(this);
                        var title = $this.text();
                        $tab.tabs("select", title);
                        $this.addClass("active");
                        $(".left-menu b").removeClass("active");
                        $(".left-menu b").next().slideUp();
                    });
                }
            });

            //退出系统
            $("#exitBtn").click(function () {
                $.ajax({
                    url: 'loginOut',
                    type: "get",
                    success: function (data) {
                        if (data.success == true) {
                            window.location.href = '${pageContext.request.contextPath}/login'
                        }
                        return false;
                    }
                });
            });

            $("#modifyPwd").click(function () {
                $('#tables').form('clear');
                // TODO 弹出修改密码窗口
                $('#mpwdWindowModel').show();
                $('#mpwdWindow').window({
                    top: ($(window).height() - 289) * 0.5 + $(document).scrollTop(),
                    left: ($(window).width() - 360) * 0.5
                });
                $('#mpwdWindow').window('open');
            });
            $("#modifyPwdBtn").click(function () {
                var data = $("#modifyPwdForm").serialize();

                if (!$("#modifyPwdForm").form('validate')) {
                    $.messager.alert('提示', "您填写的信息有误！", 'error');
                    return;
                }
                $.ajax({
                    url: "modifyPwd",
                    type: "POST",
                    data: data,
                    success: function (data) {
                        if (data.success == true) {
                            $.messager.alert('提示', data.msg, 'info', function () {
                                window.location.href = '${pageContext.request.contextPath}/login';
                            });
                            $(".messager-window .panel-tool-close").remove();
                        } else {
                            $.messager.alert('提示', data.msg, 'error');
                        }
                    }
                });
            });

            // 两次输入不一致
            $.extend($.fn.validatebox.defaults.rules, {
                equals: {
                    validator: function (value, param) {
                        return value == $(param[0]).val();
                    },
                    message: 'Field do not match.'

                }
            });
            $("#wholeInfoToggle").click(
                function () {
                    var isLocked = $(".icon-large-locked").length == 1;
                    var message = "确认进行信息解密?";
                    if (!isLocked) {
                        message = "确认进行信息加密?";
                    }

                    $.messager.confirm('提示信息', message, function (r) {
                        if (r) {
                            var isLocked = $(".icon-large-locked").length == 1;
                            if (isLocked) {
                                $.ajax({
                                    url: "/authMum/getCode?key=toggleCode",
                                    type: "GET",
                                    success: function (data) {
                                        if ("100002" == data.errorCode) {
                                            $.messager.alert('提示信息', data.msg, 'error');
                                        } else {
                                            if (data == null || "000000" != data.errorCode) {
                                                if ("100001" == data.errorCode) {
                                                    $.messager.alert('提示信息', data.msg, 'error');
                                                }
                                                return;
                                            }
                                            $.messager.prompt('提示信息', '系统已向您${mobile}手机号发送验证码，请输入您收到验证码.', function (code) {
                                                if (code) {
                                                    $.ajax({
                                                        url: "/userAuth/wholeInfoToggle?toggle=turnoff&code=" + code,
                                                        type: "POST",
                                                        success: function (data) {
                                                            if (data == null || "000000" != data.errorCode) {
                                                                if ("100001" == data.errorCode) {
                                                                    $.messager.alert('提示信息', data.errorMsg, 'error');
                                                                }
                                                                return;
                                                            }
                                                            if ("turnon" == data.setting) {
                                                                $('#wholeInfoToggle').linkbutton({
                                                                    text: '信息加密',
                                                                    iconCls: 'icon-large-locked'
                                                                });
                                                            } else if ("turnoff" == data.setting) {
                                                                $('#wholeInfoToggle').linkbutton({
                                                                    text: '信息解密',
                                                                    iconCls: 'icon-large-unlock'
                                                                });
                                                            }
                                                            window.location.reload();
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }
                                });

                            } else {
                                $.ajax({
                                    url: "/userAuth/wholeInfoToggle?toggle=turnon&code=",
                                    type: "POST",
                                    success: function (data) {
                                        if (data == null || "000000" != data.errorCode) {
                                            if ("100001" == data.errorCode) {
                                                $.messager.alert('提示信息', data.errorMsg, 'error');
                                            }
                                            return;
                                        }
                                        if ("turnon" == data.setting) {
                                            $('#wholeInfoToggle').linkbutton({
                                                text: '信息加密',
                                                iconCls: 'icon-large-locked'
                                            });
                                        } else if ("turnoff" == data.setting) {
                                            $('#wholeInfoToggle').linkbutton({
                                                text: '信息解密',
                                                iconCls: 'icon-large-unlock'
                                            });
                                        }
                                        window.location.reload();
                                    }
                                });
                            }
                        }
                    });

                })
            var toggle = '${sessionScope.wholeInfoToggle}';
            if ("turnon" == toggle) {
                $('#wholeInfoToggle').linkbutton({text: '信息加密', iconCls: 'icon-large-locked'});
            } else if ("turnoff" == toggle) {
                $('#wholeInfoToggle').linkbutton({text: '信息解密', iconCls: 'icon-large-unlock'});
            }

            $(".tabs-tool .l-btn").click(function () {
                $(".lay_menu_home").click();
                initNavShow();
            });

            $(window).resize(function () {
                if ($(".tabs-header").is(":hidden")) {
                    setTimeout(function () {
                        $(".tabs-panels").css({height: $("#tab").height()});
                        $(".tabs-container .panel-body").css({height: $("#tab").height()});
                    }, 100);
                }
            });
            var pwdSec = ${pwdSec};
            if (pwdSec == true) {
                $.messager.alert('提示', '请及时修改您的初始密码！', 'info');
            }
        });

        //信息详情查看
        var context = "";
        var title = "";
        var create_date = "";
        //信息详情查看
        $(document).on("click", "[item-oid]", function () {
            var $this = $(this);
            var center_id = $this.attr("item-oid");
            check(center_id);
        });

        function check(center_id) {
            $.ajax({
                type: "POST",
                url: '${pageContext.request.contextPath}' + "/queryMsgInfo",
                dataType: 'json',
                data: {center_id: center_id},
                success: function (msg) {
                    context = msg[0].center_text;
                    title = msg[0].center_title;
                    create_date = msg[0].create_date;
                    show();
                }

            });

        }

        function show() {
            var html =
                '<div class="lay_home_info_list">' +
                '<div class="info_title">' + title + '</div>' +
                '<div class="info_date">' + create_date + '</div>' +
                '<div class="context">' + context + '</div>' +
                '</div>';
            $("#msg").html(html);
            $('#msg').window({
                top: ($(window).height() - 400) * 0.5 + $(document).scrollTop(),
                left: ($(window).width() - 800) * 0.5
            });
            $('#msg').window('open');
        }
    </script>
</head>
<body class="easyui-layout">
<div data-options="region:'north'" style="overflow: visible;border:0;background-color: #474747;">
    <div class="lay_top">
        <div class="lay_top_left">
            铭锐达后台系统
        </div>
        <div class="lay_top_right">
            <!-- <a class="top_class" href="javascript:;" onclick="$('#chargeOL').submit();"><i class="icon_cash"></i>在线充值</a>
            <a class="top_class" href="javascript:;" id="wholeInfoToggle"><i class="icon_locked"></i>信息加密</a> -->
            <a class="top_class" href='<c:url value="/main"/>'><i class="icon_repick"></i>刷新系统</a>
            <span class="top_user">
            <a class="top_user_class j_top_user_class" href="javascript:;">${deptName}<%-- -${loginName} --%></a>
            <span class="top_dialog">
                <span>当前为&nbsp;${roleName}</span>
                <a id="modifyPwd" href="javascript:;">修改密码</a>
                <a id="exitBtn" href="javascript:;">退出系统</a>
            </span>
        </span>
        </div>
    </div>
</div>
<div data-options="region:'west'" style="width:220px;background:#f2f2f2;border:0;border-right:1px solid #d6d6d6;">
    <div class="left-menu">
        <div>
            <div class="lay_menu_home active">主页</div>
        </div>
    </div>
</div>

<div data-options="region:'center',border:false" class="1111">
    <div id="tab" class="111" style="padding:0"></div>
</div>

<div data-options="region:'south'"
     style="height:30px;line-height:30px;background:#f9f9f9;overflow:hidden;text-align: center;border-bottom:0;border-right:0;border-left:0;border-top-color:#d6d6d6;">
    &copy;2019 铭锐达后台系统
</div>

<div id="mpwdWindowModel" class="hide">
    <div id="mpwdWindow" class="easyui-window" title="修改密码"
         style="padding:15px 13px 15px 30px; width:360px; height:289px;" closed="true"
         data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
        <form id="modifyPwdForm">
            <table class="tables" id="tables">
                <tr>
                    <td>旧密码：</td>
                    <td><input type="password" name="oldPwd" class="easyui-textbox" style="width:180px;"
                               data-options="required:true,validType:'length[5,20]',missingMessage:'不能为空',invalidMessage:'长度5-20字符'">
                    </td>
                </tr>
                <tr>
                    <td>新密码：</td>
                    <td><input id="newPwd" type="password" name="newPwd" class="easyui-textbox" style="width:180px;"
                               data-options="required:true,validType:'length[5,20]',missingMessage:'不能为空',invalidMessage:'长度5-20字符'">
                    </td>
                </tr>
                <tr>
                    <td>确认密码：</td>
                    <td><input type="password" class="easyui-textbox" style="width:180px;"
                               data-options="required:true,missingMessage:'不能为空'" validType="equals['#newPwd']"
                               invalidMessage="两次输入密码不匹配"></td>
                </tr>
                <tr>
                    <td colspan="4" align="center">
                        <div class="pt15">
                            <a class="easyui-linkbutton" style="width: 80px;" href="javascript:;" id="modifyPwdBtn">提
                                交 </a>
                            <a href="javascript:$('#tables').form('clear');" class="easyui-linkbutton l-btn-grey"
                               style="width: 80px;">重 置</a>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<!--信息详情查看 -->
<div id="msg" class="easyui-window" title="系统公告" closed="true" style="width:800px;height:400px;padding:20px;"
     data-options="modal:!0,collapsible:!1,minimizable:!1,maximizable:!1,closed:!0">
</div>
<div class="lay_info_window fadeInRight animated">
    <div class="info_w_title">通知：</div>
    <div class="info_w_cont"></div>
</div>
<script type="text/javascript" src='<c:url value="/static/js/common.js?v=${applicationScope.v}"></c:url>'></script>
</body>
</html>
