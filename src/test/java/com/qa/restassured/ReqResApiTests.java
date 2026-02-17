package com.qa.restassured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ReqResApiTests {
  
    @Test
    public void getUserListTest() 
    {
    	RestAssured.baseURI="https://api.restful-api.dev";
    	
    	Response response = RestAssured
    			           .given()                    // Eg: given().header("Content-Type", "application/json")
    			           .basePath("/objects")
    			           .when()
    			           .get();  	
    	//System.out.println(response.getBody().prettyPrint());	   	
    	
    	        SoftAssert softAssert = new SoftAssert();
    	        
    	        // HTTPS Status Code
    	        try 
    	        {
    	            softAssert.assertEquals(response.getStatusCode(), 200, "Response status mismatch");
    	            System.out.println("✅ Status code check passed: "+response.getStatusCode());
    	        } catch (AssertionError e) {
    	            System.out.println("❌ Status code check failed: " + e.getMessage());}
    	         	 
    	        // Response Time
    	        try 
    	        {
    	            softAssert.assertEquals(response.getTime()/1000, 1, "Response time exceeds 2 seconds");
    	            System.out.println("✅ Response time check passed: "+response.getTime()/1000+" seconds");
    	        } catch (AssertionError e) {
    	            System.out.println("❌ Response time check failed: " + e.getMessage());}

    	        
    	        // Total Products
    	        try 
    	        {
    	            int size = response.jsonPath().getList("").size();
    	            softAssert.assertEquals(size, 13, "Product count mismatch");
    	            System.out.println("✅ Product count check passed");
    	        } catch (AssertionError e) {
    	            System.out.println("❌ Product count check failed: " + e.getMessage());}

    	        // First Product Name
    	        try 
    	        {
    	            String name = response.jsonPath().getString("[0].name");
    	            softAssert.assertEquals(name, "Google Pixel 6 Pro", "First product name mismatch");
    	            System.out.println("✅ First product name check passed");
    	        } catch (AssertionError e) {
    	            System.out.println("❌ First product name check failed: " + e.getMessage()); }

    	        // Second Product Data should be null
    	        try 
    	        {
    	            Object data = response.jsonPath().get("[1].data");
    	            softAssert.assertNull(data, "Second product data should be null");
    	            System.out.println("✅ Second product null check passed");
    	        } catch (AssertionError e) {
    	            System.out.println("❌ Second product null check failed: " + e.getMessage());  }

    	        // Price of Apple iPhone 11
    	        try
    	        {
    	            float price = response.jsonPath().getFloat("[3].data.price");
    	            softAssert.assertEquals(price, 389.99f, "Price mismatch for iPhone 11");
    	            System.out.println("✅ iPhone 11 price check passed");
    	        } catch (AssertionError e) {
    	            System.out.println("❌ iPhone 11 price check failed: " + e.getMessage()); }

    	        // Unique IDs
    	        try
    	        {
    	            List<String> ids = response.jsonPath().getList("id");
    	            Set<String> uniqueIds = new HashSet<>(ids);
    	            softAssert.assertEquals(ids.size(), uniqueIds.size(), "Duplicate IDs found");
    	            System.out.println("✅ Unique ID check passed");
    	        } catch (AssertionError e) {
    	            System.out.println("❌ Unique ID check failed: " + e.getMessage()); }

    	        // Final assertion summary
    	        softAssert.assertAll();
    	    }
 
}

