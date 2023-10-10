package com.nsdl.electronic.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.electronic.store.model.Category;
import com.nsdl.electronic.store.model.Product;



public interface ProductRepository extends JpaRepository<Product,String>
{

	Page<Product> findByTitleContaining(String subTitle,Pageable pageable);
	Page<Product> findByLiveTrue(Pageable pageable);
	
	Page<Product> findByCategory(Category category,Pageable pageable);
}
