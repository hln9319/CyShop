<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="header.jsp"></jsp:include>
<%
	String userType = (String) session.getAttribute("userType");
	if (!userType.equalsIgnoreCase("Shopper")) {
		if (userType.equalsIgnoreCase("Guest"))
			response.sendRedirect("/CyShop/controller/store/login");
	}
%>
<div class="categories">
	<jsp:include page="categoriesNav.jsp"></jsp:include>
</div>
<div class="container">
	<div class="row" style="text-align: center;">
		<div class="tab-container">
			<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 tab-menu">
				<div class="list-group">
					<a href="#" class="list-group-item text-center active">
						<h4 class="glyphicon glyphicon-gift active"></h4>
						<br />My Purchases
					</a> <a href="#" class="list-group-item text-center">
						<h4 class="glyphicon glyphicon-usd"></h4>
						<br />My Orders
					</a> <a href="#" class="list-group-item text-center">
						<h4 class="glyphicon glyphicon-pencil"></h4>
						<br />Reviews I've Given
					</a>
				</div>
			</div>
			<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 tab">
				<div class="tab-content active">
					<table id="manageItemsTable" class="table table-striped">
						<thead>
							<tr>
								<th>Picture</th>
								<th>Item</th>
								<th>Category</th>
								<th>Cost</th>
								<th>Seller</th>
								<th>Description</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${purchases}" var="item">
								<tr>
									<td><img src="http://proj-309-r14.cs.iastate.edu/CyShopImages/${item.imageSource}" style="max-width: 100px; max-height: 100px;" alt="${item.itemName}" /></td>
									<td>${item.itemName}</td>
									<td>${item.itemType}</td>
									<td>${item.itemCost}</td>
									<td>${item.sellerUsername}</td>
									<td>${item.description}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="tab-content">
						<table id="manageTransactionsTable" class="table table-striped">
							<thead>
								<tr>
									<th>Order Id</th>
									<th>Transaction ID</th>
									<th>Seller</th>
									<th>Item</th>
									<th>Cost</th>
									<th>Shipping #</th>
									<th>Review</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${shopperTransactions}" var="transaction">
									<tr>
										<td>${transaction.orderId}</td>
										<td>${transaction.transactionId}</td>
										<td>${transaction.sellerUsername}</td>
										<td>${transaction.itemName}</td>
										<td>${transaction.cost}</td>
										<td>${transaction.shippingNumber}</td>
										<c:choose>
											<c:when test = "${transaction.shopperRated}">
												<td>Given</td>
											</c:when>
											<c:otherwise>
												<td><a href="${pageContext.request.contextPath}/controller/feedback/newFeedback/${transaction.transactionId}"><button 
												class="editUserProfileButton btn btn-xs btn-warning"><span class="glyphicon glyphicon-edit"></span></button></a></td>										
											</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
							</tbody>
						</table>
				</div>
				<div class="tab-content">
					<table id="manageFeedbackTable" class="table table-striped">
						<thead>
							<tr>
								<th>Rating</th>
								<th>Comment</th>
								<th>Item</th>
								<th>Seller</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${shopperFeedback}" var="feedback">
								<tr>
									<td><span class="text-danger">
										<c:if test="${feedback.rating == 1}">
											&#9734;
										</c:if>
										<c:if test="${feedback.rating == 2}">
											&#9734;&#9734;
										</c:if>
										<c:if test="${feedback.rating == 3}">
											&#9734;&#9734;&#9734;
										</c:if>
										<c:if test="${feedback.rating == 4}">
											&#9734;&#9734;&#9734;&#9734;
										</c:if>
										<c:if test="${feedback.rating == 5}">
											&#9734;&#9734;&#9734;&#9734;&#9734;
										</c:if>
									</span></td>
									<td>${feedback.comment}</td>
									<td>${feedback.itemName}</td>
									<td>${feedback.sellerUsername}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
<script>
	$(document).ready(function() {
		$('.collapse').collapse("hide");
		$('#manageItemsTable').dataTable();
		$('#manageTransactionsTable').dataTable();
		$('#manageFeedbackTable').dataTable();

		$("div.tab-menu>div.list-group>a").click(function(e) {
			e.preventDefault();
			$(this).siblings('a.active').removeClass("active");
			$(this).addClass("active");
			var index = $(this).index();
			$("div.tab>div.tab-content").removeClass("active");
			$("div.tab>div.tab-content").eq(index).addClass("active");
		});
	});
</script>