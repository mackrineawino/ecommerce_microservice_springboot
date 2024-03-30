package com.ecommerce.productservice.entity;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "tbl_products",uniqueConstraints = {
    @UniqueConstraint(columnNames = "product_code",name = "UNIQ_PRODUCT_CODE")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @Column(name="product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "product_name")
    private String name;

    @Column(name= "product_price")
    private BigDecimal price;

    @Column(name= "product_code")
    private String productCode;

    @Column(name= "product_category")
    private ProductCategory productCategory;

    @Column(name= "imageUrl")
    private String imageUrl;

    @Column(name = "quantity") 
    private Integer quantity;
}
