<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="header.jsp"></jsp:include>
<div class="categories">
	<jsp:include page="categoriesNav.jsp"></jsp:include>
</div>
<div class="container">
	<form:form id="questionForm" class="form-horizontal" commandName="question" action="${pageContext.request.contextPath}/controller/item/question/${item.itemId}/${item.sellerId}/${shopper.userId}" method="post">
	<section id="login">
		<div class="container">
			<div class="row">
				<div class="col-xs-4"
					style="float: none; margin: 0 auto; text-align: center;">
					<div class="form-wrap">
						<h2 align="center">Ask ${seller.username}...</h2>
						<hr />
						<div class="form-group">
							<p>What is your question for ${seller.username}?</p>
							<label for="question" class="sr-only">Question</label>
							<form:input path="question" class="form-control" placeholder="Question" />
						</div>
						<input type="submit" class="btn btn-warning" value="Send Question!" />
						<hr />
					</div>
				</div>
			</div>
		</div>
	</section>
</form:form>
</div>
<jsp:include page="footer.jsp"></jsp:include>