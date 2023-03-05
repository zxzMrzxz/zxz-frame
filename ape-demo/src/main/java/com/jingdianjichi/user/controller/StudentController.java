package com.jingdianjichi.user.controller;

import com.jingdianjichi.bean.Result;
import com.jingdianjichi.tool.SpringContextUtils;
import com.jingdianjichi.tool.excel.EasyExcelUtil;
import com.jingdianjichi.user.entity.po.SysUser;
import com.jingdianjichi.user.excel.StudentExcelExport;
import com.jingdianjichi.user.excel.StudentExcelImport;
import com.jingdianjichi.user.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户数据导出controller
 *
 * @author: ChickenWing
 * @date: 2023/3/5
 */
@Api(tags = "用户数据导出controller")
@RestController
@Slf4j
@RequestMapping(value = "sysUserExport")
public class StudentController {

    @Resource
    private StudentExcelExport studentExcelExport;

    @Resource
    private SysUserService sysUserService;

    @GetMapping("/exportSmallData")
    public void exportSmallData() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1L);
        studentExcelExport.exportWithSmallData(map);
    }

    @ApiOperation(value = "导出百万数据到excel", notes = "导出百万数据到excel")
    @GetMapping("exportBigData")
    public void exportData() {
        Map<String, Object> map = new HashMap<>();
        studentExcelExport.exportWithBigData("用户列表", map);
    }


    @ApiOperation(value = "从excel读取百万数据导入到db", notes = "从excel读取百万数据导入到db")
    @PostMapping("importBigData")
    public Result<Boolean> importData(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        try {
            EasyExcelUtil.readExcel(multipartFile.getInputStream(), SysUser.class,
                    new StudentExcelImport(sysUserService), 0);
            return Result.ok(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.fail();
        }
    }

}