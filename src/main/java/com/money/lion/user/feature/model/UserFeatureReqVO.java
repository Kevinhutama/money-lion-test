package com.money.lion.user.feature.model;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFeatureReqVO {
    private String featureName;
    private String email;
    private Boolean enable;
}
