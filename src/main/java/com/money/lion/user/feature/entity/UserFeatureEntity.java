package com.money.lion.user.feature.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "t_user_feature",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"feature_name", "email"},
                        name="uk_feature_name_email"
                )
        }
)
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFeatureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "feature_name")
    private String featureName;

    @Column(name = "email")
    private String email;

    @Column(name = "is_enable")
    private Boolean enable;
}
