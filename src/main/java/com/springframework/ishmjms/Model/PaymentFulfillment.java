package com.springframework.ishmjms.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentFulfillment implements Serializable {

    static final long serialVersionUID = -6616870168772557106L;

    public UUID uuid;
    public String message;

}
