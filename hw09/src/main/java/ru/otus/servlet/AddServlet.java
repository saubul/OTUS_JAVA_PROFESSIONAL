package ru.otus.servlet;

import ru.otus.service.CalculatorService;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "AddServlet", urlPatterns = "/add")
public class AddServlet extends AbstractOperationServlet {

    @Override
    public String getOperationResult(double a, double b) {
        return Double.toString(CalculatorService.add(a, b));
    }

}
