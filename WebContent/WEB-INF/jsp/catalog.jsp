<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<style>
@import url(http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css);
.col-item
{
    border: 1px solid #E1E1E1;
    border-radius: 5px;
    background: #FFF;
}
.col-item .photo img
{
    margin: 0 auto;
    width: 100%;
}

.col-item .info
{
    padding: 10px;
    border-radius: 0 0 5px 5px;
    margin-top: 1px;
}

.col-item:hover .info {
    background-color: #F5F5DC;
}
.col-item .price
{
    /*width: 50%;*/
    float: left;
    margin-top: 5px;
}

.col-item .price h5
{
    line-height: 20px;
    margin: 0;
}

.price-text-color
{
    color: #219FD1;
}

.col-item .info .rating
{
    color: #777;
}

.col-item .rating
{
    /*width: 50%;*/
    float: left;
    font-size: 17px;
    text-align: right;
    line-height: 52px;
    margin-bottom: 10px;
    height: 52px;
}

.col-item .separator
{
    border-top: 1px solid #E1E1E1;
}

.clear-left
{
    clear: left;
}

.col-item .separator p
{
    line-height: 20px;
    margin-bottom: 0;
    margin-top: 10px;
    text-align: center;
}

.col-item .separator p i
{
    margin-right: 5px;
}
.col-item .btn-add
{
    width: 50%;
    float: left;
}

.col-item .btn-add
{
    border-right: 1px solid #E1E1E1;
}

.col-item .btn-details
{
    width: 50%;
    float: left;
    padding-left: 10px;
}
.controls
{
    margin-top: 20px;
}
[data-slide="prev"]
{
    margin-right: 10px;
}

</style>
<jsp:include page="header.jsp"></jsp:include>
<div class="categories">
	<jsp:include page="categoriesNav.jsp"></jsp:include>
