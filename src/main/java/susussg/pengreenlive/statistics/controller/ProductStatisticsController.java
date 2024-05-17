package susussg.pengreenlive.statistics.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.statistics.dto.ProductInChannelDTO;
import susussg.pengreenlive.statistics.service.ProductStatisticsService;

@RestController
public class ProductStatisticsController {

    @Autowired
    ProductStatisticsService productStatisticsService;

    private Long channelSeq = 2L; // TODO : 세션에서 channelSeq 받아오기

    @GetMapping("/products/statistics/top-products")
    public List<ProductInChannelDTO> getTopProducts() {
        return productStatisticsService.getTop10ProductByChannel(channelSeq);
    }

    @GetMapping("/products/statistics/all-products")
    public List<ProductInChannelDTO> getAllProducts() {
        return productStatisticsService.getAllProductsByChannelWithSales(channelSeq);
    }


}
