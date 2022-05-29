package io;

import org.supercsv.cellprocessor.*;
import org.supercsv.cellprocessor.ift.CellProcessor;
//import org.supercsv.cellprocessor.time.ParseZonedDateTime;

import java.util.stream.Stream;

public enum Fields
{
   TRANSACTION_ID("transaction_id", new Optional()),
    TENANT_ID("tenant_id", new Optional()),
    TRANSACTION_CREATED_AT("transaction_created_at", new Optional()),
    TRANSACTION_UPDATED_AT("transaction_updated_at", new Optional()),
    TRANSACTION_POSTED_AT("transaction_posted_at", new Optional()),
    APPLICATION_USER_ID("application_user_id", new Optional()),
    APPLICATION_USER_NAME("application_user_name", new Optional()),
    TENANT_ROOM_ID("tenant_room_id", new Optional()),
    TENANT_ROOM_NAME("tenant_room_name", new Optional()),
    TENANT_RESERVATION_NAME("tenant_reservation_name", new Optional()),
    TRANSACTION_DESCRIPTION("transaction_description", new Optional()),
    TRANSACTION_NOTES("transaction_notes", new Optional()),
    QUANTITY("quantity", new Optional(new ParseInt())),
    CURRENCY_TYPE("currency_type", new Optional()),
    TRANSACTION_TYPE("transaction_type", new Optional()),
    TRANSACTION_STATUS("transaction_status", new Optional()),
    TENANT_RESERVATION_ID("tenant_reservation_id", new Optional()),
    GLOBAL_GUEST_ID("global_guest_id", new Optional()),
    TENANT_RESERVATION_SOURCE_TYPE("tenant_reservation_source_type", new Optional()),
    TRANSACTION_STATE("transaction_state", new Optional());

    String name;
    CellProcessor cellProcessor;

    Fields(String name, CellProcessor cellProcessor) {
        this.name = name;
        this.cellProcessor = cellProcessor;
    }

    public String getName() {
        return name;
    }

    public CellProcessor getCellProcessor() {
        return cellProcessor;
    }

    public static String[] getFieldMapping() {
        return Stream.of(Fields.values())
                .map(Fields::getName)
                .toArray(String[]::new);
    }

    public static CellProcessor[] getProcessors() {
        return Stream.of(Fields.values())
                .map(Fields::getCellProcessor)
                .toArray(CellProcessor[]::new);
    }
}
