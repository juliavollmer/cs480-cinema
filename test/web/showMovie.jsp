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
            <jsp:include page="../include/header.jsp" />
        
    </HEAD>

    <BODY>
        <div class="movie">
            <h1>
                <jsp:getProperty name="movie" property="name" /></h1>
            <P>
                <img src="css/images/<jsp:getProperty name='movie' property='cover' />"
                     align="left">

            <h2>Name</h2>
                <jsp:getProperty name="movie" property="name" />

                

                    <h2>Genre</h2>
                        <jsp:getProperty name="movie" property="genre" />
                        <h2>Runtime</h2>
                            <jsp:getProperty name="movie" property="runtime" />
                            <h2>Category</h2>
                                <jsp:getProperty name="movie" property="category" />
                                <h2>Language</h2>
                                    <jsp:getProperty name="movie" property="language" />
                                    <h2>Director</h2>
                                        <jsp:getProperty name="movie" property="director" />
                                        <h2>Cast</h2>
                                        
                                            <jsp:getProperty name="movie" property="cast" />
                                            <h2>Description</h2>
                    <jsp:getProperty name="movie" property="intro" /><%@ include file="/WEB-INF/showSchedule.jsp" %> 

                                            <br></div>
                                            <jsp:include page="../include/statement.jsp" />
                                            </BODY>
                                            </HTML>