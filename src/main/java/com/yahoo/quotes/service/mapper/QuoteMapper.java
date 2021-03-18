package com.yahoo.quotes.service.mapper;


import com.yahoo.quotes.domain.*;
import com.yahoo.quotes.service.dto.QuoteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Quote} and its DTO {@link QuoteDTO}.
 */
@Mapper(componentModel = "spring", uses = {StockMapper.class})
public interface QuoteMapper extends EntityMapper<QuoteDTO, Quote> {

    @Mapping(source = "stock.id", target = "stockId")
    @Mapping(source = "stock.name", target = "stockName")
    QuoteDTO toDto(Quote quote);

    @Mapping(source = "stockId", target = "stock")
    Quote toEntity(QuoteDTO quoteDTO);

    default Quote fromId(Long id) {
        if (id == null) {
            return null;
        }
        Quote quote = new Quote();
        quote.setId(id);
        return quote;
    }
}
