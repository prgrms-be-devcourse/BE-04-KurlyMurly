package com.devcourse.kurlymurly.module.product.domain;

import com.devcourse.kurlymurly.module.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String subCategory;

    protected Category() {
    }

    public Category(String name, String subCategory) {
        this.name = name;
        this.subCategory = subCategory;
    }
}
