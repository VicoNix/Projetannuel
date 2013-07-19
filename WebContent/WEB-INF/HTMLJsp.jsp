<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.ArrayList" %>
<% com.project.servlets.Tables notreBean;%>
<!DOCTYPE html>

<html>
				<iframe id="HTMLframe"
						src="<%request.getAttribute("html");%>"
						width="500px" height="300px"></iframe>
</html>