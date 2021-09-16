package com.sereon.po.controller;

import com.sereon.po.dto.*;
import com.sereon.po.entity.ExcelCell;
import com.sereon.po.entity.IPO;
import com.sereon.po.entity.Subscription;
import com.sereon.po.security.dto.PoAuthMemberDTO;
import com.sereon.po.service.ExcelService;
import com.sereon.po.service.POService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.List;

@Controller
@RequestMapping("/po")
@Log4j2
@RequiredArgsConstructor
public class POController
{

    private final POService poService;
    private final ExcelService excelService;

    @GetMapping({"/",""})
    public String index(){
        return "redirect:/po/IPOlist";
    }

    @GetMapping("/IPOlist")
    public void IPOlist(PageRequestDTO pageRequestDTO, Model model){
        log.info("IPO list");


        model.addAttribute("result", poService.getIPOList(pageRequestDTO));

    }

    @GetMapping({"/IPOread", "/IPOmodify"})
    public void IPORead(String stockCode, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model){
        log.info("IPO Detail Page");
        log.info("stockCode : "+stockCode);

        model.addAttribute("dto", poService.IPORead(stockCode));
        model.addAttribute("firms", poService.getFirms(stockCode));

    }

    @PostMapping("/IPOmodify")
    public String IPOmodify(IPODTO dto, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes){
        log.info("IPO Modify Page");
        log.info("dto : "+ dto);

        poService.IPOModify(dto);

        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
        redirectAttributes.addAttribute("stockCode", dto.getStockCode());

        return "redirect:/po/IPOread";
    }

    @GetMapping("/IPOenroll")
    public void IPOenroll(){
        log.info("IPO Enroll Page");

    }

    @PostMapping("/IPOenroll")
    public String IPOenroll(IPODTO dto, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes){
        log.info("IPO Enroll Service");
        log.info("dto : "+ dto);

        poService.IPOSave(dto);


        return "redirect:/po/IPOlist";
    }

    @GetMapping("/myIPO")
    public void myIPO(@AuthenticationPrincipal PoAuthMemberDTO poAuthMemberDTO,  PageRequestDTO pageRequestDTO, Model model){
        log.info("myIPO list");

        pageRequestDTO.setUserId(poAuthMemberDTO.getEmail());
        pageRequestDTO.setSize(12);
        model.addAttribute("result", poService.getMyIPOListByPeriod(pageRequestDTO));
        model.addAttribute("summary", poService.getSummary(pageRequestDTO));

    }

    @GetMapping("/myIPOByPeriod")
    public void myIPOByPeriod(@AuthenticationPrincipal PoAuthMemberDTO poAuthMemberDTO, PageRequestDTO pageRequestDTO, Model model){
        log.info("myIPO list");
        pageRequestDTO.setUserId(poAuthMemberDTO.getEmail());
        model.addAttribute("result", poService.getMyIPOListByPeriod(pageRequestDTO));

    }

    @GetMapping("/myIPORegister")
    public void myIPORegister(@AuthenticationPrincipal PoAuthMemberDTO poAuthMemberDTO, String stockCode, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model) {
        log.info("My IPO Register Page");
        log.info("stockCode : " + stockCode);

        model.addAttribute("dto", poService.IPORead(stockCode));
        model.addAttribute("firms", poService.getFirms(stockCode));
        model.addAttribute("account", poService.getMyAccount(poAuthMemberDTO.getEmail()));
    }

    @PostMapping("/myIPORegister")
    public String myIPORegister(@AuthenticationPrincipal PoAuthMemberDTO poAuthMemberDTO, MyIPODTO dto,  @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes){
        log.info("myIPO Register");
        log.info("dto : "+ dto);

        pageRequestDTO.setUserId(poAuthMemberDTO.getEmail());
        poService.myIPOSave(dto);

        return "redirect:/po/myIPO";
    }


    @GetMapping({"/myIPORead", "myIPOModify"})
    public void myIPORead(@AuthenticationPrincipal PoAuthMemberDTO poAuthMemberDTO, Long sno, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model){
        log.info("myIPO Detail Page");
        log.info("sno : "+sno);

        model.addAttribute("dto", poService.myIPORead(sno));
        model.addAttribute("firms", poService.getFirmsBySno(sno));
        model.addAttribute("account", poService.getMyAccount(poAuthMemberDTO.getEmail()));
    }

    @PostMapping("/myIPOModify")
    public String myIPOModify(@AuthenticationPrincipal PoAuthMemberDTO poAuthMemberDTO, MyIPODTO dto, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes){
        log.info("myIPO Modify Page");

        pageRequestDTO.setUserId(poAuthMemberDTO.getEmail());
        log.info("dto : "+ dto);
        poService.myIPOModify(dto);


        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
        redirectAttributes.addAttribute("sno", dto.getSno());

        return "redirect:/po/myIPORead";
    }

    @PostMapping("/myIPORemove")
    public String myIPORemove(Long sno, RedirectAttributes redirectAttributes){
        log.info("myIPO Remove");

        poService.myIPORemove(sno);

        return "redirect:/po/myIPO";
    }

    @GetMapping("/IPOPopUp")
    public void IPOPopUp(PageRequestDTO pageRequestDTO, Model model){
        log.info("IPOPopUp list");

        model.addAttribute("result", poService.getIPOList(pageRequestDTO));
    }

    @GetMapping("/MyAccount")
    public void myAccountRead(@AuthenticationPrincipal PoAuthMemberDTO poAuthMemberDTO, PageRequestDTO pageRequestDTO, Model model){
        log.info("MyAccount");
        pageRequestDTO.setUserId(poAuthMemberDTO.getEmail());

        model.addAttribute("result", poService.getMyAccount(poAuthMemberDTO.getEmail()));
    }

    @PostMapping("/MyAccount")
    public void myAccountSave(@AuthenticationPrincipal PoAuthMemberDTO poAuthMemberDTO, AccountDTO dto, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO){
        log.info("myAccount Save");
        pageRequestDTO.setUserId(poAuthMemberDTO.getEmail());
        dto.setEmail(poAuthMemberDTO.getEmail());

        poService.myAccountSave(dto);
    }

    @GetMapping("/ExcelDown")
    public void excelDownload(@AuthenticationPrincipal PoAuthMemberDTO poAuthMemberDTO, PageRequestDTO pageRequestDTO, Model model, HttpServletResponse response){
        log.info("Excel Download");

        pageRequestDTO.setUserId(poAuthMemberDTO.getEmail());
        List  myIPOExcelDTOs = poService.getMyIPOList(poAuthMemberDTO.getEmail());
        List<ExcelCell> excelCell = excelService.getCellProp("myIPO");

        XSSFWorkbook xlsxWB = excelService.createExcel("MYIPO.xlsx", excelCell, myIPOExcelDTOs);
        BufferedOutputStream outputStream = null;

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=MYIPO.xlsx");
        try {
            outputStream = new BufferedOutputStream(response.getOutputStream());
            xlsxWB.write(outputStream);
        }catch (Exception e){

        }
        finally {
            try {
                xlsxWB.close();
                if (outputStream != null) outputStream.close();
            }
            catch (Exception e){

            }
        }


    }

    @GetMapping("/ajaxTest")
    public void ajaxTest(PageRequestDTO pageRequestDTO, Model model){
        log.info("TEST!!!!!!!!!!!!!!!");
        model.addAttribute("result", poService.getIPOList(pageRequestDTO));
    }
}
