package com.yahoo.quotes.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.yahoo.quotes.domain.Quote} entity.
 */
public class QuoteDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String symbol;

    @NotNull
    private LocalDate date;

    private BigDecimal open;

    private BigDecimal high;

    private BigDecimal low;

    @NotNull
    private BigDecimal close;

    @NotNull
    private BigDecimal adjclose;

    private Long volume;


    private Long stockId;

    private String stockName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigDecimal getAdjclose() {
        return adjclose;
    }

    public void setAdjclose(BigDecimal adjclose) {
        this.adjclose = adjclose;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuoteDTO)) {
            return false;
        }

        return id != null && id.equals(((QuoteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuoteDTO{" +
            "id=" + getId() +
            ", symbol='" + getSymbol() + "'" +
            ", date='" + getDate() + "'" +
            ", open=" + getOpen() +
            ", high=" + getHigh() +
            ", low=" + getLow() +
            ", close=" + getClose() +
            ", adjclose=" + getAdjclose() +
            ", volume=" + getVolume() +
            ", stockId=" + getStockId() +
            ", stockName='" + getStockName() + "'" +
            "}";
    }
}
