/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.action;

import com.myapp.admin.User;
import com.myapp.admin.UserDAO;
import com.myapp.main.Bookmark;
import com.myapp.main.BookmarkDAO;
import com.myapp.main.Category;
import com.myapp.util.Utils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author palwal
 */
public class BookmarkAction extends ActionSupport {

    private String categoryId;
    private String bookmarkId;
    private String bookmarkName;
    private String hiperLink;
    private String description;
    private String bookmarkOrder;
    private String bookmarks;
    private String message;
    private String source;
    User user = null;
    static final Logger logger = Logger.getLogger(BookmarkAction.class);
    public static final long serialVersionUID = 42L;
    private List<Category> userCategories;

    //business logic
    @Override
    public String execute() {
        logger.debug("BookmarkAction newBookmark!");
        return "success";
    }

    //business logic
    public String addBookmark() {
        logger.debug("addBookmark!" + getCategoryId());
        String returnVal = "success";
        setMessage("Enter details of new bookmark below and click Save");

        return returnVal;
    }

    //business logic
    public String editBookmark() {
        logger.debug("editBookmark!" + getBookmarkId());
        String returnVal = "success";

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();

        user = (User) session.getAttribute("user");

        logger.debug("category id:" + getCategoryId());

        userCategories = user.getUserCategories();
        logger.debug("userCategories size:" + userCategories.size());

        for (Iterator<Category> iterator = userCategories.iterator(); iterator.hasNext();) {
            Category c = iterator.next();
            logger.debug("categoryId value:" + c.getCategoryId() + ":" + c.getCategoryName());
            List<Bookmark> bookmarks = c.getBookmarks();

            for (Iterator<Bookmark> i = bookmarks.iterator(); i.hasNext();) {
                Bookmark b = i.next();
                if (b.getBookmarkId().equals(getBookmarkId())) {
                    logger.debug("bbbbbbbbbbbbbookmarkId value:" + b.getBookmarkId() + ":" + b.getBookmarkName()
                            + ":" + b.getHiperLink() + ":" + b.getDescription());
                    session.setAttribute("bookmark", b);
                    break;
                }
            }
        }
        setMessage("Make changes to the bookmark and click Save.");
        return returnVal;
    }

    //business logic
    public String saveBookmark() {
        logger.debug("BookmarkAction saveBookmark!");
        String returnVal = "success";

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        user = (User) session.getAttribute("user");

        logger.debug("category id:" + getCategoryId());
        userCategories = user.getUserCategories();
        logger.debug("userCategories size:" + userCategories.size());

        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkName(getBookmarkName());
        bookmark.setHiperLink(hiperLink);
        bookmark.setStatus("A");
        bookmark.setCreateDate(Utils.getCurrentDate());
        bookmark.setOrder(10000); //modify this later//

        for (Iterator<Category> iterator = userCategories.iterator(); iterator.hasNext();) {
            Category c = iterator.next();
            logger.debug("categoryId value:" + c.getCategoryId() + ":" + c.getCategoryName());

            if (c.getCategoryId().equals(getCategoryId())) {
                List<Bookmark> bookmarks = c.getBookmarks();
                logger.debug("bookmarks size1:" + bookmarks.size());
                bookmarks.add(bookmark);
                logger.debug("bookmarks size2:" + bookmarks.size());
                break;
            }
        }
        user.setUserCategories(userCategories);
        UserDAO userDAO = new UserDAO();
        userDAO.updateUser(user);

        setMessage("Bookmark Saved Successfully");
        return returnVal;
    }

    //business logic
    public String updateBookmark() {
        logger.debug("BookmarkAction updateBookmark!");
        String returnVal = "success";
        //String categoryChanged = "no";
        int updated = 0;

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();

        user = (User) session.getAttribute("user");

        logger.debug("category id:" + getCategoryId());
        userCategories = user.getUserCategories();
        logger.debug("userCategories size:" + userCategories.size());

        for (Iterator<Category> iterator = userCategories.iterator(); iterator.hasNext();) {
            Category c = iterator.next();
            logger.debug("categoryId value:" + c.getCategoryId() + ":" + c.getCategoryName());
            List<Bookmark> bookmarks = c.getBookmarks();

            for (Iterator<Bookmark> i = bookmarks.iterator(); i.hasNext();) {
                Bookmark b = i.next();

                if (b.getBookmarkId().equals(getBookmarkId())) {
                    logger.debug("cccccccccbookmarkId value:" + getBookmarkId() + ":" + getBookmarkName() + ":"
                            + getHiperLink() + ":" + getDescription());
                    b.setBookmarkName(getBookmarkName());
                    b.setHiperLink(getHiperLink());
                    b.setDescription(getDescription());

                    if (c.getCategoryId().equals(getCategoryId())) {
                        ;
                    } else {
                        BookmarkDAO bookmarkDAO = new BookmarkDAO();
                        updated = bookmarkDAO.updateBookmarkCategory(getBookmarkId(), getCategoryId());
                        logger.debug("Number of records updated:" + updated);
                    }
                    break;
                }
            }
        }

        user.setUserCategories(userCategories);
        UserDAO userDAO = new UserDAO();

        userDAO.updateUser(user);
        setMessage("Bookmark updated successfully.");
        return returnVal;
    }

