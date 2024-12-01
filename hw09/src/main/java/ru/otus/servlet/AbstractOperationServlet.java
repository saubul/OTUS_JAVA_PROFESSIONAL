package ru.otus.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class AbstractOperationServlet extends HttpServlet {

    public abstract String getOperationResult(double a, double b);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        double a = Double.parseDouble(req.getParameter("a"));
        double b = Double.parseDouble(req.getParameter("b"));
        String result = getOperationResult(a, b);

        // Отображаем результат
        out.println("<html><body>");
        out.println("<h2>Result: " + result + "</h2></br>");
        out.println("<a href='index.html'>Back</a>");
        out.println("</body></html>");
    }

}

