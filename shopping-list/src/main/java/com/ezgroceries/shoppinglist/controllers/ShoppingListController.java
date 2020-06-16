package com.ezgroceries.shoppinglist.controllers;

import com.ezgroceries.shoppinglist.controllers.contracts.CocktailId;
import com.ezgroceries.shoppinglist.controllers.contracts.ShoppingListCreateRequest;
import com.ezgroceries.shoppinglist.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.services.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/shopping-lists", produces = "application/json")
public class ShoppingListController {
    private ShoppingListService shoppingListService;

    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingListResource createShoppingList(@RequestBody ShoppingListCreateRequest shoppingListCreateRequest) {
        // Create a entire new shoppinglist with just the name of the list.
        return shoppingListService.createShoppingList(new ShoppingListResource(shoppingListCreateRequest.getName()));
    }

    @PostMapping(value = "/{shoppingListId}/cocktails")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CocktailId> addCocktails(@PathVariable String shoppingListId, @RequestBody ArrayList<CocktailId> cocktailIds) {
        // Search for the shopping list based on the shoppingListId and add each cocktailID if that cocktail exists...
        return shoppingListService.addCocktailsToShoppingList(UUID.fromString(shoppingListId), cocktailIds);
    }

    @GetMapping
    public List<ShoppingListResource> getShoppingLists() {
        // Return all shopping lists in the database.
        // return getDummyShoppingListResourceList();
        return shoppingListService.getAllShoppingLists();
    }

    @GetMapping(value = "/{shoppingListId}")
    public ShoppingListResource getShoppingList(@PathVariable String shoppingListId) {
        // Return a shopping list based on a shopping list id.
        // return getDummyShoppingListResource();
        return shoppingListService.getShoppingList(UUID.fromString(shoppingListId));
    }

    /*private ShoppingListResource getDummyShoppingListResource() {
        ShoppingListResource shoppingListResource = new ShoppingListResource("Stephanie's birthday");
        shoppingListResource.setShoppingListId(UUID.fromString("90689338-499a-4c49-af90-f1e73068ad4f"));
        shoppingListResource.setIngredients(Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao"));

        return shoppingListResource;
    }*/

    /*private List<ShoppingListResource> getDummyShoppingListResourceList() {
        ShoppingListResource shoppingListResource1 = new ShoppingListResource("Stephanie's birthday");
        shoppingListResource1.setShoppingListId(UUID.fromString("4ba92a46-1d1b-4e52-8e38-13cd56c7224c"));
        shoppingListResource1.setIngredients(Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao"));

        ShoppingListResource shoppingListResource2 = new ShoppingListResource("My Birthday");
        shoppingListResource2.setShoppingListId(UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465"));
        shoppingListResource2.setIngredients(Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao"));

        return Arrays.asList(shoppingListResource1, shoppingListResource2);
    }*/
}
