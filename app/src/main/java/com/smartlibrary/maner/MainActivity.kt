package com.smartlibrary.maner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SmartLibraryApp()
        }
    }
}

private val SmartRed = Color(0xFFD71920)
private val SmartBlue = Color(0xFF123A8C)
private val SmartDarkBlue = Color(0xFF08265F)
private val SmartLightBlue = Color(0xFFEAF2FF)
private val SmartLightRed = Color(0xFFFFEEEE)
private val SmartGold = Color(0xFFFFC107)
private val SmartWhite = Color.White
private val SmartText = Color(0xFF172033)

@Composable
fun SmartLibraryApp() {

    var showStudentLogin by remember { mutableStateOf(false) }
    var showAdminLogin by remember { mutableStateOf(false) }
    var showStudentSection by remember { mutableStateOf(false) }
    var showAdminSection by remember { mutableStateOf(false) }
    var showTestSection by remember { mutableStateOf(false) }
    var showCurrentAffairs by remember { mutableStateOf(false) }
    var showStudyMaterial by remember { mutableStateOf(false) }

    val colors = lightColorScheme(
        primary = SmartRed,
        secondary = SmartBlue,
        background = SmartWhite,
        surface = SmartWhite,
        onPrimary = SmartWhite,
        onSecondary = SmartWhite,
        onBackground = SmartText,
        onSurface = SmartText
    )

    MaterialTheme(colorScheme = colors) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF7F9FC)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // LOGO
                Surface(
                    modifier = Modifier.size(92.dp),
                    shape = CircleShape,
                    color = SmartBlue,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "SL",
                            color = SmartWhite,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Text(
                            text = "2021",
                            color = SmartGold,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "SMART LIBRARY",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = SmartRed,
                    letterSpacing = 1.sp
                )

                Text(
                    text = "MANER • PATNA",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = SmartBlue
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "पढ़ो आज, संवारो कल",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = SmartBlue
                )

                Spacer(modifier = Modifier.height(22.dp))

                // WELCOME
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = SmartRed
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "WELCOME TO SMART LIBRARY",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = SmartWhite,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Study • Practice • Test • Achieve",
                            fontSize = 16.sp,
                            color = SmartWhite
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // STUDENT + ADMIN
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    FeatureCard(
                        modifier = Modifier.weight(1f),
                        title = "STUDENT",
                        subtitle = "Study & Tests",
                        color = SmartBlue,
                        onClick = {
                            showStudentSection = true
                        }
                    )

                    FeatureCard(
                        modifier = Modifier.weight(1f),
                        title = "ADMIN",
                        subtitle = "Library Management",
                        color = SmartRed,
                        onClick = {
                            showAdminSection = true
                        }
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // STUDENT LOGIN
                Button(
                    onClick = {
                        showStudentLogin = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SmartBlue
                    )
                ) {
                    Text(
                        text = "👤  STUDENT LOGIN",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // ADMIN LOGIN
                OutlinedButton(
                    onClick = {
                        showAdminLogin = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, SmartRed)
                ) {
                    Text(
                        text = "🔐  ADMIN LOGIN",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = SmartRed
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                // ONLINE TEST
                ServiceCard(
                    title = "📝  ONLINE TEST",
                    subtitle = "Bihar Police • Daroga • BPSC • SSC • Railway • SSC GD",
                    buttonText = "START TEST",
                    color = SmartBlue,
                    onClick = {
                        showTestSection = true
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // CURRENT AFFAIRS
                ServiceCard(
                    title = "📰  DAILY CURRENT AFFAIRS",
                    subtitle = "Daily MCQ • Important Questions • Answers • Explanation",
                    buttonText = "VIEW CURRENT AFFAIRS",
                    color = SmartRed,
                    onClick = {
                        showCurrentAffairs = true
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // STUDY MATERIAL
                ServiceCard(
                    title = "📚  STUDY MATERIAL",
                    subtitle = "Notes • PDF • Syllabus • Previous Year Questions",
                    buttonText = "OPEN STUDY MATERIAL",
                    color = SmartBlue,
                    onClick = {
                        showStudyMaterial = true
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // FACILITIES
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = SmartWhite
                    ),
                    elevation = CardDefaults.cardElevation(3.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "SMART LIBRARY MANER",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = SmartBlue
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        FacilityText("✓ Silent & Educational Environment")
                        FacilityText("✓ Free Wi-Fi")
                        FacilityText("✓ Locker Facility")
                        FacilityText("✓ R.O. Water")
                        FacilityText("✓ Separate Sitting Available")
                        FacilityText("✓ News Paper & Magazines")
                        FacilityText("✓ 24×7 Open")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "DISCIPLINE TODAY • SUCCESS TOMORROW",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = SmartRed,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Smart Library Maner • Patna, Bihar",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

    // STUDENT LOGIN
    if (showStudentLogin) {
        StudentLoginDialog(
            onClose = {
                showStudentLogin = false
            }
        )
    }

    // ADMIN LOGIN
    if (showAdminLogin) {
        AdminLoginDialog(
            onClose = {
                showAdminLogin = false
            }
        )
    }

    // STUDENT SECTION
    if (showStudentSection) {
        SimpleDialog(
            title = "👨‍🎓 STUDENT SECTION",
            message = "Student Study & Test section तैयार है।\n\nयहाँ से विद्यार्थी Study Material, Online Test और Current Affairs access कर सकेंगे।",
            buttonText = "OPEN",
            onClose = {
                showStudentSection = false
            }
        )
    }

    // ADMIN SECTION
    if (showAdminSection) {
        SimpleDialog(
            title = "🛠️ ADMIN • LIBRARY MANAGEMENT",
            message = "Admin Management section तैयार है।\n\nयहाँ से Students, Admission, Payment, Tests और Library Management किया जा सकेगा।",
            buttonText = "OPEN",
            onClose = {
                showAdminSection = false
            }
        )
    }

    // TEST
    if (showTestSection) {
        SimpleDialog(
            title = "📝 ONLINE TEST",
            message = "Online Test section खुल गया है।\n\nBihar Police • Daroga • BPSC • SSC • Railway • SSC GD",
            buttonText = "START",
            onClose = {
                showTestSection = false
            }
        )
    }

    // CURRENT AFFAIRS
    if (showCurrentAffairs) {
        SimpleDialog(
            title = "📰 DAILY CURRENT AFFAIRS",
            message = "Daily Current Affairs section खुल गया है।\n\nMCQ • Important Questions • Answers • Explanation",
            buttonText = "OK",
            onClose = {
                showCurrentAffairs = false
            }
        )
    }

    // STUDY MATERIAL
    if (showStudyMaterial) {
        SimpleDialog(
            title = "📚 STUDY MATERIAL",
            message = "Study Material section खुल गया है।\n\nNotes • PDF • Syllabus • Previous Year Questions",
            buttonText = "OK",
            onClose = {
                showStudyMaterial = false
            }
        )
    }
}

@Composable
fun FeatureCard(
    modifier: Modifier,
    title: String,
    subtitle: String,
    color: Color,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier.clickable {
            onClick()
        },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                color = SmartWhite,
                fontSize = 19.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = subtitle,
                color = SmartWhite,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun ServiceCard(
    title: String,
    subtitle: String,
    buttonText: String,
    color: Color,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = SmartWhite
        ),
        border = BorderStroke(2.dp, color),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = color
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = subtitle,
                fontSize = 15.sp,
                color = SmartText
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = {
                    onClick()
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = color
                )
            ) {
                Text(
                    text = buttonText,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun FacilityText(text: String) {

    Text(
        text = text,
        modifier = Modifier.padding(vertical = 5.dp),
        fontSize = 15.sp,
        color = SmartText
    )
}

@Composable
fun StudentLoginDialog(
    onClose: () -> Unit
) {

    var studentId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginMessage by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Text(
                text = "👤  STUDENT LOGIN",
                fontSize = 23.sp,
                fontWeight = FontWeight.ExtraBold,
                color = SmartBlue
            )
        },
        text = {

            Column {

                OutlinedTextField(
                    value = studentId,
                    onValueChange = {
                        studentId = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Student ID")
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Password")
                    },
                    singleLine = true
                )

                if (loginMessage.isNotEmpty()) {

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = loginMessage,
                        color = SmartRed,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        confirmButton = {

            Button(
                onClick = {

                    if (studentId.isBlank() || password.isBlank()) {
                        loginMessage = "Student ID और Password डालिए।"
                    } else {
                        loginMessage = "Login details accepted. Student dashboard तैयार किया जा सकता है।"
                    }

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SmartBlue
                )
            ) {
                Text("LOGIN")
            }
        },
        dismissButton = {

            TextButton(
                onClick = onClose
            ) {
                Text(
                    text = "CLOSE",
                    color = SmartRed,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}

@Composable
fun AdminLoginDialog(
    onClose: () -> Unit
) {

    var adminId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginMessage by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onClose,
        title = {

            Text(
                text = "🔐  ADMIN LOGIN",
                fontSize = 23.sp,
                fontWeight = FontWeight.ExtraBold,
                color = SmartRed
            )
        },
        text = {

            Column {

                OutlinedTextField(
                    value = adminId,
                    onValueChange = {
                        adminId = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Admin ID")
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Password")
                    },
                    singleLine = true
                )

                if (loginMessage.isNotEmpty()) {

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = loginMessage,
                        color = SmartRed,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        confirmButton = {

            Button(
                onClick = {

                    if (adminId.isBlank() || password.isBlank()) {
                        loginMessage = "Admin ID और Password डालिए।"
                    } else {
                        loginMessage = "Admin details accepted. Admin dashboard तैयार किया जा सकता है।"
                    }

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SmartRed
                )
            ) {
                Text("LOGIN")
            }
        },
        dismissButton = {

            TextButton(
                onClick = onClose
            ) {
                Text(
                    text = "CLOSE",
                    color = SmartRed,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}

@Composable
fun SimpleDialog(
    title: String,
    message: String,
    buttonText: String,
    onClose: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onClose,

        title = {
            Text(
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = SmartBlue
            )
        },

        text = {
            Text(
                text = message,
                fontSize = 16.sp,
                color = SmartText
            )
        },

        confirmButton = {
            Button(
                onClick = onClose,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SmartBlue
                )
            ) {
                Text(
                    text = buttonText,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}
