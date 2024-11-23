package com.goodcare.server.domain.guardian.service;

import com.goodcare.server.domain.guardian.dao.Guardian;
import com.goodcare.server.domain.guardian.dto.GuardianDTO;
import com.goodcare.server.domain.guardian.repository.GuardianRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GuardianInfoService {
    private final GuardianRepository guardianRepository;

    private String getUUID(){
        UUID uuid = new AlternativeJdkIdGenerator().generateId();
        return uuid.toString();
    }

    public Boolean saveGuardian(GuardianDTO guardianDTO){
        Guardian guardian = new Guardian();
        guardian.setCode(getUUID().substring(0, 6).toUpperCase());
        guardian.setPatientCode(guardianDTO.getPatientCode());
        guardian.setName(guardianDTO.getName());
        guardian.setDateOfBirth(guardianDTO.getDateOfBirth());
        guardian.setGender(guardianDTO.getGender());
        guardian.setContactNumber(guardianDTO.getContactNumber());

        guardianRepository.save(guardian);

        return true;
    }
}
