import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CalculateStatistics extends HttpServlet {


    final ConcurrentMap<String, Integer> generalCounter = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.setAttribute("generalCounter", generalCounter);
        }
        resp.sendRedirect("survey.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session != null) {
            ConcurrentMap<String, Integer> sessionCounter = (ConcurrentMap<String, Integer>) session.getAttribute("sessionCounter");
            if (sessionCounter == null) sessionCounter = new ConcurrentHashMap<>();
            String language = req.getParameter("language");
            calculateStatisctics(language, generalCounter);
            calculateStatisctics(language, sessionCounter);
            session.setAttribute("sessionCounter", sessionCounter);
            session.setAttribute("generalCounter", generalCounter);
        }
        resp.sendRedirect("survey.jsp");
    }

    private void calculateStatisctics(String paramName, ConcurrentMap<String, Integer> counter) {
        Integer count = counter.get(paramName);
        if (count == null) {
            count = 0;
        }
        counter.put(paramName, count + 1);
    }
}