    //business logic
    public String moveBookmark() {
        logger.debug("BookmarkAction moveBookmark!");
        String returnVal = "success";
        int updated = 0;

        logger.debug("bookmark id:" + getBookmarkId());
        logger.debug("category id:" + getCategoryId());

        BookmarkDAO bookmarkDAO = new BookmarkDAO();
        updated = bookmarkDAO.updateBookmarkCategory(getBookmarkId(), getCategoryId());
        logger.debug("Number of record updated:" + updated);

        logger.debug("updated:" + updated);
        setMessage("Bookmark moved successfully");
        return returnVal;
    }

    //business logic
    public String deleteBookmark() {
        logger.debug("BookmarkAction deleteBookmark!");
        String returnVal = "success";

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();

        user = (User) session.getAttribute("user");
        logger.debug("category id:" + getCategoryId());

        userCategories = user.getUserCategories();
        logger.debug("userCategories size:" + userCategories.size());

        for (Iterator<Category> iterator = userCategories.iterator(); iterator.hasNext();) {
            Category c = iterator.next();
            logger.debug("categoryId value:" + c.getCategoryId() + ":" + c.getCategoryName());
            List<Bookmark> bookmarks = c.getBookmarks();

            Collection<Bookmark> removeBookmarks = new LinkedList<Bookmark>();
            for (Iterator<Bookmark> i = bookmarks.iterator(); i.hasNext();) {
                Bookmark b = i.next();

                if (b.getBookmarkId().equals(getBookmarkId())) {
                    logger.debug("bookmarkId value:" + b.getBookmarkId() + ":" + b.getBookmarkName() + ":"
                            + b.getHiperLink() + ":" + b.getDescription());
//                    b.setStatus("D");
//                    bookmarks.remove(b);
                    removeBookmarks.add(b);
                    break;
                }
            }
            bookmarks.removeAll(removeBookmarks);
        }
        user.setUserCategories(userCategories);
        UserDAO userDAO = new UserDAO();
        userDAO.updateUser(user);
        setMessage("Bookmark Deleted Successfully");

        return returnVal;
    }

    public String deleteBookmarks() {
        logger.debug("BookmarkAction deleteBookmarks!");
        String returnVal = "success";
        Map<String, String> bm = new HashMap<String, String>();

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();

        user = (User) session.getAttribute("user");
        logger.debug("category id:" + getCategoryId());

        userCategories = user.getUserCategories();
        logger.debug("userCategories size:" + userCategories.size());

        StringTokenizer st = new StringTokenizer(getBookmarks(), ":");
        while (st.hasMoreTokens()) {
            bookmarkId = st.nextToken();
            bm.put(bookmarkId, bookmarkId);
            logger.debug("bookmark ID1:" + bookmarkId);
        }
        logger.debug("bookmark size1:" + bm.size());

        for (Iterator<Category> iterator = userCategories.iterator(); iterator.hasNext();) {
            Category c = iterator.next();
            logger.debug("categoryId:" + c.getCategoryId() + ":" + c.getCategoryName());
            List<Bookmark> bookmarksSet = c.getBookmarks();

            Collection<Bookmark> removeBookmarks = new LinkedList<Bookmark>();
            for (Iterator<Bookmark> i = bookmarksSet.iterator(); i.hasNext();) {
                Bookmark b = i.next();
                logger.debug("bookmark ID2:" + b.getBookmarkId());

                if (bm.containsKey(b.getBookmarkId() + "")) {
                    //bookmarksSet.remove(b);
                    removeBookmarks.add(b);
                    logger.debug("bookmark ID3:" + b.getBookmarkId());
                }
            }
            bookmarksSet.removeAll(removeBookmarks);
        }
        user.setUserCategories(userCategories);
        UserDAO userDAO = new UserDAO();
        userDAO.updateUser(user);
        setMessage("Bookmarks deleted successfully");

        return returnVal;
    }

