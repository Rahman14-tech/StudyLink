import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.example.studylink.MediaViewer
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.REPEAT_MODE_OFF
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import java.net.URLEncoder

@Composable
fun VideoPlayerScreen(ChatId: String, navController: NavHostController,mediaUrl: String,hideFullscreen:Boolean = false) {

    val context = LocalContext.current
    var isFullScreen by remember { mutableStateOf(false) }

    val mediaItem = MediaItem.Builder()
        .setUri(mediaUrl)
        .build()
    val exoPlayer = remember(context, mediaItem) {
        ExoPlayer.Builder(context)
            .build()
            .also { exoPlayer ->
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = false
                exoPlayer.repeatMode = REPEAT_MODE_OFF
            }
    }

    Box {

        DisposableEffect(
           if(!hideFullscreen){
               AndroidView(factory = {
                   StyledPlayerView(context).apply {
                       player = exoPlayer
                   }
               }, update = {
                   it.player = exoPlayer
               }, modifier = Modifier.size(300.dp).fillMaxSize())
           }else{
               AndroidView(factory = {
                   StyledPlayerView(context).apply {
                       player = exoPlayer
                   }
               }, update = {
                   it.player = exoPlayer
               }, modifier = Modifier.fillMaxWidth().height(400.dp))
           }

        ) {
            onDispose { exoPlayer.release() }
        }
        if(!hideFullscreen){
            IconButton(
                onClick = {
                    val encodedUrl = URLEncoder.encode(mediaUrl)
                    navController.navigate(MediaViewer.route + "/$ChatId"+"/$encodedUrl") },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 30.dp)
            ) {
                Icon(
                    imageVector = if (isFullScreen) Icons.Default.FullscreenExit else Icons.Default.Fullscreen,
                    contentDescription = if (isFullScreen) "Exit Fullscreen" else "Fullscreen"
                )
            }
        }

    }

}

