<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="header.jsp"></jsp:include>
<div class="categories">
	<jsp:include page="categoriesNav.jsp"></jsp:include>
</div>
<div class="container">
	<h2 align="center">Order Confirmation</h2>
	<hr />
	<div class="stepwizard">
	    <div class="stepwizard-row setup-panel">
	        <div class="stepwizard-step">
	            <a href="#step-1" type="button" class="btn btn-warning btn-circle" disabled="disabled">1</a>
	            <p>Shopping Cart</p>
	        </div>
	        <div class="stepwizard-step">
	            <a href="#step-2" type="button" class="btn btn-warning btn-circle" disabled="disabled">2</a>
	            <p>Checkout</p>
	        </div>
	        <div class="stepwizard-step">
	            <a href="#step-3" type="button" class="btn btn-danger btn-circle">3</a>
	            <p><b>Confirmation</b></p>
	        </div>
	    </div>
	</div>
</div>
<div class="container">
	<h3>You have successfully purchased items!</h3>
	<h5><em>Review your order below</em></h5>
	<table id="cart-items" class="table table-striped">
		<thead>
	  		<tr>
	  			<th style="width:10em">Name</th>
	  			<th style="width:10em">Description</th>
	  			<th style="width:10em">Cost</th>
	  		</tr>
	  	</thead>
	  	<tbody></tbody>
	</table>
</div>	
<jsp:include page="footer.jsp"></jsp:include>
<script>
var cart = new ShoppingCartManager();
var count = cart.getItemCount();
var items = cart.getMyItems();
var itemsList = [];
for (var itemId in items) {
	itemsList.push(itemId);
}
var prom = cart.getItem(itemsList);
prom.then(function(data) {
	$.each(data, function(index) {
		item = data[index];
		$('#cart-items').fadeIn();
		$('#your-items-loading').fadeOut();
		var row = '<tr style="text-align:left"><td>'+item.itemName+' sold by '+item.sellerUsername+'</td><td>'+item.description+'</td><td>'+item.itemCost+'</td></tr>';
		$('#cart-items tbody').prepend(row);
	});
});
for (var itemId in items) {
	cart.deleteItem(itemId);
}
</script>