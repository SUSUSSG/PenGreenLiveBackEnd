package susussg.pengreenlive.broadcast.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.broadcast.dto.*;
import susussg.pengreenlive.broadcast.mapper.BroadcastRegisterMapper;


import java.util.List;


@Service
@Log4j2
@RequiredArgsConstructor

public class BroadcastRegisterServiceImpl implements BroadcastRegisterService {

    @Autowired
    private final BroadcastRegisterMapper broadcastRegisterMapper;

    private final BroadcastStatisticsService broadcastStatisticsService;

    @Override
    @Transactional
    public String getChannelName(long vendorId) {
        String channelName = broadcastRegisterMapper.selectChannelName(vendorId);
        if (channelName.isEmpty()) {
            throw new RuntimeException("channel name emplty");
        } else {
            return channelName;
        }
    }

    @Override
    @Transactional
    public List<BroadcastCategoryDTO> getAllCategory() {
        List<BroadcastCategoryDTO> categoryList = broadcastRegisterMapper.selectAllCategory();
        if (categoryList.isEmpty()) {
            throw new RuntimeException("cateogry  emplty");
        } else {
            return categoryList;
        }
    }

    @Override
    @Transactional
    public void saveBroadcast(BroadcastDTO broadcastDTO) {
        int result = broadcastRegisterMapper.insertBroadcast(broadcastDTO);
        BroadcastStatistics broadcastStatistics = BroadcastStatistics.builder()
                        .broadcastSeq(broadcastDTO.getBroadcastSeq())
                                .broadcastDuration(0)
                                        .avgProductClicks(0)
                                                .avgViewerCount(0)
                                                        .avgViewingTime(0)
                                                                .likesCount(0)
                                                                        .maxViewerCount(0)
                                                                                .avgPurchaseAmount(0)
                                                                                        .totalSalesAmount(0)
                                                                                                .totalSalesQty(0)
                                                                                                        .viewsCount(0)
                                                                                                                .build();
        broadcastStatisticsService.insertBroadcastStatistics(broadcastStatistics);
        if (result != 1) {
            throw new RuntimeException("broadcast insert failed");
        }
    }

    @Override
    @Transactional
    public void saveBroadcastProduct(BroadcastProductDTO broadcastProductDTO) {
        int result = broadcastRegisterMapper.insertBroadcastProduct(broadcastProductDTO);
        if (result != 1) {
            throw new RuntimeException("broadcastProduct insert failed");
        }
    }

    @Override
    @Transactional
    public void saveNotice(NoticeDTO noticeDTO) {
        int result = broadcastRegisterMapper.insertNotice(noticeDTO);
        if (result != 1) {
            throw new RuntimeException("notice insert failed");
        }
    }

    @Override
    @Transactional
    public void saveFaq(FaqDTO faqDTO) {
        int result = broadcastRegisterMapper.insertFaq(faqDTO);
        if (result != 1) {
            throw new RuntimeException("faq insert failed");
        }
    }

    @Override
    @Transactional
    public void saveBenefit(BenefitDTO benefitDTO) {
        int result = broadcastRegisterMapper.insertBenefit(benefitDTO);
        if (result != 1) {
            throw new RuntimeException("benefit insert failed");
        }
    }

    @Override
    public List<ChannelSalesProductDTO> getChannelSalesProductAll(long vendorId) {
        List<ChannelSalesProductDTO> productList = broadcastRegisterMapper.selectChannelSalesProduct(vendorId);
        if (productList.isEmpty()) {
            throw new RuntimeException("product list empty");
        } else {
            return productList;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrepareBroadcastInfoDTO> getUpcomingBroadcastInfo(long vendorId) {
        return broadcastRegisterMapper.selectPrepareBroadcastInfo(vendorId);
    }
}
