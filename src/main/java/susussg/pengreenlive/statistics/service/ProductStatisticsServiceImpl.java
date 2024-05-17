package susussg.pengreenlive.statistics.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.statistics.dto.ProductDetailsDTO;
import susussg.pengreenlive.statistics.dto.ProductInChannelDTO;
import susussg.pengreenlive.statistics.dto.SalesDataDTO;
import susussg.pengreenlive.statistics.mapper.ProductStatisticsMapper;

@Service
public class ProductStatisticsServiceImpl implements ProductStatisticsService{

    @Autowired
    ProductStatisticsMapper productStatisticsMapper;

    @Override
    public List<ProductInChannelDTO> getTop10ProductByChannel(Long channelSeq) {
        return productStatisticsMapper.findTop10ProductsByChannel(channelSeq);
    }

    @Override
    public List<ProductInChannelDTO> getAllProductsByChannelWithSales(Long channelSeq) {
        return productStatisticsMapper.findAllProductsByChannelWithSales(channelSeq);
    }

    @Override
    public SalesDataDTO getTotalSalesOrdersAvgPriceAvgBuyersAndAvgQuantityByChannel(Long channelSeq) {
        return productStatisticsMapper.findTotalSalesOrdersAvgPriceAvgBuyersAndAvgQuantityByChannel(channelSeq);
    }

    public ProductDetailsDTO getProductDetailsByProductCd(String productCd) {
        return productStatisticsMapper.findProductDetailsByProductCd(productCd);
    }
}
