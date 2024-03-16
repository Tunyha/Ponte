package hu.ponte.unit;

import hu.ponte.config.UserRole;
import hu.ponte.domain.Address;
import hu.ponte.domain.CustomUser;
import hu.ponte.domain.ProfileData;
import hu.ponte.dto.AddressCreateCommand;
import hu.ponte.dto.AddressInfo;
import hu.ponte.dto.AddressUpdateCommand;
import hu.ponte.exception.AddressNotFoundException;
import hu.ponte.exception.ProfileDataNotFoundException;
import hu.ponte.repository.AddressRepository;
import hu.ponte.service.AddressService;
import hu.ponte.service.ProfileDataService;
import hu.ponte.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ProfileDataService profileDataService;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAddress() {
        // Given
        AddressCreateCommand command = new AddressCreateCommand();
        Address address = new Address();
        AddressInfo addressInfo = new AddressInfo();
        when(modelMapper.map(command, Address.class)).thenReturn(address);
        when(profileDataService.findProfileDataById(anyInt())).thenReturn(null);
        when(addressRepository.save(address)).thenReturn(address);
        when(modelMapper.map(address, AddressInfo.class)).thenReturn(addressInfo);

        AddressInfo savedAddress = addressService.saveAddress(command, 1);

        assertNotNull(savedAddress);
        assertEquals(addressInfo, savedAddress);
    }

    @Test
    void testFindAddressById_AddressNotFound_ThrowsException() {

        when(addressRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> addressService.findAddressById(1));
    }

    @Test
    void testUpdate_AddressNotFound_ThrowsException() {

        when(addressRepository.findById(1)).thenReturn(Optional.empty());
        AddressUpdateCommand command = new AddressUpdateCommand();

        assertThrows(AddressNotFoundException.class, () -> addressService.update(1, command));
    }


    @Test
    void testLogicalDelete_AddressNotFound_ThrowsException() {

        when(addressRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> addressService.logicalDelete(1));
    }

}

