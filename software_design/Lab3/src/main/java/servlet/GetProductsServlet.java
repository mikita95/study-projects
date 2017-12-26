package servlet;

import database.DAOFactory;
import responses.GetResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetResponse getResponse = new GetResponse(DAOFactory.getInstance().getProductDaoImpl());

        response.getWriter().print(getResponse.toHTML());
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
