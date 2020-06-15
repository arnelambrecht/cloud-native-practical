package com.ezgroceries.shoppinglist.controllers;

import com.ezgroceries.shoppinglist.controllers.CocktailController;
import com.ezgroceries.shoppinglist.model.CocktailResource;
import com.ezgroceries.shoppinglist.services.external.CocktailDBClient;
import com.ezgroceries.shoppinglist.services.external.CocktailDBResponse;
import com.ezgroceries.shoppinglist.services.CocktailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

    @MockBean
    private CocktailService cocktailService;

    @Test
    public void testGetCocktails() throws Exception {
        given(cocktailService.searchCocktails("all"))
                .willReturn(createTestCocktailResourceList());

        mockMvc.perform(get("/cocktails?search=all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Margerita"))
                .andExpect(jsonPath("$[0].instructions", containsString("Take care to moisten")))
                .andExpect(jsonPath("$[1].name").value("Blue Margerita"))
                .andExpect(jsonPath("$[1].instructions", containsString("Dip rim in coarse salt")));

        verify(cocktailService).searchCocktails("all");
    }

    private List<CocktailResource> createTestCocktailResourceList() {
        CocktailResource cocktailResource1 = new CocktailResource(UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4"),
                "Margerita","Cocktail glass","Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..",
                "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
                Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt"));

        CocktailResource cocktailResource2 = new CocktailResource(UUID.fromString("d615ec78-fe93-467b-8d26-5d26d8eab073"),
                "Blue Margerita","Cocktail glass","Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..",
                "https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg",
                Arrays.asList("Tequila", "Blue Curacao", "Lime juice", "Salt"));

        return Arrays.asList(cocktailResource1, cocktailResource2);
    }
}
