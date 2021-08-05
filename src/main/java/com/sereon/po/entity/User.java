package com.sereon.po.entity;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class User extends BaseEntity{

    @Id
    @Column(length = 100)
    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    @Column(length = 11)
    private String mobile;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<UserRole> roleSet = new HashSet<>();


    public void addMemberRole(UserRole userRole){
        roleSet.add(userRole);
    }
}
