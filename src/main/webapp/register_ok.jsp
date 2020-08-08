<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>注册成功</title>

    <%--定时器 倒计时跳转页面--%>
    <script>
        let index = 5;
        //页面加载函数  相当于 $(function(){})
        window.onload = function(){
            //轮询定时器
            //参数1 : 定时器执行的函数  参数 :执行的毫秒值
            setInterval(function(){
                index--;
                if(index ==0) { //倒计时结束了 跳转页面
                    location.href="${pageContext.request.contextPath}/index.jsp";
                }
                //修改span的值
                document.getElementById("spanId").innerHTML= index;
            } , 1000);

        }
    </script>
</head>
<body>
<!--引入头部-->
<jsp:include page="header.jsp"></jsp:include>
<!-- 头部 end -->
<div style="text-align:center;height: 290px;margin-top: 50px">
    <span style="font-size: 30px">恭喜您，注册成功！</span>
    <div><span id="spanId" style="color: red">5</span>秒后，跳转到 <a href="./index.jsp">首页</a> </div>
</div>
<!--引入尾部-->
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
