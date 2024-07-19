package br.com.rocketseat.hiokdev.planner_java.factory;

import java.util.List;

public class EmailFactory {

    public static String getEmail() {
        return "joao@email.com";
    }

    public static List<String> getEmailList() {
        return List.of(
                "jose@email.com",
                "maria@email.com",
                "ana@email.com"
        );
    }

}
