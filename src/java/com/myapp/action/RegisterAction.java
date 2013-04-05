/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.action;

import com.myapp.admin.Address;
import com.myapp.admin.Credential;
import com.myapp.admin.CredentialDAO;
import com.myapp.admin.Email;
import com.myapp.admin.EmailDAO;
import com.myapp.admin.Phone;
import com.myapp.admin.User;
import com.myapp.admin.UserDAO;
import com.myapp.main.Bookmark;
import com.myapp.main.Category;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author palwal
 */
public class RegisterAction extends ActionSupport {

    private String username;
    private String password;
    private String password2;
    private String emailAddress;
    private String phoneNumber;
    private String firstname;
    private String middlename;
    private String lastname;
    private String DOB;
    private String gender;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    static final Logger logger = Logger.getLogger(RegisterAction.class);

    //business logic
    //@Override
    public String createProfile() {
        logger.debug("RegisterAction execute!");

        User user = new User();

        user.setFirstName(getFirstname());
        user.setMiddleName(getMiddlename());
        user.setLastName(getLastname());
        user.setGender(getGender());
        user.setDOB("".equals(getDOB()) ? null : getDOB());

        logger.debug("RegisterAction execute!2");
        //Address object being created
        Address address = new Address(getAddress1(), getAddress2(), getCity(), getState(), getZip());
        address.setStatus("A");
        Set<Address> userAddresses = new LinkedHashSet<Address>();
        userAddresses.add(address);

        user.setUserAddresses(userAddresses);

        logger.debug("RegisterAction execute!3");
        //Email object being created
        Email email = new Email(getEmailAddress());
        email.setStatus("A");
        Set<Email> userEmails = new LinkedHashSet<Email>();
        userEmails.add(email);

        user.setUserEmails(userEmails);

        logger.debug("RegisterAction execute!4");
        //Phone object being created
        Phone phone = new Phone(getPhoneNumber());
        phone.setStatus("A");
        Set<Phone> userPhones = new LinkedHashSet<Phone>();
        userPhones.add(phone);

        user.setUserPhones(userPhones);

        logger.debug("RegisterAction execute!5");
        //Credential object being created
        Credential credential = new Credential(getUsername(), getPassword());
        credential.setStatus("A");
        Set<Credential> userCredentials = new LinkedHashSet<Credential>();
        userCredentials.add(credential);

        user.setUserCredentials(userCredentials);

        logger.debug("RegisterAction execute!6");
        //Credential object being created
        Bookmark bookmark = new Bookmark("OFTUS", "OFTUS bookmark", "http://www.oftus.com/");
        bookmark.setStatus("A");
        bookmark.setBookmarkOrder(1);
        Set<Bookmark> userBookmarks = new LinkedHashSet<Bookmark>();
        userBookmarks.add(bookmark);

        logger.debug("RegisterAction execute!7");
        //Credential object being created
        Category category = new Category("OFTUS", "OFTUS bookmarks");
        category.setStatus("A");
        category.setCategoryOrder(1);
        category.setBookmarks(userBookmarks);
        Set<Category> userCategories = new LinkedHashSet<Category>();
        userCategories.add(category);

        user.setUserCategories(userCategories);

        logger.debug("Address Set Size:" + userAddresses.size());
        logger.debug("Email Set Size:" + userEmails.size());
        logger.debug("Phone Set Size:" + userPhones.size());
        logger.debug("Credential Set Size:" + userCredentials.size());
        logger.debug("Category Set Size:" + userCategories.size());
        logger.debug("Bookmark Set Size:" + userBookmarks.size());

        UserDAO userDAO = new UserDAO();
        userDAO.saveUser(user);

        logger.debug("RegisterAction execute, userid:" + user.getUserId());

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        return "success";
    }

    public String updateProfile() {
        logger.debug("RegisterAction updateProfile!");

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        user.setFirstName(getFirstname());
        user.setMiddleName(getMiddlename());
        user.setLastName(getLastname());
        user.setGender(getGender());
        user.setDOB("".equals(getDOB()) ? null : getDOB());

        logger.debug("RegisterAction execute!2");
        //Address object being created

        Set<Address> userAddresses = user.getUserAddresses();
        Address address = null;
        for (Iterator iterator = userAddresses.iterator(); iterator.hasNext();) {
            address = (Address) iterator.next();
        }
        address.setAddress1(address1);
        address.setAddress2(address2);
        address.setCity(city);
        address.setState(state);
        address.setZip(zip);

        userAddresses.add(address);
        user.setUserAddresses(userAddresses);

        logger.debug("RegisterAction execute!3");
        Email email = new Email(getEmailAddress());
        Set<Email> userEmails = new LinkedHashSet<Email>();
        userEmails.add(email);

        user.setUserEmails(userEmails);

        logger.debug("RegisterAction execute!4");
        //Phone object being created
        Phone phone = new Phone(getPhoneNumber());
//        phone.setStatus("A");
        Set<Phone> userPhones = new LinkedHashSet<Phone>();
        userPhones.add(phone);

        user.setUserPhones(userPhones);

        logger.debug("RegisterAction execute!5");
        //Credential object being created
        Credential credential = new Credential(getUsername(), getPassword());
//        credential.setStatus("A");
        Set<Credential> userCredentials = new LinkedHashSet<Credential>();
        userCredentials.add(credential);

        user.setUserCredentials(userCredentials);


        logger.debug("Address Set Size:" + userAddresses.size());
        logger.debug("Email Set Size:" + userEmails.size());
        logger.debug("Phone Set Size:" + userPhones.size());
        logger.debug("Credential Set Size:" + userCredentials.size());

        UserDAO userDAO = new UserDAO();
        userDAO.updateUser(user);

        logger.debug("RegisterAction execute, userid:" + user.getUserId());

        return "success";
    }

    @Override
    public void validate() {
        logger.debug("in the validate of RegisterAction:" + getActionName());

        if ("create_profile".equals(getActionName())) {
            //Check if user already exists
            EmailDAO emailDAO = new EmailDAO();
            CredentialDAO credentialDAO = new CredentialDAO();

            if (emailDAO.isEmailAvailable(getEmailAddress())) {
                logger.debug("Email available for Registration:" + getEmailAddress());
                addActionMessage("Email " + getEmailAddress() + " is available for registration.");
            } else {
                logger.debug("Email not available for Registration:" + getEmailAddress());
                addActionMessage("Email " + getEmailAddress() + " already registered.");
                addFieldError("emailAddress", "Email address already registered.");
            }

            if (credentialDAO.isUsernameAvailable(getUsername())) {
                logger.debug("Username available for Registration:" + getUsername());
                addActionMessage("Username " + getUsername() + " is available for registration.");
            } else {
                logger.debug("Username not available for Registration:" + getUsername());
                addActionMessage("Username " + getUsername() + " already registered.");
                addFieldError("username", "Username already registered.");
            }

            if (getPassword().equals(getPassword2())) {
//            addActionMessage("Both passwords match.");
            } else {
                addFieldError("password2", "Both passwords dont match");
            }
        } else if ("update_profile".equals(getActionName())) {
        } else {
        }
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getActionName() {
        ActionContext context = ActionContext.getContext();
        return context.getName();
    }
}