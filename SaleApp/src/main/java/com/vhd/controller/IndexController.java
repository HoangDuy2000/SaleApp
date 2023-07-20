/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vhd.controllers;

import com.dht.pojo.Category;
import com.dht.pojo.Product;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author PC
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(Model model){
        List<Category> cates = List.of(new Category(1, "Mobile"), new Category(2, "Tablet"), new Category(3, "Desktops"));
        model.addAttribute("categories", cates);
        
        List<Product> products = List.of(new Product(1, "Iphone13", 13000000l, ""), 
                                        new Product(2, "Iphone14", 15000000l, ""),
                                        new Product(3, "Iphone15", 18000000l, ""));
        model.addAttribute("products", products);
        return "index";
    }
}
