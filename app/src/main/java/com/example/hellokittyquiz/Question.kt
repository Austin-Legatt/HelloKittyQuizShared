import androidx.annotation.StringRes

data class Question(@StringRes val textReId: Int, val questAnswer: Boolean, var answered: Boolean = false)