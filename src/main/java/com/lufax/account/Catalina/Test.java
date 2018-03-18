package com.lufax.account.Catalina;

import jdk.nashorn.internal.runtime.linker.Bootstrap;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.loader.WebappClassLoader;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by xroad on 2017/8/8.
 */
public class Test extends StandardRoot {
    private static final String CLASS_FILE_SUFFIX = ".class";
//    protected Map<String, ResourceEntry> resourceEntries =
//            new ConcurrentHashMap<>();


    public static void t(){
        System.out.println(123);

    }
    private String binaryNameToPath(String binaryName, boolean withLeadingSlash) {
        // 1 for leading '/', 6 for ".class"
        StringBuilder path = new StringBuilder(7 + binaryName.length());
        if (withLeadingSlash) {
            path.append('/');
        }
        path.append(binaryName.replace('.', '/'));
        path.append(CLASS_FILE_SUFFIX);
        return path.toString();
    }

//    private void processWebInfLib() {
//        WebResource[] possibleJars = listResources("/WEB-INF/lib", false);
//
//        for (WebResource possibleJar : possibleJars) {
//            if (possibleJar.isFile() && possibleJar.getName().endsWith(".jar")) {
//                createWebResourceSet(ResourceSetType.CLASSES_JAR,
//                        "/WEB-INF/classes", possibleJar.getURL(), "/");
//            }
//        }
//    }

    public static void main(String[] args) throws ClassNotFoundException, LifecycleException, IOException {
        Map map = new HashMap<String, String>();
        File f = new File("D:\\code\\test2\\target\\test2-1.0-SNAPSHOT\\WEB-INF\\lib");
        File[] result = f.listFiles();
        for (File file : result) {
            String jarFilePath = file.getAbsolutePath();
            if (!jarFilePath.endsWith(".jar")) {
                continue;
            }
            JarFile jarFile = new JarFile(file);
            Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
            while (jarEntryEnumeration.hasMoreElements()) {
                String jarEntryName = jarEntryEnumeration.nextElement().getName();
                if (!jarEntryName.endsWith(".class")) {
                    continue;
                }
                if (map.containsKey(jarEntryName)) {
                    System.out.println(String.format("jar:[%s][%s],class:[%s]", map.get(jarEntryName), jarFilePath, jarEntryName));
                } else {
                    map.put(jarEntryName, file.getAbsolutePath());
                }
            }
        }
    }

}
