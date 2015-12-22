<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="header.jsp"></jsp:include>
<div class="categories">
	<jsp:include page="categoriesNav.jsp"></jsp:include>
</div>
<div class="container">
	<c:choose>
		<c:when test="${item.itemId == 0}">
			<h2 align="center">Add New Item</h2>
		</c:when>
		<c:otherwise>
			<h2 align="center">Edit Item</h2>
		</c:otherwise>
	</c:choose>
	<hr />
	<br />
	<div style="float: none; margin: 0 auto; text-align: center;">
	<form:form id="addEditItemForm" class="form-horizontal" commandName="item" method="POST" action="${pageContext.request.contextPath}/controller/item/${item.itemId}" enctype="multipart/form-data">
		<form:hidden path="sellerId"/>
		<form:hidden path="sellerUsername"/>
		<div class="form-group">
			<div class="col-sm-4 col-md-4 col-lg-4">
				<div id="item-picture-div" class="form-group" >
					<img id="item-picture" style="max-width:100%; max-height:100%;" src="http://proj-309-r14.cs.iastate.edu/CyShopImages/${item.imageSource}" alt="The Item" />
				</div>
				<div class="form-group">
					<label for="item-picture">Upload a picture of your item!</label> <input type="file" id="item-picture" name="fileUpload" onchange='readURL(this);'>
				</div>
			</div>
			<div class="col-sm-8 col-md-8 col-lg-8">
				<div class="form-group">
					<div class="col-sm-2 col-md-2 col-lg-2">
						<label for="itemName">Name</label>
					</div>
				 	<div class="col-sm-7 col-md-7 col-lg-7">
						<form:input path="itemName" cssClass="form-control" placeholder="Item Name" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-2 col-md-2 col-lg-2">
						<label for="description">Description</label>
					</div>
				 	<div class="col-sm-7 col-md-7 col-lg-7">
						<!--<form:input path="description" cssClass="form-control" placeholder="Description" />-->
						<form:textarea cssClass="form-control" path="description" rows="5" form="addEditItemForm" placeholder="Description" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-2 col-md-2 col-lg-2">
						<label for="itemType">Category</label>
					</div>
				 	<div class="col-sm-7 col-md-7 col-lg-7">
						<form:select cssClass="form-control" path="itemType">
							<form:option value="automotive">Automotive</form:option>
							<form:option value="children">Children & Toys</form:option>
							<form:option value="clothing">Clothing & Apparel</form:option>
							<form:option value="electronics">Electronics & Computers</form:option>
							<form:option value="entertainment">Entertainment</form:option>
							<form:option value="homeAndGarden">Home & Garden</form:option>
							<form:option value="sports">Sports & Outdoors</form:option>
							<form:option value="isu">Iowa State</form:option>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-2 col-md-2 col-lg-2">
						<label for="itemCost">Cost</label>
					</div>
				 	<div class="col-sm-4 col-md-4 col-lg-4">
				 		<div class="input-group">
					 		<div class="input-group-addon"><span class="glyphicon glyphicon-usd"></span></div>
							<form:input type="number" step="0.01" path="itemCost" cssClass="form-control" placeholder="Item Cost" />
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-2 col-md-2 col-lg-2">
						<label for="quantity">Quantity</label>
					</div>
				 	<div class="col-sm-3 col-md-3 col-lg-3">
						<form:input type="number" path="quantity" cssClass="form-control" placeholder="Quantity" />
					</div>
				</div>	
			</div>
		</div>
		<div id="overlay" onclick="hideDIVs()"></div>
  		<div id="alert">Updating item...</div>
		<button type="submit" class="btn btn-warning" onclick="showDIVs()">Save</button>
				<a href="${pageContext.request.contextPath}/controller/seller/view" class="btn btn-warning">Cancel</a>
	</form:form></div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
<script>
function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#item-picture')
                .attr('src', e.target.result)
                .maxWidth(150)
                .maxHeight(150);
        };
        reader.readAsDataURL(input.files[0]);
        $('#item-picture-div').show();
    }
}
</script>