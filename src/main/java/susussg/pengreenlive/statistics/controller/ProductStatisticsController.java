package susussg.pengreenlive.statistics.controller;

import java.util.List;
import java.util.PrimitiveIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.login.service.SecurityService;
import susussg.pengreenlive.statistics.dto.ProductDetailsDTO;
import susussg.pengreenlive.statistics.dto.ProductInChannelDTO;
import susussg.pengreenlive.statistics.dto.SalesDataDTO;
import susussg.pengreenlive.statistics.service.ProductStatisticsService;

@RestController
@RequestMapping("/api")
public class ProductStatisticsController {

    @Autowired
    ProductStatisticsService productStatisticsService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/products/statistics/top-products")
    public List<ProductInChannelDTO> getTopProducts() {
        Long channelSeq = securityService.getCurrentChannelSeq();
        return productStatisticsService.getTop10ProductByChannel(channelSeq);
    }

    @GetMapping("/products/statistics/all-products")
    public List<ProductInChannelDTO> getAllProducts() {
        Long channelSeq = securityService.getCurrentChannelSeq();
        return productStatisticsService.getAllProductsByChannelWithSales(channelSeq);
    }

    @GetMapping("/products/statistics/sales-data")
    public SalesDataDTO getTotalSalesOrdersAvgPriceAvgBuyersAndAvgQuantity() {
        Long channelSeq = securityService.getCurrentChannelSeq();
        return productStatisticsService.getTotalSalesOrdersAvgPriceAvgBuyersAndAvgQuantityByChannel(channelSeq);
    }
    @GetMapping("/products/statistics/product-details")
    public ProductDetailsDTO getProductDetails(@RequestParam String productCd) {
        return productStatisticsService.getProductDetailsByProductCd(productCd);
    }

}
