package entities;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "sale")
public class Sale {
    private Long id;
    private Info product;
    private Info customer;
    private StoreLocation storeLocation;
    private Date date;

    public Sale() {

    }

    public Sale(Info product, Info customer, StoreLocation storeLocation) {
        this.product = product;
        this.customer = customer;
        this.storeLocation = storeLocation;
        this.setDate(new Date());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @ManyToOne(optional = false,fetch = FetchType.LAZY,cascade= CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")

    public Info getProduct() {
        return product;
    }

    public void setProduct(Info product) {
        this.product = product;
    }

    @ManyToOne(optional = false,fetch = FetchType.LAZY,cascade= CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    public Info getCustomer() {
        return customer;
    }

    public void setCustomer(Info customer) {
        this.customer = customer;
    }

    @ManyToOne(optional = false,fetch = FetchType.LAZY,cascade= CascadeType.ALL)
    @JoinColumn(name = "store_location_id", referencedColumnName = "id")
    public StoreLocation getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(StoreLocation storeLocation) {
        this.storeLocation = storeLocation;
    }

    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
