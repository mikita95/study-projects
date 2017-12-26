package servlet;

import database.DAOFactory;
import responses.Response;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QueryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        DAOFactory daoFactory = DAOFactory.getInstance();
        Response commandResponse = Response.parse(command, daoFactory.getProductDaoImpl());

        if (commandResponse != null) {
            String html = commandResponse.toHTML();
            response.getWriter().print(html);
        } else response.getWriter().println("Unknown command: " + command);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
