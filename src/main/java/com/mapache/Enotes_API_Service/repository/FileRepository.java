package com.mapache.Enotes_API_Service.repository;

import com.mapache.Enotes_API_Service.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepository extends JpaRepository<FileDetails, Integer> {



}