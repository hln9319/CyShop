<ul class="nav nav-pills nav-stacked nav-color">
  <li class="${automotiveActive}"><a href="${pageContext.request.contextPath}/controller/categories/automotive">Automotive</a></li>
  <li class="${childrenActive}"><a href="${pageContext.request.contextPath}/controller/categories/children">Children</a></li>
  <li class="${clothingActive}"><a href="${pageContext.request.contextPath}/controller/categories/clothing">Apparel</a></li>
  <li class="${electronicsActive}"><a href="${pageContext.request.contextPath}/controller/categories/electronics">Electronics</a></li>
  <li class="${entertainmentActive}"><a href="${pageContext.request.contextPath}/controller/categories/entertainment">Entertainment</a></li>
  <li class="${homeAndGardenActive}"><a href="${pageContext.request.contextPath}/controller/categories/homeAndGarden">Home &amp; Garden</a></li>
  <li class="${sportsActive}"><a href="${pageContext.request.contextPath}/controller/categories/sports">Sports</a></li>
  <li class="${isuActive}"><a href="${pageContext.request.contextPath}/controller/categories/isu">Iowa State</a></li>
</ul>

<script>
$('.nav li a').on('click', function(e){
    var $thisLi = $(this).parent('li');
    var $ul = $thisLi.parent('ul');
    if (!$thisLi.hasClass('active'))
    {
        $ul.find('li.active').removeClass('active');
            $thisLi.addClass('active');
    }
})
</script>