package com.mapache.Enotes_API_Service.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum TodoStatus {
    NOT_STARTED(1, "Not Started"),
    IN_PROGRESS(2, "In Progress"),
    COMPLETED(3, "Completed");

    private Integer id;
    private String name;

    TodoStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // lo que hace es crear un mapa estatico para buscar el estado por id
    private static final Map<Integer, TodoStatus> LOOKUP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(TodoStatus::getId, Function.identity()));

    // metodo para obtener el estado por id
    public static TodoStatus fromId(Integer id) {
        TodoStatus st = LOOKUP.get(id);
        if (st == null) {
            throw new IllegalArgumentException("Invalid TodoStatus id: " + id);
        }
        return st;
    }
}
