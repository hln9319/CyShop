<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<style>
body {margin-top: 20px;}
</style>
<jsp:include page="header.jsp"></jsp:include>
<div class="categories">
	<jsp:include page="categoriesNav.jsp"></jsp:include>
</div>
<div class="container">
	<h2 align="center">Shopping Cart</h2>
	<hr />
	<div class="stepwizard">
	    <div class="stepwizard-row setup-panel">
	        <div class="stepwizard-step">
	            <a href="#step-1" type="button" class="btn btn-danger btn-circle">1</a>
	            <p><b>Shopping Cart</b></p>
	        </div>
	        <div class="stepwizard-step">
	            <a href="#step-2" type="button" class="btn btn-warning btn-circle" disabled="disabled">2</a>
	            <p>Checkout</p>
	        </div>
	        <div class="stepwizard-step">
	            <a href="#step-3" type="button" class="btn btn-warning btn-circle" disabled="disabled">3</a>
	            <p>Confirmation</p>
	        </div>
	    </div>
	</div>
    <div class="row">
        <div class="col-sm-12 col-md-10 col-md-offset-1">
            <table id="cart-items" class="table table-hover">
                <thead>
                	<tr>
                		<th style="text-align:left;">Product</th>
                    	<th>Quantity</th>
                    	<th>Price</th>
                    	<th>Total</th>
                    	<th><button id="empty-cart" class="btn btn-sm btn-warning">Clear All</button></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>   </td>
                        <td>   </td>
                        <td>   </td>
                        <td><h5>Subtotal</h5></td>
                        <td class="text-right"><h5><strong><span id="subtotal">$0.00</span></strong></h5></td>
                    </tr>
                    <tr>
                        <td>   </td>
                        <td>   </td>
                        <td>   </td>
                        <td><h5>Shipping</h5></td>
                        <td class="text-right"><h5><strong>$5.00 (Flat Rate)</strong></h5></td>
                    </tr>
                    <tr>
                        <td>   </td>
                        <td>   </td>
                        <td>   </td>
                        <td><h3>Total</h3></td>
                        <td class="text-right"><h3><strong><span id="total">$0.00</span></strong></h3></td>
                    </tr>
                    <tr>
                        <td>   </td>
                        <td>   </td>
                        <td>   </td>
                        <td>
                        	<a href="${pageContext.request.contextPath}/controller/store/view" class="btn btn-warning"><span class="glyphicon glyphicon-shopping-cart"></span> Continue Shopping</a>
                       	</td>
                        <td>
                        <a id="checkout-link" href="${pageContext.request.contextPath}/controller/cart/checkout" class="btn btn-danger">Checkout <span class="glyphicon glyphicon-play"></span></a>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
<script>
$(document).ready(function(){
	var cart = new ShoppingCartManager();
	var count = cart.getItemCount();
	if (count > 0) {
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
				var row = '<tr id="tr-'+item.itemId+'" class="rows-to-remove">' +
					'<input class="item-cost-value" type="hidden" value="'+item.itemCost+'"/>' +
					'<input class="new-item-cost-value" type="hidden" value="'+item.itemCost+'"/>' +
					'<td id="row" class="col-sm-8 col-md-6">' +
					'<div class="media">' +
						'<a class="thumbnail pull-left" href="${pageContext.request.contextPath}/controller/item/details/'+item.itemId+'"> <img src="http://proj-309-r14.cs.iastate.edu/CyShopImages/'+item.imageSource+'" style="max-width:72px; max-height:72px" alt="'+item.itemName+'" /></a>' +
						'<div class="pull-left">' +
							'<h4><a class="pull-left" href="${pageContext.request.contextPath}/controller/item/details/'+item.itemId+'">'+$.trim(item.itemName)+'</a></h4>' +
							'<div class="pull-left">'+item.description+'</div>' +
						'</div>' +
                    '</div></td>' +
					'<td class="col-sm-1 col-md-1" style="text-align: center">' +
						'<input id="'+item.itemId+'" min="1" max="'+item.quantity+'" type="number" class="quantity form-control" id="'+item.itemId+'" value="1">' +
					'</td>' +
					'<td class="col-sm-1 col-md-1 text-center"><strong>$'+item.itemCost+'</strong></td>' +
					'<td class="col-md-1 text-center"><span id="item-cost-display"><strong>$'+item.itemCost+'</strong></span></td>' +
					'<td class="col-md-1">' +
					'<button id="'+item.itemId+'" type="button" class="btn btn-danger btn-sm remove-item"><span class="glyphicon glyphicon-remove"></span> Remove</button></td>' +
				'</tr>';
				$('#cart-items tbody').prepend(row);
				var itemId = item.itemId;
				var userQuantity = $('#' + itemId).val();
				cart.updateItem(itemId, userQuantity);
			});
		updateTotals();
		
		});
	} else {
		$('#your-items-loading').hide();
		$('#cart-items tbody').prepend('<p style="text-align:center; padding: 10px; margin:auto 0;"><em>Sorry, you currently have no items in the cart.</em></p>');
	}
	$("#empty-cart").bind("click", function(e) {
        if(!confirm('Are you sure that you want to clear your shopping cart?')) { 
            return e.preventDefault();
        }
		cart.clearLocalStorage();
		$('.rows-to-remove').empty();
		$('#cart-items tbody').prepend('<p style="text-align:center;"><em>Sorry, you currently have no items in the cart.</em></p>');
		cart.updateBadge("0");
		updateTotals();
	});
	$(document).on('click', '.remove-item', function() {
		var itemId = $(this).attr('id');
		var rowToRemove = '#tr-'+itemId;
		cart.deleteItem(itemId);
		cart.updateBadge(cart.getItemCount());
		$(rowToRemove).remove();
		updateTotals();
	});
	$(document).on('change', '.quantity', function() {
		var quantity = $(this).val();
		
		var itemId = $(this).attr('id');
		var totalElementValue = $(this).closest('tr').find('.item-cost-value').val();
		var newItemCostValue = $(this).closest('tr').find('.new-item-cost-value');
		var totalElementDisplay = $(this).closest('tr').find('#item-cost-display');
		var itemTotal = totalElementValue * quantity;
		$(totalElementDisplay).html('<b>$'+itemTotal+'</b>');
		$(newItemCostValue).val(itemTotal);
		updateTotals();
		cart.updateItem(itemId, quantity);
	});
	function updateTotals() {
		var subtotal = 0;
		$('.new-item-cost-value').each(function() {
			subtotal += parseFloat($(this).val());
		});
		$('#subtotal').text('$'+subtotal.toFixed(2));
		var total = subtotal + 5;
		$('#total').text('$'+total.toFixed(2));
	}
});
</script>