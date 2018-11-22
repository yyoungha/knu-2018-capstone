<%--
  Created by IntelliJ IDEA.
  User: hyunwook
  Date: 2018-11-21
  Time: 오전 1:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>PUSH SCREEN</title>
</head>
<body>
<!-- push-->
<form action="/push" method="post">

    <input type="text" id="title" name="title"/>

    <input type="text" id="content" name="content"/>

    <input type="submit" value="push"/>

    <input type="reset" value="cancel"/>

</form>
</body>
</html>
