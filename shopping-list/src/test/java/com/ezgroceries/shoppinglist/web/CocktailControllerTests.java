package com.ezgroceries.shoppinglist.web;

import com.ezgroceries.shoppinglist.controllers.CocktailController;
import com.ezgroceries.shoppinglist.external.cocktail.CocktailDBClient;
import com.ezgroceries.shoppinglist.external.cocktail.CocktailDBResponse;
import com.ezgroceries.shoppinglist.services.CocktailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(CocktailController.class)
public class CocktailControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CocktailService cocktailService;

    @MockBean
    private CocktailDBClient cocktailDBClient;

    @Test
    public void testGetCocktails() throws Exception {
        given(cocktailDBClient.searchCocktails("all"))
                .willReturn(createTestCocktailDBResponse());

        mockMvc.perform(get("/cocktails?search=all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Margerita"))
                .andExpect(jsonPath("$[0].instructions", containsString("Take care to moisten")))
                .andExpect(jsonPath("$[1].name").value("Blue Margerita"))
                .andExpect(jsonPath("$[1].instructions", containsString("Dip rim in coarse salt")));

        verify(cocktailDBClient).searchCocktails("all");
    }

    private CocktailDBResponse createTestCocktailDBResponse() {
        CocktailDBResponse cocktailDBResponse = new CocktailDBResponse();

        CocktailDBResponse.DrinkResource drinkResource1 = new CocktailDBResponse.DrinkResource();
        drinkResource1.setIdDrink("1");
        drinkResource1.setStrDrink("Margerita");
        drinkResource1.setStrGlass("Cocktail glass");
        drinkResource1.setStrInstructions("Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..");
        drinkResource1.setStrDrinkThumb("https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg");
        drinkResource1.setStrIngredient1("Tequila");
        drinkResource1.setStrIngredient1("Triple sec");
        drinkResource1.setStrIngredient1("Lime juice");
        drinkResource1.setStrIngredient1("Salt");

        CocktailDBResponse.DrinkResource drinkResource2 = new CocktailDBResponse.DrinkResource();
        drinkResource2.setIdDrink("1");
        drinkResource2.setStrDrink("Blue Margerita");
        drinkResource2.setStrGlass("Cocktail glass");
        drinkResource2.setStrInstructions("Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..");
        drinkResource2.setStrDrinkThumb("https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg");
        drinkResource2.setStrIngredient1("Tequila");
        drinkResource2.setStrIngredient1("Blue Curacao");
        drinkResource2.setStrIngredient1("Lime juice");
        drinkResource2.setStrIngredient1("Salt");

        List<CocktailDBResponse.DrinkResource> drinkResourceList = new ArrayList<>();
        drinkResourceList.add(drinkResource1);
        drinkResourceList.add(drinkResource2);

        cocktailDBResponse.setDrinks(drinkResourceList);

        return cocktailDBResponse;
    }
}
