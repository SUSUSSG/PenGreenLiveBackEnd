package susussg.pengreenlive.broadcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.broadcast.service.ProductClickService;

@RestController
@RequestMapping("/api/product-clicks")
public class ProductClickController {

    @Autowired
    private ProductClickService productClickService;

//    @PostMapping
//    public ResponseEntity<Void> createProductClick(@RequestParam Long broadcastSeq, @RequestParam Long productSeq) {
//        productClickService.addProductClick(broadcastSeq, productSeq);
//        return ResponseEntity.ok().build();
//    }

//    @PostMapping("/broadcast/{broadcastSeq}")
//    public ResponseEntity<Void> createProductClickByBroadcast(@PathVariable Long broadcastSeq) {
//        productClickService.addProductClicksForBroadcast(broadcastSeq);
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/broadcast/{broadcastSeq}/product/{productSeq}/increment-click")
    public ResponseEntity<Void> incrementProductClick(@PathVariable Long broadcastSeq, @PathVariable Long productSeq) {
        productClickService.incrementProductClick(broadcastSeq, productSeq);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/broadcast/{broadcastSeq}/update-average-clicks")
    public ResponseEntity<Void> updateAverageClicks(@PathVariable Long broadcastSeq) {
        productClickService.updateAverageProductClicks(broadcastSeq);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateConversionRates/{broadcastSeq}")
    public void updateConversionRates(@PathVariable long broadcastSeq) {
        productClickService.updateConversionRates(broadcastSeq);
    }
}
