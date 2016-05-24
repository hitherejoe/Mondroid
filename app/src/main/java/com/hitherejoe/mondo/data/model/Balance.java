package com.hitherejoe.mondo.data.model;

import com.google.gson.annotations.SerializedName;

public class Balance {
    public long balance;
    public String currency;
    @SerializedName("spent_today")
    public long spentToday;
}
