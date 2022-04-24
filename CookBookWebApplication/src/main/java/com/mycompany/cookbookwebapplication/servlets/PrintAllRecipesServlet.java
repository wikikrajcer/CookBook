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
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet is responsible for printing all recipes
 *
 * @version 4.0
 * @author Wiktoria Krajcer
 */
@WebServlet(name = "PrintAllRecipesServlet", urlPatterns = {"/printAllRecipes"})
public class PrintAllRecipesServlet extends HttpServlet {


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

        Cookie[] cookies = request.getCookies();
        String lastAdded = "brak";
        String lastModified = "brak";
        String lastDeleted = "brak";

        List<RecipeModel> retrievedRecipes = StartUp.getModel().findAllRecipes();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("lastAdded")) {
                    lastAdded = cookie.getValue();
                } else if (cookie.getName().equals("lastModified")) {
                    lastModified = cookie.getValue();
                } else if (cookie.getName().equals("lastDeleted")) {
                    lastDeleted = cookie.getValue();
                }
            }
        }

        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Lista przepisów</title>");
            out.println("<link rel=\"stylesheet\" href=\"style.css\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"container\">");
            out.println("<hr><h1>Lista przepisów (baza danych)</h1><hr><br>");
            if (retrievedRecipes == null) {
                out.println("<h4>Lista przepisów jest pusta!</h4>");
            } else {

                out.print("<table>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>Potrawa</th>");
                out.print("<th>Czas przygotowania</th>");
                out.print("<th>Kuchnia</th>");
                out.print("<th>Autor</th>");
                out.print("<th>Lista składników</th>");
                out.print("</tr>");
                out.print("</thead>");

                for (var recipe : retrievedRecipes) {
                    out.print("<tr>");
                    out.println();
                    out.println("<td>" + recipe.getDish() + "</td>");
                    out.println("<td>" + String.format("%02dh %02dmin", recipe.getPreparationTime().toHoursPart(), recipe.getPreparationTime().toMinutesPart()) + "</td>");
                    out.println("<td>" + recipe.getCuisine() + "</td>");
                    out.println("<td>" + recipe.getAuthor() + "</td>");
                    out.print("<td>");
                    for (Ingredient ingredient : recipe.getIngredients()) {
                        out.print(ingredient.getIngredient() + " ");
                    }
                    out.print("</td>");
                    out.print("</tr>");
                }
                out.print("</table><br>");
                out.println("<form action=\"deleteRecipe\" method=\"POST\">");
                out.println("<p>Podaj nazwę przepisu, który chcesz usunąć:</p><input type=text name=recipeToDelete></br>");
                out.println("<input type=\"submit\" value=\"Usuń przepis\"/>");
                out.println("</form>");

                out.println("<form action=\"modifyRecipe\" method=\"POST\">");
                out.println("<p>Podaj nazwę przepisu, który chcesz zmodyfikować:</p> <input type=text name=recipeToModify></br>");
                out.println("<input type=\"submit\" value=\"Zmodyfikuj przepis\"/>");
                out.println("</form><br>");
            }

            out.println("<hr><h3>Ostatnie zmiany (cookies): </h3>");
            out.println("Ostatnio dodany przepis: " + lastAdded);
            out.println("<br>Ostatnio modyfikowany przepis: " + lastModified);
            out.println("<br>Ostatnio usunięty przepis: " + lastDeleted);
            out.println("<br><br><hr>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
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
