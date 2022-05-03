package com.company;

import java.util.Scanner;


public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Scanner scanner2 = new Scanner(System.in);
    static IDatabase db = null;
    public static void main(String[] args) {

        String opt = "";
        boolean end = false;

        System.out.println("BASE DE DATOS mongo/mysql ?");
        String dbType = scanner.nextLine();

        if (dbType.equalsIgnoreCase("mongo")){
            db = new DatabaseMongo();
        } else if (dbType.equalsIgnoreCase("mysql")){
            db = new DatabaseMySQL();
        } else {
            System.out.println("ERROR: Opció no reconeguda");
        }

        while(!end){
            printMenu();
            System.out.print("Opcio?: ");
            opt = scanner2.nextLine();
            switch (opt){
                case "1":
                    db.selectAll().forEach(result -> {
                        if (db instanceof DatabaseMySQL){
                            System.out.println(result.toStringSql());
                        } else if (db instanceof DatabaseMongo){
                            System.out.println(result.toStringMongo());
                        }
                    });
                    break;
                case "2":
                    db.selectOne(getTitle()).forEach(result -> {
                        System.out.println(result.toString());
                    });
                    break;
                case "3":
                    db.insert(getTitle());
                    break;
                case "4":
                    db.deleteOne(getTitle());
                    break;
                case "5":
                    db.deleteAll();
                    break;
                case "50":
                    end = true;
                    break;
                default:
                    System.out.println("Error: Opció no reconeguda");
            }
        }

//        System.out.println("Title: ");
//        String title = scanner.nextLine();
//
//
//
//        if (db != null) {
//            db.insertarPelicula(title);
//        }
    }

    private static String getTitle() {
        System.out.print("Titol: ");
        return scanner.nextLine();
    }

    private static void printMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("===========Base de Dades===========\n");
        sb.append("|                                 |\n");
        sb.append("| 1. Mostrar tots els registres   |\n");
        sb.append("| 2. Cercar un registre per titol |\n");
        sb.append("| 3. Insertar un registre         |\n");
        sb.append("| 4. Eliminar un registre         |\n");
        sb.append("| 5. Eliminar tots els registres  |\n");
        sb.append("|                                 |\n");
        sb.append("| 50. Sortir                      |\n");
        sb.append("|                                 |\n");
        sb.append("===================================");
        System.out.println(sb);
    }

}
