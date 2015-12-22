<footer class="container main-footer">
	<p class="text-danger">Copyright &copy;2014 CyShop | Com S 309 | <a href="${pageContext.request.contextPath}/controller/store/about">Team R14</a></p>
</footer>
<script>
$(document).ready(function() {
	var cart = new ShoppingCartManager();
	var count = cart.getItemCount();
	$('#header-list').prepend('<li><a id="cart-link" class="text-danger" href="${pageContext.request.contextPath}/controller/cart/view"><span class="glyphicon glyphicon-shopping-cart"><span class="badge badge-warning">' + count + '</span></a></li>');
});
</script>
</body>
</html>
