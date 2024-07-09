package com.gpmall.comment.dto;

import com.gpmall.comment.constant.CommentRetCode;
import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.tool.exception.ValidateException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuditCommentRequest extends AbstractRequest {


    private String commentId;

    private boolean isValid;

    private Long validationUserId;

    private String validationSuggestion;

    @Override
    public void requestCheck() {
        if (StringUtils.isEmpty(commentId) || validationUserId == null){
            throw new ValidateException(CommentRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(), CommentRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }

        if (!isValid && StringUtils.isEmpty(validationSuggestion)){
            throw new ValidateException(CommentRetCode.REQUEST_PARAM_ERROR.getCode(), CommentRetCode.REQUEST_PARAM_ERROR.getMessage());
        }

    }
}
