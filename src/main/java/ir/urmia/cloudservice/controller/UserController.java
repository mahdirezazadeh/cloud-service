package ir.urmia.cloudservice.controller;

import ir.urmia.cloudservice.domain.User;
import ir.urmia.cloudservice.mapper.UserDTOMapper;
import ir.urmia.cloudservice.mapper.UserSignUpDTOMapper;
import ir.urmia.cloudservice.service.UserService;
import ir.urmia.cloudservice.service.dto.UserDTO;
import ir.urmia.cloudservice.service.dto.UserSignUpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserSignUpDTOMapper signUpDTOMapper;
    private final UserDTOMapper userDTOMapper;
    private final UserService userService;


    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserSignUpDTO userSignUpDTO) {
        User user = signUpDTOMapper.convertDTOToEntity(userSignUpDTO);
        user = userService.singUp(user);
        UserDTO userDTO = userDTOMapper.convertEntityToDTO(user);
        return ResponseEntity.ok(userDTO);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @GetMapping("login")
    public HttpStatus login() {
//        SecurityContextHolder.getContext().getAuthentication()
        return HttpStatus.OK;
    }

}
