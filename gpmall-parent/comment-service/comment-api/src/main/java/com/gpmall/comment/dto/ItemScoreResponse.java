package com.gpmall.comment.dto;

import com.gpmall.commons.result.AbstractResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ItemScoreResponse extends AbstractResponse {

    private double score;

}
