package jwt.sec.auth.domains;

import java.sql.Timestamp;

public class DbOrder {
    private Long order_id;
    private Long customer_id;
    private Double order_total;
    private Timestamp order_timestamp;
    private String user_name;
    private String tags;
    private String customer;

    public DbOrder() {
    }

    public DbOrder(Long order_id, Long customer_id, Double order_total, Timestamp order_timestamp, String user_name) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.order_total = order_total;
        this.order_timestamp = order_timestamp;
        this.user_name = user_name;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Double getOrder_total() {
        return order_total;
    }

    public void setOrder_total(Double order_total) {
        this.order_total = order_total;
    }

    public Timestamp getOrder_timestamp() {
        return order_timestamp;
    }

    public void setOrder_timestamp(Timestamp order_timestamp) {
        this.order_timestamp = order_timestamp;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCustomer() { return customer; }

    public void setCustomer(String customer) { this.customer = customer; }

    @Override
    public String toString() {
        return "DbOrder{" +
                "order_id=" + order_id +
                ", customer_id=" + customer_id +
                ", order_total=" + order_total +
                ", order_timestamp=" + order_timestamp +
                ", user_name='" + user_name + '\'' +
                ", tags='" + tags + '\'' +
                ", customer='" + customer + '\'' +
                '}';
    }
}
