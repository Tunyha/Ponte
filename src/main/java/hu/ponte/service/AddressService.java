package hu.ponte.service;
import org.modelmapper.ModelMapper;
import hu.ponte.domain.Address;
import hu.ponte.dto.AddressCreateCommand;
import hu.ponte.dto.AddressInfo;
import hu.ponte.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;
    private final ProfileDataService profileDataService;
    private final ModelMapper modelMapper;

    public AddressInfo saveAddress(AddressCreateCommand command, Integer profileDataId) {
        Address toSaveAddress = modelMapper.map(command, Address.class);
        toSaveAddress.setProfileData(profileDataService.findProfileDataById(profileDataId));
        Address savedAddress = addressRepository.save(toSaveAddress);
        return modelMapper.map(savedAddress, AddressInfo.class);
    }

}
