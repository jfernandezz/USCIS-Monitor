package com.ferzal.monitoring.uscis.dto;

import com.google.gson.annotations.SerializedName;

public class CaseStatusResponseBody {
    @SerializedName("receiptNumber")
    private String receiptNumber;

    @SerializedName("isValid")
    private boolean isValid;

    @SerializedName("detailsEng")
    private Details detailsEng;

    @SerializedName("detailsEs")
    private Details detailsEs;

}