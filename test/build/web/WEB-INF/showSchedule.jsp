<%-- 
    Document   : showSchedule
    Created on : Apr 23, 2018, 8:38:54 PM
    Author     : jsvollmer2
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="cs4280.Schedule"%>
<h3> Schedule </h3>
<FORM method='GET' action='' id='sched'>    
<input name='action' type='hidden' value='seating' />
 <select name="sid" form="sched">
  <% List<Schedule> times = (ArrayList<Schedule>)request.getAttribute("times");
    for(Schedule time : times)
    { request.setAttribute("schedule", time);
       %>
       <jsp:useBean id="schedule" 
             type="cs4280.Schedule" 
             scope="request" />      
  <option value="<jsp:getProperty name="schedule" property="sid" />"><jsp:getProperty name="schedule" property="date" />
<jsp:getProperty name="schedule" property="time" />
<jsp:getProperty name="schedule" property="price" />  </option>

<%
    }
 
%></select>
<br><br>
  <INPUT TYPE="SUBMIT" VALUE="Submit Order">
</FORM>
