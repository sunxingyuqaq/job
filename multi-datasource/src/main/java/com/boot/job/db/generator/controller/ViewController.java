package com.boot.job.db.generator.controller;


import com.boot.job.db.common.entity.FebsConstant;
import com.boot.job.db.common.utils.FebsUtil;
import com.boot.job.db.generator.entity.GeneratorConfig;
import com.boot.job.db.generator.servie.IGeneratorConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author MrBird
 */
@Controller("generatorViews")
@RequestMapping(FebsConstant.VIEW_PREFIX + "generator")
public class ViewController {

    @Autowired
    private IGeneratorConfigService generatorConfigService;

    @GetMapping("generator")
    @RequiresPermissions("generator:view")
    public String generator() {
        return FebsUtil.view("generator/generator");
    }

    @GetMapping("configure")
    @RequiresPermissions("generator:configure:view")
    public String generatorConfigure(Model model) {
        GeneratorConfig generatorConfig = generatorConfigService.findGeneratorConfig();
        model.addAttribute("config", generatorConfig);
        return FebsUtil.view("generator/configure");
    }
}
