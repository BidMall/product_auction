package com.example.product_auction.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product_auction.product.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}