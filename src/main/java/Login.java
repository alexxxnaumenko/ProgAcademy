import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Login extends HttpServlet {

    static final Map<String, String> cred = new HashMap<String, String>();

    static {
        cred.put("user", "1");
        cred.put("admin", "2");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").trim();
        String passwordParam = req.getParameter("password");
        if (login == null || "".equals(login) || passwordParam == null || "".equals(passwordParam)) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String passwordDB = cred.get(login);
        if (passwordDB.equals(passwordParam)) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user_login", login);
            resp.sendRedirect("statistics");
        } else {
            resp.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String a = request.getParameter("a");
        HttpSession session = request.getSession(false);

        if ("exit".equals(a) && (session != null)) {
            session.removeAttribute("user_login");
            session.removeAttribute("sessionCounter");
        }
        response.sendRedirect("login.jsp");
    }
}
