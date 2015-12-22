<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="header.jsp"></jsp:include>
<div class="categories">
	<jsp:include page="categoriesNav.jsp"></jsp:include>
</div>
<form:form id="loginForm" class="form-horizontal" commandName="logUserIn"
	action="${pageContext.request.contextPath}/controller/user/logUserIn" method="post">
	<section id="login">
		<div class="container">
			<div class="row">
				<div class="col-xs-4"
					style="float: none; margin: 0 auto; text-align: center;">
					<div class="form-wrap">
						<h2>Login</h2>
						<hr />
						<div class="form-group">
							<label for="username" class="sr-only">Username</label>
							<form:input path="username" class="form-control" placeholder="Username" />
						</div>
						<div class="form-group">
							<label for="password" class="sr-only">Password</label>
							<form:input type="password" path="password" name="password" cssClass="form-control" placeholder="Password" />
						</div>
						<input type="submit" id="btn-login" class="btn btn-warning" value="Login" /> <a id="forgotPassword"
							class="text-danger">Forgot your password?</a>
						<hr />
					</div>
				</div>
			</div>
		</div>
	</section>
</form:form>
<form:form id="forgotPasswordForm" class="form-horizontal" commandName="forgotPassword"
	action="${pageContext.request.contextPath}/controller/user/forgotPassword" method="post">
	<div class="row" id="forgotPasswordDialog" style="display: none;" title="Forgot Password?" align="center">
		<div class="form-wrap">
			<h2>Enter your username to retrieve your password.</h2>
			<hr />				<div class="form-group">
				<label for="username" class="sr-only">Email</label>
				<form:input id="usernameForPassword" path="username" class="form-control" placeholder="Username" />
			</div>
			<input type="submit" id="forgotPasswordButton" class="btn btn-warning" value="Send Password" />
			<hr />
		</div>
	</div>
</form:form>
<script>
	$(document).ready(function() {
		$('#forgotPasswordDialog').dialog({minWidth: 400, autoOpen: false});
		$('#forgotPassword').click(function() {
			$('#forgotPasswordDialog').dialog("open");
		});
		$('#forgotPasswordButton').click(function(e) {
			e.preventDefault();
			var username = $('#usernameForPassword').val();
				$.post("${pageContext.request.contextPath}/controller/user/forgotPassword/" + username, function() {
					$('#forgotPasswordDialog').dialog("close");
					alert("Your password has been sent to your email!");
				});
		});
	});
</script>
<jsp:include page="footer.jsp"></jsp:include>