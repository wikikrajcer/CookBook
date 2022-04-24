/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.cookbookwebapplication.servlets;

import com.mycompany.cookbookwebapplication.model.StartUp;
import com.mycompany.cookbookwebapplication.exception.WrongRecipeFormatException;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet is responsible for editing recipe
 *
 * @version 4.0
 * @author Wiktoria Krajcer
 */
@WebServlet(name = "EditRecipeServlet", urlPatterns = {"/editRecipe"})
public class EditRecipeServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String dish = request.getParameter("dish");
        String preparationTime = request.getParameter("preparationTime");
        String author = request.getParameter("author");
        String cuisine = request.getParameter("cuisine");
        ArrayList<String> ingredients = new ArrayList();
        String[] insertedIngredients = request.getParameter("ingredients").split(",");

        for (var ingredient : insertedIngredients) {
            ingredients.add(ingredient);
        }
        try {
            StartUp.getModel().validateAndModifyRecipe(dish, preparationTime, cuisine, author, ingredients);
            Cookie cookie = new Cookie("lastModified", dish);
            response.addCookie(cookie);
            response.sendRedirect("index.html");
        } catch (java.lang.NumberFormatException e) {
            response.sendError(response.SC_BAD_REQUEST, "Wprowadzono znak zamiast liczby!");
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            response.sendError(response.SC_BAD_REQUEST, "Wprowadzono zły format czasu przygotowania!");
        } catch (WrongRecipeFormatException e) {
            response.sendError(response.SC_BAD_REQUEST, e.getMessage());
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
}
