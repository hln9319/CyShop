<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:include page="header.jsp"></jsp:include>
<div class="categories">
	<jsp:include page="categoriesNav.jsp"></jsp:include>
</div>
<div class="container">
	<div class="row">
		<h2 align="center">${user.username}'s Profile</h2>
		<hr />
		<div align="center">
			<button onclick="goBack()" class="btn btn-warning">Go Back</button>
		</div>
		<br />
		<c:choose>
			<c:when test="${user.imageSource != null}">
				<img id="item-picture"
					style="max-width: 50%; max-height: 50%;"
					src="http://proj-309-r14.cs.iastate.edu/CyShopUserImages/${user.imageSource}"
					alt="Profile Picture" />
			</c:when>
			<c:otherwise>
				<img
					src="${pageContext.request.contextPath}/img/itemPlaceholder.jpg"
					style="max-width: 50%; max-height: 50%;"
					alt="No image uploaded!" />
			</c:otherwise>
		</c:choose>
		<p><h4 class="text-danger">Contact</h4>
			<p><b>Phone</b>: ${user.phoneNumber}<br /><b>Email</b>: ${user.email}</p>
		<p><h4 class="text-danger">Interests</h4></p>
		<p>${user.interests}</p>
		<!-- 
		<h4 class="text-danger">Feedback as a Shopper</h4>
		<table id="shopperFeedback" class="table table-striped">
			<thead>
				<tr>
					<th>Product</th>
					<th>Seller</th>
					<th>Rating</th>
					<th>Comment</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${shopperFeedback}" var="shopperFeedback">
					<tr>
						<td>${shopperFeedback.itemName}</td>
						<td>${shopperFeedback.sellerUsername}</td>
						<td><span class="text-danger"> <c:if
									test="${shopperFeedback.rating == 1}">
											&#9734;
										</c:if> <c:if test="${shopperFeedback.rating == 2}">
											&#9734;&#9734;
										</c:if> <c:if test="${shopperFeedback.rating == 3}">
											&#9734;&#9734;&#9734;
										</c:if> <c:if test="${shopperFeedback.rating == 4}">
											&#9734;&#9734;&#9734;&#9734;
										</c:if> <c:if test="${shopperFeedback.rating == 5}">
											&#9734;&#9734;&#9734;&#9734;&#9734;
										</c:if>
						</span></td>
						<td>${shopperFeedback.comment}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<h4 class="text-danger">Feedback as a Seller</h4>
		<table id="sellerFeedback" class="table table-striped">
			<thead>
				<tr>
					<th>Product</th>
					<th>Shopper</th>
					<th>Rating</th>
					<th>Comment</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${sellerFeedback}" var="sellerFeedback">
					<tr>
						<td>${sellerFeedback.itemName}</td>
						<td>${sellerFeedback.sellerUsername}</td>
						<td><span class="text-danger"> <c:if
									test="${sellerFeedback.rating == 1}">
											&#9734;
										</c:if> <c:if test="${sellerFeedback.rating == 2}">
											&#9734;&#9734;
										</c:if> <c:if test="${sellerFeedback.rating == 3}">
											&#9734;&#9734;&#9734;
										</c:if> <c:if test="${sellerFeedback.rating == 4}">
											&#9734;&#9734;&#9734;&#9734;
										</c:if> <c:if test="${sellerFeedback.rating == 5}">
											&#9734;&#9734;&#9734;&#9734;&#9734;
										</c:if>
						</span></td>
						<td>${sellerFeedback.comment}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		 -->
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
<script>
	function goBack() {
		window.history.back();
	}
</script>