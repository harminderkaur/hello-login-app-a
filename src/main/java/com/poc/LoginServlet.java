package com.poc;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(LoginServlet.class);
    // Demo credentials only
    private static final String USER = "admin";
    private static final String PASS = "admin123";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        render(resp, req.getParameter("error") != null ? "Invalid username or password." : "");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String user = req.getParameter("username");
        String pass = req.getParameter("password");
        LOG.info("Login attempt for user {}", user);
        if (USER.equals(user) && PASS.equals(pass)) {
            req.getSession(true).setAttribute("user", user);
            resp.sendRedirect("/hello");
        } else {
            resp.sendRedirect("/login?error=1");
        }
    }

    private void render(HttpServletResponse resp, String error) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String body = "<h1 id=\"title\" style=\"color:" + Page.COLOR + ";\">Login</h1>"
          + "<p id=\"error\" style=\"color:#b00;min-height:18px;\">" + StringEscapeUtils.escapeHtml4(error) + "</p>"
          + "<form method=\"post\" action=\"/login\">"
          + "<p><label>Username<br><input name=\"username\" id=\"username\" style=\"width:100%;padding:8px;box-sizing:border-box;\"></label></p>"
          + "<p><label>Password<br><input name=\"password\" id=\"password\" type=\"password\" style=\"width:100%;padding:8px;box-sizing:border-box;\"></label></p>"
          + Page.button("submit", "Sign in")
          + "</form>";
        resp.getWriter().write(Page.wrap("Login", body));
    }
}
