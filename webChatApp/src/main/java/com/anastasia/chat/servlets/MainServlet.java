package com.anastasia.chat.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Meow on 03.05.16.
 */

@WebServlet(value = "/login")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String isLogged = (String)req.getSession().getAttribute("isLogged");

        if(isLogged != null && isLogged.equals("true")){
            req.getRequestDispatcher("test.jsp").forward(req, resp);
        }else{
            req.getRequestDispatcher("start.jsp").forward(req, resp);
        }
    }
}


