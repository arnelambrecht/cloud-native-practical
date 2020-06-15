package com.ezgroceries.shoppinglist.controllers.contracts;

import java.util.UUID;

public class CocktailId {
    /* FIELDS */

    private UUID cocktailId;

    /* CONSTRUCTORS */

    public CocktailId() {
    }

    public CocktailId(UUID cocktailId) {
        this.cocktailId = cocktailId;
    }

    /* GETTERS & SETTERS */

    public UUID getCocktailId() {
        return cocktailId;
    }

    public void setCocktailId(UUID cocktailId) {
        this.cocktailId = cocktailId;
    }
}
