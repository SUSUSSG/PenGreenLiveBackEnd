package susussg.pengreenlive.broadcast.service;

import susussg.pengreenlive.broadcast.dto.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface BroadcastService {
    // 방송 카테고리
    List<BroadcastCategoryDTO> getAllCategory();

    // 방송 정보 등록
    void registerBroadcast(BroadcastRegistrationRequestDTO broadcastRegisterInfo, long vendorId);

    // 채널별 판매 상품 목록
    List<ChannelSalesProductDTO> getChannelSalesProductAll(long vendorId);

    // 방송 예정 정보
    List<PrepareBroadcastInfoDTO> getUpcomingBroadcastInfo(long vendorId);

    List<BroadcastDTO> getBroadcastsByVendorAndDateRange(long vendorSeq, LocalDateTime startDateTime, LocalDateTime endDateTime);

    void startBroadcast(BroadcastTimeDTO broadcastTimeDTO);

    void endBroadcast(BroadcastTimeDTO broadcastTimeDTO);
}
