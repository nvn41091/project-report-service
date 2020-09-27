package com.nvn41091.web.rest.errors;

import com.nvn41091.utils.DataUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * author: tamdx
 */
public class FileValidator implements ConstraintValidator<ValidFile, Object> {
    protected ValidFile validFile;

    @Override
    public void initialize(ValidFile constraintAnnotation) {
        this.validFile = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object lstFile, ConstraintValidatorContext context) {
        //Truong hop du lieu null => Validator hop le
        if(DataUtil.isNullOrEmpty(lstFile)) {
            return true;
        }
        if (lstFile instanceof List) {
            List<Object> lstObj = (List<Object>) lstFile;
            if (DataUtil.isNullOrEmpty(lstObj) || !(lstObj.get(0) instanceof MultipartFile)) {
                return true;
            }
            for (Object obj : lstObj) {
                if (!isValidFile(obj, context)) {
                    return false;
                }
            }
        } else if (lstFile instanceof MultipartFile) {
            return isValidFile(lstFile, context);
        }

        return true;
    }

    private boolean isValidFile(Object obj, ConstraintValidatorContext context) {
        MultipartFile multipartFile = (MultipartFile) obj;
        String contentType = multipartFile.getContentType();
        if (!isSupportedContentType(contentType)) {
            buildContext(context);
            return false;
        }
        return true;
    }

    private void buildContext(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                validFile.message())
                .addConstraintViolation();
    }

    public boolean isSupportedContentType(String contentType) {
        Set<String> contents = Arrays.stream(this.validFile.filesAllow()).collect(Collectors.toSet());
        return contents.contains(contentType);
    }
}

