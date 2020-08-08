<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>注册</title>
    <link rel="stylesheet" href="css/register.css">
</head>
<body>
<!--引入头部-->
<jsp:include page="header.jsp"></jsp:include>
<!-- 头部 end -->
<div class="rg_layout">
    <div class="rg_form clearfix">
        <%--左侧--%>
        <div class="rg_form_left">
            <p>新用户注册</p>
            <p>USER REGISTER</p>
        </div>
        <div class="rg_form_center">
            <!--注册表单-->
            <form id="registerForm" action="${pageContext.request.contextPath}/user" method="post">
                <!--提交处理请求的标识符 , 就是调用执行服务器中的方法名称-->
                <input type="hidden" name="action" value="register">
                <table style="margin-top: 25px;width: 558px">
                    <tr>
                        <td class="td_left">
                            <label for="username">用户名</label>
                        </td>
                        <td class="td_right">
                            <input type="text" id="username" name="username" placeholder="请输入账号">
							<span id="userInfo" style="font-size:10px"></span>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                            <label for="telephone">手机号</label>
                        </td>
                        <td class="td_right">
                            <input type="text" id="telephone" name="telephone" placeholder="请输入您的手机号">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                            <label for="password">密码</label>
                        </td>
                        <td class="td_right">
                            <input type="password" id="password" name="password" placeholder="请输入密码">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                            <label for="smsCode">验证码</label>
                        </td>
                        <td class="td_right check">
                            <input type="text" id="smsCode" name="smsCode" class="check" placeholder="请输入验证码">
                            <a href="javaScript:void(0)" id="sendSmsCode">发送手机验证码</a>
                            <span id="telephoneInfo" style="font-size:10px"></span>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                        </td>
                        <td class="td_right check">
                            <input type="submit" class="submit" value="注册">
                            <%--异常提示的span标签--%>
                            <span id="msg" style="color: red;">${msg}</span>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <%--右侧--%>
        <div class="rg_form_right">
            <p>
                已有账号？
                <a href="javascript:$('#loginBtn').click()">立即登录</a>
            </p>
        </div>
    </div>
</div>
<!--引入尾部-->
<jsp:include page="footer.jsp"></jsp:include>


</body>
</html>


<script>
    $(function () {
        $("#username").blur(function (){
            let usernameVal = $("#username").val();
            if(usernameVal == ''){
                //<span id="userInfo" style="font-size:10px"></span>
                $("#userInfo").html("<span id=\"userInfo\" style=\"font-size:10px;color:red\">USERNAME NOT EMPTY ! CHECK IT.</span>");
                return ;
            }
            let url ="${pageContext.request.contextPath}/user";
            let data = {'username':usernameVal,'action':'checkName'};
            $.post(url,data,function (resultInfo) {
                //<span id="userInfo" style="font-size:10px;color:red">用户名</span>
                if(resultInfo.flag){
                    $("#userInfo").html("<span id=\"userInfo\" style=\"font-size:7px;color:skyblue\">LUCK DOG , YOUR INFO CAN USE .</span>");
                }else{
                    $("#userInfo").html("<span id=\"userInfo\" style=\"font-size:7px;color:red\">INFO CANNOT USE , YOU MUST CHANGE OTHORS .</span>");
                }
            },"json");
        })
    })
</script>
<script>
    $(function () {
        $("#sendSmsCode").click(function () {
            let phoneVal = $("#telephone").val();
            if(phoneVal == ''){
                //<span id="userInfo" style="font-size:10px"></span>
                $("#telephoneInfo").html("<span id=\"telephoneInfo\" style=\"font-size:10px;color:red\">TELE NOT EMPTY!</span>");
                return ;
            }
            let url ="${pageContext.request.contextPath}/user";
            let data = {'telephone':phoneVal,'action':'sendRegisterCode'};
            $.post(url,data,function (resultInfo) {
                if(resultInfo.flag){
                    $("#telephoneInfo").html("<span id=\"telephoneInfo\" style=\"font-size:10px;color:skyblue\">MESSAGE SEND SUCCESS.</span>");
                }else{
                    $("#telephoneInfo").html("<span id=\"telephoneInfo\" style=\"font-size:10px;color:red\">MESSAGE SEND WRONG , TRY AGAIN.</span>");
                }
            },"json");
        })
    })
</script>