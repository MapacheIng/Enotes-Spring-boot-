package com.mapache.Enotes_API_Service.controller;

import com.mapache.Enotes_API_Service.endpoint.CacheEndpoint;
import com.mapache.Enotes_API_Service.service.CacheManagerService;
import com.mapache.Enotes_API_Service.util.CommonUtil;
import org.springframework.cache.Cache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class cacheController implements CacheEndpoint {

    private final CacheManagerService cacheManagerService;

    public cacheController(CacheManagerService cacheManagerService) {
        this.cacheManagerService = cacheManagerService;
    }

    @Override
    public ResponseEntity<?> getAllCache() {
        Collection<String> cache = cacheManagerService.getCache();
        return CommonUtil.createBuilderResponse(cache, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getCacheByName(String cacheName) {
        Cache name = cacheManagerService.getCacheName(cacheName);
        return CommonUtil.createBuilderResponse(name, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> removeAllCache() {
        cacheManagerService.removeAllCache();
        return CommonUtil.createBuilderResponseMessage("Cache cleared", HttpStatus.OK);
    }
}
