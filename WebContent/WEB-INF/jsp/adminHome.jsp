<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="header.jsp"></jsp:include>
<%
	//allow access only if user is an administrator
	String userType = (String) session.getAttribute("userType");
	if (!userType.equalsIgnoreCase("Administrator")) {
		if (userType.equalsIgnoreCase("Guest"))
			response.sendRedirect("/CyShop/controller/store/login");
		else if (userType.equalsIgnoreCase("Seller"))
			response.sendRedirect("/CyShop/controller/seller/view");
		else if (userType.equalsIgnoreCase("Shopper"))
			response.sendRedirect("/CyShop/controller/shopper/view");
	}
%>
<div class="categories">
	<jsp:include page="categoriesNav.jsp"></jsp:include>
</div>
<div class="container">
	<div class="row">
        <div class="tab-container">
            <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 tab-menu">
              <div class="list-group">
                <a href="#" class="list-group-item active text-center">
                  <h4 class="glyphicon glyphicon-user"></h4><br/>Users
                </a>
                <a href="#" class="list-group-item text-center">
                  <h4 class="glyphicon glyphicon-gift"></h4><br/>Items
                </a>
                <a href="#" class="list-group-item text-center">
                  <h4 class="glyphicon glyphicon-usd"></h4><br/>Transactions
                </a>
                <a href="#" class="list-group-item text-center">
                  <h4 class="glyphicon glyphicon-pencil"></h4><br/>Feedback
                </a>
                <a href="#" class="list-group-item text-center">
                  <h4 class="glyphicon glyphicon-book"></h4><br/>Facts
                </a>
              </div>
            </div>
            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 tab">
                <div class="tab-content active">
					<table id="manageUsersTable" class="table table-striped">
						<thead>
							<tr>
								<th>User</th>
								<th>Email</th>
								<th>User Type</th>
								<th>Creation Date</th>
								<th>Edit Profile</th>
								<th>Logged In</th>
								<th>Delete User</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${allUsers}" var="user">
								<tr>
									<td>${user.username}</td>
									<td>${user.email}</td>
									<td>${user.userType}</td>
									<td><fmt:formatDate value="${user.created}" pattern="MM/dd/yyyy" /></td>
									<td><a href="${pageContext.request.contextPath}/controller/user/profile/${user.userId}"><button 
											class="editUserProfileButton btn btn-xs btn-warning"
											userId="${user.userId}" username="${user.username}"><span class="glyphicon glyphicon-edit"></span></button></a></td>
									<c:choose>
										<c:when test="${user.loggedIn == 1}">
											<td>Yes</td>
										</c:when>
										<c:otherwise>
											<td>No</td>										
										</c:otherwise>
									</c:choose>		
									<td><button class="deleteUserButton btn btn-xs btn-danger" userId="${user.userId}" username="${user.username}"><span class="glyphicon glyphicon-remove"></span></button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
                </div>
                <div class="tab-content">
					<table id="manageItemsTable" class="table table-striped">
						<thead>
							<tr>
								<th>Seller</th>
								<th>Item</th>
								<th>Category</th>
								<th>Quantity</th>
								<th>Cost</th>
								<th>Creation Date</th>
								<th>Edit Post</th>
								<th>Delete Post</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${allItems}" var="item">
								<tr>
									<td>${item.sellerUsername}</td>
									<td>${item.itemName}</td>
									<td>${item.itemType}</td>
									<td>${item.quantity}</td>
									<td>${item.itemCost}</td>
									<td><fmt:formatDate value="${item.created}" pattern="MM/dd/yyyy" /></td>
									<td><a href="${pageContext.request.contextPath}/controller/item/${item.itemId}"
											class="editItemButton btn btn-xs btn-warning"><span class="glyphicon glyphicon-edit"></span></a></td>
									<td><a href="${pageContext.request.contextPath}/controller/item/${item.itemId}/delete?admin=true"
											class="deleteItemButton btn btn-xs btn-danger"><span class="glyphicon glyphicon-remove"></span></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
                </div>
                <div class="tab-content">
					<table id="manageTransactionsTable" class="table table-striped">
						<thead>
							<tr>
								<th>Order ID</th>
								<th>TRX ID</th>
								<th>Seller</th>
								<th>Buyer</th>
								<th>Item</th>
								<th>Cost</th>
								<th>Shipping #</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${allTransactions}" var="transaction">
								<tr>
									<td>${transaction.orderId}</td>
									<td>${transaction.transactionId}</td>
									<td>${transaction.sellerUsername}</td>
									<td>${transaction.shopperUsername}</td>
									<td>${transaction.itemName}</td>
									<td>${transaction.cost}</td>
									<td>${transaction.shippingNumber}</td>
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
								<th>Item ID</th>
								<th>Shopper ID</th>
								<th>Seller ID</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${allFeedback}" var="feedback">
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
									<td>${feedback.itemId}</td>
									<td>${feedback.shopperId}</td>
									<td>${feedback.sellerId}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
                </div>
                <div class="tab-content" align="center">
                	<button id="newFactButton" align="center" class="btn btn-warning">New Fact</button>
						<div class="row" id="newFactDialog"
							style="display: none;" title="Add a new fact:" align="center">
							<div class="form-wrap">
								<h2>Enter a new fact:</h2>
								<hr />
								<input type="text" id="newFact" placeholder="Fact" />
								<input type="submit" id="sendFactButton" class="btn btn-warning" value="Submit Fact" />
								<hr />
							</div>
						</div>
					<table id="manageFactsTable" class="table table-striped">
						<thead>
							<tr>
								<th>ID</th>
								<th>Fact</th>
								<th>Delete</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${allFacts}" var="fact">
								<tr>
									<td>${fact.factId}</td>
									<td>${fact.fact}</td>
									<td><button class="deleteFactButton btn btn-xs btn-danger" factId="${fact.factId}"><span class="glyphicon glyphicon-remove"></span></button></td>
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
	    var userTable = $('#manageUsersTable').dataTable();
	    $('#manageTransactionsTable').dataTable();
	    $('#manageFeedbackTable').dataTable();
	    var factTable = $('#manageFactsTable').dataTable();
		
	    $("div.tab-menu>div.list-group>a").click(function(e) {
	        e.preventDefault();
	        $(this).siblings('a.active').removeClass("active");
	        $(this).addClass("active");
	        var index = $(this).index();
	        $("div.tab>div.tab-content").removeClass("active");
	        $("div.tab>div.tab-content").eq(index).addClass("active");
	    });
	    
		$('.deleteUserButton').click(function() {
			var row = $(this).closest("tr").get(0);
			var userId = $(this).attr("userId");
			var username = $(this).attr("username");
			if (confirm("Are you sure you want to delete " + username + "?")) {
				$.post("/CyShop/controller/admin/deleteUser/" + userId, function() {
					 userTable.fnDeleteRow(userTable.fnGetPosition(row));
				});
			}
		});
		
		$('.deleteFactButton').click(function() {
			var row = $(this).closest("tr").get(0);
			var factId = $(this).attr("factId");
			if (confirm("Are you sure you want to delete this fact?")) {
				$.post("/CyShop/controller/admin/deleteFact/" + factId, function() {
					 factTable.fnDeleteRow(factTable.fnGetPosition(row));
				});
			}
		});
		
		$('#newFactDialog').dialog({minWidth: 400, autoOpen: false});
		$('#newFactButton').click(function() {
			$('#newFactDialog').dialog("open");
		});
		
		$('#sendFactButton').click(function(e) {
					e.preventDefault();
					var fact = $('#newFact').val();
					$.post("${pageContext.request.contextPath}/controller/admin/newFact/" + fact,
						function() {
							$('#newFactDialog').dialog("close");
							alert("The new fact has been added to the database!");
						});
				});
	});
</script>