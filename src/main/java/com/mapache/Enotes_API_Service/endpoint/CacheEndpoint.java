package com.mapache.Enotes_API_Service.endpoint;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Caching", description = "All the Cache APIs")
@RequestMapping("/api/v1/cache")
public interface CacheEndpoint {

    @GetMapping
    ResponseEntity<?> getAllCache();

    @GetMapping("/{cache_name}")
    ResponseEntity<?> getCacheByName(@PathVariable("cache_name") String cacheName);

    @DeleteMapping
    ResponseEntity<?> removeAllCache();

}
