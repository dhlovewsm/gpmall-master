package com.gpmall.comment.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CommentDTO implements Serializable {


    private String id;

    private String orderId;

    private String itemId;

    private Byte type;

    private Boolean isAnonymous;

    private String content;

    private LocalDateTime commentTime;

    private Boolean isPublic;

    private Boolean isValid;

    private Long validationUserId;

    private LocalDateTime validationTime;

    private Boolean isTop;

    private Long userId;

    private Boolean isDeleted;

    private LocalDateTime deletionTime;

    private Long deletionUserId;

}
