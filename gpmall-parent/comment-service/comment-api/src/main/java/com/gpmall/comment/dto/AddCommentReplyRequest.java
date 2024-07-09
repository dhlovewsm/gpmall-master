package com.gpmall.comment.dto;

import com.gpmall.comment.constant.CommentRetCode;
import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.tool.exception.ValidateException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;


@EqualsAndHashCode(callSuper = true)
@Data
public class AddCommentReplyRequest extends AbstractRequest {


    private String commentId;

    private Long userId;

    private String content;


    private String replyNick;

    @Override
    public void requestCheck() {
        if (StringUtils.isEmpty(commentId) || StringUtils.isEmpty(content) || userId == null){
            throw new ValidateException(CommentRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(), CommentRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }
    }
}
