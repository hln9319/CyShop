<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="header.jsp"></jsp:include>
<div class="categories">
	<jsp:include page="categoriesNav.jsp"></jsp:include>
</div>
<div class="container">
	<h2 align="center">About Us</h2>
	<hr />
	<p>Team R14 is a team working on CyShop for Computer Science 309 at Iowa State University. The team consists of the following four bright individuals:</p>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Role</th>
				<th>Team Member</th>
				<th>Major</th>
				<th>Classification</th>
				<th>Email</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>Repo Lead</td>
				<td>Lunan Huang</td>
				<td>Physics and Astronomy</td>
				<td>Graduate</td>
				<td><a href="mailto:lunan@iastate.edu">lunan@iastate.edu</a></td>
			</tr>
			<tr>
				<td>Schedule Lead</td>
				<td>Saldin Bajric</td>
				<td>Software Engineering</td>
				<td>Senior</td>
				<td><a href="mailto:sbajric@iastate.edu">sbajric@iastate.edu</a></td>
			</tr>
			<tr>
				<td>Tech Lead</td>
				<td>Brian Suther</td>
				<td>Software Engineering</td>
				<td>Senior</td>
				<td><a href="mailto:bcsuther@iastate.edu">bcsuther@iastate.edu</a></td>
			</tr>
			<tr>
				<td>Test Lead</td>
				<td>Dane Drvol</td>
				<td>Computer Science</td>
				<td>Junior</td>
				<td><a href="mailto:dadrvol@iastate.edu">dadrvol@iastate.edu</a></td>
			</tr>
		</tbody>
	</table>
</div>
<jsp:include page="footer.jsp"></jsp:include>