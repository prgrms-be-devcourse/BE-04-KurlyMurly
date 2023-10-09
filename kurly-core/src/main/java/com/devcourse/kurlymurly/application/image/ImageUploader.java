package com.devcourse.kurlymurly.application.image;

import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface ImageUploader {
    /**
     * 클라이언트에게 받은 이미지 파일을 이미지 서버에 저장한다.
     * @param image 20MB를 넘는 이미지는 업로드할 수 없다.
     * @return 서버에 저장된 경로를 반환한다.
     */
    CompletableFuture<String> upload(MultipartFile image);
}
