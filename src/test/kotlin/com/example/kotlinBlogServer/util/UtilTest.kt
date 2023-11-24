package com.example.kotlinBlogServer.util

import com.example.kotlinBlogServer.service.common.FileUploaderService
import com.example.kotlinBlogServer.service.common.LocalFileUploaderServiceImpl
import com.fasterxml.jackson.databind.ObjectMapper
import mu.two.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths

class UtilTest {
    private val log = KotlinLogging.logger {  }
    private val mapper = ObjectMapper()

    @Test
    fun bcryptEncodeTest(){
        val encoder = BCryptPasswordEncoder()
        val encodePassword = encoder.encode("1234")

        log.info { encodePassword }
    }

    @Test
    fun localFileUploaderTest() {
        val fileUploader: FileUploaderService = LocalFileUploaderServiceImpl()

        val path = Paths.get("src/test/resources/test/test.png")
        val name = "test.png"
        val originalFileName = "test.png"
        val contentType = MediaType.IMAGE_PNG_VALUE
        val content = Files.readAllBytes(path)

        val mockFile: MultipartFile = MockMultipartFile(
            name,
            originalFileName,
            contentType,
            content
        )

        fileUploader.upload(mockFile)
    }

//    @Test
//    fun generateJwtTest() {
//        val jwtManager = JwtManager(accessTokenExpireSecond = 60)
//        val details = PrincipalDetails(Member.createFakeMember(1))
//        val jsonPrincipal = mapper.writeValueAsString(details)
//        val accessToken = jwtManager.generateAccessToken(jsonPrincipal)
//        val decodedJWT = jwtManager.validatedJwt(accessToken)
//
//        val principalString = decodedJWT.getClaim(jwtManager.claimPrincipal).asString()
//        val principalDetails: PrincipalDetails = mapper.readValue(principalString, PrincipalDetails::class.java)
//
//        log.info { "결과: ${principalDetails.member}" }
//
//        details.authorities.forEach{
//            println(it.authority)
//        }
//    }
}