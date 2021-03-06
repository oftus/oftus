/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.servlet;

import com.myapp.admin.User;
import com.myapp.main.Document;
import com.myapp.main.DocumentDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author palwal
 */
public class ImageServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    static final Logger logger = Logger.getLogger(ImageServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("image/jpg");
            HttpSession session = request.getSession();

            Object ob = session.getAttribute("user");
            User user;

            if (ob instanceof User) {
                user = (User) ob;
            } else {
                return;
            }
            logger.debug("showPicture:" + user.getUserId());

            DocumentDAO documentDAO = new DocumentDAO();
            Document document = documentDAO.getPicture(user.getUserId());

//            logger.debug("showPicture:" + document.getContentType());
//            logger.debug("showPicture:" + document.getFileName());
//            logger.debug("showPicture:" + document.getFileType());
//            logger.debug("showPicture:" + document.getStatus());

            //(assuming you have a ResultSet named RS)
            int blobLength = (int) document.getBlob().length();
            byte[] imageInByte = document.getBlob().getBytes(1, blobLength);
            response.getOutputStream().write(imageInByte, 0, blobLength);
            response.getOutputStream().flush();
            logger.debug("showPicture:" + document.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
