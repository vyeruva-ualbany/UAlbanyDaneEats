package com.ualbany.daneeats.controller;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ualbany.daneeats.model.MenuItem;
import com.ualbany.daneeats.model.Restaurant;
import com.ualbany.daneeats.service.RestaurantService;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

	   @Autowired 
	    private RestaurantService service;

	    @GetMapping("/add")
	    public String addrestaurant(Model model) {
	        model.addAttribute("userForm", new Restaurant());

	        return "addrestaurant";
	    }
	    
	    @PostMapping(path="/add") 
	     public @ResponseBody String addNewRestaurant (@ModelAttribute("restaurantForm") Restaurant restaurant, 
	    		 BindingResult bindingResult,Model model) {
	    	Date now = new Date();
	    	restaurant.setCreatedAt(now);
	    	restaurant.setUpdatedAt(now);
	    	service.save(restaurant);
	    return "Saved";
	  }

	  @GetMapping(path="/all")
	  public @ResponseBody String getAllRestaurants() {

			List<Restaurant> restaurants = service.findAll();
			JSONArray jsonArray = new JSONArray();

			for (Restaurant restaurant : restaurants)
			{
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", restaurant.getId());
				jsonObject.put("name", restaurant.getName());
				jsonObject.put("phone", restaurant.getPhone());
				jsonObject.put("address1", restaurant.getAddress1());
				jsonObject.put("address2", restaurant.getAddress2());
				jsonArray.put(jsonObject);
			}
			return jsonArray.toString();
	  }
	  
	  @GetMapping(path="/allitems")
	  public @ResponseBody String getAllItemsById(@RequestParam Long id) {

			List<MenuItem> menuItems = service.findAllMenuItems(id);
			Restaurant restaurant = service.findById(id);
			JSONArray jsonArray = new JSONArray();

			for (MenuItem item : menuItems)
			{
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", item.getId());
				jsonObject.put("name", item.getName());
				jsonObject.put("price", item.getPrice());
				jsonObject.put("restaurant", restaurant.getName());
				jsonArray.put(jsonObject);
			}
			return jsonArray.toString();
	  }

}
