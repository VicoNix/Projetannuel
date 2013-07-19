<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.ArrayList" %>
<% com.project.servlets.Tables notreBean;%>
<!DOCTYPE html>

<html>
<select id="champs" name="champs" size="10">
				<% 
				notreBean = (com.project.servlets.Tables) request.getAttribute("tables");
            ArrayList<String> listechamps = notreBean.getListechamp();
            if(listechamps!=null)
            {
	            for(int i=0;i<listechamps.size();i++)
	            {
	            out.println( "<option>"+listechamps.get(i)+"</option>" );
	            }
            }
            else
            {
            out.println( "<option></option>");
            }
            %>
            </select>
</html>