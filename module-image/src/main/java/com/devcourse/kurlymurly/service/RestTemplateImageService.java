package com.devcourse.kurlymurly.service;

import com.devcourse.kurlymurly.common.CustomInputStreamResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

@Component
class RestTemplateImageService implements ImageService {
    private static final String IMAGE_KEY = "image";

    private final RestTemplate restTemplate;
    private final String imageUrl;

    public RestTemplateImageService(
            RestTemplate restTemplate,
            @Value("${kurly.image.server}") String imageUrl
    ) {
        this.restTemplate = restTemplate;
        this.imageUrl = imageUrl;
    }

    @Override
    public String upload(MultipartFile image) {
        HttpEntity<MultiValueMap<String, Object>> request = generateHttpEntityRequest(image);
        return sendRequestToImageServer(request);
    }

    private String sendRequestToImageServer(HttpEntity<MultiValueMap<String, Object>> request) {
        ResponseEntity<String> response = restTemplate.exchange(imageUrl, POST, request, String.class);
        handleErrors(response.getStatusCode());
        return response.getBody();
    }

    private void handleErrors(HttpStatusCode status) { // todo: 유의미한 예외 던지기
        if (status.is4xxClientError()) {
            throw new RuntimeException("너무 큰 이미지를 올렸습니다.");
        }

        if (status.is5xxServerError()) {
            throw new RuntimeException("파일 업로드에 실패했습니다.");
        }
    }

    private HttpEntity<MultiValueMap<String, Object>> generateHttpEntityRequest(MultipartFile image) {
        HttpHeaders httpHeaders = multipartHeaders();
        MultiValueMap<String, Object> valueMap = getMultiValueMap(image);
        return new HttpEntity<>(valueMap, httpHeaders);
    }

    private MultiValueMap<String, Object> getMultiValueMap(MultipartFile image) {
        InputStreamResource resource = initResource(image);
        return new LinkedMultiValueMap<>() {{
            add(IMAGE_KEY, resource);
        }};
    }

    private InputStreamResource initResource(MultipartFile image) {
        try {
            return new CustomInputStreamResource(image.getInputStream(), image.getOriginalFilename(), image.getSize());
        } catch (IOException e) {
            throw new RuntimeException("파일 변환에 실패했습니다.", e); // todo: 유의미한 에러 던지기
        }
    }

    private HttpHeaders multipartHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MULTIPART_FORM_DATA);
        return httpHeaders;
    }
}
