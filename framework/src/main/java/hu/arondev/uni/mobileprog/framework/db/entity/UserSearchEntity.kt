package hu.arondev.uni.mobileprog.framework.db.entity

data class UserSearchEntity(
    val incomplete_results: Boolean = false,
    val items: List<UserEntity> = listOf(),
    val total_count: Int = 0
)