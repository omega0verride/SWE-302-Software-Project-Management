package com.redscooter.API.order;

public enum OrderStatus {
    NEW("NEW", "https://img.icons8.com/carbon-copy/100/000000/checked-checkbox.png"),
    CONFIRMED("KONFIRMUAR", "https://img.icons8.com/color/96/null/verified-account--v1.png"),
    OUT_FOR_DELIVERY("NË DËRGIM", "https://img.icons8.com/clouds/100/null/in-transit.png"),
    CANCELED("REFUZUAR", "https://img.icons8.com/external-creatype-filed-outline-colourcreatype/100/null/external-package-shipping-and-logistic-creatype-filed-outline-colourcreatype-4.png"), // TODO:? cancellation reason
    CLOSED("MBYLLUR", "https://img.icons8.com/external-flat-berkahicon/64/null/external-Delivered-delivery-flat-berkahicon.png");
    private String name;
    private String iconUrl;

    OrderStatus(String name, String iconUrl) {
        this.name = name;
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}
