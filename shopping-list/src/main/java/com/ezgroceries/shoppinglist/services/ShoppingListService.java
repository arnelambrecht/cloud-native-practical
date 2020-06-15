package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.controllers.contracts.CocktailId;
import com.ezgroceries.shoppinglist.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.persistence.entities.CocktailShoppingListEntity;
import com.ezgroceries.shoppinglist.persistence.entities.ShoppingListEntity;
import com.ezgroceries.shoppinglist.persistence.repositories.CocktailRepository;
import com.ezgroceries.shoppinglist.persistence.repositories.CocktailShoppingListRepository;
import com.ezgroceries.shoppinglist.persistence.repositories.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {
    private final ShoppingListRepository shoppingListRepository;
    private final CocktailShoppingListRepository cocktailShoppingListRepository;

    @Autowired
    private CocktailService cocktailService;

    public ShoppingListService(ShoppingListRepository shoppingListRepository, CocktailShoppingListRepository cocktailShoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.cocktailShoppingListRepository = cocktailShoppingListRepository;
    }

    public ShoppingListResource create(ShoppingListResource shoppingListResource) {
        ShoppingListEntity shoppingListEntity = new ShoppingListEntity(shoppingListResource.getShoppingListId(), shoppingListResource.getName());

        ShoppingListEntity createdShoppingListEntity = shoppingListRepository.save(shoppingListEntity);

        return new ShoppingListResource(createdShoppingListEntity.getEntityId(), createdShoppingListEntity.getName());
    }

    public Boolean shoppingListExist (UUID shoppingListId) {
        return (this.shoppingListRepository.countByEntityId(shoppingListId) > 0);
    }

    public List<CocktailId> addCocktailsToShoppingList (UUID shoppingListId, List<CocktailId> cocktailIds) {
        if (this.shoppingListExist(shoppingListId)) {
            List<UUID> addedCocktails =
                    cocktailShoppingListRepository.findByShoppingListId(shoppingListId)
                            .stream().map(CocktailShoppingListEntity::getCocktailId).collect(Collectors.toList());

            for (CocktailId cocktailId : cocktailIds) {
                if (!addedCocktails.contains(cocktailId.getCocktailId())) {
                    // TODO: Check via existence via service or repo?
                    if (cocktailService.cocktailExist(cocktailId.getCocktailId())) {
                        CocktailShoppingListEntity cocktailShoppingListEntity = new CocktailShoppingListEntity();
                        cocktailShoppingListEntity.setCocktailId(cocktailId.getCocktailId());
                        cocktailShoppingListEntity.setShoppingListId(shoppingListId);
                        cocktailShoppingListRepository.save(cocktailShoppingListEntity);
                    }

                    // Else: do nothing or throw exception
                }
            }
        }

        // Else: do nothing or throw exception

        // TODO: Get list back from database or just create it here?
        return cocktailShoppingListRepository.findByShoppingListId(shoppingListId).stream().map(
                entity -> new CocktailId(entity.getCocktailId())
        ).collect(Collectors.toList());
    }
}
