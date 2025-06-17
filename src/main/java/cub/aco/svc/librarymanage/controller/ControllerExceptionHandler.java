package cub.aco.svc.librarymanage.controller;

import cub.aco.svc.librarymanage.dto.ActionDetail;
import cub.aco.svc.librarymanage.dto.ActionResponse;
import cub.aco.svc.librarymanage.enums.ResponseCode;
import cub.aco.svc.librarymanage.exception.ErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;


@Slf4j
@ControllerAdvice(basePackages  =  {"cub.aco.svc.librarymanage.controller"})
public class ControllerExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ActionResponse handleValidationError(MethodArgumentNotValidException e) {

        final String errMsgs = e.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> new HashMap.SimpleEntry<>(err.getField(),err.getDefaultMessage()))
                .collect(Collectors.groupingBy(AbstractMap.SimpleEntry::getKey,
                        mapping(AbstractMap.SimpleEntry::getValue,
                                Collectors.joining(","))))
                .entrySet().stream()
                .map(entry -> entry.getKey() + " : " + entry.getValue())
                .collect(Collectors.joining(","));
        log.warn("Method Argument Validation fail : {}, {}", e.getBindingResult().getTarget(), errMsgs);
        return ActionResponse.builder().mwHeader(ActionDetail.builder()
                .returnCode(ResponseCode.NOT_VALID.getCode())
                .returnDesc(String.format(ResponseCode.NOT_VALID.getDescription(), errMsgs))
                .build()).build();
    }


    @ExceptionHandler(ErrorException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ActionResponse handleValidationError(ErrorException e) {
        log.error("class : {}, method : {}, message : {}",
                e.getStackTrace()[0].getClassName(),
                e.getStackTrace()[0].getMethodName(),
                e.getMessage());

        return ActionResponse.builder().mwHeader(ActionDetail.builder()
                .returnCode(ResponseCode.ERROR.getCode())
                .returnDesc(String.format(ResponseCode.ERROR.getDescription(), e.getMessage()))
                .build()).build();
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ActionResponse handleOtherError(Exception e) {
        log.error("", e);

        return ActionResponse.builder().mwHeader(ActionDetail.builder()
                .returnCode(ResponseCode.SYSTEM_ERROR.getCode())
                .returnDesc(String.format(ResponseCode.SYSTEM_ERROR.getDescription()))
                .build()).build();
    }

}
