package hu.ponte.service;
import hu.ponte.config.UserRole;
import hu.ponte.domain.CustomUser;
import hu.ponte.dto.AddressUpdateCommand;
import hu.ponte.exception.AddressNotFoundException;
import hu.ponte.exception.ProfileDataNotFoundException;
import org.modelmapper.ModelMapper;
import hu.ponte.domain.Address;
import hu.ponte.dto.AddressCreateCommand;
import hu.ponte.dto.AddressInfo;
import hu.ponte.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;
    private final ProfileDataService profileDataService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public AddressInfo saveAddress(AddressCreateCommand command, Integer profileDataId) {
        CustomUser currentCustomUser = getCurrentCustomUser(profileDataId);
        validateUserPermissions(currentCustomUser, profileDataId);
        Address toSaveAddress = modelMapper.map(command, Address.class);
        toSaveAddress.setProfileData(profileDataService.findProfileDataById(profileDataId));
        Address savedAddress = addressRepository.save(toSaveAddress);
        return modelMapper.map(savedAddress, AddressInfo.class);
    }

    public List<AddressInfo> listAddresses(Integer profileDataId) {
        CustomUser currentCustomUser = getCurrentCustomUser(profileDataId);
        validateUserPermissions(currentCustomUser, profileDataId);
        List<AddressInfo> currentAddressesForAdmin = addressRepository.findAll(Pageable.ofSize(profileDataId)).stream()
                .map(address -> modelMapper.map(address, AddressInfo.class))
                .collect(Collectors.toList());
        List<AddressInfo> currentAddressesForUser = new ArrayList<>();
        for (AddressInfo addressInfo : currentAddressesForAdmin) {
            if (addressInfo.isDeleted()) {
                currentAddressesForUser.add(addressInfo);
            }
        }
        if (currentCustomUser.getRoles().contains(UserRole.ROLE_ADMIN)) {
            return currentAddressesForAdmin;
        } else {
            return currentAddressesForUser;
        }
    }

    public Address findAddressById(Integer addressId) throws AddressNotFoundException {
        Optional<Address> addressOptional = addressRepository.findById(addressId);
        if (addressOptional.isEmpty()) {
            throw new AddressNotFoundException(addressId);
        }
        CustomUser currentCustomUser = getCurrentCustomUser(addressOptional.get().getProfileData().getId());
        if (currentCustomUser.getRoles().contains(UserRole.ROLE_USER) && currentCustomUser.getRoles().size() == 1 && !addressOptional.get().isDeleted()) {
            throw new AddressNotFoundException(addressId);
        }
        return addressOptional.get();
    }

    public AddressInfo update(Integer addressId, AddressUpdateCommand command) {
        Address toUpdateAddress = findAddressById(addressId);
        CustomUser currentCustomUser = getCurrentCustomUser(toUpdateAddress.getProfileData().getId());
        validateUserPermissions(currentCustomUser, toUpdateAddress.getProfileData().getId());
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(command, toUpdateAddress);
        return modelMapper.map(toUpdateAddress, AddressInfo.class);
    }

    public AddressInfo logicalDelete(Integer addressId) {
        Address toLogicalDelete = findAddressById(addressId);
        CustomUser currentCustomUser = getCurrentCustomUser(toLogicalDelete.getProfileData().getId());
        validateUserPermissions(currentCustomUser, toLogicalDelete.getProfileData().getId());
        toLogicalDelete.setDeleted(true);
        return modelMapper.map(toLogicalDelete, AddressInfo.class);
    }

    private CustomUser getCurrentCustomUser(Integer profileDataId) {
        User userInSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByEmail(userInSession.getUsername())
                .orElseThrow(() -> new ProfileDataNotFoundException(profileDataId));
    }

    private void validateUserPermissions(CustomUser currentCustomUser, Integer profileDataId) {
        if (!(currentCustomUser.getRoles().contains(UserRole.ROLE_ADMIN) ||
                currentCustomUser.getProfileData().getId().equals(profileDataId))) {
            throw new ProfileDataNotFoundException(profileDataId);
        }
    }
}
