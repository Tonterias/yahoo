package com.yahoo.quotes.web.rest;

import com.yahoo.quotes.YahooApp;
import com.yahoo.quotes.domain.Quote;
import com.yahoo.quotes.repository.QuoteRepository;
import com.yahoo.quotes.service.QuoteService;
import com.yahoo.quotes.service.dto.QuoteDTO;
import com.yahoo.quotes.service.mapper.QuoteMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link QuoteResource} REST controller.
 */
@SpringBootTest(classes = YahooApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class QuoteResourceIT {

    private static final String DEFAULT_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_SYMBOL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_OPEN = new BigDecimal(1);
    private static final BigDecimal UPDATED_OPEN = new BigDecimal(2);

    private static final BigDecimal DEFAULT_HIGH = new BigDecimal(1);
    private static final BigDecimal UPDATED_HIGH = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LOW = new BigDecimal(1);
    private static final BigDecimal UPDATED_LOW = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CLOSE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CLOSE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ADJCLOSE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ADJCLOSE = new BigDecimal(2);

    private static final Long DEFAULT_VOLUME = 1L;
    private static final Long UPDATED_VOLUME = 2L;

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private QuoteMapper quoteMapper;

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuoteMockMvc;

    private Quote quote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quote createEntity(EntityManager em) {
        Quote quote = new Quote()
            .symbol(DEFAULT_SYMBOL)
            .date(DEFAULT_DATE)
            .open(DEFAULT_OPEN)
            .high(DEFAULT_HIGH)
            .low(DEFAULT_LOW)
            .close(DEFAULT_CLOSE)
            .adjclose(DEFAULT_ADJCLOSE)
            .volume(DEFAULT_VOLUME);
        return quote;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quote createUpdatedEntity(EntityManager em) {
        Quote quote = new Quote()
            .symbol(UPDATED_SYMBOL)
            .date(UPDATED_DATE)
            .open(UPDATED_OPEN)
            .high(UPDATED_HIGH)
            .low(UPDATED_LOW)
            .close(UPDATED_CLOSE)
            .adjclose(UPDATED_ADJCLOSE)
            .volume(UPDATED_VOLUME);
        return quote;
    }

    @BeforeEach
    public void initTest() {
        quote = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuote() throws Exception {
        int databaseSizeBeforeCreate = quoteRepository.findAll().size();
        // Create the Quote
        QuoteDTO quoteDTO = quoteMapper.toDto(quote);
        restQuoteMockMvc.perform(post("/api/quotes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
            .andExpect(status().isCreated());

        // Validate the Quote in the database
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeCreate + 1);
        Quote testQuote = quoteList.get(quoteList.size() - 1);
        assertThat(testQuote.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testQuote.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testQuote.getOpen()).isEqualTo(DEFAULT_OPEN);
        assertThat(testQuote.getHigh()).isEqualTo(DEFAULT_HIGH);
        assertThat(testQuote.getLow()).isEqualTo(DEFAULT_LOW);
        assertThat(testQuote.getClose()).isEqualTo(DEFAULT_CLOSE);
        assertThat(testQuote.getAdjclose()).isEqualTo(DEFAULT_ADJCLOSE);
        assertThat(testQuote.getVolume()).isEqualTo(DEFAULT_VOLUME);
    }

    @Test
    @Transactional
    public void createQuoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quoteRepository.findAll().size();

        // Create the Quote with an existing ID
        quote.setId(1L);
        QuoteDTO quoteDTO = quoteMapper.toDto(quote);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuoteMockMvc.perform(post("/api/quotes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Quote in the database
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSymbolIsRequired() throws Exception {
        int databaseSizeBeforeTest = quoteRepository.findAll().size();
        // set the field null
        quote.setSymbol(null);

        // Create the Quote, which fails.
        QuoteDTO quoteDTO = quoteMapper.toDto(quote);


        restQuoteMockMvc.perform(post("/api/quotes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
            .andExpect(status().isBadRequest());

        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = quoteRepository.findAll().size();
        // set the field null
        quote.setDate(null);

        // Create the Quote, which fails.
        QuoteDTO quoteDTO = quoteMapper.toDto(quote);


        restQuoteMockMvc.perform(post("/api/quotes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
            .andExpect(status().isBadRequest());

        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCloseIsRequired() throws Exception {
        int databaseSizeBeforeTest = quoteRepository.findAll().size();
        // set the field null
        quote.setClose(null);

        // Create the Quote, which fails.
        QuoteDTO quoteDTO = quoteMapper.toDto(quote);


        restQuoteMockMvc.perform(post("/api/quotes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
            .andExpect(status().isBadRequest());

        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdjcloseIsRequired() throws Exception {
        int databaseSizeBeforeTest = quoteRepository.findAll().size();
        // set the field null
        quote.setAdjclose(null);

        // Create the Quote, which fails.
        QuoteDTO quoteDTO = quoteMapper.toDto(quote);


        restQuoteMockMvc.perform(post("/api/quotes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
            .andExpect(status().isBadRequest());

        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuotes() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get all the quoteList
        restQuoteMockMvc.perform(get("/api/quotes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quote.getId().intValue())))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].open").value(hasItem(DEFAULT_OPEN.intValue())))
            .andExpect(jsonPath("$.[*].high").value(hasItem(DEFAULT_HIGH.intValue())))
            .andExpect(jsonPath("$.[*].low").value(hasItem(DEFAULT_LOW.intValue())))
            .andExpect(jsonPath("$.[*].close").value(hasItem(DEFAULT_CLOSE.intValue())))
            .andExpect(jsonPath("$.[*].adjclose").value(hasItem(DEFAULT_ADJCLOSE.intValue())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.intValue())));
    }
    
    @Test
    @Transactional
    public void getQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get the quote
        restQuoteMockMvc.perform(get("/api/quotes/{id}", quote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quote.getId().intValue()))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.open").value(DEFAULT_OPEN.intValue()))
            .andExpect(jsonPath("$.high").value(DEFAULT_HIGH.intValue()))
            .andExpect(jsonPath("$.low").value(DEFAULT_LOW.intValue()))
            .andExpect(jsonPath("$.close").value(DEFAULT_CLOSE.intValue()))
            .andExpect(jsonPath("$.adjclose").value(DEFAULT_ADJCLOSE.intValue()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingQuote() throws Exception {
        // Get the quote
        restQuoteMockMvc.perform(get("/api/quotes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        int databaseSizeBeforeUpdate = quoteRepository.findAll().size();

        // Update the quote
        Quote updatedQuote = quoteRepository.findById(quote.getId()).get();
        // Disconnect from session so that the updates on updatedQuote are not directly saved in db
        em.detach(updatedQuote);
        updatedQuote
            .symbol(UPDATED_SYMBOL)
            .date(UPDATED_DATE)
            .open(UPDATED_OPEN)
            .high(UPDATED_HIGH)
            .low(UPDATED_LOW)
            .close(UPDATED_CLOSE)
            .adjclose(UPDATED_ADJCLOSE)
            .volume(UPDATED_VOLUME);
        QuoteDTO quoteDTO = quoteMapper.toDto(updatedQuote);

        restQuoteMockMvc.perform(put("/api/quotes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
            .andExpect(status().isOk());

        // Validate the Quote in the database
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeUpdate);
        Quote testQuote = quoteList.get(quoteList.size() - 1);
        assertThat(testQuote.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testQuote.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testQuote.getOpen()).isEqualTo(UPDATED_OPEN);
        assertThat(testQuote.getHigh()).isEqualTo(UPDATED_HIGH);
        assertThat(testQuote.getLow()).isEqualTo(UPDATED_LOW);
        assertThat(testQuote.getClose()).isEqualTo(UPDATED_CLOSE);
        assertThat(testQuote.getAdjclose()).isEqualTo(UPDATED_ADJCLOSE);
        assertThat(testQuote.getVolume()).isEqualTo(UPDATED_VOLUME);
    }

    @Test
    @Transactional
    public void updateNonExistingQuote() throws Exception {
        int databaseSizeBeforeUpdate = quoteRepository.findAll().size();

        // Create the Quote
        QuoteDTO quoteDTO = quoteMapper.toDto(quote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuoteMockMvc.perform(put("/api/quotes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Quote in the database
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        int databaseSizeBeforeDelete = quoteRepository.findAll().size();

        // Delete the quote
        restQuoteMockMvc.perform(delete("/api/quotes/{id}", quote.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
