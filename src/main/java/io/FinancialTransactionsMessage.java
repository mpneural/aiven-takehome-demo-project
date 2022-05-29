package io;

import org.springframework.stereotype.Component;

@Component
public class FinancialTransactionsMessage {
    private String transaction_id;
    private String tenant_id;
    private String transaction_created_at;
    private String transaction_updated_at;
    private String transaction_posted_at;
    private String application_user_id;
    private String application_user_name;
    private String tenant_room_id;
    private String tenant_room_name;
    private String tenant_reservation_name;
    private String transaction_description;
    private String transaction_notes;
    private int quantity;
    private String currency_type;
    private String transaction_status; //1 = posted, 0 = voided, 3 = deleted, 2 = refunded
    private String transaction_type;
    private String tenant_reservation_id;
    private String tenant_reservation_source_type;
    private String global_guest_id;
    private String transaction_state; // completed, pending

    @Override
    public String toString() {
        return "FinancialTransactionsMessage{" +
                "transaction_id='" + transaction_id + '\'' +
                ", tenant_id='" + tenant_id + '\'' +
                ", transaction_created_at='" + transaction_created_at + '\'' +
                ", transaction_updated_at='" + transaction_updated_at + '\'' +
                ", transaction_posted_at='" + transaction_posted_at + '\'' +
                ", application_user_id='" + application_user_id + '\'' +
                ", application_user_name='" + application_user_name + '\'' +
                ", tenant_room_id='" + tenant_room_id + '\'' +
                ", tenant_room_name='" + tenant_room_name + '\'' +
                ", tenant_reservation_name='" + tenant_reservation_name + '\'' +
                ", transaction_description='" + transaction_description + '\'' +
                ", transaction_notes='" + transaction_notes + '\'' +
                ", quantity='" + quantity +
                ", currency_type='" + currency_type + '\'' +
                ", transaction_status='" + transaction_status + '\'' +
                ", transaction_type='" + transaction_type + '\'' +
                ", tenant_reservation_id='" + tenant_reservation_id + '\'' +
                ", global_guest_id='" + global_guest_id + '\'' +
                ", tenant_reservation_source_type='" + tenant_reservation_source_type + '\'' +
                ", transaction_state='" + global_guest_id +
                '}';
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public void setTransaction_created_at(String transaction_created_at) {
        this.transaction_created_at = transaction_created_at;
    }

    public void setTransaction_updated_at(String transaction_updated_at) {
        this.transaction_updated_at = transaction_updated_at;
    }

    public void setTransaction_posted_at(String transaction_posted_at) {
        this.transaction_posted_at = transaction_posted_at;
    }

    public void setApplication_user_id(String application_user_id) {
        this.application_user_id = application_user_id;
    }

    public void setApplication_user_name(String application_user_name) {
        this.application_user_name = application_user_name;
    }

    public void setTenant_room_id(String tenant_room_id) {
        this.tenant_room_id = tenant_room_id;
    }

    public void setTenant_room_name(String tenant_room_name) {
        this.tenant_room_name = tenant_room_name;
    }

    public void setTenant_reservation_name(String tenant_reservation_name) {
        this.tenant_reservation_name = tenant_reservation_name;
    }

    public void setTransaction_description(String transaction_description) {
        this.transaction_description = transaction_description;
    }

    public void setTransaction_notes(String transaction_notes) {
        this.transaction_notes = transaction_notes;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCurrency_type(String currency_type) {
        this.currency_type = currency_type;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public void setTenant_reservation_id(String tenant_reservation_id) {
        this.tenant_reservation_id = tenant_reservation_id;
    }

    public void setTenant_reservation_source_type(String tenant_reservation_source_type) {
        this.tenant_reservation_source_type = tenant_reservation_source_type;
    }

    public void setGlobal_guest_id(String global_guest_id) {
        this.global_guest_id = global_guest_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public String getTenant_id() {
        return tenant_id;
    }

    public String getTransaction_created_at() {
        return transaction_created_at;
    }

    public String getTransaction_updated_at() {
        return transaction_updated_at;
    }

    public String getTransaction_posted_at() {
        return transaction_posted_at;
    }

    public String getApplication_user_id() {
        return application_user_id;
    }

    public String getApplication_user_name() {
        return application_user_name;
    }

    public String getTenant_room_id() {
        return tenant_room_id;
    }

    public String getTenant_room_name() {
        return tenant_room_name;
    }

    public String getTenant_reservation_name() {
        return tenant_reservation_name;
    }

    public String getTransaction_description() {
        return transaction_description;
    }

    public String getTransaction_notes() {
        return transaction_notes;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCurrency_type() {
        return currency_type;
    }

    public String getTransaction_status() {
        return transaction_status;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public String getTenant_reservation_id() {
        return tenant_reservation_id;
    }

    public String getTenant_reservation_source_type() {
        return tenant_reservation_source_type;
    }

    public String getGlobal_guest_id() {
        return global_guest_id;
    }

    public String getTransaction_state() {
        return transaction_state;
    }

    public void setTransaction_state(String transaction_state) {
        this.transaction_state = transaction_state;
    }
}
