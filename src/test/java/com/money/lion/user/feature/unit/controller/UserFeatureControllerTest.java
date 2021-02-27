package com.money.lion.user.feature.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.money.lion.user.feature.contoller.UserFeatureController;
import com.money.lion.user.feature.model.UserFeatureReqVO;
import com.money.lion.user.feature.model.UserFeatureRespVO;
import com.money.lion.user.feature.service.UserFeatureService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserFeatureController.class)
public class UserFeatureControllerTest {

    @MockBean
    private UserFeatureService service;

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenValidRequest_whenGetFeature_thenReturnCanAccess()
            throws Exception {
        UserFeatureRespVO response = new UserFeatureRespVO(true);
        given(service.selectByEmailAndFeatureName("kevin.hutama91@gmail.com", "feature1"))
                .willReturn(response);

        mvc.perform(get("/feature?email=kevin.hutama91@gmail.com&featureName=feature1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.canAccess").value(response.getCanAccess()));
    }

    @Test()
    public void givenMissingFeatureParamRequest_whenGetFeature_thenReturnBadRequest()
            throws Exception {
        UserFeatureRespVO response = new UserFeatureRespVO(true);
        mvc.perform(get("/feature?email=kevin.hutama91@gmail.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void givenValidRequest_whenInsertData_thenReturnHttpStatusOK()
            throws Exception {
        UserFeatureReqVO requestSample = new UserFeatureReqVO("feature1", "kevin.hutama91@gmail.com",
                Boolean.TRUE);

        mvc.perform(post("/feature")
                .content(new ObjectMapper().writeValueAsString((requestSample)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service, times(1)).insert(any());
    }

    @Test
    @SneakyThrows
    public void givenInvalidRequest_whenInsertData_thenThrowException(){
        UserFeatureReqVO requestSample = UserFeatureReqVO.builder()
                .email("kevin.hutama91@gmail.com")
                .build();
        doThrow(IllegalStateException.class)
                .when(service)
                .insert(any());
        assertThrows(Exception.class, ()-> mvc.perform(post("/feature")
                    .content(new ObjectMapper().writeValueAsString((requestSample)))
                    .contentType(MediaType.APPLICATION_JSON))
        );
    }

}
