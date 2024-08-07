package com.gpmall.comment.dto;

import com.gpmall.comment.constant.CommentRetCode;
import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.tool.exception.ValidateException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;


@EqualsAndHashCode(callSuper = true)
@Data
public class TotalCommentRequest extends AbstractRequest {


    private String itemId;

    private Integer type;

    @Override
    public void requestCheck() {
        if (StringUtils.isEmpty(itemId)){
            throw new ValidateException(CommentRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(), CommentRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }

        if (type != null && (type < 1 || type > 3)){
            type = null;
        }
    }
}
