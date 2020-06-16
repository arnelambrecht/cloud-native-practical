package com.ezgroceries.shoppinglist.persistence.repositories;

import com.ezgroceries.shoppinglist.persistence.entities.ShoppingListEntity;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.UUID;

public interface ShoppingListRepository extends Repository<ShoppingListEntity, UUID> {
    /* CREATE */

    ShoppingListEntity save(ShoppingListEntity shoppingListEntity);


    /* FIND */

    ShoppingListEntity findByEntityId(UUID shoppingListId);

    List<ShoppingListEntity> findAll();

}
