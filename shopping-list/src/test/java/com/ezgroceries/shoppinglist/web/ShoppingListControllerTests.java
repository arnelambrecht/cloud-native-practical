package com.ezgroceries.shoppinglist.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(ShoppingListController.class)
public class ShoppingListControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateShoppingList() throws Exception {
        mockMvc.perform(post("/shopping-lists")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Stephanie's birthday\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Stephanie's birthday"));
    }

    @Test
    public void testAddCocktails() throws Exception {
        mockMvc.perform(post("/shopping-lists/23b3d85a-3928-41c0-a533-6538a71e17c4/cocktails/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"cocktailId\": \"23b3d85a-3928-41c0-a533-6538a71e17c4\"},{\"cocktailId\": \"d615ec78-fe93-467b-8d26-5d26d8eab073\"}]"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].cocktailId").value("23b3d85a-3928-41c0-a533-6538a71e17c4"));
    }



    @Test
    public void testGetShoppingList() throws Exception {
        mockMvc.perform(get("/shopping-lists/23b3d85a-3928-41c0-a533-6538a71e17c4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Stephanie's birthday"))
                .andExpect(jsonPath("$.ingredients[1]").value("Triple sec"))
                .andExpect(jsonPath("$.ingredients", hasSize(5)))
                .andExpect(jsonPath("$.ingredients", hasItem("Lime juice")));
    }

    @Test
    public void testGetShoppingLists() throws Exception {
        mockMvc.perform(get("/shopping-lists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Stephanie's birthday"))
                .andExpect(jsonPath("$[0].ingredients", hasItem("Lime juice")))
                .andExpect(jsonPath("$[1].ingredients", hasItem("Tequila")));
    }






}
