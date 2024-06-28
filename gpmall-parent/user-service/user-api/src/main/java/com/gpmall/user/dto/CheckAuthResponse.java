package com.gpmall.user.dto;

import com.gpmall.commons.result.AbstractResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CheckAuthResponse extends AbstractResponse {

    private String userInfo;

}
