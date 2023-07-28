/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vhd.controller;

import com.vhd.pojo.Category;
import com.vhd.pojo.Product;
import com.vhd.service.CategoryService;
import com.vhd.service.ProductService;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author PC
 */
@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private Environment env;

    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {

        model.addAttribute("categories", this.categoryService.getCategories());
        model.addAttribute("products", this.productService.getProducts(params));
        int count = this.productService.countProducts();
        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        model.addAttribute("pages", Math.ceil(count * 1.0 / pageSize));
        
        return "index";
    }
}
