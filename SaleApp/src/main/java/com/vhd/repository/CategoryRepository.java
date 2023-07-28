/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vhd.repository;

import com.vhd.pojo.Category;
import java.util.List;

/**
 *
 * @author PC
 */
public interface CategoryRepository {
    List<Category> getCategories();
}
