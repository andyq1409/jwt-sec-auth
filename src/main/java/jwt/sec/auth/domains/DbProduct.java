package jwt.sec.auth.domains;

import java.sql.Timestamp;

public class DbProduct {

    private Long product_id;
    private String product_name;
    private String product_description;
    private String category;
    private String product_avail;
    private Double list_price;
    private byte[] product_image;
    private String mimetype;
    private String filename;
    private Timestamp image_last_update;

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProduct_avail() {
        return product_avail;
    }

    public void setProduct_avail(String product_avail) {
        this.product_avail = product_avail;
    }

    public Double getList_price() {
        return list_price;
    }

    public void setList_price(Double list_price) {
        this.list_price = list_price;
    }

    public byte[] getProduct_image() {
        return product_image;
    }

    public void setProduct_image(byte[] product_image) {
        this.product_image = product_image;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Timestamp getImage_last_update() {
        return image_last_update;
    }

    public void setImage_last_update(Timestamp image_last_update) {
        this.image_last_update = image_last_update;
    }
}

