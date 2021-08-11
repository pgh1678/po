package com.sereon.po.service;

import com.querydsl.jpa.OpenJPATemplates;
import com.sereon.po.dto.*;
import com.sereon.po.entity.FirmsByStockCode;
import com.sereon.po.entity.IPO;
import com.sereon.po.entity.Subscription;
import com.sereon.po.repository.FirmsRepository;
import com.sereon.po.repository.IPORepository;
import com.sereon.po.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class POServiceImpl implements POService{

    private final IPORepository ipoRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final FirmsRepository firmsRepository;

    @Override
    public PageResultDTO<IPODTO, IPO> getIPOList(PageRequestDTO requestDTO){
        log.info(requestDTO);

        Function<IPO, IPODTO> fn = (en -> entityToDTO((IPO) en));

        log.info(requestDTO.getYear()+requestDTO.getMonth());

        String YYYYMM = requestDTO.getYear()+String.format("%02d",requestDTO.getMonth());

        log.info(YYYYMM);

        Page<IPO> result = ipoRepository.findAllM(requestDTO.getPageable(Sort.by("startDate")), YYYYMM );

        log.info("rseult: "+result.stream().findFirst());

        return new PageResultDTO(result, fn);
    }


    @Override
    public String IPOSave(IPODTO dto){

        log.info(dto);

        IPO ipo = dtoToEntity(dto);
        ipoRepository.save(ipo);

        return dto.getStockCode();
    }

    @Override
    public IPODTO IPORead(String stockCode){
        Optional<IPO> result = ipoRepository.getIPOByStockCode(stockCode);

        log.info(result);
        return result.isPresent()? entityToDTO((result.get())): null;
    }

    @Override
    public void IPORemove(String stockCode){

    }
    @Override
    public void IPOModify(IPODTO dto){
        Optional<IPO> result = ipoRepository.getIPOByStockCode(dto.getStockCode());

        if(result.isPresent()){
            IPO entity = result.get();

            entity.setStartDate(dto.getStartDate());
            entity.setEndDate(dto.getEndDate());
            entity.setListedDate(dto.getListedDate());
            entity.setLeadingFirm(dto.getLeadingFirm());
            entity.setOfferingPrice(dto.getOfferingPrice());
            entity.setParValue(dto.getParValue());
            entity.setRefundDate(dto.getRefundDate());

            ipoRepository.save(entity);

        }
    }

    @Override
    public PageResultDTO<MyIPODTO, Object[]> getMyIPOList(PageRequestDTO requestDTO){
        log.info(requestDTO);

        Function<Object[], MyIPODTO> fn = (en -> entityToDTO((Subscription)en[0],(IPO)en[1]));

        Page<Object[]> result = subscriptionRepository.findAllByUserId(requestDTO.getPageable(Sort.by(Sort.Direction.DESC,"IPO.listedDate")),requestDTO.getUserId());

        if(result != null) {
            for (Object[] arr : result) {
                log.info(Arrays.toString(arr));
                //log.info("rseult: " + result.stream().findFirst());
            }
        }
        return new PageResultDTO(result, fn);
    }

    @Override
    public PageResultDTO<MyIPODTO, Subscription> getMyIPOListByPeriod(PageRequestDTO requestDTO){
        log.info(requestDTO);

        Function<Object[], MyIPODTO> fn = (en -> entityToDTO((Subscription)en[0],(IPO)en[1]));

        String fromDt = requestDTO.getFromDt()==null ? "":requestDTO.getFromDt().replace("-","");
        String toDt = requestDTO.getToDt()==null ? "":requestDTO.getToDt().replace("-","");


        Page<Object[]> result1 = null;
        if(requestDTO.getType() == null){
            log.info("Search Type :" + "a");
            result1 = subscriptionRepository.findAllByUserId(requestDTO.getPageable(Sort.by(Sort.Direction.DESC,"IPO.listedDate")),requestDTO.getUserId());
        }else if(requestDTO.getType().equals("l")) {
            log.info("Search Type :" + requestDTO.getType());
            log.info(requestDTO.getToDt().replace("-",""));
            result1 = subscriptionRepository.findAllByListedDateBetween(requestDTO.getPageable(Sort.by(Sort.Direction.DESC,"IPO.listedDate")),  fromDt, toDt);
        }else if(requestDTO.getType().equals("f")){
            log.info("Search Type :" + requestDTO.getType());
            result1 = subscriptionRepository.findAllByStartDateBetween(requestDTO.getPageable(Sort.by(Sort.Direction.DESC,"IPO.listedDate")), fromDt, toDt);
        }else if(requestDTO.getType().equals("t")){
            log.info("Search Type :" + requestDTO.getType());
            result1 = subscriptionRepository.findAllByEndDateBetween(requestDTO.getPageable(Sort.by(Sort.Direction.DESC,"IPO.listedDate")), fromDt, toDt);
        }else{
            log.info("Search Type :" + requestDTO.getType());
            result1 = subscriptionRepository.findAllByUserId(requestDTO.getPageable(Sort.by(Sort.Direction.DESC,"IPO.listedDate")),requestDTO.getUserId());
        }

        if(result1 != null) {
            for (Object[] arr : result1) {
                log.info(Arrays.toString(arr));
                //log.info("rseult: " + result.stream().findFirst());
            }
        }
        return new PageResultDTO(result1, fn);
    }

    @Override
    public List<Long> getSummary(PageRequestDTO requestDTO){
        log.info("Summary : "+ requestDTO);

        String fromDt = requestDTO.getFromDt()==null ? "":requestDTO.getFromDt().replace("-","");
        String toDt = requestDTO.getToDt()==null ? "":requestDTO.getToDt().replace("-","");

        Long ytd = subscriptionRepository.findYTD(requestDTO.getUserId(), fromDt, toDt);

        Long maxDeposit = subscriptionRepository.findMaxDeposit(requestDTO.getUserId(),fromDt, toDt);
        System.out.println("ytd = "+ytd + ", max =  "+ maxDeposit);

        List<Long> result = new ArrayList<Long>();

        result.add(ytd);
        result.add(maxDeposit);


        return result;
    }

    @Override
    public Long myIPOSave(MyIPODTO dto){
        log.info("myIPO Save: "+dto);

        Subscription entity = dtoToEntity(dto);
        subscriptionRepository.save(entity);

        return entity.getSno();
    }

    @Override
    public MyIPODTO myIPORead(Long sno){
        log.info("myIPO Read");
        Optional<Subscription> result = subscriptionRepository.findById(sno);

        log.info(result);
        return result.isPresent()? entityToDTO((result.get())): null;
    }

    @Override
    public void myIPORemove(Long sno){
        log.info("myIPO remove : "+ sno);
        subscriptionRepository.deleteById(sno);
    }

    @Override
    public List<FirmsByStockCode> getFirms (String stockCode){

        log.info("Firm list : "+stockCode);

        List<FirmsByStockCode> result = firmsRepository.findByStockCode(stockCode);
        log.info(result);

        return result;
    }

    @Override
    public List<FirmsByStockCode> getFirmsBySno(Long sno){
        log.info("Firm list : "+sno);

        List<FirmsByStockCode> result = firmsRepository.findBySno(sno);
        log.info(result);

        return result;
    }
    @Override
    public void myIPOModify(MyIPODTO dto){
        Optional<Subscription> result = subscriptionRepository.findById(dto.getSno());

        if(result.isPresent()){
            Subscription entity = result.get();

            entity.setAssignAmt(dto.getAssignAmt());
            entity.setDeposit(dto.getDeposit());
            entity.setFee(dto.getFee());
            entity.setTax(dto.getTax());
            entity.setSellPrice(dto.getSellPrice());
            entity.setIPO(IPO.builder().stockCode(dto.getStockCode()).build());
            entity.setStockFirm(dto.getStockFirm());
            entity.setSubsAmt(dto.getSubsAmt());


            subscriptionRepository.save(entity);

        }
    }

}
