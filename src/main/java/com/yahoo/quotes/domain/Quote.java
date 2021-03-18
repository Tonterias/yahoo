package com.yahoo.quotes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Quote.
 */
@Entity
@Table(name = "quote")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Quote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "symbol", nullable = false)
    private String symbol;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "open", precision = 21, scale = 2)
    private BigDecimal open;

    @Column(name = "high", precision = 21, scale = 2)
    private BigDecimal high;

    @Column(name = "low", precision = 21, scale = 2)
    private BigDecimal low;

    @NotNull
    @Column(name = "close", precision = 21, scale = 2, nullable = false)
    private BigDecimal close;

    @NotNull
    @Column(name = "adjclose", precision = 21, scale = 2, nullable = false)
    private BigDecimal adjclose;

    @Column(name = "volume")
    private Long volume;

    @ManyToOne
    @JsonIgnoreProperties(value = "quotes", allowSetters = true)
    private Stock stock;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public Quote symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDate getDate() {
        return date;
    }

    public Quote date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public Quote open(BigDecimal open) {
        this.open = open;
        return this;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public Quote high(BigDecimal high) {
        this.high = high;
        return this;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public Quote low(BigDecimal low) {
        this.low = low;
        return this;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public Quote close(BigDecimal close) {
        this.close = close;
        return this;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigDecimal getAdjclose() {
        return adjclose;
    }

    public Quote adjclose(BigDecimal adjclose) {
        this.adjclose = adjclose;
        return this;
    }

    public void setAdjclose(BigDecimal adjclose) {
        this.adjclose = adjclose;
    }

    public Long getVolume() {
        return volume;
    }

    public Quote volume(Long volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Stock getStock() {
        return stock;
    }

    public Quote stock(Stock stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quote)) {
            return false;
        }
        return id != null && id.equals(((Quote) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quote{" +
            "id=" + getId() +
            ", symbol='" + getSymbol() + "'" +
            ", date='" + getDate() + "'" +
            ", open=" + getOpen() +
            ", high=" + getHigh() +
            ", low=" + getLow() +
            ", close=" + getClose() +
            ", adjclose=" + getAdjclose() +
            ", volume=" + getVolume() +
            "}";
    }
}
