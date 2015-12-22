<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="header.jsp"></jsp:include>
<div class="categories">
	<jsp:include page="categoriesNav.jsp"></jsp:include>
</div>
<div class="container">
	<h2 align="center">Checkout</h2>
	<hr />
	<div class="stepwizard">
	    <div class="stepwizard-row setup-panel">
	        <div class="stepwizard-step">
	            <a href="#step-1" type="button" class="btn btn-warning btn-circle" disabled="disabled">1</a>
	            <p>Shopping Cart</p>
	        </div>
	        <div class="stepwizard-step">
	            <a href="#step-2" type="button" class="btn btn-danger btn-circle">2</a>
	            <p><b>Checkout</b></p>
	        </div>
	        <div class="stepwizard-step">
	            <a href="#step-3" type="button" class="btn btn-warning btn-circle" disabled="disabled">3</a>
	            <p>Confirmation</p>
	        </div>
	    </div>
	</div>
	<form:form id="checkoutForm" class="form-horizontal" commandName="user" method="GET" action="${pageContext.request.contextPath}/controller/cart">
		<form:hidden id="userId" path="userId"/>
		<form:hidden id="username" path="username"/>
		<div id="checkout-container" class="row">
			<div id="shipping-information" class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
				<div class="form-group">
			    	<label for="street" class="col-sm-2 control-label">Street</label>
			    	<div class="col-sm-10">
						<form:input id="street" path="street" cssClass="form-control" placeholder="Street" />			    	
			    	</div>
			  	</div>
			  	<div class="form-group">
			    	<label for="city" class="col-sm-2 control-label">City</label>
			    	<div class="col-sm-10">
						<form:input id="city" path="city" cssClass="form-control" placeholder="City" />			    	
			    	</div>
			  	</div>
			  	<div class="form-group">
			    	<label for="city" class="col-sm-2 control-label">State</label>
			    	<div class="col-sm-10">
						<form:input id="state" path="state" cssClass="form-control" placeholder="State" />	
			    	</div>
			  	</div>		  	
			  	<div class="form-group">
			    	<label for="zipCode" class="col-sm-2 control-label">Zip</label>
			    	<div class="col-sm-10">
						<form:input id="zipCode" path="zipCode" cssClass="form-control" placeholder="Zip Code" />			    	
			    	</div>
			  	</div>		  	
				<div class="form-group">
					<label for="email" class="col-sm-2 control-label">Email</label>
					<div class="col-sm-10">
						<form:input id="email" path="email" cssClass="form-control" placeholder="Email" />
					</div>
				</div>	  	
		  </div>
		  <div id="card-information" class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
		  	<div class="form-group">
		    	<label for="cardHolder" class="col-sm-2 col-lg-3 control-label">Cardholder</label>
		    	<div class="col-sm-10 col-lg-9">
					<form:input id="cardHolder" path="cardHolder" cssClass="form-control" placeholder="Card Holder" />			    	
		    	</div>
		  	</div>	
		  	<div class="form-group">
		    	<label for="cardType" class="col-sm-2 col-lg-3 control-label">Card Type</label>
		    	<div class="col-sm-10 col-lg-9">
					<form:select id="cardType" path="cardType" cssClass="form-control" placeholder="Card Type">
						<form:option value="${user.cardType}">${user.cardType}</form:option>
						<form:option value="Visa">Visa</form:option>
						<form:option value="Master Card">Master Card</form:option>
						<form:option value="Discover">Discover</form:option>
						<form:option value="American Express">American Express</form:option>
					</form:select>			    	
		    	</div>
		  	</div>
		  	<div class="form-group">
		    	<label for="cardNumber" class="col-sm-2 col-lg-3 control-label">Card #</label>
		    	<div class="col-sm-10 col-lg-9">
					<form:input id="cardNumber" path="cardNumber" cssClass="form-control" placeholder="Card Number" />			    	
		    	</div>
		  	</div>		
		  	<div class="form-group">
		    	<label for="cardExpireDate" class="col-sm-2 col-lg-3 control-label">Expire Date</label>
		    	<div class="col-sm-10 col-lg-9">
		    		<div class="bfh-datepicker">
						<form:input id="cardExpire" path="cardExpireDate" cssClass="form-control" placeholder="Card Expiration Date" />			    	
					</div>
		    	</div>
		  	</div>		
		  </div>
		</div>
		<div style="text-align:center;">
			<a href="${pageContext.request.contextPath}/controller/store/view" class="btn btn-warning"><span class="glyphicon glyphicon-shopping-cart"></span> Continue Shopping</a>
			<button id="confirm-btn" type="button" class="btn btn-danger">Confirm <span class="glyphicon glyphicon-ok"></span></button>
		</div>
	</form:form>
</div>	
<jsp:include page="footer.jsp"></jsp:include>
<script>
$('#confirm-btn').bind("click", function() {
	if (confirm("Are you sure you want to place this order?")) {
		var isValid = $('#checkoutForm').valid();
		if(isValid) {
			var url = $("#checkoutForm").attr('action') + '/confirmation';
			var cart = new ShoppingCartManager();
			var data = $('#checkoutForm').serializeArray();
			var items = cart.getMyItems();
			var itemsList = [];
			for (var itemId in items) {
				itemsList.push(itemId);
			}
			$.ajax({
				type: 'POST',
				data: {itemList: itemsList},
				traditional: true,
				url: url,
		      	success:function(data) {
		      		$("#checkoutForm").attr("action", $("#checkoutForm").attr('action')+"/orderConfirmation");
		      		$("#checkoutForm").submit();
		       	}
			});
		}
	}
});

$(document).ready(function () {	
	$('#datepicker').bfhdatepicker('toggle');
    $('#checkoutForm').validate({
        rules: {
            street: {
                minlength: 2,
                required: true
            },
            state: {
                required: true
            },
            city: {
                minlength: 2,
                required: true
            },
            zipCode: {
                minlength: 5,
                maxlength: 5,
                required: true
            },
            email: {
                required: true,
                email: true
            },
            cardHolder: {
                minlength: 2,
                required: true
            },
            cardType: {
                required: true
            },
            cardNumber: {
                minlength: 12,
                maxlength: 19,
                required: true
            },
            cardExpire: {
                minlength: 2,
                required: true
            }
        },
        highlight: function (element) {
            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
        },
        success: function (element) {
            element.text('OK!').addClass('valid').closest('.form-group').removeClass('has-error').addClass('has-success');
        }
    });
});
</script>