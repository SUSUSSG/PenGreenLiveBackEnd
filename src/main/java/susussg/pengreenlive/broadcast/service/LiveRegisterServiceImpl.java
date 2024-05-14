package susussg.pengreenlive.broadcast.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.broadcast.dto.*;
import susussg.pengreenlive.broadcast.mapper.LiveRegisterMapper;


import java.util.List;


@Service
@Log4j2
@RequiredArgsConstructor

public class LiveRegisterServiceImpl implements LiveRegisterService {

    @Autowired
    private final LiveRegisterMapper liveRegisterMapper;

    @Override
    @Transactional
    public String getChannelName(long vendorId) {
        String channelName = liveRegisterMapper.selectChannelName(vendorId);
        if (channelName.isEmpty()) {
            throw new RuntimeException("channel name emplty");
        } else {
            return channelName;
        }
    }

    @Override
    @Transactional
    public List<BroadcastCategoryDTO> getAllCategory() {
        List<BroadcastCategoryDTO> categoryList = liveRegisterMapper.selectAllCategory();
        if (categoryList.isEmpty()) {
            throw new RuntimeException("cateogry  emplty");
        } else {
            return categoryList;
        }
    }

    @Override
    @Transactional
    public void saveBroadcast(BroadcastDTO broadcastDTO) {
        int result = liveRegisterMapper.insertBroadcast(broadcastDTO);
        if (result != 1) {
            throw new RuntimeException("broadcast insert failed");
        }
    }

    @Override
    @Transactional
    public void saveBroadcastProduct(BroadcastProductDTO broadcastProductDTO) {
        int result = liveRegisterMapper.insertBroadcastProduct(broadcastProductDTO);
        if (result != 1) {
            throw new RuntimeException("broadcastProduct insert failed");
        }
    }

    @Override
    @Transactional
    public void saveNotice(NoticeDTO noticeDTO) {
        int result = liveRegisterMapper.insertNotice(noticeDTO);
        if (result != 1) {
            throw new RuntimeException("notice insert failed");
        }
    }

    @Override
    @Transactional
    public void saveFaq(FaqDTO faqDTO) {
        int result = liveRegisterMapper.insertFaq(faqDTO);
        if (result != 1) {
            throw new RuntimeException("faq insert failed");
        }
    }

    @Override
    @Transactional
    public void saveBenefit(BenefitDTO benefitDTO) {
        int result = liveRegisterMapper.insertBenefit(benefitDTO);
        if (result != 1) {
            throw new RuntimeException("benefit insert failed");
        }
    }

    @Override
    public List<ChannelSalesProductDTO> getChannelSalesProductAll(long vendorId) {
        List<ChannelSalesProductDTO> productList = liveRegisterMapper.selectChannelSalesProduct(vendorId);
        if (productList.isEmpty()) {
            throw new RuntimeException("product list empty");
        } else {
            return productList;
        }
    }

    @Override
    public List<UpcomingBroadcastInfoDTO> getUpcomingBroadcastInfo(long vendorId) {
        List<UpcomingBroadcastInfoDTO> broadcastInfoList = liveRegisterMapper.selectUpcomingBroadcastInfo(vendorId);
//        broadcastInfoList.forEach(info -> log.info(info.getBroadcastSeq()));
        return broadcastInfoList;
    }
}
