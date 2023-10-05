package com.devcourse.kurlymurly.application.member;

import com.devcourse.kurlymurly.domain.user.shipping.Shipping;
import com.devcourse.kurlymurly.web.user.GetAddress;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public GetAddress.Response toGetAddressResponse(Shipping shipping) {
        return new GetAddress.Response(shipping.isDefault(),
                shipping.getAddress().isExpress(),
                shipping.getAddress().getDescribedAddress(),
                shipping.getInfo().getReceiver(),
                shipping.getInfo().getContact()
        );
    }
}
