package susussg.pengreenlive.order.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TB_BROADCAST")
public class Broadcast {
    @Id
    @Column(name = "BROADCAST_SEQ")
    private long broadcastSeq;

    @Column(name = "CHANNEL_NM")
    private String channelNm;

    @Column(name = "BROADCAST_TITLE")
    private String broadcastTitle;

    @Column(name = "BROADCAST_IMAGE")
    private String broadcastImage;

    @Column(name = "BROADCAST_SUMMARY")
    private String broadcastSummary;

    @Column(name = "BROADCAST_SCHEDULED_TIME")
    private LocalDateTime broadcastScheduledTime;

    @Column(name = "BROADCAST_START_TIME")
    private LocalDateTime broadcastStartTime;

    @Column(name = "BROADCAST_END_TIME")
    private LocalDateTime broadcastEndTime;

    @Column(name = "LIKES_COUNT")
    private int likesCount;

    @Column(name = "VIEW_COUNT")
    private int viewCount;

    @Column(name = "SCRIPT")
    private String script;

    @Column(name = "BROADCAST_YN")
    private boolean broadcastYn;

    @Column(name = "BROADCAST_SOURCE_INFO")
    private String broadcastSourceInfo;

    @Column(name = "CATEGORY_CD")
    private String categoryCd;

    @Column(name = "CHANNEL_SEQ")
    private long channelSeq;
}
