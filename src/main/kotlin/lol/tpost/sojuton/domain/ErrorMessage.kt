package lol.tpost.sojuton.domain

object ErrorMessage {
    enum class Invalid(val message: String) {
        STREAMER_HAS_STARTED_VOTE("해당 스트리머는 이미 시작된 투표가 있습니다"),
        REACTION_GROUPS_PERCENT_SUM_MUST_BE_100("리액션 그룹 확률의 합은 100이어야합니다"),
        REACTION_GROUP_PERCENT_MUST_NOT_BE_MINUS("리액션 그룹 확률은 음수가 될 수 없습니다"),
    }

    enum class NotFound(val message: String) {
        VOTE("투표를 찾을 수 없습니다"),
        STREAMER("스트리머를 찾을 수 없습니다"),
    }
}
