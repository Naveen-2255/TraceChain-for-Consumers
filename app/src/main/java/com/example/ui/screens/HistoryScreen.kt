package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.ProductCard
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
fun HistoryScreen(
    viewModel: TraceChainViewModel,
    modifier: Modifier = Modifier
) {
    val purchases by viewModel.allPurchases.collectAsStateWithLifecycle()
    val searchQuery by viewModel.historySearchQuery.collectAsStateWithLifecycle()
    val selectedFilter by viewModel.historyFilterTab.collectAsStateWithLifecycle()

    val filteredPurchases = purchases.filter { purchase ->
        val matchesSearch = searchQuery.isBlank() ||
                purchase.productName.contains(searchQuery, ignoreCase = true) ||
                purchase.batchNumber.contains(searchQuery, ignoreCase = true) ||
                purchase.retailer.contains(searchQuery, ignoreCase = true)

        val matchesFilter = when (selectedFilter) {
            "Verified" -> purchase.verificationStatus.equals("Verified", ignoreCase = true)
            "Expiring" -> purchase.verificationStatus.contains("Expir", ignoreCase = true)
            else -> true
        }

        matchesSearch && matchesFilter
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
        ) {
            // Title Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Product History",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Graphite
                )
            }

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setHistorySearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .testTag("history_search_input"),
                placeholder = {
                    Text("Search by product or batch...", fontSize = 14.sp, color = SecondaryText)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = SecondaryText,
                        modifier = Modifier.size(20.dp)
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setHistorySearchQuery("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = SecondaryText,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = LightSurface,
                    unfocusedContainerColor = LightSurface,
                    focusedBorderColor = DarkEmerald,
                    unfocusedBorderColor = BorderColor,
                    cursorColor = DarkEmerald,
                    focusedTextColor = Graphite,
                    unfocusedTextColor = Graphite
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Verified", "Expiring").forEach { tab ->
                    val isSelected = selectedFilter == tab
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) DarkEmerald else LightSurface)
                            .border(
                                BorderStroke(1.dp, if (isSelected) DarkEmerald else BorderColor),
                                RoundedCornerShape(12.dp)
                            )
                            .clickable { viewModel.setHistoryFilterTab(tab) }
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                            .testTag("history_filter_$tab")
                    ) {
                        Text(
                            text = if (tab == "Expiring") "Expiring Soon" else tab,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                            color = if (isSelected) Ivory else Graphite
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Chronological list
            if (filteredPurchases.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(Honeydew),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = null,
                                tint = DarkEmerald,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "No history found",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Graphite
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Your product history will appear here once verified.",
                            fontSize = 13.sp,
                            color = SecondaryText
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 120.dp)
                ) {
                    items(
                        items = filteredPurchases,
                        key = { it.id }
                    ) { purchase ->
                        ProductCard(
                            purchase = purchase,
                            onClick = { viewModel.openProductVerification(purchase.productId) }
                        )
                    }
                }
            }
        }
    }
}
