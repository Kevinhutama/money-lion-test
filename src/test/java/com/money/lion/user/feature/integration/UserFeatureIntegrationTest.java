package com.money.lion.user.feature.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.money.lion.user.feature.entity.UserFeatureEntity;
import com.money.lion.user.feature.model.UserFeatureReqVO;
import com.money.lion.user.feature.model.UserFeatureRespVO;
import com.money.lion.user.feature.repository.UserFeatureRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class UserFeatureIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserFeatureRepository repo;

    @Test
    public void givenNoRecordInDB_whenGetFeature_thenReturnCannotAccess()
            throws Exception {
        UserFeatureRespVO response = new UserFeatureRespVO(true);
        mvc.perform(get("/feature?email=kevin.hutama91@gmail.com&featureName=non_existed_feature")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.canAccess").value(false));
    }

    @Test
    @SneakyThrows
    public void givenRecordInDB_whenGetFeature_thenReturnCanAccess() {
        repo.saveAndFlush(UserFeatureEntity.builder()
                .email("kevin.hutama91@gmail.com")
                .featureName("feature1")
                .enable(true)
                .build());
        UserFeatureRespVO response = new UserFeatureRespVO(true);
        mvc.perform(get("/feature?email=kevin.hutama91@gmail.com&featureName=feature1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.canAccess").value(true));
    }

    @Test
    public void givenValidRequest_whenInsertData_thenDataInsertedAndReturnHttpStatusOK()
            throws Exception {
        UserFeatureReqVO requestSample = new UserFeatureReqVO("new_feature", "kevin.hutama91@gmail.com",
                Boolean.TRUE);

        mvc.perform(post("/feature")
                .content(new ObjectMapper().writeValueAsString((requestSample)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertTrue(repo.findByEmailAndFeatureName("kevin.hutama91@gmail.com", "new_feature")
                .isPresent());
    }

    @Test
    @SneakyThrows
    public void givenInvalidRequest_whenInsertData_thenDataNotInsertedAndReturnHttpStatusNotModified(){
        UserFeatureReqVO requestSample = UserFeatureReqVO.builder()
                .email("kevin.hutama91@gmail.com")
                .build();

        mvc.perform(post("/feature")
                .content(new ObjectMapper().writeValueAsString((requestSample)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotModified());
    }

}
