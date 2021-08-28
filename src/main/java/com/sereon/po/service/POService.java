package com.sereon.po.service;

import com.querydsl.jpa.OpenJPATemplates;
import com.sereon.po.dto.*;
import com.sereon.po.entity.FirmsByStockCode;
import com.sereon.po.entity.IPO;
import com.sereon.po.entity.MyAccount;
import com.sereon.po.entity.Subscription;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface POService {


    String IPOSave(IPODTO dto);
    IPODTO IPORead(String stockCode);
    void IPORemove(String stockCode);
    void IPOModify(IPODTO dto);

    Long myIPOSave(MyIPODTO dto);
    MyIPODTO myIPORead(Long sno);
    void myIPORemove(Long sno);
    void myIPOModify(MyIPODTO dto);
    List<FirmsByStockCode> getFirms (String stockCode);
    List<FirmsByStockCode> getFirmsBySno(Long sno);
    List<Long> getSummary(PageRequestDTO requestDTO);

    PageResultDTO<IPODTO, IPO> getIPOList(PageRequestDTO requestDTO);

    PageResultDTO<MyIPODTO, Object[]> getMyIPOList(PageRequestDTO requestDTO);

    PageResultDTO<MyIPODTO, Subscription> getMyIPOListByPeriod(PageRequestDTO requestDTO);

    List<MyIPOExcelDTO> getMyIPOList(String userId);

    AccountDTO getMyAccount(String email);
    void myAccountSave(AccountDTO dto);

    default IPO dtoToEntity(IPODTO dto){
        IPO entity = IPO.builder()
                .stockCode(dto.getStockCode())
                .name(dto.getName())
                .endDate(dto.getEndDate())
                .startDate(dto.getStartDate())
                .listedDate(dto.getListedDate())
                .refundDate(dto.getRefundDate())
                .parValue(dto.getParValue())
                .offeringPrice(dto.getOfferingPrice())
                .leadingFirm(dto.getLeadingFirm())
                .unlistedPrice(dto.getUnlistedPrice())
                .detailURL(dto.getDetailURL())
                .type(dto.getType())
                .build();
        return entity;
    }

    default IPODTO entityToDTO(IPO entity){
        IPODTO dto = IPODTO.builder()
                .stockCode(entity.getStockCode())
                .name(entity.getName())
                .endDate(entity.getEndDate())
                .startDate(entity.getStartDate())
                .listedDate(entity.getListedDate())
                .refundDate(entity.getRefundDate())
                .parValue(entity.getParValue())
                .offeringPrice(entity.getOfferingPrice())
                .leadingFirm(entity.getLeadingFirm())
                .unlistedPrice(entity.getUnlistedPrice())
                .detailURL(entity.getDetailURL())
                .type(entity.getType())
                .build();

        return dto;
    }

    default Subscription dtoToEntity(MyIPODTO dto){
        Subscription entity = Subscription.builder()
                .assignAmt(dto.getAssignAmt())
                .deposit((dto.getDeposit()))
                .fee(dto.getFee())
                .sellPrice(dto.getSellPrice())
                .sno(dto.getSno())
                .IPO(IPO.builder().stockCode(dto.getStockCode()).build())
                .stockFirm(dto.getStockFirm())
                .subsAmt(dto.getSubsAmt())
                .tax(dto.getTax())
                .interest(dto.getInterest())
                .userId(dto.getUserId())
                .loan(dto.getLoan())
                .build();
        return entity;
    }

    default MyIPODTO entityToDTO(Subscription entity){
        MyIPODTO dto = MyIPODTO.builder()
                .assignAmt(entity.getAssignAmt())
                .fee(entity.getFee()==null ? 0 : entity.getFee())
                .IPO(entity.getIPO())
                .deposit(entity.getDeposit())
                .sno(entity.getSno())
                .sellPrice(entity.getSellPrice()==null ? 0 : entity.getSellPrice())
                .stockFirm(entity.getStockFirm())
                .subsAmt(entity.getSubsAmt())
                .tax(entity.getTax()==null ? 0 : entity.getTax())
                .interest((entity.getInterest()==null ? 0 : entity.getInterest()))
                .name(entity.getIPO().getName())
                .userId(entity.getUserId())
                .listedDate(entity.getIPO().getListedDate())
                .offeringPrice(entity.getIPO().getOfferingPrice())
                .refundDate(entity.getIPO().getRefundDate())
                .startDate(entity.getIPO().getStartDate())
                .endDate(entity.getIPO().getEndDate())
                .stockCode(entity.getIPO().getStockCode())
                .loan(entity.getLoan())
                .build();

        return dto;
    }

    default MyIPODTO entityToDTO(Subscription entity, IPO ipo){
        MyIPODTO dto = MyIPODTO.builder()
                .assignAmt(entity.getAssignAmt())
                .fee(entity.getFee()==null ? 0 : entity.getFee())
                .IPO(entity.getIPO())
                .deposit(entity.getDeposit())
                .sno(entity.getSno())
                .sellPrice(entity.getSellPrice()==null ? 0 : entity.getSellPrice())
                .stockFirm(entity.getStockFirm())
                .subsAmt(entity.getSubsAmt())
                .tax(entity.getTax()==null ? 0 : entity.getTax())
                .interest((entity.getInterest()==null ? 0 : entity.getInterest()))
                .name(entity.getIPO().getName())
                .userId(entity.getUserId())
                .listedDate(ipo.getListedDate())
                .offeringPrice(ipo.getOfferingPrice())
                .refundDate(ipo.getRefundDate())
                .startDate(ipo.getStartDate())
                .endDate(ipo.getEndDate())
                .stockCode(ipo.getStockCode())
                .loan(entity.getLoan())
                .build();

        return dto;
    }

    default FirmsDTO entityToDTO(FirmsByStockCode entity){

        FirmsDTO dto  = FirmsDTO.builder()
                .stockCode(entity.getStockCode())
                .firmName(entity.getFirmName())
                .assignAmt(entity.getAssignAmt())
                .limitAmt(entity.getLimitAmt())
                .RMK(entity.getRMK())
                .build();

        return dto;
    }

    default MyAccount dtoToEntity(AccountDTO dto){
        MyAccount entity = MyAccount.builder()
                .email(dto.getEmail())
                .bankName(dto.getBankName())
                .investmentMoney(dto.getInvestmentMoney())
                .interestRate(dto.getInterestRate())
                .build();

        return entity;
    }

    default  AccountDTO entityToDTO(MyAccount entity){
        AccountDTO dto = AccountDTO.builder()
                .email(entity.getEmail())
                .bankName(entity.getBankName())
                .investmentMoney(entity.getInvestmentMoney())
                .interestRate(entity.getInterestRate())
                .build();

        return dto;
    }

    default MyIPOExcelDTO entityToExcelDTO(Subscription entity, IPO ipo) {
        Long buyAmt = entity.getAssignAmt()==null? null: ipo.getOfferingPrice()*entity.getAssignAmt();
        Long profit = (entity.getAssignAmt()==null? null: entity.getSellPrice()==null || entity.getSellPrice()==0? 0: (entity.getSellPrice()-ipo.getOfferingPrice())* entity.getAssignAmt() - (entity.getFee()==null?0:entity.getFee()) - (entity.getInterest()==null?0:entity.getInterest()) - (entity.getTax()==null?0:entity.getTax()));
        Double profitRate = profit == null||buyAmt==null||buyAmt==0? null : ((float) profit / (float) buyAmt) * 100.00;

        MyIPOExcelDTO dto = MyIPOExcelDTO.builder()
                .assignAmt(entity.getAssignAmt())
                .deposit(entity.getDeposit())
                .fee(entity.getFee())
                .interest(entity.getInterest())
                .listedDate(ipo.getListedDate())
                .loan(entity.getLoan())
                .name(ipo.getName())
                .offeringPrice(ipo.getOfferingPrice())
                .refundDate(ipo.getRefundDate())
                .sno(entity.getSno())
                .sellPrice(entity.getSellPrice())
                .endDate(ipo.getEndDate())
                .startDate(ipo.getStartDate())
                .stockCode(ipo.getStockCode())
                .stockFirm(entity.getStockFirm())
                .subsAmt(entity.getSubsAmt())
                .tax(entity.getTax())
                .buyAmt(entity.getAssignAmt()==null? null: buyAmt)
                .profit(entity.getAssignAmt()==null? null: entity.getSellPrice()==null || entity.getSellPrice()==0? 0: profit)
                .profitRate(entity.getAssignAmt()==null? 0.0: entity.getSellPrice()==null || entity.getSellPrice()==0? 0.0: profitRate)
                .totalMoney(entity.getAssignAmt()==null||entity.getSellPrice()==null? null: entity.getSellPrice()* entity.getAssignAmt() - (entity.getFee()==null?0:entity.getFee()) - (entity.getTax()==null?0:entity.getTax()))
                .refundAmt(entity.getAssignAmt()==null? null: entity.getDeposit() - ipo.getOfferingPrice()*entity.getAssignAmt())
                .build();

        return dto;
    }

}


