package servlet;

import database.DAOFactory;
import database.Product;
import responses.AddResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));

        AddResponse addResponse = new AddResponse(DAOFactory.getInstance().getProductDaoImpl());

        response.getWriter().print(addResponse.toHTML(new Product(name, price)));
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
