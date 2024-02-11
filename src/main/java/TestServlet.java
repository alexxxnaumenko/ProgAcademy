import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class TestServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html"); // Content-Type: text/html
        // MIME types

        PrintWriter pw = resp.getWriter();
        pw.println("<html><head><title>Prog Academy Test</title></head>");
        pw.println("<body><h1>Hello, Java Junior :)</h1></body></html>");
    }
}
