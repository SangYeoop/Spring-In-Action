package com.taco.taco.tacos.User;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RegistrationFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return RegistrationForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegistrationForm registrationForm = (RegistrationForm)o;

        if(!registrationForm.getPassword().equals(registrationForm.getConfirm())){
            errors.rejectValue("password", "invalid.password", new Object[]{registrationForm.getPassword()}, "패스워드가 일치하지 않습니다.");
        }
    }
}
