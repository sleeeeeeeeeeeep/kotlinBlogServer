package com.example.kotlinBlogServer.service.common

import com.example.kotlinBlogServer.config.aop.CustomAopObject
import jakarta.annotation.PostConstruct
import mu.two.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.Path

@Service
class LocalFileUploaderServiceImpl(

) : FileUploaderService {
    private val log = KotlinLogging.logger {  }

    val localImgFolder = "postImg"

    @PostConstruct
    fun init() = CustomAopObject.wrapTryCatchWithVoidFunc {
        log.info { "이미지 폴더 만듬: $localImgFolder" }
        Files.createDirectories(Paths.get(localImgFolder))
    }

    override fun upload(file: MultipartFile) {
        Files.write(Path(localImgFolder + "/" + file.originalFilename), file.bytes)
    }
}