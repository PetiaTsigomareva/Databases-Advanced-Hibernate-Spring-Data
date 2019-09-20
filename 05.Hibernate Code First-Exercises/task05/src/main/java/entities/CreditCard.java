package entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.Month;
import java.time.Year;

@Entity(name = "credit_card")
public class CreditCard extends BillingDetail {

    private String cardType;
    private int expirationMonth;
    private int expirationYear;

    public CreditCard() {
    }

    public CreditCard(int number, User user, String cardType) {
        super(number, user);
        this.cardType = cardType;
        this.expirationMonth = 10;
        this.expirationYear = 2019;
    }

    @Column(name = "card_type")
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
   @Column(name = "expiration_month")
    public int getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }
    @Column(name = "expiration_year")
    public int getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }
}
