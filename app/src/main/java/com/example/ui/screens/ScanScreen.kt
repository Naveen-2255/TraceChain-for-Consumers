package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.ui.theme.BorderColor
import com.example.ui.theme.DarkEmerald
import com.example.ui.theme.Emerald
import com.example.ui.theme.Graphite
import com.example.ui.theme.Honeydew
import com.example.ui.theme.Ivory
import com.example.ui.theme.LightSurface
import com.example.ui.theme.MainBodyGradient
import com.example.ui.theme.SecondaryText
import com.example.ui.viewmodel.TraceChainViewModel

@Composable
fun ScanScreen(
    viewModel: TraceChainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    // One-time scanning line sweep
    val scanLineOffset = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        scanLineOffset.snapTo(0f)
        scanLineOffset.animateTo(220f, tween(1000, easing = FastOutSlowInEasing))
        scanLineOffset.animateTo(0f, tween(1000, easing = FastOutSlowInEasing))
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MainBodyGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(bottom = 110.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { viewModel.navigateBack() },
                    modifier = Modifier.testTag("scan_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Graphite
                    )
                }

                Text(
                    text = "Scan Product",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Graphite,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Position the QR code inside the frame",
                fontSize = 14.sp,
                color = SecondaryText,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Scanner Viewport
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Graphite.copy(alpha = 0.08f))
                    .border(3.dp, DarkEmerald, RoundedCornerShape(24.dp))
                    .clickable {
                        // Tapping frame triggers instant QR scan verification
                        viewModel.processScannedQr("TC-AMUL-001")
                    }
                    .testTag("scan_camera_frame"),
                contentAlignment = Alignment.Center
            ) {
                // Outer darkened camera background simulation
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Graphite.copy(alpha = 0.12f))
                )

                // Center QR accent
                Icon(
                    imageVector = Icons.Default.QrCode2,
                    contentDescription = null,
                    tint = DarkEmerald.copy(alpha = 0.35f),
                    modifier = Modifier.size(120.dp)
                )

                // One-time Emerald scan indicator bar
                Box(
                    modifier = Modifier
                        .offset(y = (scanLineOffset.value - 110).dp)
                        .fillMaxWidth(0.85f)
                        .height(2.5.dp)
                        .background(Emerald)
                )
            }
        }
    }
}
