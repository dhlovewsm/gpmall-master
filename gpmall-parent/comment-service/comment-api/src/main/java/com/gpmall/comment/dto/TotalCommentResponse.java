package com.gpmall.comment.dto;

import com.gpmall.commons.result.AbstractResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TotalCommentResponse extends AbstractResponse {

    private long total;

}
