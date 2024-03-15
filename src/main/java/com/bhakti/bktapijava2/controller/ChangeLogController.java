package com.bhakti.bktapijava2.controller;

import com.bhakti.bktapijava2.entity.MstConfig;
import com.bhakti.bktapijava2.service.IMstConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChangeLogController {

    @Autowired
    private IMstConfigService mstConfigService;

    @GetMapping("/changelog")
    public String changelog(Model model) {
        MstConfig mstConfig = mstConfigService.getConfigByConfigNameIsVaPaymentConsumer();

        model.addAttribute("mstConfig", mstConfig);
        return "changelog";
    }

}
