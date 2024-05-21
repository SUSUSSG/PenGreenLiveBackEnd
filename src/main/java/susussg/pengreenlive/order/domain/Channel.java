package susussg.pengreenlive.order.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TB_CHANNEL")
public class Channel {

    @Id
    @Column(name="CHANNEL_SEQ")
    private Long channelSeq;

    @Column(name="VENDOR_SEQ")
    private Long vendorSeq;

    @Column(name = "CHANNEL_NM")
    private String channelNm;

    @Column(name = "CHANNEL_URL")
    private String channelUrl;

    @Column(name = "CHANNEL_IMAGE")
    private String channelImage;

    @Column(name = "CHANNEL_INFO")
    private String channelInfo;
}
