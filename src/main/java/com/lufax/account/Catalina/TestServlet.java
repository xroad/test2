package com.lufax.account.Catalina;

import org.apache.catalina.loader.WebappClassLoader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Application;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by xroad on 2017/8/9.
 */
public class TestServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        Test.t();
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

//    @Override
//    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
//        WebappClassLoader webappClassLoader = new WebappClassLoader();
//        try {
//
//            webappClassLoader.findClass("org.springframework.context.support.FileSystemXmlApplicationContext");
//            res.
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        WebappClassLoader webappClassLoader = new WebappClassLoader();
        try {
            System.out.println("123");
            Application application = new Application();
            System.out.println(this.getClass().getClassLoader().loadClass("javax.ws.rs.core.Application"));
//            Thread.currentThread().getContextClassLoader().
//            webappClassLoader.findClass("java.lang.String");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        printWriter.println("hello");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
