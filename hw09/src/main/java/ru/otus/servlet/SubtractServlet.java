package ru.otus.servlet;

import ru.otus.service.CalculatorService;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "SubtractServlet", urlPatterns = "/subtract")
public class SubtractServlet extends AbstractOperationServlet {

    @Override
    public String getOperationResult(double a, double b) {
        return Double.toString(CalculatorService.subtract(a, b));
    }

}
