package com.mapache.Enotes_API_Service.repository;

import com.mapache.Enotes_API_Service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
