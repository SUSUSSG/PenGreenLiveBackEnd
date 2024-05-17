package susussg.pengreenlive.statistics.service;


import java.util.List;
import susussg.pengreenlive.statistics.dto.ProductInChannelDTO;
import susussg.pengreenlive.statistics.dto.SalesDataDTO;

public interface ProductStatisticsService {

    List<ProductInChannelDTO> getTop10ProductByChannel(Long channelSeq);

    List<ProductInChannelDTO> getAllProductsByChannelWithSales(Long channelSeq);

    SalesDataDTO getTotalSalesOrdersAvgPriceAvgBuyersAndAvgQuantityByChannel(Long channelSeq);
}
