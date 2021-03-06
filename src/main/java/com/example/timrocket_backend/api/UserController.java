package com.example.timrocket_backend.api;

import com.example.timrocket_backend.domain.User;
import com.example.timrocket_backend.security.SecurityServiceInterface;
import com.example.timrocket_backend.service.UserService;
import com.example.timrocket_backend.service.dto.CoachDTO;
import com.example.timrocket_backend.service.dto.CreateUserDTO;
import com.example.timrocket_backend.service.dto.UpdateUserDTO;
import com.example.timrocket_backend.service.dto.UserDTO;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;
    private final Logger logger;
    private final SecurityServiceInterface securityService;

    public UserController(UserService userService, SecurityServiceInterface securityService) {
        this.userService = userService;
        this.securityService = securityService;
        this.logger = LoggerFactory.getLogger(UserController.class);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@Valid @RequestBody CreateUserDTO createUserDTO){
        UserDTO userDTO = userService.createUser(createUserDTO);
        return userDTO;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            logger.error(errorMessage, exception);
        });
        return errors;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, params={"email"})
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserByEmail(@RequestParam String email){
        return userService.getByEmail(email);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, params = {"coach"})
    @ResponseStatus(HttpStatus.OK)
    public List<CoachDTO> getAllCoaches(@RequestParam String coach) {
        return userService.getAllCoaches();
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@PathVariable String id){
        logger.info("Get User By Id started");
        UserDTO userDTO;
        try {
            UUID uuid = UUID.fromString(id);
            userDTO = userService.getById(uuid);
        } catch (Exception e) {
            userDTO = null;
        }
        logger.info("Get User By Id finished");
        return userDTO;
    }

    @PreAuthorize("hasAuthority('GET_COACH_INFORMATION')")
    @GetMapping(produces = APPLICATION_JSON_VALUE, params = {"coach"}, path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CoachDTO getCoachById(@PathVariable UUID id, @RequestParam String coach){
        System.out.println("I am here ");
        System.out.println("The id is " + id);
        return userService.getCoachBy(id);
    }

//    public CoachDTO getCoachProfile(@PathVariable UUID id)

    @PutMapping(produces = APPLICATION_JSON_VALUE, path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('UPDATE_PROFILE')")
    public UserDTO updateUser(@PathVariable String id, @RequestBody UpdateUserDTO updateUserDTO, Authentication authentication) {
        return userService.updateUser(id, updateUserDTO, authentication);
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('BECOME_A_COACH')")
    public UserDTO updateToCoach(Authentication authentication) {
        return userService.updateRoleToCoach(authentication);
    }
}

