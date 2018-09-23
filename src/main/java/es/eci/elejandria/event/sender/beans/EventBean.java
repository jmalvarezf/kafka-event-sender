package es.eci.elejandria.event.sender.beans;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class EventBean {

    public enum EventType {
        INFO, BUY, RETURN
    }

    private enum Channel {
        WEB, STORE, PHONE
    }

    public EventBean() {
        id = UUID.randomUUID().toString();
    }


    private EventType eventType;

    private List<ProductBean> products;

    private Channel channel;

    private String id;

    private CustomerBean customer;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.mmm'Z'")
    private Date timestamp;

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel origin) {
        this.channel = origin;
    }

    public List<ProductBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductBean> products) {
        this.products = products;
    }

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EventBean{");
        sb.append("id='").append(id).append('\'');
        sb.append(", eventType=").append(eventType);
        sb.append(", products=").append(products);
        sb.append(", channel=").append(channel);
        sb.append(", customer=").append(customer);
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }
}
