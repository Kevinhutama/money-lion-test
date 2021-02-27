package com.money.lion.user.feature.service;

import com.money.lion.user.feature.entity.UserFeatureEntity;
import com.money.lion.user.feature.model.UserFeatureReqVO;
import com.money.lion.user.feature.model.UserFeatureRespVO;
import com.money.lion.user.feature.repository.UserFeatureRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserFeatureService {

    @Autowired
    private UserFeatureRepository repo;

    @Autowired
    private ModelMapper modelMapper;

    public void insert(UserFeatureReqVO requestVO){
        validateInsertRequest(requestVO);

        //Update existing record if email and feature name is exist to avoid duplicate record
        UserFeatureEntity entity = repo.findByEmailAndFeatureName(requestVO.getEmail(), requestVO.getFeatureName())
                .map(record -> {
                    record.setEnable(requestVO.getEnable());
                    return record;
                }).orElse(modelMapper.map(requestVO, UserFeatureEntity.class));
        repo.saveAndFlush(entity);
    }

    private void validateInsertRequest(UserFeatureReqVO requestVO) {
        if(Objects.isNull(requestVO.getEmail()))
                throw new IllegalArgumentException("email is mandatory");
        else if(Objects.isNull(requestVO.getFeatureName()))
            throw new IllegalArgumentException("feature name is mandatory");
        else if(Objects.isNull(requestVO.getEnable()))
            throw new IllegalArgumentException("enable flag is mandatory");
    }

    public UserFeatureRespVO selectByEmailAndFeatureName(String email, String fetaureName) {
        return UserFeatureRespVO.builder()
                .canAccess(repo.findByEmailAndFeatureName(email,fetaureName)
                                .map(UserFeatureEntity::getEnable)
                                .orElse(false))
                .build();

    }
}
