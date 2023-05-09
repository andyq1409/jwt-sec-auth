package jwt.sec.auth.domains;

public class DbOrderItem {
    private Long order_item_id;
    private Long order_id;
    private Long product_id;
    private Double unit_price;
    private Integer quantity;
    private String product_name;
    private String product_description;
    private Double list_price;
    private Double item_value;

    public Double getItem_value() {
        return item_value;
    }

    public void setItem_value(Double item_value) {
        this.item_value = item_value;
    }

    public Long getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(Long order_item_id) {
        this.order_item_id = order_item_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public Double getList_price() {
        return list_price;
    }

    public void setList_price(Double list_price) {
        this.list_price = list_price;
    }

    @Override
    public String toString() {
        return "DbOrderItem{" +
                "order_item_id=" + order_item_id +
                ", order_id=" + order_id +
                ", product_id=" + product_id +
                ", unit_price=" + unit_price +
                ", quantity=" + quantity +
                ", product_name='" + product_name + '\'' +
                ", product_description='" + product_description + '\'' +
                ", list_price=" + list_price +
                ", item_value=" + item_value +
                '}';
    }
}
