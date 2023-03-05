package com.jingdianjichi.tool.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * easyExcel数据导入监听抽象模板
 *
 * @author: ChickenWing
 * @date: 2023/3/5
 */
@Slf4j
public abstract class AbstractEasyExcelImport<T> extends AnalysisEventListener<T> {

    /**
     * 存储数据库的最大数量
     */
    public static final int SAVE_DB_MAX_SIZE = 100000;

    /**
     * 自定义用于暂时存储data。
     */
    private List<T> dataList = new ArrayList<>();

    @Override
    public void invoke(T object, AnalysisContext context) {
        dataList.add(object);
        if (dataList.size() >= SAVE_DB_MAX_SIZE) {
            saveData();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
    }

    /**
     * 保存数据到 DB
     */
    private void saveData() {
        if (dataList.size() > 0) {
            doSaveData(dataList);
            dataList.clear();
        }
    }

    protected abstract void doSaveData(List<T> data);

}
