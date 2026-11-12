# hello-login-app-a (Devin POC)

Small Java app: login page, then a Hello World page. Uses vulnerable library
versions on purpose so Devin can fix them.

    mvn exec:java        # http://localhost:8080/login  (admin / admin123)
    mvn clean test       # regression gate Devin must keep green
