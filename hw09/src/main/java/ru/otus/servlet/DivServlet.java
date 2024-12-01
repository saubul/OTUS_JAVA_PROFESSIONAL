package ru.otus.servlet;

import ru.otus.service.CalculatorService;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "DivServlet", urlPatterns = "/div")
public class DivServlet extends AbstractOperationServlet {

    @Override
    public String getOperationResult(double a, double b) {
        if (b == 0) {
            return "Error! Divide by 0.";
        }
        return Double.toString(CalculatorService.div(a, b));
    }

}
