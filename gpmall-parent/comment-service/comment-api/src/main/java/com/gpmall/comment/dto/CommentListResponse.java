package com.gpmall.comment.dto;

import com.gpmall.commons.result.AbstractResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentListResponse extends AbstractResponse {


    private List<CommentDTO> commentDTOList;

    private int page;

    private int size;

    private long total;

}
