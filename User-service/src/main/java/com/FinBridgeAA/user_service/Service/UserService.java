package com.FinBridgeAA.user_service.Service;

import com.FinBridgeAA.user_service.DTO.UserProfileResponseDto;
import com.FinBridgeAA.user_service.Entity.UserProfile;
import com.FinBridgeAA.user_service.Enums.KycStatus;
import com.FinBridgeAA.user_service.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public void createUserIfNotExists(UUID userId) {
        if (!userRepository.existsById(userId)) {
            UserProfile user = new UserProfile();
            user.setUserId(userId);
            user.setKycStatus(KycStatus.NOT_STARTED);
            userRepository.save(user);
        }
    }

    public UserProfileResponseDto getProfile(UUID userId) {
        UserProfile user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return modelMapper.map(user, UserProfileResponseDto.class);
    }

    public UserProfileResponseDto updateProfile(UUID userId, UserProfileResponseDto dto) {
        UserProfile user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setDob(dto.getDob());

        UserProfile saved = userRepository.save(user);

        return modelMapper.map(saved, UserProfileResponseDto.class);
    }

    public KycStatus getKycStatus(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getKycStatus();
    }
}