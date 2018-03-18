package com.lufax.account.Catalina;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Test6 {


    public static void main(String[] args) throws FileNotFoundException {
        File file=new File("123.txt");
        FileInputStream  fileInputStream=new FileInputStream(file);

    }
}
