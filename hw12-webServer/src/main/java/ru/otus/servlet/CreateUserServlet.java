package ru.otus.servlet;

import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.services.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class CreateUserServlet extends HttpServlet {
    private static final String PARAM_NAME = "name";
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String USER_PAGE_TEMPLATE = "user.html";

    private final TemplateProcessor templateProcessor;
    private final DBServiceUser dbServiceUser;

    public CreateUserServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
        this.templateProcessor = templateProcessor;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USER_PAGE_TEMPLATE, Collections.emptyMap()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter(PARAM_NAME);
        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);

        dbServiceUser.saveUser(new User(name, login, password));
        response.sendRedirect("/users");
    }

}