    //business logic to update the category
    public String updateBookmarkOrder() {
        logger.debug("updateCategoryOrder!" + getBookmarkOrder());
        String returnVal = "success";
        int updated = 0;

        StringTokenizer st = new StringTokenizer(getBookmarkOrder(), ":");
        for (int i = 0; st.hasMoreTokens(); i++) {
            bookmarkId = st.nextToken();
            logger.debug("Bookmark ID" + bookmarkId);
            BookmarkDAO bookmarkDAO = new BookmarkDAO();
            updated = bookmarkDAO.updateBookmarkOrder(bookmarkId, i);
            logger.debug("Records Updated" + updated);
        }
        setMessage("Bookmark order updated");
        return returnVal;
    }

    //business logic to update the category
    public String openBookmark() {
        logger.debug("openBookmark!" + getBookmarkId());
        String returnVal = "success";
        String updated = "";

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpSession session = request.getSession();
        user = (User) session.getAttribute("user");

        logger.debug("in open_bookmark:" + getBookmarkId());
        BookmarkDAO bookmarkDAO = new BookmarkDAO();
        updated = bookmarkDAO.openBookmark(user.getUserId(), getBookmarkId());
        logger.debug("Records Updated" + updated);

        Bookmark bookmark = bookmarkDAO.getBookmark(getBookmarkId());

        try {
            String url = response.encodeRedirectURL(bookmark.getHiperLink());
            response.sendRedirect(url);
        } catch (Exception e) {
            logger.debug("Exception");
            e.printStackTrace();
        } finally {
            logger.debug("finally block");
        }
        setMessage("Bookmark opened");

        return returnVal;
    }

    //simple validation
    @Override
    public void validate() {
        logger.debug("in the validate of BookmarkAction");
//        HttpServletRequest request = ServletActionContext.getRequest();
//        HttpSession session = request.getSession();

        if (getActionName().equals("new_bookmark")) {
            logger.debug("in new_bookmark");
        } else if (getActionName().equals("update_bookmark_order")) {
            logger.debug("in update_bookmark_order");
        } else if (getActionName().equals("move_bookmark")) {
            logger.debug("in move_bookmark");
        } else if (getActionName().equals("save_bookmark")) {
            if (getBookmarkName() == null || "".equals(getBookmarkName())) {
                addFieldError("bookmarkName", "Bookmark title cant be empty");
            }

            if (getHiperLink() == null || "".equals(getHiperLink())) {
                addFieldError("hiperLink", "Bookmark/URL cant be empty");
            }

            if (getCategoryId().equals("-1")) {
                addFieldError("categoryId", "Please select a category");
            }
        } else if (getActionName().equals("update_bookmark") || getActionName().equals("update_bookmark_list")) {
            if (getBookmarkName() == null || "".equals(getBookmarkName())) {
                addFieldError("bookmarkName", "Bookmark title cant be empty");
            }

            if (getHiperLink() == null || "".equals(getHiperLink())) {
                addFieldError("hiperLink", "Bookmark/URL cant be empty");
            }

            if (getCategoryId().equals("-1")) {
                addFieldError("categoryId", "Please select a category");
            }
        } else if (getActionName().equals("update_bookmark_list")) {
            logger.debug("in else action update_bookmark list");
        } else if (getActionName().equals("update_bookmark_mainpage")) {
            logger.debug("in else action update_bookmark mainpage");
        } else if (getActionName().equals("open_bookmark")) {
            logger.debug("in else action open_bookmark");
        } else if (getActionName().equals("open_bookmark")) {
            logger.debug("in else action delete_bookmarks");
        }
        logger.debug("action");
    }

    public String getActionName() {
        ActionContext context = ActionContext.getContext();
        return context.getName();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(String bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public String getBookmarkName() {
        return bookmarkName;
    }

    public void setBookmarkName(String bookmarkName) {
        this.bookmarkName = bookmarkName;
    }

    public String getHiperLink() {
        return hiperLink;
    }

    public void setHiperLink(String hiperLink) {
        this.hiperLink = hiperLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookmarkOrder() {
        return bookmarkOrder;
    }

    public void setBookmarkOrder(String bookmarkOrder) {
        this.bookmarkOrder = bookmarkOrder;
    }

    public String getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(String bookmarks) {
        this.bookmarks = bookmarks;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
