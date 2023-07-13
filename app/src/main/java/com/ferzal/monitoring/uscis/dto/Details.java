package com.ferzal.monitoring.uscis.dto;

import com.google.gson.annotations.SerializedName;

public class Details {
    @SerializedName("formNum")
    private String formNum;

    @SerializedName("formTitle")
    private String formTitle;

    @SerializedName("actionCodeText")
    private String actionCodeText;

    @SerializedName("actionCodeDesc")
    private String actionCodeDesc;

    @SerializedName("empty")
    private boolean empty;
}
