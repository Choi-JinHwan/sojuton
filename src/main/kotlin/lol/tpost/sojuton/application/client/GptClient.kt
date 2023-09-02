package lol.tpost.sojuton.application.client

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import lol.tpost.sojuton.application.config.OpenAiProperties
import mu.KLogging
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class GptClient(
    private val openAiProperties: OpenAiProperties,
) {
    companion object : KLogging()

    private val endpoint = "https://api.openai.com/v1/chat/completions"
    private val restTemplate = RestTemplate()
    private val objectMapper = ObjectMapper().registerKotlinModule()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    private val prompt = """
    당신은 유능한 주당꾼입니다. 손님에 취향을 파악하고, "술 종류 카테고리 리스트"에서 손님이 좋아할 법한 술을 추천 해줘야합니다!
    - 술 종류 카테고리 리스트-
    1.전통주
    2. 양주
    3. 칵테일
    4. 소주
    5. 맥주
    6. 와인
    7. 위스키
    - 규칙 -
    - 내용은 한국어로 표시합니다.
    - 실제 있는 술만 알려주셔야합니다.
    - 포맷 -
    - 아래와 추천하는 술은 총 1개,  술이름,도수에 대해 같이 전달 부탁드립니다.
    - 내용은 JSON으로 전달해야 합니다.
    {
        "category" : "(술 종류 카테고리)",
        "nameKr" : "(술이름 한문)",
        "nameEn" : "(술이름 영문)",
        "abv" : "(술 도수 숫자로만 표시)",
        "munchies" : "(추천 하는 술과 함께 어울리는 안주 3개 ,로 알려줘)",
        "place" : "(이 술을 마시기 좋은 장소로 3개 추천해줘)",
        "reason" : "(추천 사유)"
    }
      You must write your question unconditionally and absolutely in JSON format, as shown below.
              Never add anything else. numbers, letters, etc.
    """.trimIndent()

    fun request(gptQuestions: List<String>): GptResultContent {
        val headers = HttpHeaders().apply {
            this.contentType = MediaType.APPLICATION_JSON
            this["Authorization"] = "Bearer ${openAiProperties.key}"
        }
        val system = ChatCompletionsMessageDto("system", prompt)
        val user = ChatCompletionsMessageDto("user", gptQuestions.joinToString(", "))
        val request = ChatCompletionsRequest(messages = listOf(system, user))
        val requestEntity = HttpEntity(request, headers)
        val response: ResponseEntity<String> =
            restTemplate.postForEntity(endpoint, requestEntity, String::class.java)

        logger.info { response.body!! }
        return objectMapper.readValue(
            objectMapper.readValue(
                response.body!!,
                GptResult::class.java
            ).choices[0].message.content, GptResultContent::class.java
        )
    }
}

class ChatCompletionsMessageDto(
    val role: String,
    val content: String,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ChatCompletionsRequest(
    val model: String = "gpt-4",
    val maxTokens: Int = 1024,
    val topP: Double = 0.7,
    val messages: List<ChatCompletionsMessageDto>
)

data class GptResult(
    val choices: List<GptChoice>
)

data class GptChoice(
    val message: GptMessage
)

data class GptMessage(
    val content: String,
)

data class GptResultContent(
    val category: String,
    val nameKr: String,
    val nameEn: String,
    val abv: String,
    val reason: String,
    val munchies: String,
    val place: String,
)
