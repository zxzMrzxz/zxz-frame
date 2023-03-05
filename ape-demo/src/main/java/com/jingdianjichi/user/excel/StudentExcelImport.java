package com.jingdianjichi.user.excel;

import com.jingdianjichi.tool.excel.AbstractEasyExcelImport;
import com.jingdianjichi.user.entity.po.SysUser;
import com.jingdianjichi.user.service.SysUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 学生excel百万数据导入监听
 *
 * @author tojson
 * @date 2022/7/9 6:30
 */
@Component
public class StudentExcelImport extends AbstractEasyExcelImport<SysUser> {

    private SysUserService sysUserService;

    public StudentExcelImport(){}

    public StudentExcelImport(SysUserService sysUserService){
        this.sysUserService = sysUserService;
    }

    @Override
    protected  void doSaveData(List<SysUser> data) {
        sysUserService.batchInsert(data);
    }

}
