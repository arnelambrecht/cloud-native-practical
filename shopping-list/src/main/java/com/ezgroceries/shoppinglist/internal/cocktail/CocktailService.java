package com.ezgroceries.shoppinglist.internal.cocktail;

import java.util.List;

public interface CocktailService {
    List<CocktailResource> searchCocktails(String search);
}
