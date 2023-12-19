package com.example.backend0.service;

import com.example.backend0.dto.FavoriteDTO;
import com.example.backend0.entity.Collect;
import com.example.backend0.repository.CollectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CollectService
 * @Description
 **/
@Service
public class CollectService {
    @Autowired
    CollectRepository collectRepository;
    public Collect save(Collect collect){
        return collectRepository.save(collect);
    }
    public List<FavoriteDTO> getFavoritesByUserID(Integer userID){
        List<FavoriteDTO> dto=new ArrayList<>();
//        return collectRepository.getFavoritesByUserID(userID);
        return null;
    }
}
