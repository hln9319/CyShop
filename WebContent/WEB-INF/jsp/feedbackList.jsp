<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:include page="header.jsp"></jsp:include>
<div class="categories">
	<jsp:include page="categoriesNav.jsp"></jsp:include>
</div>
<div class="container">
	<div class="row" style="text-align: center;">
		<div class="tab-container">
			<c:if test="${type == 'item'}">
				<h2>Feedback for ${type} : ${name.itemName}</h2>
			</c:if>
			<c:if test="${type != 'item'}">
				<h2>Feedback for ${type} : ${name.username}</h2>
			</c:if>
			<c:choose>
				<c:when test="${type != 'item' && name.imageSource != null}">
					<img id="item-picture"
						style="max-width: 150px; max-height: 150px;"
						src="http://proj-309-r14.cs.iastate.edu/CyShopUserImages/${name.imageSource}"
						alt="Profile Picture" />
				</c:when>
				<c:when test="${type == 'item' && name.imageSource != null}">
					<img id="item-picture"
						style="max-width: 150px; max-height: 150px;"
						src="http://proj-309-r14.cs.iastate.edu/CyShopImages/${name.imageSource}"
						alt="Profile Picture" />
				</c:when>
				<c:otherwise>
					<img
						src="${pageContext.request.contextPath}/img/itemPlaceholder.jpg"
						style="max-width: 150px; max-height: 150px;"
						alt="No image uploaded!" />
				</c:otherwise>
			</c:choose>
			<c:if test="${averageRating != null && reviews != null}">
				<h4>
					Average <span class="text-danger"> <c:if
							test="${averageRating == 1}">
											&#9734;
										</c:if> <c:if test="${averageRating == 2}">
											&#9734;&#9734;
										</c:if> <c:if test="${averageRating == 3}">
											&#9734;&#9734;&#9734;
										</c:if> <c:if test="${averageRating == 4}">
											&#9734;&#9734;&#9734;&#9734;
										</c:if> <c:if test="${averageRating == 5}">
											&#9734;&#9734;&#9734;&#9734;&#9734;
										</c:if>
					</span> from ${reviews} reviews
				</h4>
			</c:if>
			<table id="manageFeedbackTable" class="table table-striped">
				<thead>
					<tr>
						<c:choose>
							<c:when test="${type=='seller'}">
								<th>Product</th>
								<th>Shopper</th>
							</c:when>
							<c:when test="${type=='shopper'}">
								<th>Product</th>
								<th>Seller</th>
							</c:when>
							<c:otherwise>
								<th>Shopper</th>
							</c:otherwise>
						</c:choose>
						<th>Rating</th>
						<th>Comment</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${feedback}" var="feedback">
						<tr>
							<c:choose>
								<c:when test="${type=='seller'}">
									<td><a
										href="${pageContext.request.contextPath}/controller/item/details/${feedback.itemId}">${feedback.itemName}</a></td>
									<td><a
										href="${pageContext.request.contextPath}/controller/feedback/shopper/${feedback.shopperId}/">${feedback.shopperUsername}</a></td>
								</c:when>
								<c:when test="${type=='shopper'}">
									<td><a
										href="${pageContext.request.contextPath}/controller/item/details/${feedback.itemId}">${feedback.itemName}</a></td>
									<td><a
										href="${pageContext.request.contextPath}/controller/feedback/seller/${feedback.sellerId}/">${feedback.sellerUsername}</a></td>
								</c:when>
								<c:otherwise>
									<td><a
										href="${pageContext.request.contextPath}/controller/feedback/shopper/${feedback.shopperId}/">${feedback.shopperUsername}</a></td>
								</c:otherwise>
							</c:choose>
							<td><span class="text-danger"> <c:if
										test="${feedback.rating == 1}">
											&#9734;
										</c:if> <c:if test="${feedback.rating == 2}">
											&#9734;&#9734;
										</c:if> <c:if test="${feedback.rating == 3}">
											&#9734;&#9734;&#9734;
										</c:if> <c:if test="${feedback.rating == 4}">
											&#9734;&#9734;&#9734;&#9734;
										</c:if> <c:if test="${feedback.rating == 5}">
											&#9734;&#9734;&#9734;&#9734;&#9734;
										</c:if>
							</span></td>
							<td>${feedback.comment}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<br />
		<button onclick="goBack()" class="btn btn-warning">Go Back</button>
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
<script>
	function goBack() {
		window.history.back();
	}
</script>