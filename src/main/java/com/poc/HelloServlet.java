package com.poc;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.text.StringEscapeUtils;

public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("/login");
            return;
        }
        String user = StringEscapeUtils.escapeHtml4((String) session.getAttribute("user"));
        resp.setContentType("text/html;charset=UTF-8");
        String body = "<h1 id=\"title\" style=\"color:" + Page.COLOR + ";\">" + "Hello World" + "</h1>"
          + "<p>Signed in as " + user + "</p>"
          + "<a href=\"/login\" id=\"logout\" style=\"color:" + Page.COLOR + ";\">Sign out</a>";
        resp.getWriter().write(Page.wrap("Hello", body));
    }
}
