package hu.ponte.service;

import hu.ponte.domain.ProfileData;
import hu.ponte.dto.ProfileDataInfo;
import hu.ponte.dto.ProfileDataUpdateCommand;
import hu.ponte.exception.ProfileDataNotFoundException;
import hu.ponte.repository.ProfileDataRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ProfileDataService {

    private final ProfileDataRepository profileDataRepository;
    private final ModelMapper modelMapper;

    public ProfileData findProfileDataById(Integer profileDataId) throws ProfileDataNotFoundException {
        Optional<ProfileData> profileDataOptional = profileDataRepository.findById(profileDataId);
        if (profileDataOptional.isEmpty()) {
            throw new ProfileDataNotFoundException(profileDataId);
        }
        return profileDataOptional.get();
    }

    public ProfileDataInfo update(Integer id, ProfileDataUpdateCommand command) {
        ProfileData toUpdate = findProfileDataById(id);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(command, toUpdate);
        return modelMapper.map(toUpdate, ProfileDataInfo.class);
    }
}
