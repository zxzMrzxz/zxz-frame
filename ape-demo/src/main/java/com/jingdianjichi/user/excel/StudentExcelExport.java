package com.jingdianjichi.user.excel;

import com.jingdianjichi.bean.PageResponse;
import com.jingdianjichi.tool.excel.AbstractEasyExcelExport;
import com.jingdianjichi.user.entity.po.SysUser;
import com.jingdianjichi.user.entity.req.SysUserReq;
import com.jingdianjichi.user.service.SysUserService;
import com.mysql.cj.log.Log;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 商品数据导出
 *
 * @author: ChickenWing
 * @date: 2023/3/5
 */
@Component
public class StudentExcelExport extends AbstractEasyExcelExport<SysUser> {

    @Resource
    private SysUserService sysUserService;

    /**
     * 小数据量数据导出excel
     */
    public void exportWithSmallData(Map<String, Object> conditions) {
        List<List<String>> head = new ArrayList<List<String>>();
        head.add(Collections.singletonList("用户编号"));
        head.add(Collections.singletonList("用户姓名"));
        List<SysUser> sysUserList = sysUserService.queryByExport(conditions);
        this.exportSmallData("用户列表", head, sysUserList);
    }

    /**
     * 百万数据导出excel
     */
    public void exportWithBigData( String fileName, Map<String, Object> conditions) {
        List<List<String>> head = new ArrayList<List<String>>();
        head.add(Collections.singletonList("用户编号"));
        head.add(Collections.singletonList("用户姓名"));
        this.exportWithBigData( fileName, head, conditions);
    }

    @Override
    protected Long dataTotalCount(Map<String, Object> conditions) {
        return sysUserService.queryCount(conditions);
    }

    @Override
    protected Long eachSheetTotalCount() {
        return 500000L;
    }

    @Override
    protected Long eachTimesWriteSheetTotalCount() {
        return 5000L;
    }

    @Override
    protected void buildDataList(List<List<String>> resultList, Map<String, Object> queryCondition,
                                 Long pageNo, Long pageSize) {
        SysUserReq sysUserReq = new SysUserReq();
        sysUserReq.setPageNo(pageNo);
        sysUserReq.setPageSize(pageSize);
        PageResponse<SysUser> pageResponse = sysUserService.queryByPage(sysUserReq);
        List<SysUser> sysUserList = pageResponse.getResult();
        if (CollectionUtils.isNotEmpty(sysUserList)) {
            sysUserList.forEach(sysUser -> {
                resultList.add(Arrays.asList(sysUser.getId().toString(), sysUser.getName()));
            });
        }
    }

}
