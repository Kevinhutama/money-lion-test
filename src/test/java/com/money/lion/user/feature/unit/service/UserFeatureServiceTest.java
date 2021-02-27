package com.money.lion.user.feature.unit.service;

import com.money.lion.user.feature.entity.UserFeatureEntity;
import com.money.lion.user.feature.model.UserFeatureReqVO;
import com.money.lion.user.feature.model.UserFeatureRespVO;
import com.money.lion.user.feature.repository.UserFeatureRepository;
import com.money.lion.user.feature.service.UserFeatureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserFeatureServiceTest {

    private UserFeatureService userFeatureService;

    @Mock
    private UserFeatureRepository repo;

    @BeforeEach
    public void init(){
        userFeatureService = new UserFeatureService();
        ReflectionTestUtils.setField( userFeatureService, "repo", repo);
        ReflectionTestUtils.setField( userFeatureService, "modelMapper", new ModelMapper());
    }

    @Test
    public void givenValidRequest_whenInsert_thenSuccess(){
        userFeatureService.insert( new UserFeatureReqVO("feature1", "kevin.hutama91@gmail.com",
                Boolean.TRUE));
        verify(repo, times(1)).saveAndFlush(any());
    }

    @Test
    public void givenInvalidRequest_whenInsert_thenThrowIllegalArgumentException(){
        assertThrows(Exception.class, ()-> userFeatureService.insert( UserFeatureReqVO.builder()
                .email("kevin.hutama91@gmail.com")
                .enable(true)
                .build())
        );
    }

    @Test
    public void givenValidEmailAndFeature_whenGetFeature_thenReturnCanAccess(){
        UserFeatureEntity entity = UserFeatureEntity.builder()
            .email("kevin.hutama91@gmail.com")
            .featureName("feature1")
            .enable(true)
            .build();
        when(repo.findByEmailAndFeatureName("kevin.hutama91@gmail.com","feature1"))
                .thenReturn(Optional.of(entity));
        UserFeatureRespVO response = userFeatureService.selectByEmailAndFeatureName("kevin.hutama91@gmail.com", "feature1");
        assertThat(response.getCanAccess()).isEqualTo(true);
    }

    @Test
    public void givenMissingRecord_whenGetFeature_thenReturnCannotAccess(){
        when(repo.findByEmailAndFeatureName("kevin.hutama91@gmail.com","feature1"))
                .thenReturn(Optional.empty());
        UserFeatureRespVO response = userFeatureService.selectByEmailAndFeatureName("kevin.hutama91@gmail.com", "feature1");
        assertThat(response.getCanAccess()).isEqualTo(false);
    }
}
