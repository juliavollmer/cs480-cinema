<%-- 
    Document   : showMovie
    Created on : Apr 15, 2018, 4:17:53 PM
    Author     : jsvollmer2
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<HTML>
<HEAD>
<jsp:useBean id="movie" 
             type="cs4280.MovieDetail" 
             scope="request" />

<TITLE><jsp:getProperty name="movie" property="name" /></TITLE>
        <jsp:include page="../include/statement.jsp" />
<!--<LINK REL=STYLESHEET
      HREF="app-styles.css"
      TYPE="text/css">-->
</HEAD>

<BODY>
<TABLE BORDER=5 ALIGN="CENTER">
  <TR><TH CLASS="TITLE">
  <jsp:getProperty name="movie" property="name" /></TABLE>
<P>
<IMG SRC="<jsp:getProperty name='movie' property='cover' />"
     ALIGN="RIGHT">
     
<H3>Name</H2>
<jsp:getProperty name="movie" property="name" />
     
<H3>Description</H2>
<jsp:getProperty name="movie" property="intro" />

<H3>Genre</H2>
<jsp:getProperty name="movie" property="genre" />
<H3>Runtime</H2>
<jsp:getProperty name="movie" property="runtime" />
<H3>Category</H2>
<jsp:getProperty name="movie" property="category" />
<H3>Language</H2>
<jsp:getProperty name="movie" property="language" />
<H3>Director</H2>
<jsp:getProperty name="movie" property="director" />
<H3>Cast</H2>
<jsp:getProperty name="movie" property="cast" />
<%@ include file="/WEB-INF/showSchedule.jsp" %> 

 <br>
        <jsp:include page="../include/statement.jsp" />
</BODY>
</HTML>