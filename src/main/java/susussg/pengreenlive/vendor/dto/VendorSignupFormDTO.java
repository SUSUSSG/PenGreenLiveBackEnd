package susussg.pengreenlive.vendor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorSignupFormDTO {
    private Long vendorSeq;
    private Long channelSeq;
    private String businessId;
    private String vendorNm;
    private String vendorPw;
    private String vendorTel;
    private String vendorEmail;
    private String companyNm;
    private String industry;
    private String businessType;
    private String representativeNm;
    private LocalDate establishmentDt;
    private String handledItems;
    private String companyTel;
    private String corporateNumber;
    private String enterpriseType;
    private String companyAddress;
    private String businessClassification;
    private Long revenue;
    private String faxNumber;
    private String websiteUrl;
    private Boolean optionalAgreementConsent;
}
