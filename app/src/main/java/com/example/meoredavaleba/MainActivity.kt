package com.example.meoredavaleba

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentFormTheme {
                StudentFormScreen()
            }
        }
    }
}

@Composable
fun StudentFormTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF5E35B1),
            secondary = Color(0xFF00ACC1),
            surface = Color.White,
            background = Color(0xFFF3F4F6)
        ),
        content = content
    )
}

@Composable
fun StudentFormScreen() {
    val context = LocalContext.current
    
    // State variables as requested
    var nameState by remember { mutableStateOf("") }
    var surnameState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    var dateState by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var isAgreed by remember { mutableStateOf(false) }

    // DatePickerDialog logic
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            dateState = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Unique UI Colors
    val primaryColor = Color(0xFF5E35B1)
    val accentColor = Color(0xFFD81B60)
    val headerGradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF311B92), Color(0xFF5E35B1))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .verticalScroll(rememberScrollState())
    ) {
        // Unique Header Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(headerGradient, shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .padding(24.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Column {
                Text(
                    text = "Student Form",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black
                    )
                )
                Text(
                    text = "Please fill in your details",
                    style = TextStyle(
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Form Section
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        ) {
            // Name Field
            LabelText("Name")
            CustomInputField(
                value = nameState,
                onValueChange = { nameState = it },
                placeholder = "Enter your name"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Surname Field
            LabelText("Surname")
            CustomInputField(
                value = surnameState,
                onValueChange = { surnameState = it },
                placeholder = "Enter your surname"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Date Picker Field
            LabelText("Birth Date")
            OutlinedTextField(
                value = dateState,
                onValueChange = {},
                placeholder = { Text("DD/MM/YYYY") },
                readOnly = true,
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePickerDialog.show() },
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Pick Date", tint = primaryColor)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color(0xFFD1D5DB),
                    disabledPlaceholderColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email Field
            LabelText("Email Address")
            CustomInputField(
                value = emailState,
                onValueChange = { emailState = it },
                placeholder = "your.email@example.com"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Favorite Direction (RadioButtons)
            Text(
                text = "Your favorite direction",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF374151)
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            val directions = listOf("Android", "iOS", "Web")
            directions.forEach { direction ->
                Surface(
                    onClick = { selectedOption = direction },
                    shape = RoundedCornerShape(12.dp),
                    color = if (selectedOption == direction) primaryColor.copy(alpha = 0.1f) else Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    tonalElevation = 2.dp,
                    shadowElevation = 1.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        RadioButton(
                            selected = (selectedOption == direction),
                            onClick = { selectedOption = direction },
                            colors = RadioButtonDefaults.colors(selectedColor = primaryColor)
                        )
                        Text(
                            text = direction,
                            modifier = Modifier.padding(start = 12.dp),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF1F2937)
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Switch Section
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp).fillMaxWidth()
                ) {
                    Switch(
                        checked = isAgreed,
                        onCheckedChange = { isAgreed = it },
                        colors = SwitchDefaults.colors(checkedTrackColor = accentColor)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "ვეთანხმები წესებს და პირობებს",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF4B5563)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Submit Button
            Button(
                onClick = {
                    val allTextFilled = nameState.isNotBlank() && surnameState.isNotBlank() && 
                                       emailState.isNotBlank() && dateState.isNotBlank()
                    val optionSelected = selectedOption != null
                    
                    if (allTextFilled && optionSelected && isAgreed) {
                        Toast.makeText(context, "მონაცემები გაიგზავნა!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
            ) {
                Text(
                    text = "Submit",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun LabelText(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6B7280)
        ),
        modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
    )
}

@Composable
fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF5E35B1),
            unfocusedBorderColor = Color(0xFFD1D5DB)
        )
    )
}
