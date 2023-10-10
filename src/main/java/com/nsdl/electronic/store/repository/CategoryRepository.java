package com.nsdl.electronic.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.electronic.store.model.Category;

public interface CategoryRepository extends JpaRepository<Category,String> {

}
