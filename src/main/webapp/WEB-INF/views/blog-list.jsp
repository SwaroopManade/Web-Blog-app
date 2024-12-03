<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="blog-list" method="get" class="form-inline mb-3">
   <label for="sort" class="mr-2">Sort By:</label>
   <select name="sort" id="sort" class="form-control mr-2">
      <option value="date">Date</option>
      <option value="author">Author</option>
   </select>
   <button type="submit" class="btn btn-primary">Sort</button>
</form>

</body>
</html>