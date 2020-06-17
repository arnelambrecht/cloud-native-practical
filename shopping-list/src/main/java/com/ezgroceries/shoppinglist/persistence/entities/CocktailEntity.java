package com.ezgroceries.shoppinglist.persistence.entities;

import com.ezgroceries.shoppinglist.utils.StringSetConverter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "cocktail")
public class CocktailEntity {
    /* FIELDS */

    @Id
    @Column(name = "id")
    private UUID entityId;

    @Column(name = "id_drink")
    private String idDrink;

    @Column(name = "name")
    private String name;

    @Column(name = "ingredients")
    @Convert(converter = StringSetConverter.class)
    private Set<String> ingredients;

    @Column(name = "glass")
    private String glass;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "image_link")
    private String imageLink;


    /* GETTERS & SETTERS */

    public UUID getEntityId() { return entityId; }

    public void setEntityId(UUID entityId) { this.entityId = entityId; }

    public String getIdDrink() { return idDrink; }

    public void setIdDrink(String idDrink) { this.idDrink = idDrink; }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getGlass() { return glass; }

    public void setGlass(String glass) { this.glass = glass; }

    public String getInstructions() { return instructions; }

    public void setInstructions(String instructions) { this.instructions = instructions; }

    public String getImageLink() { return imageLink; }

    public void setImageLink(String imageLink) { this.imageLink = imageLink; }


    /* CONSTRUCTORS */

    public CocktailEntity() {
    }

    public CocktailEntity(UUID entityId) {
        this.entityId = entityId;
    }
}