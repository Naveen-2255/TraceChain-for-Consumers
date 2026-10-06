package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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

    // Scanning line animation
    val infiniteTransition = rememberInfiniteTransition(label = "scan_laser")
    val scanLineOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 220f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scan_line_offset"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MainBodyGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
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

            Spacer(modifier = Modifier.height(24.dp))

            // Scanner Viewport
            Box(
                modifier = Modifier
                    .size(260.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Graphite.copy(alpha = 0.08f))
                    .border(3.dp, DarkEmerald, RoundedCornerShape(24.dp))
                    .testTag("scan_camera_frame"),
                contentAlignment = Alignment.Center
            ) {
                // Outer darkened camera background simulation
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Graphite.copy(alpha = 0.12f))
                )

                // Corner bracket accents
                Icon(
                    imageVector = Icons.Default.QrCode2,
                    contentDescription = null,
                    tint = DarkEmerald.copy(alpha = 0.35f),
                    modifier = Modifier.size(110.dp)
                )

                // Subtle Emerald scan indicator bar
                Box(
                    modifier = Modifier
                        .offset(y = (scanLineOffset - 110).dp)
                        .fillMaxWidth(0.85f)
                        .height(2.5.dp)
                        .background(Emerald)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Camera status / Permission explanation
            if (!hasCameraPermission) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = LightSurface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderColor)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Camera Permission Required",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Graphite
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Camera access enables instant QR verification on product packaging.",
                            fontSize = 12.sp,
                            color = SecondaryText,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = { launcher.launch(Manifest.permission.CAMERA) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DarkEmerald,
                                contentColor = Ivory
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Grant Permission", fontSize = 13.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Quick Simulate / Test QR codes (crucial for emulator testing)
            Text(
                text = "Tap a sample code to simulate scan:",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = SecondaryText
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SampleQrButton(
                    title = "Amul Taaza Milk 1L",
                    batch = "AM-2026-001 (Verified)",
                    code = "TC-AMUL-001",
                    onSelect = { viewModel.processScannedQr("TC-AMUL-001") }
                )

                SampleQrButton(
                    title = "Modern White Bread",
                    batch = "MD-2026-114 (Expiring Soon)",
                    code = "TC-BREAD-114",
                    onSelect = { viewModel.processScannedQr("TC-BREAD-114") }
                )

                SampleQrButton(
                    title = "Organic Wildflower Honey",
                    batch = "OH-2026-042 (Verified)",
                    code = "TC-HONEY-042",
                    onSelect = { viewModel.processScannedQr("TC-HONEY-042") }
                )

                SampleQrButton(
                    title = "Counterfeit / Unregistered Code",
                    batch = "INVALID-999-FAKE",
                    code = "INVALID-999-FAKE",
                    onSelect = { viewModel.processScannedQr("INVALID-999-FAKE") }
                )
            }
        }
    }
}

@Composable
private fun SampleQrButton(
    title: String,
    batch: String,
    code: String,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onSelect)
            .testTag("sample_qr_$code"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = LightSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Honeydew),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.QrCode2,
                        contentDescription = null,
                        tint = DarkEmerald,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Graphite
                    )
                    Text(
                        text = batch,
                        fontSize = 11.sp,
                        color = SecondaryText
                    )
                }
            }

            Text(
                text = "Simulate →",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = DarkEmerald
            )
        }
    }
}
