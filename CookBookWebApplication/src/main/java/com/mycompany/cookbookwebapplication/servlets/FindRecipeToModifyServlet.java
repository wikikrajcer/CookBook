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
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet is responsible for finding recipe to modify
 * @version 4.0
 * @author Wiktoria Krajcer
 */
@WebServlet(name = "ModifyRecipeServlet", urlPatterns = {"/modifyRecipe"})
public class FindRecipeToModifyServlet extends HttpServlet {

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

        String name = request.getParameter("recipeToModify");

        RecipeModel recipeToModify = StartUp.getModel().findRecipeByName(request.getParameter("recipeToModify"));

        if (recipeToModify == null) {
            response.sendError(response.SC_BAD_REQUEST, "Przepis o podanej nazwie nie istnieje!");
        } else {
            String dish = recipeToModify.getDish();
            String preparationTime = String.format("%02d:%02d", recipeToModify.getPreparationTime().toHoursPart(), recipeToModify.getPreparationTime().toMinutesPart());
            String author = recipeToModify.getAuthor();
            String cuisine = recipeToModify.getCuisine().toString();
            ArrayList<Ingredient> ingredients = recipeToModify.getIngredients();
            String ingredientsList = "";

            for (Ingredient ingredient : ingredients) {
                ingredientsList = ingredientsList + ingredient.getIngredient() + ",";
            }

            try ( PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Zmodyfikuj przepis</title>");
                out.println("<link rel=\"stylesheet\" href=\"style.css\">");
                out.println("</head>");
                out.println("<body>");
                
                out.println("<div class=\"container\">");
                out.println("<hr><h1>Zmodyfikuj przepis</h1><hr>");
                out.println("<form action=\"editRecipe\" method=\"POST\">");
                out.println("<p>Nazwa przepisu:</p><input type=text size=20 name=dish readonly value=" + dish + ">");
                out.println("<p>Autor: </p><input type=text size=20 name=author value=" + author + ">");
                out.println("<p>Czas przygotowania: </p><input type=text size=20 name=preparationTime value=" + preparationTime + ">");
                out.println("<p>Składniki: </p><input type=text size=30 name=ingredients value=" + ingredientsList + ">");
                out.println("<p>Kuchnia: </p><input type=text size=20 name=cuisine value=" + cuisine + "><br><br>");
                out.println("<input type=\"submit\" value=\"Zmodyfikuj przepis\"/>");
                out.println("</form>");
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
