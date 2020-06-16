package com.ezgroceries.shoppinglist.persistence.entities;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "shopping_list")
public class ShoppingListEntity {
    /* FIELDS */

    @Id
    @Column(name = "id")
    private UUID entityId;

    @Column(name = "name")
    private String name;

    @OneToMany
    @JoinTable(
            name = "cocktail_shopping_list",
            joinColumns = @JoinColumn(name = "shopping_list_id"),
            inverseJoinColumns = @JoinColumn(name = "cocktail_id"))
    Set<CocktailEntity> cocktails;


    /* CONSTRUCTORS */

    public ShoppingListEntity() {
    }

    public ShoppingListEntity(UUID entityId, String name) {
        this.entityId = entityId;
        this.name = name;
    }


    /* GETTERS & SETTERS */

    public UUID getEntityId() {
        return entityId;
    }

    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CocktailEntity> getCocktails() {
        return cocktails;
    }

    public void setCocktails(Set<CocktailEntity> cocktails) {
        this.cocktails = cocktails;
    }

    public void addCocktail(CocktailEntity cocktail) {
        this.cocktails.add(cocktail);
    }
}
