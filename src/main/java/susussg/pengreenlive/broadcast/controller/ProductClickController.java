package susussg.pengreenlive.broadcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.broadcast.service.ProductClickService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/product-clicks")
public class ProductClickController {

    @Autowired
    private ProductClickService productClickService;

    @Operation(summary = "제품 클릭 수를 증가시킵니다.", description = "방송 ID와 제품 ID를 받아 해당 제품의 클릭 수를 증가시킵니다.")
    @PostMapping("/broadcast/{broadcastSeq}/product/{productSeq}/increment-click")
    public ResponseEntity<Void> incrementProductClick(@PathVariable Long broadcastSeq, @PathVariable Long productSeq) {
        productClickService.incrementProductClick(broadcastSeq, productSeq);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "평균 클릭 수를 업데이트합니다.", description = "방송 ID를 받아 해당 방송의 평균 제품 클릭 수를 업데이트합니다.")
    @PostMapping("/broadcast/{broadcastSeq}/update-average-clicks")
    public ResponseEntity<Void> updateAverageClicks(@PathVariable Long broadcastSeq) {
        productClickService.updateAverageProductClicks(broadcastSeq);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "구매 전환율을 업데이트합니다.", description = "방송 ID를 받아 해당 방송 제품의 구매 전환율을 업데이트합니다.")
    @PostMapping("/updateConversionRates/{broadcastSeq}")
    public void updateConversionRates(@PathVariable long broadcastSeq) {
        productClickService.updateConversionRates(broadcastSeq);
    }
}
