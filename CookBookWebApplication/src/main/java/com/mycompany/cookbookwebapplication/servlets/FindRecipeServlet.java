/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.cookbookwebapplication.servlets;

import com.mycompany.cookbookwebapplication.model.StartUp;
import com.mycompany.cookbookwebapplication.model.Ingredient;
import com.mycompany.cookbookwebapplication.model.RecipeModel;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet is responsible for finding recipe
 * @version 4.0
 * @author Wiktoria Krajcer
 */
@WebServlet(name = "FindRecipeServlet", urlPatterns = {"/findRecipe"})
public class FindRecipeServlet extends HttpServlet {
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
        
         
        RecipeModel recipe = StartUp.getModel().findRecipeByName(request.getParameter("recipeToFind"));

       
        if (recipe == null) {
            response.sendError(response.SC_BAD_REQUEST, "Przepis o podanej nazwie nie istnieje!");
        } else {
            response.setContentType("text/html;charset=UTF-8");
            try ( PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Znaleziony przepis</title>");
                out.println("<link rel=\"stylesheet\" href=\"style.css\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class=\"container\">");
                out.println("<hr><h1>Znaleziony przepis:</h1><hr>");
                request.getParameter("recipeToFind");
                out.println();
                
                out.println("<p>Potrawa: </p><label>" + recipe.getDish() + "</label>");
                out.println("<p>Czas przygotowania: </p><label>" + String.format("%02dh %02dmin", recipe.getPreparationTime().toHoursPart(), recipe.getPreparationTime().toMinutesPart()) + "</label>");
                out.println("<p>Kuchnia: </p><label>" + recipe.getCuisine() + "</label>");
                out.println("<p>Autor: </p><label>" + recipe.getAuthor() + "</label>");
                out.print("<p>Lista składników: </p><label> ");
                for (Ingredient ingredient : recipe.getIngredients()) {
                    out.print(ingredient.getIngredient() + " ");
                }
                 
                out.println("</label>");
                out.println("<br><hr></div>");
                out.println("</body>");
                out.println("</html>");
                
                
            }
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
