package com.ezgroceries.shoppinglist.controllers;

import com.ezgroceries.shoppinglist.controllers.contracts.CocktailId;
import com.ezgroceries.shoppinglist.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.services.ShoppingListService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(ShoppingListController.class)
public class ShoppingListControllerTests {
    final String shoppingListId = "23b3d85a-3928-41c0-a533-6538a71e17c4";
    final String cocktailId1 = "31e41ad0-0c54-4604-8316-2639d1b46094";
    final String cocktailId2 = "d615ec78-fe93-467b-8d26-5d26d8eab073";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingListService shoppingListService;

    // TODO: HOW TO?
    //  How to mock the repo instead of the service?
    @Test
    public void testCreateShoppingList() throws Exception {
        ShoppingListResource shoppingListResourceExpectedResult = new ShoppingListResource(UUID.fromString(shoppingListId), "Stephanie's birthday");

        given(shoppingListService.createShoppingList(any(ShoppingListResource.class)))
                .willReturn(shoppingListResourceExpectedResult);

        mockMvc.perform(post("/shopping-lists")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Stephanie's birthday\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Stephanie's birthday"))
                .andExpect(jsonPath("$.shoppingListId").value(shoppingListId));

        verify(shoppingListService).createShoppingList(any());
    }

    @Test
    public void testAddCocktailsToShoppingList() throws Exception {
        final List<CocktailId> addCocktails = Arrays.asList(
                new CocktailId(UUID.fromString(cocktailId1)),
                new CocktailId(UUID.fromString(cocktailId2)));

        given(shoppingListService.addCocktailsToShoppingList(any(UUID.class), ArgumentMatchers.any()))
                .willReturn(addCocktails);

        mockMvc.perform(post("/shopping-lists/{shoppingListId}/cocktails/", shoppingListId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"cocktailId\": \"" + cocktailId1 + "\"},{\"cocktailId\": \"" + cocktailId2 + "\"}]"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].cocktailId").value(cocktailId1))
                .andExpect(jsonPath("$[1].cocktailId").value(cocktailId2));

        verify(shoppingListService).addCocktailsToShoppingList(any(), any());
    }

    @Test
    public void testGetShoppingList() throws Exception {
        ShoppingListResource shoppingListResourceExpectedResult
                = new ShoppingListResource(UUID.fromString(shoppingListId), "Stephanie's birthday",
                Arrays.asList("Lime juice", "Triple sec", "Tequila", "Lemonade", "Ice"));

        given(shoppingListService.getShoppingList(any(UUID.class)))
                .willReturn(shoppingListResourceExpectedResult);

        mockMvc.perform(get("/shopping-lists/{shoppingListId}", shoppingListId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Stephanie's birthday"))
                .andExpect(jsonPath("$.ingredients[1]").value("Triple sec"))
                .andExpect(jsonPath("$.ingredients", hasSize(5)))
                .andExpect(jsonPath("$.ingredients", hasItem("Lime juice")));

        verify(shoppingListService).getShoppingList(any());
    }

    @Test
    public void testGetAllShoppingLists() throws Exception {
        List<ShoppingListResource> shoppingListResources = Arrays.asList(
                new ShoppingListResource(UUID.fromString(shoppingListId), "Stephanie's birthday",
                    Arrays.asList("Lime juice", "Triple sec", "Tequila", "Lemonade", "Ice")),
                new ShoppingListResource(UUID.fromString(shoppingListId), "Johnny's retirement",
                        Arrays.asList("Lime juice", "Tequila", "Carrots", "Lemonade", "Pineapple"))
        );
        given(shoppingListService.getAllShoppingLists())
                .willReturn(shoppingListResources);

        mockMvc.perform(get("/shopping-lists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Stephanie's birthday"))
                .andExpect(jsonPath("$[0].ingredients", hasItem("Lime juice")))
                .andExpect(jsonPath("$[1].ingredients", hasItem("Tequila")));

        verify(shoppingListService).getAllShoppingLists();
    }
}
