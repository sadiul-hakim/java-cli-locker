package com.hakim.cliApp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    private static final Scanner input=new Scanner(System.in);
    public static void main(String[] args) {

        System.out.println("Welcome to our Locker 🎉");
        System.out.println("Here you can store your secret Data...");
        System.out.println("1. Log in to account:");
        System.out.println("2. Create account.");
        Path userIn;
        int userChoice=0;
        try{
            userChoice=input.nextInt();

        }catch (InputMismatchException e){
            System.out.println("Enter right option...");
        }

        if(userChoice==1){
            userIn=login();
        }else if(userChoice == 2){
            userIn=register();
        }else{
            System.out.println("Invalid option.");
            return;
        }

        if(userIn==null){
            return;
        }

        System.out.println("You are in...");

        System.out.println("1. Read Locker.");
        System.out.println("2. Write Locker.");
        System.out.println("3. Append Locker.");
        System.out.println("4. Delete Locker/Account.");

        int userAction=0;

        try{
            userAction=input.nextInt();

        }catch (InputMismatchException e){
            System.out.println("Enter right option..");
        }

        if(userAction == 1){
            read(userIn);
        }else if(userAction == 2){
            write(userIn);
        }else if(userAction == 3){
            write(userIn,true);
        }else if(userAction == 4){
            delete(userIn);
        }else{
            System.out.println("Invalid option");
        }
    }

    private static void read(Path path){
        try{
            String text=Files.readString(path);
            System.out.println("Locker text : ");
            System.out.println(text);
        }catch (IOException e){
            System.out.println("System error..");
        }
    }

    private static void write(Path path){
        String finalText="\n";
        System.out.println("Enter text('exit' to exit) :");


        while(true ){
            String text=input.nextLine();
            if(text.equals("exit"))
                break;
            finalText=finalText+"\n"+text;
        }

        try{
            Files.writeString(path,finalText);
        }catch (IOException e){
            System.out.println("System error");
        }

    }

    private static void write(Path path,boolean append){
        String textPath=path.toString();

        String finalText="\n";
        System.out.println("Enter text('exit' to exit) :");


        while(true ){
            String text=input.nextLine();
            if(text.equals("exit"))
                break;
            finalText=finalText+"\n"+text;
        }

        try( FileWriter fw=new FileWriter(textPath,append)){

            fw.write(finalText);
            fw.flush();
        }catch (IOException e){
            System.out.println("System error");
        }
    }

    private static void delete(Path path){
        try{
            Files.delete(path);
            System.out.println("Account Deleted...");
        }catch (IOException e){
            System.out.println("System error.");
        }
    }

    private static Path createFile(int code){
        String cwd=System.getProperty("user.dir");
        String filePath=cwd+ File.separator+"users"+File.separator+"user-"+code+".txt";
        Path userPath=Path.of(filePath);
        Path created=null;
        try{
            created=Files.createFile(userPath);

        }catch (IOException e){
            System.out.println("System failed");
        }

        return created;
    }

    private static boolean checkCode(int code){
        String cwd=System.getProperty("user.dir");
        File dir=new File(cwd+ File.separator+"users");
        if(!dir.exists()){
            boolean created=dir.mkdir();
        }
        String filePath=cwd+ File.separator+"users"+File.separator+"user-"+code+".txt";
        File file=new File(filePath);

        return file.exists();
    }

    private static Path createPath(int code){
        String cwd=System.getProperty("user.dir");
        String filePath=cwd+ File.separator+"users"+File.separator+"user-"+code+".txt";
        return Path.of(filePath);

    }

    private static Path register(){
        System.out.println("Enter a secret code : ");
        int code=0;
        try{
            code=input.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Enter code in number..");
            return null;
        }
        if (checkCode(code)){
            System.out.println("Locker already in use..");
            System.out.println("Try another:");
            code=input.nextInt();
        }

        return createFile(code);

    }

    private static Path login(){
        System.out.println("Secret code : ");
        int code=0;
        try{
            code=input.nextInt();

        }catch (InputMismatchException e){
            System.out.println("Enter right code..");
            return null;
        }
        boolean right=checkCode(code);
        if(!right){
            System.out.println("Invalid code..");
            System.out.println("Need to register...");
            register();
        }

        return createPath(code);

    }

}
