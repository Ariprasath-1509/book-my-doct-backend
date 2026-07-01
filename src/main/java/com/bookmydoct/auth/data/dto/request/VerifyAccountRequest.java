package com.bookmydoct.auth.data.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyAccountRequest {

    private String email;

    private String emailOtp;

    private String mobileNumber;

    private String mobileOtp;

}
