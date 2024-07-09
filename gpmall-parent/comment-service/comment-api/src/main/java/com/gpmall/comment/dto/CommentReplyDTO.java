package com.gpmall.comment.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CommentReplyDTO implements Serializable {


    private String id;

    private String commentId;

    private String parentId;

    private String content;

    private LocalDateTime replyTime;

    private String replyNick;

    private Long userId;

    private Boolean isDeleted;

    private LocalDateTime deletionTime;

    private Long deletionUserId;

}
