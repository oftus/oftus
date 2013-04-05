<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
    <head>

        <title>Development</title>

        <link rel="shortcut icon" type="image/x-icon" class="header-icon" href="images/favicon.ico"/>
        <link rel="stylesheet" type="text/css" href="css/myapp.css"/>
        <link rel="stylesheet" type="text/css" media="all" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/themes/sunny/jquery-ui.css"/>

        <script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>
        <script type="text/javascript" src="js/jquery.highlight-3.js"></script>
        <script type="text/javascript" src="js/constants.js"></script>

        
        <!--script src="http://code.jquery.com/jquery-1.8.3.js"></script-->
        <script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>

        <!--
        This combination works for date picker
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
        <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
        <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
        -->
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <style type="text/css">
        </style>

        <script type="text/javascript">
            $(function() {
                $("#datepicker").datepicker({dateFormat:"yy/mm/dd"});
            });
            
            
            function loadData () {
                
            }
        </script>
    </head>


    <body>
        <div class="register-modal">

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



            <div class="register-register">
                <div>Register User:</div>
                <s:form action="register">

                    <s:textfield name="username" size="25" maxlength="200" key="register.username-label"/>
                    <s:password name="password" size="25" key="register.password-label"/>
                    <s:password name="password2" size="25" key="register.password2-label"/>
                    <s:textfield name="emailAddress" size="35" maxlength="200" key="register.email-label"/>
                    <s:textfield name="phoneNumber" size="35" maxlength="200" key="register.phone-label"/>

                    <s:textfield name="firstname" size="30" maxlength="200" key="register.firstname-label"/>
                    <s:textfield name="middlename" size="30" maxlength="200" key="register.middlename-label"/>
                    <s:textfield name="lastname" size="30" maxlength="200" key="register.lastname-label"/>
                    <s:radio name="gender" list="%{#session.genders}" listKey="genderCode" listValue="genderName" key="register.gender-label"/>
                    <s:textfield name="DOB" id="datepicker" key="register.DOB-label"/>

                    <s:textfield name="address1" size="50" maxlength="200" key="register.address1-label"/>
                    <s:textfield name="address2" size="50" maxlength="200" key="register.address2-label"/>
                    <s:textfield name="city" size="30" maxlength="200" key="register.city-label"/>
                    <s:select name="state"  list="%{#session.states}" headerKey="-1" headerValue="Select State..." key="register.state-label" listKey="stateCode" listValue="stateName"/>
                    <s:textfield name="zip" size="10" maxlength="20" key="register.zip-label"/>
                    <tr>
                        <td colspan="2" align="center">
                            <s:submit name="register" key="register.register-label" theme="simple" onclick="alert('alwal');"/>
                            <s:reset name="cancel" key="register.cancel-label" theme="simple"/>
                            <s:submit name="close" key="register.close-label" theme="simple"/>
                        </td>
                    </tr>

                </s:form>
                <s:a href="register_faq">Register FAQ</s:a>
            </div>
            <form action="index">
                <div class="register-login">
                    <br></br>Already have an account? <a href="index">Login now</a>
                </div>
            </form>
        </div>

        <script type="text/javascript" src="js/myapp.js"></script>

    </body>
</html>
