<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="header.jsp"></jsp:include>
<div class="categories">
	<jsp:include page="categoriesNav.jsp"></jsp:include>
</div>
<form:form id="feedbackForm" class="form-horizontal" commandName="feedback" method="POST" action="${pageContext.request.contextPath}/controller/feedback/submitFeedback/${transaction.transactionId}">
	<section id="createFeedback">
		<div class="container">
			<div class="row">
				<div class="col-xs-4" style="float: none; margin: 0 auto; text-align: center;">
					<div class="form-wrap">
						<c:choose>
							<c:when test = "${shopperToSeller}">
								<h2>Give a rating for "${transaction.itemName}" by ${transaction.sellerUsername}</h2>
							</c:when>
							<c:otherwise>
								<h2>Give a rating for ${transaction.shopperUsername} who bought ${transaction.quantity} of your "${transaction.itemName}"</h2>
							</c:otherwise>
						</c:choose>
						
						<hr />
						<div class="form-group">
							<label for="rating" class="sr-only">Rating</label>
							<form:select path="rating" cssClass="form-control" placeholder="Rating (1-5) Stars">
								<form:option value="1">&#9734;</form:option>
								<form:option value="2">&#9734;&#9734;</form:option>
								<form:option value="3">&#9734;&#9734;&#9734;</form:option>
								<form:option value="4">&#9734;&#9734;&#9734;&#9734;</form:option>
								<form:option value="5">&#9734;&#9734;&#9734;&#9734;&#9734;</form:option>
							</form:select>
						</div>
						<div class="form-group">
							<label for="comment" class="sr-only">Comment</label>
							<form:textarea path="comment" cssClass="form-control" placeholder="Tell us why!" />
						</div>
						<div id="overlay" onclick="hideDIVs()">
							<form:input path="itemName" value="${transaction.itemName}"/>
							<form:input path="itemId" value="${transaction.itemId}"/>
							<form:input path="sellerId" value="${transaction.sellerId}"/>
							<form:input path="shopperId" value="${transaction.shopperId}"/>
							<form:input path="sellerUsername" value="${transaction.sellerUsername}"/>
							<form:input path="shopperUsername" value="${transaction.shopperUsername}"/>
							<form:input path="transactionId" value="${transaction.transactionId}"/>
							<form:input path="shopperToSeller" value="${shopperToSeller}"/>
						</div>
  						<div id="alert">Creating Feedback...</div>
						<input type="submit" class="btn btn-warning" value="Send Feedback" onclick="showDIVs()"/>
						<hr />
					</div>
				</div>
			</div>
		</div>
	</section>
</form:form>
<jsp:include page="footer.jsp"></jsp:include>
