package com.example.android.bakingapp.ui

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.android.bakingapp.model.Step

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepDetailScreen(
    step: Step,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Step ${step.id}") },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.semantics { contentDescription = "Back" }
                    ) {
                        Text("<") // Placeholder for back icon
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (step.videoURL.isNotEmpty()) {
                VideoPlayer(videoUrl = step.videoURL)
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(text = "No video available for this step.")
                }
            }

            Text(
                text = step.shortDescription,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )

            Text(
                text = step.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun VideoPlayer(videoUrl: String) {
    val context = LocalContext.current
    
    // Initialize ExoPlayer
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(videoUrl)
            setMediaItem(mediaItem)
            prepare()
        }
    }

    // Use AndroidView to embed PlayerView
    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )

    // Release player when the Composable leaves the composition
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}
