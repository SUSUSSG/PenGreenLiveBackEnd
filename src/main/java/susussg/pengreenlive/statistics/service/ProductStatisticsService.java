package susussg.pengreenlive.statistics.service;


import java.util.List;
import susussg.pengreenlive.statistics.dto.ProductInChannelDTO;

public interface ProductStatisticsService {

    List<ProductInChannelDTO> getTop10ProductByChannel(Long channelSeq);
    List<ProductInChannelDTO> getAllProductsByChannelWithSales(Long channelSeq);

}