</div>
<div class="container">
	<c:choose>
		<c:when test="${empty items1}">
			<div class="row">
			<c:set var="itemList" value="${catalogItems}"/>
			<c:choose>
				<c:when test="${empty itemList}">
					<span class="text-danger">No search results found, please try a different search.</span>
				</c:when>
				<c:otherwise>
					<c:if test="${keyword != null}"><span class="text-danger">Search results for '${keyword}'</span></c:if><p></p>
				</c:otherwise>
			</c:choose>
			<c:forEach items="${catalogItems}" var="item">
				<div class="item-container">
					<input class="item-id" style="display:none;" value="${item.itemId}"/>
					<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
						<div class="thumbnail" style="height:275px; position:relative;">
						    <a href="${pageContext.request.contextPath}/controller/item/details/${item.itemId}">
							    <div id="image" align="center">
									<c:choose>
										<c:when test="${item.imageSource == null}">
											<img src="${pageContext.request.contextPath}/img/itemPlaceholder.jpg" style="max-width:150px; max-height:150px" alt="No image uploaded!" />
										</c:when>
										<c:otherwise>
											<img src="http://proj-309-r14.cs.iastate.edu/CyShopImages/${item.imageSource}" style="max-width:150px; max-height:150px" alt="${item.itemName}" />
										</c:otherwise>
									</c:choose>
								</div>
							</a>
							<div class="caption" style="position:absolute; bottom:0; left:0; right:0">
								<div id="item-name">
									<a class="catalog-item-name" href="${pageContext.request.contextPath}/controller/item/details/${item.itemId}">${item.itemName}</a>
									<div class="pull-right shopping-cart-label" style="display:none;"><span class="label label-success">Added to cart!</span></div>
								</div>
								<div id="item-price">
									<strong><fmt:formatNumber value="${item.itemCost}" type="currency" currencySymbol="$" pattern="¤ #,##0.00;¤ -#,##0.00"/></strong>
								</div>
								<div id="item-details">
									<a class="pull-right text-warning" href="${pageContext.request.contextPath}/controller/item/details/${item.itemId}">See product details</a>
								</div>
								<button class="catalog-add-to-cart-btn btn btn-danger btn-lg btn-block" role="button">Add to Cart</button> 
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		</c:when>
		<c:otherwise>
		<div class="row">
            <div class="col-md-9">
                <h3>${carouselHeader}</h3>
            </div>
            <div class="col-md-3">
                <!-- Controls -->
                <div class="controls pull-right hidden-xs">
                    <a class="left fa fa-chevron-left btn btn-warning" href="#item-carousel"
                        data-slide="prev"></a><a class="right fa fa-chevron-right btn btn-warning" href="#item-carousel"
                            data-slide="next"></a>
                </div>
            </div>
        </div>
        <div id="item-carousel" class="carousel slide hidden-xs" data-ride="carousel">
            <!-- Wrapper for slides -->
            <div class="carousel-inner">
                <div class="item active">
                    <div class="row">
                    	<c:forEach items="${items1}" var="item">
							<div class="item-container">	
								<input class="item-id" style="display:none;" value="${item.itemId}"/>
								<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
									<div class="thumbnail" style="height:275px; position:relative;">
									    <a href="${pageContext.request.contextPath}/controller/item/details/${item.itemId}">
										    <div id="image" align="center">
												<c:choose>
													<c:when test="${item.imageSource == null}">
														<img src="${pageContext.request.contextPath}/img/itemPlaceholder.jpg" style="max-width:150px; max-height:150px" alt="No image uploaded!" />
													</c:when>
													<c:otherwise>
														<img src="http://proj-309-r14.cs.iastate.edu/CyShopImages/${item.imageSource}" style="max-width:150px; max-height:150px" alt="${item.itemName}" />
													</c:otherwise>
												</c:choose>
											</div>
										</a>
										<div class="caption" style="position:absolute; bottom:0; left:0; right:0">
											<div id="item-name">
												<a class="catalog-item-name" href="${pageContext.request.contextPath}/controller/item/details/${item.itemId}">${item.itemName}</a>
												<div class="pull-right shopping-cart-label" style="display:none;"><span class="label label-success">Added to cart!</span></div>
											</div>
											<div id="item-price">
												<strong><fmt:formatNumber value="${item.itemCost}" type="currency" currencySymbol="$" pattern="¤ #,##0.00;¤ -#,##0.00"/></strong>
											</div>
											<div id="item-details">
												<a class="pull-right text-warning" href="${pageContext.request.contextPath}/controller/item/details/${item.itemId}">See product details</a>
											</div>
											<button class="catalog-add-to-cart-btn btn btn-danger btn-lg btn-block" role="button">Add to Cart</button> 
										</div>
									</div>
								</div>
							</div>
                    	</c:forEach>
                	</div>
             	</div>
             	<div class="item">
                    <div class="row">
                    	<c:forEach items="${items2}" var="item">
							<div class="item-container">	
								<input class="item-id" style="display:none;" value="${item.itemId}"/>
								<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
									<div class="thumbnail" style="height:275px; position:relative;">
									    <a href="${pageContext.request.contextPath}/controller/item/details/${item.itemId}">
										    <div id="image" align="center">
												<c:choose>
													<c:when test="${item.imageSource == null}">
														<img src="${pageContext.request.contextPath}/img/itemPlaceholder.jpg" style="max-width:150px; max-height:150px" alt="No image uploaded!" />
													</c:when>
													<c:otherwise>
														<img src="http://proj-309-r14.cs.iastate.edu/CyShopImages/${item.imageSource}" style="max-width:150px; max-height:150px" alt="${item.itemName}" />
													</c:otherwise>
												</c:choose>
											</div>
										</a>
										<div class="caption" style="position:absolute; bottom:0; left:0; right:0">
											<div id="item-name">
												<a class="catalog-item-name" href="${pageContext.request.contextPath}/controller/item/details/${item.itemId}">${item.itemName}</a>
												<div class="pull-right shopping-cart-label" style="display:none;"><span class="label label-success">Added to cart!</span></div>
											</div>
											<div id="item-price">
												<strong><fmt:formatNumber value="${item.itemCost}" type="currency" currencySymbol="$" pattern="¤ #,##0.00;¤ -#,##0.00"/></strong>
											</div>
											<div id="item-details">
												<a class="pull-right text-warning" href="${pageContext.request.contextPath}/controller/item/details/${item.itemId}">See product details</a>
											</div>
											<button class="catalog-add-to-cart-btn btn btn-danger btn-lg btn-block" role="button">Add to Cart</button> 
										</div>
									</div>
								</div>
							</div>
                    	</c:forEach>
                   	</div>
                </div>
	            <div class="item">
                    <div class="row">
                    	<c:forEach items="${items3}" var="item">
							<div class="item-container">	
								<input class="item-id" style="display:none;" value="${item.itemId}"/>
								<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
									<div class="thumbnail" style="height:275px; position:relative;">
									    <a href="${pageContext.request.contextPath}/controller/item/details/${item.itemId}">
										    <div id="image" align="center">
												<c:choose>
													<c:when test="${item.imageSource == null}">
														<img src="${pageContext.request.contextPath}/img/itemPlaceholder.jpg" style="max-width:150px; max-height:150px" alt="No image uploaded!" />
													</c:when>
													<c:otherwise>
														<img src="http://proj-309-r14.cs.iastate.edu/CyShopImages/${item.imageSource}" style="max-width:150px; max-height:150px" alt="${item.itemName}" />
													</c:otherwise>
												</c:choose>
											</div>
										</a>
										<div class="caption" style="position:absolute; bottom:0; left:0; right:0">
											<div id="item-name">
												<a class="catalog-item-name" href="${pageContext.request.contextPath}/controller/item/details/${item.itemId}">${item.itemName}</a>
												<div class="pull-right shopping-cart-label" style="display:none;"><span class="label label-success">Added to cart!</span></div>
											</div>
											<div id="item-price">
												<strong><fmt:formatNumber value="${item.itemCost}" type="currency" currencySymbol="$" pattern="¤ #,##0.00;¤ -#,##0.00"/></strong>
											</div>
											<div id="item-details">
												<a class="pull-right text-warning" href="${pageContext.request.contextPath}/controller/item/details/${item.itemId}">See product details</a>
											</div>
											<button class="catalog-add-to-cart-btn btn btn-danger btn-lg btn-block" role="button">Add to Cart</button> 
										</div>
									</div>
								</div>
							</div>
                    	</c:forEach>
                	</div>
                </div>
            </div>
    </div>
		</c:otherwise>
	</c:choose>
</div>
<jsp:include page="footer.jsp"></jsp:include>
<script>
$(document).ready(function() {
   	var cart = new ShoppingCartManager();
	var items = cart.getMyItems();
    $('.catalog-add-to-cart-btn').bind("click", function() {
    	var userId = '<%= session.getAttribute("userId") %>';
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