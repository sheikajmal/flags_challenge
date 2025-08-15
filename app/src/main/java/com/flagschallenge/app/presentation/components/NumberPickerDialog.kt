package com.flagschallenge.app.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flagschallenge.app.presentation.feature.ui.theme.primary
import com.flagschallenge.app.presentation.feature.ui.theme.theme
import com.flagschallenge.app.utils.Dimen
import com.flagschallenge.app.utils.Typo

@Composable
fun NumberPickerDialog(
    title: String,
    range: IntRange,
    initialValue: Int,
    onValueSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var selected by remember { mutableIntStateOf(initialValue) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                color = primary,
                fontSize = 18.sp,
                lineHeight = 18.sp,
                fontFamily = Typo.interFamily,
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(range.toList()) { number ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selected = number },
                        text = number.toString(),
                        color = if (selected == number) theme else primary,
                        fontSize = if (selected == number) 28.sp else 20.sp,
                        fontFamily = Typo.interFamily,
                        fontWeight = if (selected == number) FontWeight.Bold else FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        confirmButton = {
            Button(
                modifier = Modifier
                    .width(100.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = theme,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(Dimen.themeRadius),
                onClick = {
                    onValueSelected(selected)
                }
            ) {
                Text(
                    text = "OK",
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontFamily = Typo.interFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                modifier = Modifier
                    .width(100.dp)
                    .height(40.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = primary
                ),
                shape = RoundedCornerShape(Dimen.themeRadius),
                onClick = onDismiss
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontFamily = Typo.interFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    )
}