package com.ahnv;

import com.ahnv.crypt.CryptionHelper;
import com.ahnv.db.Database;
import com.ahnv.entities.Password;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;


/**
 * Created by Abhinav.
 */
public class PasswordManager {
    Database db = new Database();

    public void MainMenu(){
        System.out.println("Select an option : ");
        System.out.println("1. Add Records");
        System.out.println("2. Update Record");
        System.out.println("3. List All Record");
        System.out.println("4. Generate Random Password");
        System.out.println("5. Delete Record");
        System.out.println("6. Exit");
    }

    public void InsertRecord(){
        Scanner sc = new Scanner(System.in);
        Password p = new Password();
        System.out.print("\nEnter Name : "); p.setName(sc.nextLine());
        System.out.print("\nEnter Username : "); p.setUsername(sc.nextLine());
        System.out.print("\nEnter Password : "); p.setPassword(CryptionHelper.encrypt(sc.nextLine()));
        System.out.print("\nEnter URL : "); p.setUrl(sc.nextLine());
        System.out.print("\nEnter Notes : "); p.setNotes(sc.nextLine());
        db.insert(p);
    }

    public void PrintRecord(int id, boolean shown){
        Password p = db.findOne(id);
        if (p != null) {
            System.out.println("ID : " + p.getId());
            System.out.println("Name : " + p.getName());
            System.out.println("URL : " + p.getUrl());
            System.out.println("Username : " + p.getUsername());
            if (shown)
                System.out.println("Password : " + CryptionHelper.decrypt(p.getPassword()));
            else
                for (int i = 0; i < CryptionHelper.decrypt(p.getPassword()).length(); i++)
                    System.out.print("*");
            System.out.print("\n");
            System.out.print("Would you like to view the password ?");
            char ch = 'n';
            try {
                ch = (char) System.in.read();
                if (ch == 'y'){
                    System.out.println("Password : " + CryptionHelper.decrypt(p.getPassword()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{
            System.out.println("Not Found");
        }
    }

    public void PrintRecords(List<Password> list){
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Password p = list.get(i);
                System.out.println("ID : " + p.getId() + "\tName : " + p.getName());
            }
            System.out.println("Would you like to view Particular Record ?");
            char ch = 'n';
            try {
                ch = (char) System.in.read();
                if (ch == 'y') {
                    System.out.println("Enter ID of Record ?");
                    int id = (new Scanner(System.in)).nextInt();
                    PrintRecord(id, false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("No Records Found");
        }
    }

    public void Update(){
        System.out.println("Enter ID of Record ?");
        int id = (new Scanner(System.in)).nextInt();
        PrintRecord(id,false);
        Password p = db.findOne(id);
        char ch = 'y';
        do {

            System.out.println("What would you like to update ?");
            System.out.println("1. Name");
            System.out.println("2. URL");
            System.out.println("3. Username");
            System.out.println("4. Password");
            System.out.println("5. Notes");
            int c = (new Scanner(System.in)).nextInt();
            switch (c) {
                case 1:
                    p.setName((new Scanner(System.in)).nextLine());
                    break;
                case 2:
                    p.setUrl((new Scanner(System.in)).nextLine());
                    break;
                case 3:
                    p.setUsername((new Scanner(System.in)).nextLine());
                    break;
                case 4:
                    p.setPassword(CryptionHelper.encrypt((new Scanner(System.in)).nextLine()));
                    break;
                case 5:
                    p.setNotes((new Scanner(System.in)).nextLine());
                    break;
                default:
                    break;
            }
            System.out.println("\nContinue ?");
            ch = (new Scanner(System.in)).next().charAt(0);
        } while (ch == 'y');
        db.update(p);
    }

    public void Remove(){
        System.out.println("Enter ID of Record ?");
        int id = (new Scanner(System.in)).nextInt();
        PrintRecord(id,false);
        Password p = db.findOne(id);
        db.remove(p);
    }

    public static void main(String[] args){
        PasswordManager pm = new PasswordManager();
        Scanner sc = new Scanner(System.in);
        int ch;
        char c = 'y';
        do {
        pm.MainMenu();
        System.out.println("Enter Your Choice : ");
        ch = sc.nextInt();
        switch (ch){
            case 1:
                pm.InsertRecord();
                break;
            case 2:
                pm.Update();
                break;
            case 3:
                pm.PrintRecords(pm.db.findAll());
                break;
            case 4:
                try {
                    System.out.print("\nEnter min length : "); int minLen = sc.nextInt();
                    System.out.print("\nEnter max length : "); int maxLen = sc.nextInt();
                    System.out.print("\nEnter Number of Capital Letters : "); int noOfCAPSAlpha = sc.nextInt();
                    System.out.print("\nEnter Number of Digits : "); int noOfDigits = sc.nextInt();
                    System.out.print("\nEnter Number of Special Characters : "); int noOfSplChars = sc.nextInt();
                    System.out.println(RandomPasswordGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits, noOfSplChars));
                }catch (IllegalArgumentException e){
                    System.out.print(e);
                }
                break;
            case 5:
                pm.Remove();
                break;
            case 6: return;
            default:
                break;
        }
        System.out.println("\nContinue Menu ?");
        ch = sc.next().charAt(0);
        }while (ch == 'y');
    }
}
