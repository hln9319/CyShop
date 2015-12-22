<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="header.jsp"></jsp:include>
<div class="categories">
	<jsp:include page="categoriesNav.jsp"></jsp:include>
</div>
<form:form id="createAccountForm" class="form-horizontal" commandName="user" method="POST" action="${pageContext.request.contextPath}/controller/user/saveCreateAccount">
	<section id="createAccount">
		<div class="container">
			<div class="row">
				<div class="col-xs-4" style="float: none; margin: 0 auto; text-align: center; max-width: 100%;">
					<div class="form-wrap">
						<h2>Create an Account</h2>
						<hr />
						<div class="form-group">
							<label for="email" class="sr-only control-label">Email</label>
							<div class="controls">
								<form:input id="email" path="email" cssClass="form-control" placeholder="Email" />
							</div>
						</div>
						<div class="form-group">
							<label for="username" class="sr-only control-label">Username</label>
							<div class="controls">
								<form:input id="username" path="username" cssClass="form-control" placeholder="Username" />
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="sr-only control-label">Password</label>
							<div class="controls">
								<form:input id="password" type="password" path="password" cssClass="form-control" placeholder="Password" />
							</div>
						</div>
						<div class="form-group">
							<label for="userType" class="sr-only">Account Type</label>
							<form:select path="userType" cssClass="form-control" placeholder="Account Type">
								<form:option value="Shopper">Shopper</form:option>
								<form:option value="Seller">Seller</form:option>
							</form:select>
						</div>
						<input type="submit" class="btn btn-warning" value="Create Account" />
						<p><br />Already have an account? <a href="${pageContext.request.contextPath}/controller/store/login">Login</a>!</p>
						<hr />
					</div>
				</div>
			</div>
		</div>
	</section>
</form:form>
<jsp:include page="footer.jsp"></jsp:include>
<script>
$(document).ready(function () {
	var API_ROOT = '/CyShop/controller';
    $('#createAccountForm').validate({
        rules: {
            username: {
            	maxlength: 8,
                minlength: 2,
                required: true,
                remote: {
                    url: API_ROOT + '/user/usernameCheck',
                    type: "post"
                 }
            },
            email: {
                required: true,
                email: true,
                remote: {
                    url: API_ROOT + '/user/emailCheck',
                    type: "post"
                 }
            },
            password: {
            	maxlength: 8,
                minlength: 2,
                required: true
            }
        },
        messages: {
            email: {
                remote: "Email is already in use"
            },
            username: {
                remote: "Username is already in use"
            }
        },
        highlight: function (element) {
            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
        },
        success: function (element) {
            element.text('OK!').addClass('valid').closest('.form-group').removeClass('has-error').addClass('has-success');
        }
    });
});
</script>
