package com.money.lion.user.feature.repository;

import com.money.lion.user.feature.entity.UserFeatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFeatureRepository extends JpaRepository<UserFeatureEntity, Long> {
    Optional<UserFeatureEntity> findByEmailAndFeatureName(String email, String featureName);
}
