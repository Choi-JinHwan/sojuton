package lol.tpost.sojuton.application.presentation

import com.fasterxml.jackson.annotation.JsonIgnore
import lol.tpost.sojuton.application.client.GptClient
import mu.KLogging
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class SurveyController(
    private val gptClient: GptClient,
) {
    companion object : KLogging()

    private val questions = listOf(
        QuestionResponse(
            "너무 졸릴때 둘중 하나를 먹어야 한다면?!",
            answer = listOf(
                AnswerResponse(
                    answer = "달달한 초콜릿",
                    gptQuestion = "달달한걸 좋아합니다."
                ),

                AnswerResponse(
                    answer = "시원한 아이스아메리카노",
                    gptQuestion = "산미 있는걸 좋아합니다."
                )
            )
        ),
        QuestionResponse(
            "둘 중 내가 좋아하는 날씨는 어떤 걸까요?",
            answer = listOf(
                AnswerResponse(
                    answer = "흐린날이 더 좋아요!",
                    gptQuestion = "흐리고 비오는 날을 좋아합니다."
                ),

                AnswerResponse(
                    answer = "햇빛이 쌘 날이 좋아요!",
                    gptQuestion = "비 오는 날 보단, 쨍쨍한 날씨를 좋아합니다."
                )
            )
        ),
        QuestionResponse(
            "어떤 풍미를 좋아하나요?",
            answer = listOf(
                AnswerResponse(
                    answer = "부드러운 풍미",
                    gptQuestion = "부드러운 풍미를 좋아합니다."
                ),

                AnswerResponse(
                    answer = "강렬한 풍미",
                    gptQuestion = "강렬 한 풍미를 좋아합니다."
                )
            )
        ),
        QuestionResponse(
            "바캉스 장소로 선호하는 곳은?",
            answer = listOf(
                AnswerResponse(
                    answer = "새파란 바다로 갈래요",
                    gptQuestion = "바다에서 마실 술을 찾고 있습니다."
                ),

                AnswerResponse(
                    answer = "푸릇푸릇한 산으로 갈래요",
                    gptQuestion = "산에서 마실 술을 찾고 있습니다."
                )
            )
        ),
        QuestionResponse(
            "내가 좋아하는 고기는?",
            answer = listOf(
                AnswerResponse(
                    answer = "닭이 좋아요",
                    gptQuestion = "닭고기를 좋아합니다."
                ),

                AnswerResponse(
                    answer = "돼지가 좋아요",
                    gptQuestion = "돼지고기를 좋아합니다."
                )
            )
        ),
        QuestionResponse(
            "어떤 시간대에 술을 즐기시나요?",
            answer = listOf(
                AnswerResponse(
                    answer = "아침",
                    gptQuestion = "주로 아침에 술을 마십니다."
                ),

                AnswerResponse(
                    answer = "저녁",
                    gptQuestion = "주로 저녁에 술을 마십니다."
                )
            )
        ),
        QuestionResponse(
            "소주를 좋아하시나요?",
            answer = listOf(
                AnswerResponse(
                    answer = "소주를 좋아해요",
                    gptQuestion = "소주를 선호합니다."
                ),

                AnswerResponse(
                    answer = "소주를 좋아하지 않아요",
                    gptQuestion = "소주를 선호하지 않습니다."
                )
            )
        ),
        QuestionResponse(
            "술은 왜 마시나요?",
            answer = listOf(
                AnswerResponse(
                    answer = "당근 취하려고 마시죠!",
                    gptQuestion = "취하기 위해 술을 마십니다."
                ),

                AnswerResponse(
                    answer = "맛있어서 마셔요!",
                    gptQuestion = "맛있는 술을 먹기 위해 술을 마십니다."
                )
            )
        ),
        QuestionResponse(
            "바다로 가면 무얼 드시겠어요?",
            answer = listOf(
                AnswerResponse(
                    answer = "해산물을 좋아해요",
                    gptQuestion = "해산물을 선호합니다."
                ),

                AnswerResponse(
                    answer = "해산물 보단 다른걸 먹을래요",
                    gptQuestion = "해산물을 싫어해요"
                )
            )
        ),
        QuestionResponse(
            "내가 선호하는 디저트는 뭘까요?",
            answer = listOf(
                AnswerResponse(
                    answer = "초코 케이크",
                    gptQuestion = "초코 케이크를 선호해요!"
                ),

                AnswerResponse(
                    answer = "바닐라 아이스크림",
                    gptQuestion = "바닐라 아이스크림을 선호해요!"
                )
            )
        ),
        QuestionResponse(
            "즐거운 술자리! 친구들과 무얼 하고싶나요?",
            answer = listOf(
                AnswerResponse(
                    answer = "역시 술자리는 술게임이지!",
                    gptQuestion = "시끄럽고 활동적인곳에서 술을 마시고 싶어합니다."
                ),

                AnswerResponse(
                    answer = "조용하게 술을 마시는 편이에요!",
                    gptQuestion = "조용한 분위기에서 술을 마시고 싶어합니다."
                )
            )
        ),
        QuestionResponse(
            "생일에 이런 특이한 파티를 해보고싶다?",
            answer = listOf(
                AnswerResponse(
                    answer = "낭만 해변 캠프 파티",
                    gptQuestion = "낭만적인 해변 캠프 파티를 하고싶어 합니다."
                ),

                AnswerResponse(
                    answer = "요트에서 즐기는 호화 파티",
                    gptQuestion = "요트에서 즐기는 호화 파티를 하고싶어 합니다."
                )
            )
        ),
        QuestionResponse(
            "내가 좋아하는 타입은 무엇일까요?",
            answer = listOf(
                AnswerResponse(
                    answer = "다음날 머리가 깨져야 술이지!",
                    gptQuestion = "독한 술을 좋아합니다."
                ),

                AnswerResponse(
                    answer = "술은 좀 달아야 해요",
                    gptQuestion = "달달한 술을 좋아합니다."
                )
            )
        ),
        QuestionResponse(
            "둘중 지금 먹고 싶은 건 어떤 건가요?",
            answer = listOf(
                AnswerResponse(
                    answer = "육즙 터지는 스테이크",
                    gptQuestion = "지금 스테이크가 먹고싶어요!"
                ),

                AnswerResponse(
                    answer = "바삭한 후라이드치킨",
                    gptQuestion = "지금 후라이드치킨이 먹고싶어요!"
                )
            )
        ),
        QuestionResponse(
            "반주를 즐기시나요?",
            answer = listOf(
                AnswerResponse(
                    answer = "반주 너무 좋아요",
                    gptQuestion = "안주보다는 식사를 선호합니다."
                ),

                AnswerResponse(
                    answer = "안주 정도만 좋아요",
                    gptQuestion = "식사보다는 안주와 함께 먹는걸 선호합니다."
                )
            )
        ),
        QuestionResponse(
            "음주를 자주 하시나요?",
            answer = listOf(
                AnswerResponse(
                    answer = "가끔만 마셔요",
                    gptQuestion = "음주는 가끔 합니다."
                ),

                AnswerResponse(
                    answer = "음주는 저의 낙이에요",
                    gptQuestion = "음주를 자주 합니다"
                )
            )
        ),
        QuestionResponse(
            "지금 입고 있는 옷은 어떤 옷인가요?",
            answer = listOf(
                AnswerResponse(
                    answer = "편안한 캐주얼!",
                    gptQuestion = "캐주얼한 옷을 입고 있습니다."
                ),

                AnswerResponse(
                    answer = "포멀한 정장",
                    gptQuestion = "포멀한 옷을 입고 있습니다."
                )
            )
        ),
        QuestionResponse(
            "지금 흘러나오는 음악의 볼륨은 어떤가요?",
            answer = listOf(
                AnswerResponse(
                    answer = "작거나 거의 들리지 않아요",
                    gptQuestion = "배경음악이 작습니다."
                ),

                AnswerResponse(
                    answer = "큰 볼륨의 음악이 흘러나와요",
                    gptQuestion = "배경음악이 큽니다."
                )
            )
        ),
        QuestionResponse(
            "현재 있는 곳은 실내인가요 실외인가요?",
            answer = listOf(
                AnswerResponse(
                    answer = "실내",
                    gptQuestion = "실내에 있습니다."
                ),

                AnswerResponse(
                    answer = "실외",
                    gptQuestion = "실외에 있습니다."
                )
            )
        ),
        QuestionResponse(
            "음주는 얼마나 오래 즐기실 예정이신가요?",
            answer = listOf(
                AnswerResponse(
                    answer = "가볍게 1시간 내외",
                    gptQuestion = "1시간 내외로 음주할 예정입니다."
                ),

                AnswerResponse(
                    answer = "진득하게 1시간 이상",
                    gptQuestion = "1시간 이상 음주할 예정입니다."
                )
            )
        ),
        QuestionResponse(
            "술과 인생 중 어떤게 더 쓴가요?",
            answer = listOf(
                AnswerResponse(
                    answer = "술은 내 인생보다 달아요",
                    gptQuestion = "힘들다는 말을 달고 산다."
                ),

                AnswerResponse(
                    answer = "무슨 소리에요 술은 그냥 써요",
                    gptQuestion = "힘들다는 말을 달고 살진 않는다."
                )
            )
        ),
        QuestionResponse(
            "완벽한 한 잔을 위해서라면...",
            answer = listOf(
                AnswerResponse(
                    answer = "얼마든 상관 없어요",
                    gptQuestion = "비싼 술도 상관없어요!"
                ),

                AnswerResponse(
                    answer = "그래도 가성비 좋은 술이 좋아요",
                    gptQuestion = "저렴한 술로 부탁드려요!"
                )
            )
        ),
        QuestionResponse(
            "내 머리는 이래요",
            answer = listOf(
                AnswerResponse(
                    answer = "탈모다",
                    gptQuestion = "건강에 좋은 술을 원해요"
                ),

                AnswerResponse(
                    answer = "풍성하다",
                    gptQuestion = "건강에 좋지 않아도 괜찮아요"
                )
            )
        ),
        QuestionResponse(
            "나는 무모한 도전을 즐긴다",
            answer = listOf(
                AnswerResponse(
                    answer = "뱀?술 한번 마셔보고 싶어요",
                    gptQuestion = "뱀술을 한번 마셔보고 싶어요"
                ),

                AnswerResponse(
                    answer = "뱀술은 싫어요",
                    gptQuestion = "뱀술은 마시기 싫어요"
                )
            )
        ),
        QuestionResponse(
            "나는 이런 술이 좋아요",
            answer = listOf(
                AnswerResponse(
                    answer = "이국적인 술이 좋아요",
                    gptQuestion = "한국 전통주를 제외해주세요"
                ),

                AnswerResponse(
                    answer = "한국적인 술이 좋아요",
                    gptQuestion = "전통주가 좋아요"
                )
            )
        )
    )

    private val results: MutableSet<SurveyResultResponse> = mutableSetOf()

    @GetMapping("/all-questions")
    fun allQuestions(): AllQuestionResponse {
        return AllQuestionResponse(questions.shuffled().subList(0, 7))
    }

    @PostMapping("/result")
    fun survey(@RequestBody request: SurveyResultRequest): SurveyResultResponse {
        val gptQuestions = questions.flatMap { it.answer }
            .filter { request.answers.contains(it.answer) }
            .map { it.gptQuestion }

        val result = gptClient.request(gptQuestions)

        logger.info { gptQuestions }

        val response = SurveyResultResponse(
            id = UUID.randomUUID().toString(),
            category = result.category,
            nameKr = result.nameKr,
            nameEn = result.nameEn,
            abv = result.abv,
            reason = result.reason.replace(". ", ".\n\n"),
            munchies = result.munchies.split(", ").joinToString(" ") { "#$it".replace(" ", "_") },
            place = result.place.split(", ").joinToString(" ") { "#$it".replace(" ", "_") },
        )

        results.add(response)
        return response
    }

    @GetMapping("/result/{id}")
    fun result(@PathVariable id: String): SurveyResultResponse {
        return results.find { it.id == id } ?: throw NoSuchElementException("설문 결과를 찾을 수 없습니다")
    }

    @GetMapping("/results")
    fun result(): List<SurveyResultResponse> {
        return results.toList()
    }
}

data class AllQuestionResponse(
    val questions: List<QuestionResponse>
)

data class QuestionResponse(
    val question: String,
    val answer: List<AnswerResponse>,
)

data class AnswerResponse(
    val answer: String,
    @JsonIgnore
    val gptQuestion: String,
)

data class SurveyResultRequest(
    val answers: Set<String>
)

data class SurveyResultResponse(
    val id: String,
    val category: String,
    val nameKr: String,
    val nameEn: String,
    val abv: String,
    val reason: String,
    val munchies: String,
    val place: String,
)
