package com.gpmall.user.dto;

import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.tool.exception.ValidateException;
import com.gpmall.user.constants.SysRetCodeConstants;
import io.netty.util.internal.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserLoginRequest extends AbstractRequest {

    private String username;
    private String password;

    @Override
    public void requestCheck() {

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new ValidateException(
                    SysRetCodeConstants.REQUEST_CHECK_FAILED.getCode(),
                    SysRetCodeConstants.REQUEST_CHECK_FAILED.getMessage());
        }

    }
}
