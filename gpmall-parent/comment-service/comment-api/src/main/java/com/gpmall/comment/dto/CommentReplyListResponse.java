package com.gpmall.comment.dto;

import com.gpmall.commons.result.AbstractResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentReplyListResponse extends AbstractResponse {

    private long total;

    private int page;

    private int size;

    private List<CommentReplyDTO> commentReplyDTOList;

}
