/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vhd.repository.impl;

import com.vhd.pojo.Product;
import com.vhd.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author PC
 */
@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class ProductRepositoryImpl implements ProductRepository{
    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Autowired
    private Environment env;
    
    @Override
    public List<Product> getProducts(Map<String, String> params) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Product> q = b.createQuery(Product.class);
        Root r = q.from(Product.class);
        q.select(r);
        
        if (params != null){
            List<Predicate> predicates = new ArrayList<>();
            
            String kw = params.get("kw");
            if(kw != null && !kw.isEmpty()) {
                predicates.add(b.like(r.get("name"), String.format("%%%s%%", kw)));
            }
            
            String fromPrice = params.get("fromPrice");
            if (fromPrice != null && !fromPrice.isEmpty()) {
                predicates.add(b.greaterThanOrEqualTo(r.get("price"), Long.parseLong(fromPrice)));
            }
            
            String toPrice = params.get("toPrice");
            if (toPrice != null && !toPrice.isEmpty()) {
                predicates.add(b.greaterThanOrEqualTo(r.get("price"), Long.parseLong(toPrice)));
            }
            
            String cateId = params.get("cateId");
            if (cateId != null && !cateId.isEmpty()) {
                predicates.add(b.equal(r.get("categoryId"), Long.parseLong(cateId)));
            }
            
            q.where(predicates.toArray(Predicate[]::new));
        }
        
        Query query = session.createQuery(q);
        
        if (params != null) {
            String page = params.get("page");
            if(page != null && !page.isEmpty()) {
                int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
                query.setMaxResults(pageSize);
                query.setFirstResult((Integer.parseInt(page) - 1) * pageSize);
            }
        }
        
        return query.getResultList();
    }

    @Override
    public int countProducts() {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT COUNT(*) FROM Product");
        
        return Integer.parseInt(q.getSingleResult().toString());
    }
}
