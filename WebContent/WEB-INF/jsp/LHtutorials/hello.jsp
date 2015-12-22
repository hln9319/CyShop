<%@ page contentType="text/html; charset=UTF-8" %>
<link href="${pageContext.request.contextPath}/css/cyShop.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/alert.js"></script>
<html>
<head>
<title>Hello World</title>
</head>
<body>
   <h2>${message}</h2>
   <input type="button" value="alert" onclick="showDIVs()"/>
   <div id="overlay" onclick="hideDIVs()"></div>
   <div id="alert">mmm</div>
</body>
</html>
