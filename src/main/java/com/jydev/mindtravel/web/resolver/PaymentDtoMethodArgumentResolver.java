package com.jydev.mindtravel.web.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jydev.mindtravel.common.exception.PaymentFailException;
import com.jydev.mindtravel.domain.payment.dto.PaymentDto;
import com.jydev.mindtravel.domain.payment.utils.PaymentDtoJsonParser;
import com.jydev.mindtravel.web.annotation.Payment;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class PaymentDtoMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private final ObjectMapper mapper;
    private final PaymentDtoJsonParser jsonParser = new PaymentDtoJsonParser();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(Payment.class);
        boolean hasPaymentDto = PaymentDto.class.isAssignableFrom(parameter.getParameterType());
        return hasAnnotation && hasPaymentDto;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        try {
            Map json = mapper.readValue(request.getInputStream(), Map.class);
            Map paymentDtoJson = (Map) json.get("payment");
            return jsonParser.parse(paymentDtoJson);
        } catch (Exception e) {
            throw new PaymentFailException("결제 데이터 파싱 오류");
        }
    }
}
