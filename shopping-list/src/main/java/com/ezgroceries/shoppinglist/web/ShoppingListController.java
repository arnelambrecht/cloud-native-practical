package com.ezgroceries.shoppinglist.web;

import com.ezgroceries.shoppinglist.internal.cocktail.CocktailResource;
import com.ezgroceries.shoppinglist.internal.shoppinglist.ShoppingListResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/shopping-lists", produces = "application/json")
public class ShoppingListController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingListResource createShoppingList(@RequestBody ShoppingListResource newShoppingList) {
        newShoppingList.setShoppingListId(UUID.randomUUID());
        // Create a new shoppingList and store it...

        return newShoppingList;
    }

    @PostMapping(value = "/{shoppingListId}/cocktails")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CocktailResource> addCocktails(@PathVariable long shoppingListId, @RequestBody ArrayList<CocktailResource> cocktailResources) {
        // Search for the shopping list based on the shoppingListId and save each cocktailID if that cocktail exists...

        return cocktailResources; // TODO: Not sure on how to only retrieve the id's and not the entire Cocktail object.
    }

}
