var API_ROOT = '/CyShop/controller';

function ShoppingCartManager() {
	this.myItems = localStorage.getItem('shoppingCartItems');
	if (!this.myItems) {
		this.myItems = {};
		localStorage.setItem('shoppingCartItems', JSON.stringify(this.myItems));
	} else {
		this.myItems = JSON.parse(this.myItems);
	}
}

ShoppingCartManager.prototype.getMyItems = function() { return this.myItems; };

ShoppingCartManager.prototype.addItem = function(itemID) {
	this.myItems[itemID] = itemID;
	localStorage.setItem('shoppingCartItems', JSON.stringify(this.myItems));
};

ShoppingCartManager.prototype.updateItem = function(itemID, quantity) {
	this.myItems[itemID] = quantity;
	localStorage.setItem('shoppingCartItems', JSON.stringify(this.myItems));
};

ShoppingCartManager.prototype.clearLocalStorage = function() {
	localStorage.removeItem("shoppingCartItems");
};

ShoppingCartManager.prototype.updateBadge = function(count) {
	$('a#cart-link').html('<span class="glyphicon glyphicon-shopping-cart"><span class="badge badge-warning">' + count + '</span>');
};

ShoppingCartManager.prototype.hasItem = function(itemId) {
	return this.myItems[itemId];
}

ShoppingCartManager.prototype.getItemCount = function() {
	var count = 0;
	for (var key in this.myItems) {
		count++;
	}
	return count;
};

ShoppingCartManager.prototype.getItem = function(listOfIds) {
	return $.ajax({
		type: 'POST',
		data: {itemList: listOfIds},
		traditional: true,
		url: API_ROOT + '/cart/items'
	});
};

ShoppingCartManager.prototype.deleteItem = function(itemId) {
	delete this.myItems[itemId];
	localStorage.setItem('shoppingCartItems', JSON.stringify(this.myItems));
};

