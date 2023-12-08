package com.example.backend0.service;

import com.example.backend0.entity.Platform;
import com.example.backend0.repository.PlatformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName PlatFormService
 * @Description
 **/
@Service
public class PlatformService {
    @Autowired
    PlatformRepository platformRepository;
    public Platform save(Platform platform){
        if(platform!=null){
            return platformRepository.save(platform);
        }
        return null;
    }
    public Platform getPlatformById(Integer id){
        return platformRepository.findById(id).orElse(null);
    }
}
