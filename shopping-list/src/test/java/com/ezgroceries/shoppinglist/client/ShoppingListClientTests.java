package com.ezgroceries.shoppinglist.client;

import com.ezgroceries.shoppinglist.ShoppingListApplication;
import com.ezgroceries.shoppinglist.controllers.contracts.ShoppingListCreateRequest;
import com.ezgroceries.shoppinglist.model.CocktailResource;
import com.ezgroceries.shoppinglist.model.ShoppingListResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {ShoppingListApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShoppingListClientTests {
    private static final String BASE_URL = "http://localhost:8080";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getCocktails_using_no_user_should_succeed() {
        String url = BASE_URL + "/cocktails?search=mary";

        ResponseEntity<CocktailResource[]> responseEntity
                = restTemplate.getForEntity(url, CocktailResource[].class, 0);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        CocktailResource[] cocktailResources = responseEntity.getBody();

        assertNotNull(cocktailResources);
        assertTrue(cocktailResources.length >= 1);
        assertEquals("Bloody Mary", cocktailResources[0].getName());
        assertEquals(6, cocktailResources[0].getIngredients().size());
    }

    @Test
    public void getCocktails_using_valid_user_should_succeed() {
        String url = BASE_URL + "/cocktails?search=mary";

        ResponseEntity<CocktailResource[]> responseEntity
                = restTemplate.withBasicAuth("user", "password")
                .getForEntity(url, CocktailResource[].class, 0);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        CocktailResource[] cocktailResources = responseEntity.getBody();

        assertNotNull(cocktailResources);
        assertTrue(cocktailResources.length >= 1);
        assertEquals("Bloody Mary", cocktailResources[0].getName());
        assertEquals(6, cocktailResources[0].getIngredients().size());
    }

    @Test
    public void getCocktails_using_invalid_user_should_fail_401() {
        String url = BASE_URL + "/cocktails?search=mary";

        ResponseEntity<CocktailResource[]> responseEntity
                = restTemplate.withBasicAuth("hacker", "password")
                .getForEntity(url, CocktailResource[].class, 0);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void getActuator_using_user_should_fail_403() {
        String url = BASE_URL + "/actuator";

        ResponseEntity<String> responseEntity
                = restTemplate.withBasicAuth("user", "password")
                .getForEntity(url, String.class, 0);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void getActuator_using_admin_should_succeed() {
        String url = BASE_URL + "/actuator";

        ResponseEntity<String> responseEntity
                = restTemplate.withBasicAuth("admin", "password")
                .getForEntity(url, String.class, 0);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getActuator_using_invalid_user_should_fail_401() {
        String url = BASE_URL + "/actuator";

        ResponseEntity<String> responseEntity
                = restTemplate.withBasicAuth("hacker", "password")
                .getForEntity(url, String.class, 0);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void createShoppingList_using_invalid_user_should_fail_401() {
        ShoppingListCreateRequest shoppingListCreateRequest = new ShoppingListCreateRequest();
        shoppingListCreateRequest.setName("Mary's shopping list");

        String url = BASE_URL + "/shopping-lists/";

        ResponseEntity<ShoppingListResource> responseEntity
                = restTemplate.withBasicAuth("hacker", "password")
                .postForEntity(url, shoppingListCreateRequest, ShoppingListResource.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void createShoppingList_using_user_should_succeed() {
        ShoppingListCreateRequest shoppingListCreateRequest = new ShoppingListCreateRequest();
        shoppingListCreateRequest.setName("Brody's shopping list");

        String url = BASE_URL + "/shopping-lists/";

        ResponseEntity<ShoppingListResource> responseEntity
                = restTemplate.withBasicAuth("user", "password")
                .postForEntity(url, shoppingListCreateRequest, ShoppingListResource.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ShoppingListResource shoppingListResourceResponse = responseEntity.getBody();

        assert shoppingListResourceResponse != null;
        assertThat(shoppingListResourceResponse.getName()).isEqualTo("Brody's shopping list");
        assertThat(shoppingListResourceResponse.getShoppingListId()).isNotNull();

    }

    @Test
    public void createShoppingList_using_admin_should_succeed() {
        ShoppingListCreateRequest shoppingListCreateRequest = new ShoppingListCreateRequest();
        shoppingListCreateRequest.setName("Anthony's shopping list");

        String url = BASE_URL + "/shopping-lists/";

        ResponseEntity<ShoppingListResource> responseEntity
                = restTemplate.withBasicAuth("admin", "password")
                .postForEntity(url, shoppingListCreateRequest, ShoppingListResource.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ShoppingListResource shoppingListResourceResponse = responseEntity.getBody();

        assert shoppingListResourceResponse != null;
        assertThat(shoppingListResourceResponse.getName()).isEqualTo("Anthony's shopping list");
        assertThat(shoppingListResourceResponse.getShoppingListId()).isNotNull();
    }
}
