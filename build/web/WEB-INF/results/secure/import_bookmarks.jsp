<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>

        <%@include file="/WEB-INF/results/open/imports.jsp"%>

        <style type="text/css">
        </style>
    </head>


    <body>
        <%@include file="/WEB-INF/results/open/header.jsp"%>

        <div class="message">
            <s:property value="%{message}"/>
        </div>
        <div class="mainTable">

            <s:if test="hasActionErrors()">
                <div id="fieldErrors">
                    <s:actionerror/>
                </div>
            </s:if>

            <s:if test="hasActionMessages()">
                <div class="welcome">
                    <s:actionmessage/>
                </div>
            </s:if>

            <s:fielderror/>

            <div class="login-login">
                <div class="page-title">Import Bookmarks</div>
                <div class="login-form">
                    <s:form action="import_bookmarks" method="post" enctype="multipart/form-data" >
                        <s:file name="file" label="Bookmarks File" />
                        <s:submit />
                    </s:form>
                </div>
            </div>
            <div class="login-register">
                <div class="page-title">Download Bookmarks Template</div>
                <div class="login-box">
                    Download Bookmarks Template - <s:a href="template/export_bookmarks.xlsx">Template File</s:a>
                        <br></br>
                        Note: This lists your categories. You want to see the bookmarks, click Manage Bookmarks link.
                        <br></br>
                        <br></br>
                    </div>
                </div>
            </div>

        <%@include file="/WEB-INF/results/open/footer.jsp"%>
        <script type="text/javascript" src="js/myapp.js"></script>
    </body>
</html>
