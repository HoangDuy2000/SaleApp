/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vhd.repository;

import com.vhd.pojo.Product;
import java.util.List;
import java.util.Map;

/**
 *
 * @author PC
 */
public interface ProductRepository {
    List<Product> getProducts(Map<String, String> params);
    int countProducts();
}
