package ru.otus.servlet;

import ru.otus.service.CalculatorService;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "MultiplyServlet", urlPatterns = "/multiply")
public class MultiplyServlet extends AbstractOperationServlet {

    @Override
    public String getOperationResult(double a, double b) {
        return Double.toString(CalculatorService.multiply(a, b));
    }

}
