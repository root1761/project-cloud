package com.lnsoft.qxgd.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.lnsoft.qxgd.model.User;
import com.lnsoft.qxgd.response.Response;
import com.lnsoft.qxgd.service.IUserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author Louyp
 * @since 2020-11-23
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
private IUserService iUserService;
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping(value = "/getActivitiDefined",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getActivitiDefined(HttpServletResponse response) throws IOException {
        final List<User> list = new ArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户详情","用户"),
                User .class, list);
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode("用户详情.xls","UTF-8"));
        workbook.write(response.getOutputStream());
    }
    @GetMapping("/test")
    public Boolean test(){
        return iUserService.insert();
    }
}
