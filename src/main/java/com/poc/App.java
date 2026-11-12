package com.poc;

import java.io.File;
import java.nio.file.Files;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class App {

    private final Tomcat tomcat = new Tomcat();

    public int start(int port) throws Exception {
        File base = Files.createTempDirectory("poc-tomcat").toFile();
        tomcat.setBaseDir(base.getAbsolutePath());
        tomcat.setPort(port);
        tomcat.getConnector();

        Context ctx = tomcat.addContext("", base.getAbsolutePath());
        Tomcat.addServlet(ctx, "login", new LoginServlet());
        Tomcat.addServlet(ctx, "hello", new HelloServlet());
        Tomcat.addServlet(ctx, "health", new HealthServlet());
        ctx.addServletMappingDecoded("/login", "login");
        ctx.addServletMappingDecoded("/hello", "hello");
        ctx.addServletMappingDecoded("/health", "health");
        ctx.addServletMappingDecoded("", "login");
        ctx.addServletMappingDecoded("/", "login");

        tomcat.start();
        return tomcat.getConnector().getLocalPort();
    }

    public void stop() throws LifecycleException {
        tomcat.stop();
        tomcat.destroy();
    }

    public void await() {
        tomcat.getServer().await();
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        int port = app.start(8080);
        System.out.println("App running: http://localhost:" + port + "/login");
        app.await();
    }
}
