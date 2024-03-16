package hu.ponte.service;

import hu.ponte.config.UserRole;
import hu.ponte.domain.CustomUser;
import hu.ponte.domain.ProfileData;
import hu.ponte.dto.UserCreateCommand;
import hu.ponte.dto.UserPasswordResetCommand;
import hu.ponte.dto.UserSaveInfo;
import hu.ponte.exception.PasswordsDontMatchException;
import hu.ponte.exception.UserWithCodeNotFoundException;
import hu.ponte.exception.UserWithEmailNotFoundException;
import hu.ponte.exception.UserWithIdNotFoundException;
import hu.ponte.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private EmailService emailService;

    private PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final ProfileDataService profileDataService;


    public CustomUser findUserById(Integer userId) throws UserWithIdNotFoundException {
        Optional<CustomUser> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserWithIdNotFoundException(userId);
        }
        return userOptional.get();
    }

    public Optional<CustomUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserSaveInfo update(UserPasswordResetCommand command) {
        User userInSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser toUpdate = findByEmail(userInSession.getUsername()).orElseThrow();
        modelMapper.map(command, toUpdate);
        return modelMapper.map(toUpdate, UserSaveInfo.class);
    }

    public String registerUser(UserCreateCommand command) {
        if (userRepository.findByEmail(command.getEmail()).isPresent()) {
            return "This email is already in use";
        }
        if (!command.getPassword1().equals(command.getPassword2())) {
            return "Passwords dont match";
        }

        CustomUser toRegister = modelMapper.map(command, CustomUser.class);
        toRegister.setPassword(passwordEncoder.encode(command.getPassword1()));

        toRegister.setEnabled(false);
        toRegister.setActivation(UUID.randomUUID().toString());
        emailService.sendActivation(toRegister.getEmail(), toRegister.getActivation());
        userRepository.save(toRegister);

        return "An activation email has been sent to: " + toRegister.getEmail();
    }

    public String userActivation(String code) {
        Optional<CustomUser> user = userRepository.findByActivation(code);
        if (user.isEmpty()) {
            return "No result";
        }
        CustomUser userToActivate = user.get();
        userToActivate.setRoles(List.of(UserRole.ROLE_USER));
        userToActivate.setEnabled(true);
        userToActivate.setActivation("");
        createProfileData(userToActivate);
        userRepository.save(userToActivate);
        return "Activation successful";
    }

    private void createProfileData(CustomUser userToActivate) {
        ProfileData profileData = new ProfileData();
        profileData.setCustomUser(userToActivate);
        userToActivate.setProfileData(profileData);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomUser user = findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        String[] roles = user.getRoles().stream()
                .map(Enum::toString)
                .toArray(String[]::new);
        profileDataService.findProfileDataById(user.getProfileData().getId()).setLastLogin(java.time.LocalDate.now());
        return User
                .withUsername(user.getEmail())
                .authorities(AuthorityUtils.createAuthorityList(roles))
                .password(user.getPassword())
                .build();
    }

    public List<UserSaveInfo> listCustomUsersEmailAddresses() {
        return userRepository.findAll().stream()
                .map(customUser -> modelMapper.map(customUser, UserSaveInfo.class))
                .collect(Collectors.toList());
    }

    public String sendForgottenEmail(String email) {
        CustomUser customUser = findByEmail(email).orElseThrow(() -> new UserWithEmailNotFoundException(email));
        customUser.setActivation(UUID.randomUUID().toString());
        emailService.sendPasswordResetLink(email, customUser.getActivation());
        return "A password reset link has been sent to: " + customUser.getEmail();
    }

    public String resetPassword(String resetCode, UserPasswordResetCommand command) {
        CustomUser customUser = userRepository.findByActivation(resetCode)
                .orElseThrow(() -> new UserWithCodeNotFoundException(resetCode));
        if (!command.getPassword1().equals(command.getPassword2())) {
            throw new PasswordsDontMatchException();
        }
        customUser.setPassword(passwordEncoder.encode(command.getPassword1()));
        customUser.setActivation("");
        return "Password has been changed";
    }
}
