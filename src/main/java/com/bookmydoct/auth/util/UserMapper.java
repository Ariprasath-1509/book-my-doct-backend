package com.bookmydoct.auth.util;

import com.bookmydoct.auth.data.dto.response.UserProfileResponse;
import com.bookmydoct.auth.data.dto.response.UserSummaryResponse;
import com.bookmydoct.auth.data.entity.User;

import java.util.Base64;

public final class UserMapper {

    private UserMapper() {}

    public static UserProfileResponse toUserProfileResponse(User user) {

        UserProfileResponse response = new UserProfileResponse();

        response.setUuid(user.getUuid());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole().getRoleName());
        response.setStatus(user.getStatus().name());     // AccountStatus enum → String
        response.setCreatedAt(user.getCreatedAt());

        boolean hasImage = user.getProfileImage() != null
                && user.getProfileImage().length > 0;

        response.setHasProfileImage(hasImage);
//        response.setProfileImageUrl(hasImage ? "/api/v1/users/" + user.getUuid() + "/profile-image" : null);

        String base64Image=null;
        if(user.getProfileImage()!=null && user.getProfileImage().length>0) {
            base64Image= Base64.getEncoder().encodeToString(user.getProfileImage());
        }
        response.setProfileImageUrl(base64Image);

        return response;
    }

    public static UserSummaryResponse toUserSummaryResponse(User user) {

        UserSummaryResponse response = new UserSummaryResponse();

        response.setUuid(user.getUuid());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().getRoleName());
        response.setStatus(user.getStatus().name());

        return response;
    }
}