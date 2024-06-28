package com.gpmall.user.dto;

import com.gpmall.commons.result.AbstractResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserLoginResponse extends AbstractResponse {


    private Long id;
    private String username;
    private String phone;
    private String email;
    private String sex;
    private String image;
    private String description;
    private Integer points;
    private Long balance;
    private int state;
    private String token;

}
