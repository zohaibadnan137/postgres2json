package org.example;

public class Main {
    // This code has been used for testing purposes
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/bitnine";
        String username = "postgres";
        String password = "postgres";

        Postgres2JSONDriver driver = new Postgres2JSONDriver(url, username, password);
        System.out.println(driver.executeQuery("SELECT * from public.user_table"));
    }
}
