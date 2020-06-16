package com.ezgroceries.shoppinglist.controllers.contracts;

import java.util.List;
import java.util.UUID;

public class ShoppingListCreateRequest {
    /* FIELDS */

    private String name;


    /* CONSTRUCTORS */

    public ShoppingListCreateRequest() {
    }


    /* GETTERS & SETTERS */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
