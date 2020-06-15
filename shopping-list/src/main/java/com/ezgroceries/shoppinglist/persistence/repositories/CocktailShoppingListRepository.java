package com.ezgroceries.shoppinglist.persistence.repositories;

import com.ezgroceries.shoppinglist.persistence.entities.CocktailShoppingListEntity;
import com.ezgroceries.shoppinglist.persistence.entities.CocktailShoppingListId;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.UUID;

public interface CocktailShoppingListRepository extends Repository<CocktailShoppingListEntity, CocktailShoppingListId> {
    List<CocktailShoppingListEntity> findByShoppingListId(UUID shoppingListId);

    CocktailShoppingListEntity save(CocktailShoppingListEntity cocktailShoppingListEntity);
}
