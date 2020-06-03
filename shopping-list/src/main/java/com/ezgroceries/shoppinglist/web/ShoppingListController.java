package com.ezgroceries.shoppinglist.web;

import com.ezgroceries.shoppinglist.internal.cocktail.CocktailResource;
import com.ezgroceries.shoppinglist.internal.shoppinglist.ShoppingListResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/shopping-lists", produces = "application/json")
public class ShoppingListController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingListResource createShoppingList(@RequestBody ShoppingListResource newShoppingList) {
        // Create a new shoppingList and store it...
        // Right now the id is set manually.
        newShoppingList.setShoppingListId(UUID.randomUUID());

        return newShoppingList;
    }

    @PostMapping(value = "/{shoppingListId}/cocktails")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CocktailResource> addCocktails(@PathVariable String shoppingListId, @RequestBody ArrayList<CocktailResource> cocktailResources) {
        // Search for the shopping list based on the shoppingListId and save each cocktailID if that cocktail exists...

        return cocktailResources;

        // TODO: Not sure on how to only retrieve the id's and not the entire Cocktail object.
        // Currently done with spring.jackson.default-property-inclusion param in application.properties but this applies to all responses
        // and results in a warning because this property isn't used (although it does the trick).
    }

    @GetMapping
    public List<ShoppingListResource> get() {
        return getDummyShoppingListResourceList();
    }

    @GetMapping(value = "/{shoppingListId}")
    public ShoppingListResource get(@PathVariable String shoppingListId) {
        return getDummyShoppingListResource();
    }

    private ShoppingListResource getDummyShoppingListResource() {
        return new ShoppingListResource(UUID.fromString("90689338-499a-4c49-af90-f1e73068ad4f"),
                "Stephanie's birthday", Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao"));
    }

    private List<ShoppingListResource> getDummyShoppingListResourceList() {
        return Arrays.asList(
                new ShoppingListResource(UUID.fromString("4ba92a46-1d1b-4e52-8e38-13cd56c7224c"),
                "Stephanie's birthday", Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao")),
                new ShoppingListResource(UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465"),
                "My Birthday", Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao")));
    }
}
