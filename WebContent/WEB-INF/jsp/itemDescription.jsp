<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="header.jsp"></jsp:include>
<div class="categories">
	<jsp:include page="categoriesNav.jsp"></jsp:include>
</div>
<div class="container item-container">
	<h2 align="center">${item.itemName}</h2>
	<hr />
	<div class="image">
		<c:choose>
			<c:when test="${item.imageSource == null}">
				<img src="${pageContext.request.contextPath}/img/itemPlaceholder.jpg" style="max-width: 100%; max-height: 100%;"
					alt="No image uploaded!" />
			</c:when>
			<c:otherwise>
				<img src="http://proj-309-r14.cs.iastate.edu/CyShopImages/${item.imageSource}" style="max-width: 100%; max-height: 100%;"
					alt="${item.itemName}" />
			</c:otherwise>
		</c:choose>
	</div>
	<div class="rightColumn">
		<input class="item-id" style="display:none;" value="${item.itemId}"/>
		<div>
			<b>${item.itemName}</b>
		by <a href="${pageContext.request.contextPath}/controller/feedback/seller/${item.sellerId}/">${item.sellerUsername != null? item.sellerUsername : "CyShop"}</a></div>
		<div>${item.quantity} in stock</div>
		<div><fmt:formatNumber value="${item.itemCost}" type="currency" currencySymbol="$" pattern="¤ #,##0.00;¤ -#,##0.00"/></div>
		<c:choose>
		<c:when test="${averageRating > 0 && reviews > 0}">
			<div><span class="text-danger">
			<c:if test="${averageRating == 1}">
				&#9734;
			</c:if>
			<c:if test="${averageRating == 2}">
				&#9734;&#9734;
			</c:if>
			<c:if test="${averageRating == 3}">
				&#9734;&#9734;&#9734;
			</c:if>
			<c:if test="${averageRating == 4}">
				&#9734;&#9734;&#9734;&#9734;
			</c:if>
			<c:if test="${averageRating == 5}">
				&#9734;&#9734;&#9734;&#9734;&#9734;
			</c:if></span> (${reviews} customer <a href="${pageContext.request.contextPath}/controller/feedback/item/${item.itemId}/">reviews</a>)</div>
		</c:when>
		<c:otherwise>
			<div>No Reviews</div>
		</c:otherwise>
		</c:choose>
		<div><br />${item.description}</div>
		<div id="buttonContainer">
			<div class="pull-right shopping-cart-label" style="display:none;"><span class="label label-success">Added to cart!</span></div>
			<button class="catalog-add-to-cart-btn btn btn-warning" role="button">Add to Cart</button>
			<% boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
			if (loggedIn) { %>
			<p><br />Have a question? <a href="${pageContext.request.contextPath}/controller/item/ask/${item.itemId}/${item.sellerId}/<%= session.getAttribute("userId") %>">Ask</a> ${item.sellerUsername}!</p>
			<% } %>
		</div>
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
<script>
$(document).ready(function() {
   	var cart = new ShoppingCartManager();
	var items = cart.getMyItems();
    $('.catalog-add-to-cart-btn').bind("click", function() {
    	var userId = '<%=session.getAttribute("userId")%>';
    	var itemId = $(this).closest(".item-container").find(".item-id").val();
    	var itemName = $(this).closest(".item-container").find(".catalog-item-name").val();
    	cart.addItem(itemId);
    	cart.updateBadge(cart.getItemCount());
		var cartLabel = $(this).closest(".item-container").find(".shopping-cart-label");
		cartLabel.show();
		cartLabel.fadeOut(4000);
    });
});
</script>
