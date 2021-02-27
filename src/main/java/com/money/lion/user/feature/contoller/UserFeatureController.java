package com.money.lion.user.feature.contoller;

import com.money.lion.user.feature.model.UserFeatureReqVO;
import com.money.lion.user.feature.model.UserFeatureRespVO;
import com.money.lion.user.feature.service.UserFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feature")
public class UserFeatureController {

    @Autowired
    private UserFeatureService service;

    @PostMapping
    public void insertFeature(@RequestBody UserFeatureReqVO requestVO){
        service.insert(requestVO);
    }

    @GetMapping
    public UserFeatureRespVO getFeature(@RequestParam("email") String email,
                                        @RequestParam("featureName") String featureName){
        return service.selectByEmailAndFeatureName(email, featureName);
    }
}
