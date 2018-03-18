package com.lufax.account.Catalina;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@WebListener
public class TestListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Map map = new HashMap<String, List<String>>();
        File f = new File("D:\\code\\test2\\target\\test2-1.0-SNAPSHOT\\WEB-INF\\lib");
        String jarParentFilePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//        File f = new File(jarParentFilePath.substring(0,jarParentFilePath.length()-8) + "lib");
        File[] result = f.listFiles();
        for (File file : result) {
            String jarFilePath = file.getAbsolutePath();
            if (!jarFilePath.endsWith(".jar")) {
                continue;
            }
            JarFile jarFile = null;
            try {
                jarFile = new JarFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
            while (jarEntryEnumeration.hasMoreElements()) {
                String jarEntryName = jarEntryEnumeration.nextElement().getName();
                if (!jarEntryName.endsWith(".class")) {
                    continue;
                }
                if (map.containsKey(jarEntryName)) {
                    System.out.println(String.format("jar:[%s][%s],class:[%s]", map.get(jarEntryName), jarFilePath, jarEntryName));
                    List<String> jars= (List<String>) map.get(jarEntryName);
                    jars.add(jarFilePath);
                } else {
                    List<String> list=new ArrayList<String>();
                    list.add(jarFilePath);
                    map.put(jarEntryName, list);
                }
            }
        }

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    public static void main(String[] args) {
        new TestListener().contextInitialized(null);
    }
}
