<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="header.jsp"></jsp:include>
<div class="categories">
	<jsp:include page="categoriesNav.jsp"></jsp:include>
</div>
<style>
	.form-group {
		max-width:75%; 
		max-height:75%;
	}
</style>
<form:form id="profileForm" class="form-horizontal" commandName="user" method="POST" action="${pageContext.request.contextPath}/controller/user/editProfile/${user.userId}" enctype="multipart/form-data">
	<section id="userProfile">
		<div class="container">
			<div class="row">
				<div class="col-xs-12" style="float: none; margin: 0 auto; text-align: center;">
					<div class="form-wrap">
						<h2>Profile: ${user.username}</h2>
						<hr />
						<input type="submit" class="btn btn-warning" value="Update Profile"  onclick="showDIVs()"/>
						<br />
						<div id="overlay" onclick="hideDIVs()"></div>
  						<div id="alert">Updating profile...</div>
  						<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6" align="center">
							<h4 class="text-danger">Picture &amp; Interests</h4>
							<div class="form-group" align="center">
								<img id="item-picture" style="max-width:75%; max-height:75%;" src="http://proj-309-r14.cs.iastate.edu/CyShopUserImages/${user.imageSource}" alt="Profile Picture" />
							</div>
							<div class="form-group" align="center">
								<label>Profile Picture</label>
								<input type="file" name="fileUpload" />
							</div>
							<div class="form-group">
								<label for="interests" class="sr-only">Interests</label>
								<form:textarea cssClass="form-control" path="interests" rows="5" form="profileForm" placeholder="Interests" value="${user.interests}" />
							</div>
						<h4 class="text-danger">My Account Information</h4>
						<table id="userPersonalInfoTable" class="table table-striped">
							<tr>
								<td><b>Username</b></td>
								<td>${user.username}</td>
							</tr>
							<tr>
								<td><b>Account Type</b></td>
								<td>${user.userType}</td>
							</tr>
							<tr>
								<td><b>CyShopper Since</b></td>
								<td><fmt:formatDate value="${user.created}" pattern="MM/dd/yyyy" /></td>
							</tr>
						</table>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6" align="center">
							<h4 class="text-danger">My Personal Information</h4>
							<div class="form-group">
								<label for="firstName" class="sr-only">First Name</label>
								<form:input path="firstName" cssClass="form-control" placeholder="First Name" value="${user.firstName}" />
							</div>
							<div class="form-group">
								<label for="lastName" class="sr-only">Last Name</label>
								<form:input path="lastName" cssClass="form-control" placeholder="Last Name" value="${user.lastName}" />
							</div>
							<div class="form-group">
								<label for="email" class="sr-only">Email</label>
								<form:input path="email" cssClass="form-control" placeholder="Email" value="${user.email}" />
							</div>
							<div class="form-group">
								<label for="phoneNumber" class="sr-only">Phone Number</label>
								<form:input path="phoneNumber" cssClass="form-control" placeholder="Phone Number" value="${user.phoneNumber}" />
							</div>
							<div class="form-group">
								<label for="street" class="sr-only">Street</label>
								<form:input path="street" cssClass="form-control" placeholder="Street" value="${user.street}" />
							</div>
							<div class="form-group">
								<label for="city" class="sr-only">City</label>
								<form:input path="city" cssClass="form-control" placeholder="City" value="${user.city}" />
							</div>
							<div class="form-group">
								<label for="state" class="sr-only">State</label>
								<form:input path="state" cssClass="form-control" placeholder="State" value="${user.state}" />
							</div>
							<div class="form-group">
								<label for="zipCode" class="sr-only">Zip Code</label>
								<form:input path="zipCode" cssClass="form-control" placeholder="Zip Code" value="${user.zipCode}" />
							</div>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6" align="center">
							<h4 class="text-danger">My Payment Method</h4>
							<div class="form-group">
								<label for="cardHolder" class="sr-only">Card Holder</label>
								<form:input path="cardHolder" cssClass="form-control" placeholder="Card Holder Name" value="${user.cardHolder}" />
							</div>
							<div class="form-group">
								<label for="cardNumber" class="sr-only">Card Number</label>
								<form:input path="cardNumber" cssClass="form-control" placeholder="Card Number" value="${user.cardNumber}" />
							</div>
							<div class="form-group">
								<label for="cardType" class="sr-only">Card Type</label>
								<form:select path="cardType" cssClass="form-control" placeholder="Card Type">
									<form:option value="${user.cardType}">${user.cardType}</form:option>
									<form:option value="Visa">Visa</form:option>
									<form:option value="MasterCard">MasterCard</form:option>
									<form:option value="Discover">Discover</form:option>
									<form:option value="American Express">American Express</form:option>
								</form:select>
							</div>
							<div class="form-group">
								<label for="cardExpireDate" class="sr-only">Expire Date</label>
								<fmt:formatDate value="${user.cardExpireDate}" type="date" pattern="MM/dd/yyyy" var="theFormattedDate" />
								<form:input path="cardExpireDate" cssClass="form-control" placeholder="Expire Date" value="${theFormattedDate}" />
							</div>
						</div>
						<hr />
					</div>
				</div>
			</div>
		</div>
	</section>
</form:form>
<jsp:include page="footer.jsp"></jsp:include>