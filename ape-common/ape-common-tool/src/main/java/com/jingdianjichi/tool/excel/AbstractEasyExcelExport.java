package com.jingdianjichi.tool.excel;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.jingdianjichi.tool.SpringContextUtils;
import com.jingdianjichi.tool.UuidUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * easyExcel数据导出监听抽象模板
 *
 * @author: ChickenWing
 * @date: 2023/3/5
 */
@Slf4j
public abstract class AbstractEasyExcelExport<T> {

    /**
     * 导出小数据量(百万以下)
     */
    protected void exportSmallData(String fileName, List<List<String>> head, Collection<T> data) {
        HttpServletResponse response = SpringContextUtils.getHttpServletResponse();
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String sheetName = fileName;
            fileName = URLEncoder.encode(fileName + UuidUtils.getUuid(), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName
                    + ExcelTypeEnum.XLSX.getValue());
            EasyExcelUtil.write(outputStream, sheetName, head, data);
        } catch (Exception e) {
            log.error("AbstractEasyExcelExport.exportWithSmallData.error:{}", e.getMessage(), e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    log.error("AbstractEasyExcelExport.exportWithSmallData.close.error:{}", e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 导出大数据量(百万级别)
     */
    protected void exportWithBigData(String fileName, List<List<String>> head,
                                     Map<String, Object> queryCondition) {
        HttpServletResponse response = SpringContextUtils.getHttpServletResponse();
        // 总的记录数
        Long totalCount = dataTotalCount(queryCondition);
        // 每一个Sheet存放的数据
        Long sheetDataRows = eachSheetTotalCount();
        // 每次写入的数据量20w
        Long writeDataRows = eachTimesWriteSheetTotalCount();
        if (totalCount < sheetDataRows) {
            sheetDataRows = totalCount;
        }
        if (sheetDataRows < writeDataRows) {
            writeDataRows = sheetDataRows;
        }
        doExport(response, fileName, head, queryCondition, totalCount, sheetDataRows, writeDataRows);
    }


    private void doExport(HttpServletResponse response, String fileName, List<List<String>> head,
                          Map<String, Object> queryCondition, Long totalCount, Long sheetDataRows,
                          Long writeDataRows) {
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            WriteWorkbook writeWorkbook = new WriteWorkbook();
            writeWorkbook.setOutputStream(outputStream);
            writeWorkbook.setExcelType(ExcelTypeEnum.XLSX);
            ExcelWriter writer = new ExcelWriter(writeWorkbook);
            WriteTable table = new WriteTable();
            table.setHead(head);
            // 计算需要的Sheet数量
            Long sheetNum = totalCount % sheetDataRows == 0 ? (totalCount / sheetDataRows) : (totalCount / sheetDataRows + 1);
            // 计算一般情况下每一个Sheet需要写入的次数(一般情况不包含最后一个sheet,因为最后一个sheet不确定会写入多少条数据)
            Long oneSheetWriteCount = totalCount > sheetDataRows ? sheetDataRows / writeDataRows :
                    totalCount % writeDataRows > 0 ? totalCount / writeDataRows + 1 : totalCount / writeDataRows;
            // 计算最后一个sheet需要写入的次数
            Long lastSheetWriteCount = totalCount % sheetDataRows == 0 ? oneSheetWriteCount :
                    (totalCount % sheetDataRows % writeDataRows == 0 ? (totalCount / sheetDataRows / writeDataRows) :
                            (totalCount / sheetDataRows / writeDataRows + 1));
            // 分批查询分次写入
            List<List<String>> dataList = new ArrayList<>();
            for (int i = 0; i < sheetNum; i++) {
                // 创建Sheet
                WriteSheet sheet = new WriteSheet();
                sheet.setSheetNo(i);
                sheet.setSheetName(sheetNum == 1 ? fileName : fileName + i);
                // 循环写入次数: j的自增条件是当不是最后一个Sheet的时候写入次数为正常的每个Sheet写入的次数,如果是最后一个就需要使用计算的次数lastSheetWriteCount
                for (int j = 0; j < (i != sheetNum - 1 || i == 0 ? oneSheetWriteCount : lastSheetWriteCount); j++) {
                    // 集合复用,便于GC清理
                    dataList.clear();
                    buildDataList(dataList, queryCondition, j + 1 + oneSheetWriteCount * i, writeDataRows);
                    // 写数据
                    writer.write(dataList, sheet, table);
                }
            }
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String((fileName + UuidUtils.getUuid()).getBytes("gb2312"),
                    "ISO-8859-1") + ".xlsx");
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            writer.finish();
            outputStream.flush();
        } catch (Exception e) {
            log.error("AbstractEasyExcelExport.exportWithBigData.error:{}", e.getMessage(), e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    log.error("AbstractEasyExcelExport.exportWithBigData.close.error:{}", e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 计算导出数据的总数
     */
    protected abstract Long dataTotalCount(Map<String, Object> conditions);

    /**
     * 每一个sheet存放的数据总数
     */
    protected abstract Long eachSheetTotalCount();

    /**
     * 每次写入sheet的总数
     */
    protected abstract Long eachTimesWriteSheetTotalCount();

    protected abstract void buildDataList(List<List<String>> resultList, Map<String, Object> queryCondition,
                                          Long pageNo, Long pageSize);

}
