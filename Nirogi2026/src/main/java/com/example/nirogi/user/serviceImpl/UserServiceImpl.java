package com.example.nirogi.user.serviceImpl;


import com.example.nirogi.user.dto.CreateUserRequestDTO;
import com.example.nirogi.user.dto.UserResponseDTO;
import com.example.nirogi.user.entity.User;
import com.example.nirogi.user.repository.UserRepository;
import com.example.nirogi.user.service.UserService;
import com.example.nirogi.user.utils.PasswordGenerator;
import com.example.nirogi.user.utils.UsernameGenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final UsernameGenerator usernameGenerator;

    private final PasswordGenerator passwordGenerator;

    private final PasswordEncoder encoder;


    @Override
    public UserResponseDTO createUser(
            CreateUserRequestDTO request,
            Long createdBy
    ) {

        Long sequence =
                repository.countByRoleAndFirstName(
                        request.getRole(),
                        request.getFirstName()
                ) + 1;

        String username =
                usernameGenerator.generate(
                        request.getRole(),
                        request.getFirstName(),
                        sequence
                );

        while (repository.existsByUsername(username)) {

            sequence++;

            username =
                    usernameGenerator.generate(
                            request.getRole(),
                            request.getFirstName(),
                            sequence
                    );
        }

        String password =
                encoder.encode(
                        passwordGenerator.generateInitialPassword(username)
                );

        User user = User.builder()
                .username(username)
                .passwordHash(password)
                .role(request.getRole())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .mobile(request.getMobile())
                .email(request.getEmail())
                .designationId(request.getDesignationId())
                .districtId(request.getDistrictId())
                .facilityId(request.getFacilityId())
                .facilityTypeId(request.getFacilityTypeId())
                .createdBy(createdBy)
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();

        repository.save(user);

        return mapToDTO(user);
    }


    @Override
    public void disableUser(Long userId) {

        User user = repository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setIsActive(false);

        repository.save(user);
    }


    @Override
    public List<UserResponseDTO> getUsersByDistrict(
            Long districtId
    ) {

        return repository.findAll()
                .stream()
                .filter(user ->
                        user.getDistrictId().equals(districtId))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    private UserResponseDTO mapToDTO(User user) {

        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .mobile(user.getMobile())
                .email(user.getEmail())
                .designationId(user.getDesignationId())
                .districtId(user.getDistrictId())
                .facilityId(user.getFacilityId())
                .facilityTypeId(user.getFacilityTypeId())
                .isActive(user.getIsActive())
                .build();
    }
}