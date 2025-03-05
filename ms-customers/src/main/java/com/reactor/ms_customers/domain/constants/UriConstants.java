package com.reactor.ms_customers.domain.constants;

public class UriConstants {
    public static final String BASE_URI = "/api/v1";
    public static final String CUSTOMERS_URI = BASE_URI + "/customers";
    public static final String ADD_CUSTOMER = CUSTOMERS_URI;
    public static final String GET_ALL_CUSTOMERS = CUSTOMERS_URI;
    public static final String GET_CUSTOMER_BY_ID = BASE_URI + "/customers/{idCustomer}";
    public static final String UPDATE_CUSTOMER = CUSTOMERS_URI;

    private UriConstants() {
    }
}

