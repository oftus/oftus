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
                <div>Edit User Profile</div>
                <s:form action="update_profile">

                    <s:hidden name="userid" value="%{#session.user.userId}"/>
                    <!--
                    <s:iterator value="%{#session.user.userCategories}" id="category">
                        AAAAA<s:property value="#category.categoryName"/>
                        <s:iterator value="#category.bookmarks" id="bookmark">
                            BBBBB<s:property value="#bookmark.bookmarkName"/>
                        </s:iterator>
                    </s:iterator>
                    -->
                    <s:iterator value="%{#session.user.userCredentials}" id="credential">
                        CCCC<s:property value="#credential.username"/>
                        <s:textfield name="username" value="%{#credential.username}" size="25" maxlength="200" key="register.username-label"/>
                        <s:password name="password" value="" size="25" key="register.password-label"/>
                        <s:password name="password2" value="" size="25" key="register.password2-label"/>
                    </s:iterator>

                    <s:iterator value="%{#session.user.userEmails}" id="email">
                        <s:textfield name="emailAddress" value="%{#email.emailAddress}" size="35" maxlength="200" key="register.email-label"/>
                    </s:iterator>

                    <s:iterator value="%{#session.user.userPhones}" id="phone">
                        <s:textfield name="phoneNumber" value="%{#phone.phoneNumber}" size="35" maxlength="200" key="register.phone-label"/>
                    </s:iterator>

                    <s:textfield name="firstname" value="%{#session.user.firstName}" size="30" maxlength="200" key="register.firstname-label"/>
                    <s:textfield name="middlename" value="%{#session.user.middleName}" size="30" maxlength="200" key="register.middlename-label"/>
                    <s:textfield name="lastname" value="%{#session.user.lastName}" size="30" maxlength="200" key="register.lastname-label"/>
                    <s:radio name="gender" list="%{#session.genders}" listKey="genderCode" listValue="genderName" value="%{#session.user.gender}" key="register.gender-label"/>
                    <s:textfield name="DOB" value="%{#session.user.DOB}" id="datepicker" key="register.DOB-label"/>

                    <s:iterator value="%{#session.user.userAddresses}" id="address">
                        <s:textfield name="address1" value="%{#address.address1}" size="50" maxlength="200" key="register.address1-label"/>
                        <s:textfield name="address2" value="%{#address.address2}" size="50" maxlength="200" key="register.address2-label"/>
                        <s:textfield name="city" value="%{#address.city}" size="30" maxlength="200" key="register.city-label"/>
                        <s:select name="state"  list="%{#session.states}" headerKey="0" value="%{#address.state}" listKey="stateCode" listValue="stateName" headerValue="Select State..." key="register.state-label"/>
                        <s:textfield name="zip" value="%{#address.zip}" size="10" maxlength="20" key="register.zip-label"/>
                    </s:iterator>

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
        </div>

        <script type="text/javascript" src="js/myapp.js"></script>

    </body>
</html>
