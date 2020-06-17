package com.ezgroceries.shoppinglist.persistence.repositories;

import com.ezgroceries.shoppinglist.persistence.entities.CocktailEntity;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.UUID;

public interface CocktailRepository extends Repository<CocktailEntity, UUID> {
    /* CREATE */

    CocktailEntity save(CocktailEntity cocktailEntity);


    /* FIND */

    List<CocktailEntity> findByIdDrinkIn(List<String> idDrinks);
    List<CocktailEntity> findByNameContainingIgnoreCase(String search);


    /* OTHER */

    long countByEntityId(UUID cocktailId);

}
