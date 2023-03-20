package com.ureport.ureportkeep.core.parser.json.cell.model.value;

import com.ureport.ureportkeep.core.definition.Order;
import com.ureport.ureportkeep.core.definition.value.AggregateType;
import com.ureport.ureportkeep.core.parser.json.JsonModel;
import com.ureport.ureportkeep.core.parser.json.cell.model.value.filter.CellFilterModel;

import java.util.List;

/**
 * @Author: summer
 * @Date: 2022/11/27 16:31
 * @Description: 单元格值model
 **/
public class ValueModel implements JsonModel {
    private static final long serialVersionUID = 1L;

    /**
     * SIMPLE、EXPRESS单元格值
     */
    private String value;

    private String datasetName;

    private String property;

    private AggregateType aggregate;

    private Order order = Order.none;

    /**
     * 过滤条件
     */
    private List<CellFilterModel> filters;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public AggregateType getAggregate() {
        return aggregate;
    }

    public void setAggregate(AggregateType aggregate) {
        this.aggregate = aggregate;
    }

    public List<CellFilterModel> getFilters() {
        return filters;
    }

    public void setFilters(List<CellFilterModel> filters) {
        this.filters = filters;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
