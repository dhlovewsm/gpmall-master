package com.gpmall.comment.dto;

import com.gpmall.comment.constant.CommentRetCode;
import com.gpmall.commons.result.AbstractRequest;
import com.gpmall.commons.tool.exception.ValidateException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;


@EqualsAndHashCode(callSuper = true)
@Data
public class CommentReplyListRequest extends AbstractRequest {


    private String commentId;

    private int page;

    private int size;

    @Override
    public void requestCheck() {
        if (StringUtils.isEmpty(commentId)){
            throw new ValidateException(CommentRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(), CommentRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }

        if (page < 1){
            setPage(1);
        }
        if (size < 1){
            setSize(10);
        }
    }
}
