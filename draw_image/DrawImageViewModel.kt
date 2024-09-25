package ru.chantreck.brics2024.draw_image

import android.net.Uri
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.storage.upload
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import ru.chantreck.brics2024.KtorClient
import ru.chantreck.brics2024.SupabaseClient
import ru.chantreck.brics2024.common.BaseViewModel

class DrawImageViewModel : BaseViewModel<DrawImageState, DrawImageAction>() {

    override fun createInitState(): DrawImageState {
        return DrawImageState(
            isLoading = true,
            uri = null,
        )
    }

    fun saveUri(uri: Uri) {
        _screenState.value = _screenState.value.copy(uri = uri)
        viewModelScope.launch {
            SupabaseClient.imageBucket.upload(path = "uploads/some.jpg", uri, upsert = true)
            val url = SupabaseClient.imageBucket.publicUrl("uploads/some.jpg")
            val result =
                KtorClient.client.post("https://vision.googleapis.com/v1/images:annotate") {
                    header(
                        "Authorization",
                        "Bearer ya29.a0AcM612xY_n2JEFWfQYsglRX0foEj5DbVvqm1nxb51Zn_LQduRt_jY_khMMWKrs1LmKyz0brdKh3Uao0oASHU-OkZON0UkPJccyqHMuiQHzzS0zB45i_H6Rbp__GMGca-LNy_sbFgUnNJ77ow7e2mT9X0tVj7ewGFtljqHvgCKgaCgYKAYwSARESFQHGX2Miy6OXC2_lUsIb8vzgR1YAYA0177"
                    )
                    header("Content-Type", "application/json")
                    setBody(url.toBody())
                }.bodyAsText()
            _screenState.value = _screenState.value.copy(result = result)
        }
    }

    private fun String.toBody(): AnnotateImageBody {
        return AnnotateImageBody(
            requests = listOf(
                RequestBody(
                    image = Image(
                        source = Source(
                            imageUri = this,
                        ),
                    ),
                    features = listOf(
                        Feature(type = "LABEL_DETECTION")
                    ),
                )
            )
        )
    }
}

@Serializable
data class AnnotateImageBody(
    val requests: List<RequestBody>,
)

@Serializable
data class RequestBody(
    val image: Image,
    val features: List<Feature>,
)

@Serializable
data class Image(
    val source: Source,
)

@Serializable
data class Source(
    val imageUri: String,
)

@Serializable
data class Feature(
    val type: String,
)
