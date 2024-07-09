package com.gpmall.comment.dto;

import com.gpmall.comment.constant.CommentRetCode;
import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.tool.exception.ValidateException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddCommentRequest extends AbstractRequest {

    private String orderItemId;

    private Integer star;

    private Integer type;

    private Boolean isAnonymous;

    private String content;

    private List<String> picPath;

    private Boolean isPublic;
    @Override
    public void requestCheck() {
        if (StringUtils.isEmpty(orderItemId) || StringUtils.isEmpty(content) || isPublic == null){
            throw new ValidateException(CommentRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(), CommentRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }
    }
}
