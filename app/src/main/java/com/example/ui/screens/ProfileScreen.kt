package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.theme.BorderColor
import com.example.ui.theme.DarkEmerald
import com.example.ui.theme.Emerald
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.Graphite
import com.example.ui.theme.Ivory
import com.example.ui.theme.LightSurface
import com.example.ui.theme.MainBodyGradient
import com.example.ui.theme.SecondaryText
import com.example.ui.viewmodel.TraceChainViewModel

@Composable
fun ProfileScreen(
    viewModel: TraceChainViewModel,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.userProfile.collectAsStateWithLifecycle()
    var showDialogInfo by remember { mutableStateOf<Pair<String, String>?>(null) }
    var showLogoutDialog by remember { mutableStateOf(false) }

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
                .padding(bottom = 50.dp)
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
                    modifier = Modifier.testTag("profile_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Graphite
                    )
                }

                Text(
                    text = "Profile & Settings",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Graphite,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // User Info Header Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(22.dp)),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = LightSurface),
                border = BorderStroke(1.dp, BorderColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .shadow(elevation = 3.dp, shape = CircleShape)
                            .clip(CircleShape)
                            .background(DarkEmerald),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = profile.initials,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Ivory
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = profile.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Graphite
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = profile.email,
                            fontSize = 12.sp,
                            color = SecondaryText
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Customer Account • Verified",
                            fontSize = 11.sp,
                            color = DarkEmerald,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Section 1: Account
            ProfileSectionTitle("Account")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = LightSurface),
                border = BorderStroke(1.dp, BorderColor)
            ) {
                Column {
                    ProfileMenuRow(
                        icon = Icons.Default.Person,
                        title = "Personal Information",
                        onClick = {
                            showDialogInfo = Pair(
                                "Personal Information",
                                "Name: ${profile.name}\nEmail: ${profile.email}\nPhone: ${profile.phone}\nLocation: ${profile.location}"
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Section 2: Notifications
            ProfileSectionTitle("Notifications")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = LightSurface),
                border = BorderStroke(1.dp, BorderColor)
            ) {
                Column {
                    ProfileSwitchRow(
                        icon = Icons.Default.Notifications,
                        title = "Expiry Alerts",
                        subtitle = "Notify before products reach expiration",
                        checked = profile.expiryAlertsEnabled,
                        onCheckedChange = { viewModel.toggleExpiryAlerts() }
                    )
                    HorizontalDivider(color = BorderColor, thickness = 0.8.dp)
                    ProfileSwitchRow(
                        icon = Icons.Default.Notifications,
                        title = "Product Notifications",
                        subtitle = "Recall warnings and verification logs",
                        checked = profile.productNotificationsEnabled,
                        onCheckedChange = { viewModel.toggleProductNotifications() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Section 3: Security
            ProfileSectionTitle("Security")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = LightSurface),
                border = BorderStroke(1.dp, BorderColor)
            ) {
                Column {
                    ProfileMenuRow(
                        icon = Icons.Default.Lock,
                        title = "Login & Security",
                        onClick = {
                            showDialogInfo = Pair(
                                "Login & Security",
                                "Account secured with device biometric authentication and public key cryptographic verification."
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Section 4: About
            ProfileSectionTitle("About")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = LightSurface),
                border = BorderStroke(1.dp, BorderColor)
            ) {
                Column {
                    ProfileMenuRow(
                        icon = Icons.Default.Info,
                        title = "About TraceChain",
                        onClick = {
                            showDialogInfo = Pair(
                                "About TraceChain",
                                "TraceChain v1.0.0\n\nA consumer-focused blockchain product lifecycle and traceability platform. Verify authenticity, track transit temperature logs, and prevent counterfeit food and medicine."
                            )
                        }
                    )
                    HorizontalDivider(color = BorderColor, thickness = 0.8.dp)
                    ProfileMenuRow(
                        icon = Icons.Default.Policy,
                        title = "Privacy Policy",
                        onClick = {
                            showDialogInfo = Pair(
                                "Privacy Policy",
                                "Your verification records and purchases are stored locally on your device with cryptographic signatures. No personal identifying data is sold to third parties."
                            )
                        }
                    )
                    HorizontalDivider(color = BorderColor, thickness = 0.8.dp)
                    ProfileMenuRow(
                        icon = Icons.Default.Policy,
                        title = "Terms & Conditions",
                        onClick = {
                            showDialogInfo = Pair(
                                "Terms & Conditions",
                                "TraceChain provides decentralized proof-of-origin for consumer goods. Information is verified through certified supply chain partners and IoT audit nodes."
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Section 5: Logout
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = LightSurface),
                border = BorderStroke(1.dp, BorderColor)
            ) {
                ProfileMenuRow(
                    icon = Icons.Default.Logout,
                    title = "Logout",
                    iconColor = ErrorRed,
                    textColor = ErrorRed,
                    onClick = { showLogoutDialog = true }
                )
            }
        }
    }

    // Info Dialog
    showDialogInfo?.let { (title, message) ->
        AlertDialog(
            onDismissRequest = { showDialogInfo = null },
            title = { Text(text = title, fontWeight = FontWeight.Bold, color = Graphite) },
            text = { Text(text = message, color = Graphite, lineHeight = 20.sp) },
            confirmButton = {
                TextButton(onClick = { showDialogInfo = null }) {
                    Text("OK", color = DarkEmerald, fontWeight = FontWeight.SemiBold)
                }
            },
            containerColor = LightSurface,
            shape = RoundedCornerShape(20.dp)
        )
    }

    // Logout Confirmation Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Log Out", fontWeight = FontWeight.Bold, color = Graphite) },
            text = { Text("Are you sure you want to log out of TraceChain?", color = Graphite) },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    viewModel.navigateBack()
                }) {
                    Text("Log Out", color = ErrorRed, fontWeight = FontWeight.SemiBold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel", color = SecondaryText)
                }
            },
            containerColor = LightSurface,
            shape = RoundedCornerShape(20.dp)
        )
    }
}

@Composable
private fun ProfileSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = SecondaryText,
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
    )
}

@Composable
private fun ProfileMenuRow(
    icon: ImageVector,
    title: String,
    iconColor: androidx.compose.ui.graphics.Color = DarkEmerald,
    textColor: androidx.compose.ui.graphics.Color = Graphite,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = textColor,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = SecondaryText.copy(alpha = 0.6f),
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun ProfileSwitchRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = DarkEmerald,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Graphite
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = SecondaryText
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Ivory,
                checkedTrackColor = DarkEmerald,
                uncheckedThumbColor = SecondaryText,
                uncheckedTrackColor = LightSurface
            )
        )
    }
}
