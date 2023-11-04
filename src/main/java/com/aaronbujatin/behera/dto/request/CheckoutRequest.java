package com.aaronbujatin.behera.dto.request;

import com.aaronbujatin.behera.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CheckoutRequest {

    private Long userId;
    private String address;

}
