package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.controllers.contracts.CocktailId;
import com.ezgroceries.shoppinglist.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.persistence.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.persistence.entities.ShoppingListEntity;
import com.ezgroceries.shoppinglist.persistence.repositories.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {
    /* FIELDS */

    private final ShoppingListRepository shoppingListRepository;

    @Autowired
    private CocktailService cocktailService;


    /* CONSTRUCTORS */

    public ShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }


    /* PUBLIC METHODS */

    public ShoppingListResource createShoppingList(ShoppingListResource shoppingListResource) {
        ShoppingListEntity shoppingListEntity = new ShoppingListEntity(shoppingListResource.getShoppingListId(), shoppingListResource.getName());
        shoppingListEntity = shoppingListRepository.save(shoppingListEntity);

        return new ShoppingListResource(shoppingListEntity.getEntityId(), shoppingListEntity.getName());
    }

    public ShoppingListResource getShoppingList (UUID shoppingListId) {
        ShoppingListEntity shoppingListEntity = shoppingListRepository.findByEntityId(shoppingListId);

        return mapShoppingListEntityToResource(shoppingListEntity);
    }

    public List<ShoppingListResource> getAllShoppingLists () {
        List<ShoppingListEntity> shoppingListEntities = shoppingListRepository.findAll();

        List<ShoppingListResource> shoppingListResources = new ArrayList<>();
        for (ShoppingListEntity shoppingListEntity : shoppingListEntities) {
            ShoppingListResource shoppingListResource = mapShoppingListEntityToResource(shoppingListEntity);

            shoppingListResources.add(shoppingListResource);
        }

        return  shoppingListResources;
    }

    public List<CocktailId> addCocktailsToShoppingList (UUID shoppingListId, List<CocktailId> cocktailIds) {
        ShoppingListEntity shoppingListEntity = shoppingListRepository.findByEntityId(shoppingListId);

        if (shoppingListEntity != null) {
            List<CocktailId> addedCocktails = shoppingListEntity.getCocktails().stream()
                    .map(entity -> new CocktailId(entity.getEntityId())).collect(Collectors.toList());

            for (CocktailId cocktailId : cocktailIds) {
                if (!addedCocktails.contains(cocktailId)) {
                    // TODO: Best Practice?
                    //  Do you need to retrieve the entire cocktail or is filling in the id ok?
                    //  This way it's immediately checked if the cocktail exists.
                    if (cocktailService.cocktailExist(cocktailId.getCocktailId())) {
                        // TODO: Best practice?
                        //  Check via existence of a cocktail or let the db call fail?
                        //  If you do the check, you use the service or the repo?
                        shoppingListEntity.addCocktail(new CocktailEntity(cocktailId.getCocktailId()));
                    }
                }
            }

            shoppingListRepository.save(shoppingListEntity);

            // TODO: Best practice?
            //  Use CocktailShoppingListRepo or work with ManyToMany relations?
            //  Is it ok that each time all linked cocktails are loaded if you just need their id's?
            /*List<UUID> addedCocktails =
                    cocktailShoppingListRepository.findByShoppingListId(shoppingListId)
                            .stream().map(CocktailShoppingListEntity::getCocktailId).collect(Collectors.toList());

            for (CocktailId cocktailId : cocktailIds) {
                if (!addedCocktails.contains(cocktailId.getCocktailId())) {
                    if (cocktailService.cocktailExist(cocktailId.getCocktailId())) {
                        CocktailShoppingListEntity cocktailShoppingListEntity = new CocktailShoppingListEntity();
                        cocktailShoppingListEntity.setCocktailId(cocktailId.getCocktailId());
                        cocktailShoppingListEntity.setShoppingListId(shoppingListId);
                        cocktailShoppingListRepository.save(cocktailShoppingListEntity);
                    }
                }
            }*/
        }

        return shoppingListRepository.findByEntityId(shoppingListId).getCocktails().stream()
                .map(entity -> new CocktailId(entity.getEntityId())).collect(Collectors.toList());
    }


    /* PRIVATE METHODS */

    private ShoppingListResource mapShoppingListEntityToResource(ShoppingListEntity shoppingListEntity) {
        ShoppingListResource shoppingListResource = new ShoppingListResource(shoppingListEntity.getEntityId(), shoppingListEntity.getName());

        shoppingListResource.setIngredients(
                shoppingListEntity.getCocktails().stream()
                        .map(CocktailEntity::getIngredients)
                        .flatMap(Set::stream).distinct().collect(Collectors.toList()));
        return shoppingListResource;
    }
}
