package com.gpmall.comment.dto;

import com.gpmall.commons.result.AbstractResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentResponse extends AbstractResponse {

    private List<CommentDTO> commentDTOList;

}
