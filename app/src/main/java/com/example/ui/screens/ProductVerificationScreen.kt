package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.theme.BorderColor
import com.example.ui.theme.DarkEmerald
import com.example.ui.theme.Emerald
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.Graphite
import com.example.ui.theme.Honeydew
import com.example.ui.theme.Ivory
import com.example.ui.theme.LightSurface
import com.example.ui.theme.MainBodyGradient
import com.example.ui.theme.SecondaryText
import com.example.ui.viewmodel.Screen
import com.example.ui.viewmodel.TraceChainViewModel

@Composable
fun ProductVerificationScreen(
    productId: String,
    isInvalid: Boolean,
    invalidCode: String?,
    viewModel: TraceChainViewModel,
    modifier: Modifier = Modifier
) {
    val selectedProduct by viewModel.selectedProduct.collectAsStateWithLifecycle()
    val product = selectedProduct

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
                .padding(bottom = 110.dp)
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
                    modifier = Modifier.testTag("verification_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Graphite
                    )
                }

                Text(
                    text = "Verification Details",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Graphite,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (isInvalid || product == null) {
                // INVALID STATE
                InvalidVerificationView(
                    invalidCode = invalidCode ?: "Unknown QR",
                    onScanAgain = {
                        viewModel.navigateBack()
                        viewModel.navigateTo(Screen.Scan)
                    }
                )
            } else {
                // VERIFIED STATE
                VerifiedProductView(
                    product = product,
                    onViewJourney = { viewModel.openProductJourney(product.id) }
                )
            }
        }
    }
}

@Composable
private fun VerifiedProductView(
    product: com.example.data.model.Product,
    onViewJourney: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        // Status Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = LightSurface),
            border = BorderStroke(1.dp, DarkEmerald.copy(alpha = 0.3f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(DarkEmerald.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Verified Icon",
                        tint = DarkEmerald,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "✓ Product Verified",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkEmerald
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Cryptographic integrity intact on TraceChain",
                        fontSize = 12.sp,
                        color = SecondaryText
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Product Details Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(22.dp)),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = LightSurface),
            border = BorderStroke(1.dp, BorderColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = product.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Graphite
                )

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = BorderColor, thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))

                DetailRow(label = "Product ID", value = product.id)
                DetailRow(label = "Batch Number", value = product.batchNumber)
                DetailRow(label = "Manufacturing Date", value = product.manufacturingDate)
                DetailRow(label = "Expiry Date", value = product.expiryDate)
                DetailRow(label = "Current Status", value = product.currentStatus)
                DetailRow(label = "Retailer", value = product.retailer)
                DetailRow(label = "Origin Facility", value = product.originLocation)
                DetailRow(label = "Temperature Log", value = product.temperatureLog)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Certifications Card
        if (product.certifications.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Honeydew),
                border = BorderStroke(1.dp, BorderColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = DarkEmerald,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Chain Security & Certifications",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkEmerald
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    product.certifications.forEach { cert ->
                        Text(
                            text = "• $cert",
                            fontSize = 12.sp,
                            color = Graphite,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Primary Action: View Product Journey
        Button(
            onClick = onViewJourney,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("view_product_journey_button"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkEmerald,
                contentColor = Ivory
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "View Product Journey",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Ivory
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Emerald,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun InvalidVerificationView(
    invalidCode: String,
    onScanAgain: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(ErrorRed.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Cancel,
                contentDescription = "Not Verified",
                tint = ErrorRed,
                modifier = Modifier.size(44.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Product Not Verified",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = ErrorRed
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "We couldn't find a valid TraceChain record for this QR code ($invalidCode). It may be counterfeit or unregistered.",
            fontSize = 14.sp,
            color = SecondaryText,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onScanAgain,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("scan_again_button"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkEmerald,
                contentColor = Ivory
            )
        ) {
            Icon(
                imageVector = Icons.Default.QrCodeScanner,
                contentDescription = null,
                tint = Emerald,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Scan Again",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = SecondaryText
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Graphite
        )
    }
}
