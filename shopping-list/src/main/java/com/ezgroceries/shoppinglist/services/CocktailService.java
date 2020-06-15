package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.external.cocktail.CocktailDBClient;
import com.ezgroceries.shoppinglist.external.cocktail.CocktailDBResponse;
import com.ezgroceries.shoppinglist.internal.cocktail.CocktailResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CocktailService {
    @Autowired
    CocktailDBClient cocktailDBClient;

    public List<CocktailResource> searchCocktails(String search) {
        List<CocktailResource> cocktailResourceList = new ArrayList<>();

        CocktailDBResponse cocktailDBResponse = cocktailDBClient.searchCocktails(search);
        List<CocktailDBResponse.DrinkResource> drinkResourceList = cocktailDBResponse.getDrinks();

        if (drinkResourceList == null) return cocktailResourceList;

        for (CocktailDBResponse.DrinkResource drinkResource : drinkResourceList) {
            CocktailResource cocktailResource = new CocktailResource();

            cocktailResource.setCocktailId(UUID.randomUUID());
            cocktailResource.setGlass(drinkResource.getStrGlass());
            cocktailResource.setImage(drinkResource.getStrDrinkThumb());
            cocktailResource.setInstructions(drinkResource.getStrInstructions());
            cocktailResource.setName(drinkResource.getStrDrink());
            cocktailResource.setIngredients(this.listIngredients(drinkResource));

            cocktailResourceList.add(cocktailResource);
        }

        return cocktailResourceList;
    }

    private List<String> listIngredients(CocktailDBResponse.DrinkResource drinkResource) {
        List<String> list = new ArrayList<>();

        if (drinkResource.getStrIngredient1() != null) list.add(drinkResource.getStrIngredient1());
        if (drinkResource.getStrIngredient2() != null) list.add(drinkResource.getStrIngredient2());
        if (drinkResource.getStrIngredient3() != null) list.add(drinkResource.getStrIngredient3());
        if (drinkResource.getStrIngredient4() != null) list.add(drinkResource.getStrIngredient4());
        if (drinkResource.getStrIngredient5() != null) list.add(drinkResource.getStrIngredient5());
        if (drinkResource.getStrIngredient6() != null) list.add(drinkResource.getStrIngredient6());
        if (drinkResource.getStrIngredient7() != null) list.add(drinkResource.getStrIngredient7());
        if (drinkResource.getStrIngredient8() != null) list.add(drinkResource.getStrIngredient8());
        if (drinkResource.getStrIngredient9() != null) list.add(drinkResource.getStrIngredient9());
        if (drinkResource.getStrIngredient10() != null) list.add(drinkResource.getStrIngredient10());
        if (drinkResource.getStrIngredient11() != null) list.add(drinkResource.getStrIngredient11());
        if (drinkResource.getStrIngredient12() != null) list.add(drinkResource.getStrIngredient12());
        if (drinkResource.getStrIngredient13() != null) list.add(drinkResource.getStrIngredient13());
        if (drinkResource.getStrIngredient14() != null) list.add(drinkResource.getStrIngredient14());
        if (drinkResource.getStrIngredient15() != null) list.add(drinkResource.getStrIngredient15());

        return list;
    }
}
