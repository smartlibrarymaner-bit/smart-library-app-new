package com.smartlibrary.maner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

/* ================= COLORS ================= */

private val SmartRed = Color(0xFFD71920)
private val SmartBlue = Color(0xFF123A8C)
private val SmartDarkBlue = Color(0xFF08265F)
private val SmartLightBlue = Color(0xFFEAF2FF)
private val SmartLightRed = Color(0xFFFFEEEE)
private val SmartGold = Color(0xFFFFC107)
private val SmartText = Color(0xFF172033)
private val SmartWhite = Color.White

/* ================= APP ================= */

@Composable
fun SmartLibraryApp() {

    var screen by remember { mutableStateOf("HOME") }

    var showStudentLogin by remember { mutableStateOf(false) }
    var showAdminLogin by remember { mutableStateOf(false) }

    var studentId by remember { mutableStateOf("") }
    var studentPassword by remember { mutableStateOf("") }

    var adminId by remember { mutableStateOf("") }
    var adminPassword by remember { mutableStateOf("") }

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF7F9FC)
        ) {

            when (screen) {

                "HOME" -> HomeScreen(
                    onStudentLogin = {
                        showStudentLogin = true
                    },
                    onAdminLogin = {
                        showAdminLogin = true
                    },
                    onStudent = {
                        screen = "STUDENT"
                    },
                    onAdmin = {
                        screen = "ADMIN"
                    },
                    onTest = {
                        screen = "TEST"
                    },
                    onCurrentAffairs = {
                        screen = "CURRENT"
                    },
                    onStudyMaterial = {
                        screen = "STUDY"
                    }
                )

                "STUDENT" -> StudentDashboard(
                    onBack = {
                        screen = "HOME"
                    },
                    onTest = {
                        screen = "TEST"
                    },
                    onCurrent = {
                        screen = "CURRENT"
                    },
                    onStudy = {
                        screen = "STUDY"
                    }
                )

                "ADMIN" -> AdminDashboard(
                    onBack = {
                        screen = "HOME"
                    }
                )

                "TEST" -> TestScreen(
                    onBack = {
                        screen = "HOME"
                    }
                )

                "CURRENT" -> CurrentAffairsScreen(
                    onBack = {
                        screen = "HOME"
                    }
                )

                "STUDY" -> StudyMaterialScreen(
                    onBack = {
                        screen = "HOME"
                    }
                )
            }
        }

        /* ================= STUDENT LOGIN ================= */

        if (showStudentLogin) {

            AlertDialog(
                onDismissRequest = {
                    showStudentLogin = false
                },

                title = {
                    Text(
                        text = "👤 STUDENT LOGIN",
                        fontSize = 22.sp,
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

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        OutlinedTextField(
                            value = studentPassword,
                            onValueChange = {
                                studentPassword = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text("Password")
                            },
                            singleLine = true
                        )
                    }
                },

                confirmButton = {

                    Button(
                        onClick = {

                            showStudentLogin = false
                            screen = "STUDENT"

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
                        onClick = {
                            showStudentLogin = false
                        }
                    ) {
                        Text(
                            "CLOSE",
                            color = SmartRed
                        )
                    }
                }
            )
        }

        /* ================= ADMIN LOGIN ================= */

        if (showAdminLogin) {

            AlertDialog(
                onDismissRequest = {
                    showAdminLogin = false
                },

                title = {
                    Text(
                        text = "🔐 ADMIN LOGIN",
                        fontSize = 22.sp,
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

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        OutlinedTextField(
                            value = adminPassword,
                            onValueChange = {
                                adminPassword = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text("Password")
                            },
                            singleLine = true
                        )
                    }
                },

                confirmButton = {

                    Button(
                        onClick = {

                            showAdminLogin = false
                            screen = "ADMIN"

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
                        onClick = {
                            showAdminLogin = false
                        }
                    ) {
                        Text(
                            "CLOSE",
                            color = SmartBlue
                        )
                    }
                }
            )
        }
    }
}

/* ================= HOME SCREEN ================= */

@Composable
fun HomeScreen(
    onStudentLogin: () -> Unit,
    onAdminLogin: () -> Unit,
    onStudent: () -> Unit,
    onAdmin: () -> Unit,
    onTest: () -> Unit,
    onCurrentAffairs: () -> Unit,
    onStudyMaterial: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(92.dp)
                .background(
                    SmartBlue,
                    RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
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

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "SMART LIBRARY",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            color = SmartRed
        )

        Text(
            text = "MANER • PATNA",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = SmartBlue
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "पढ़ो आज, संवारो कल",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = SmartBlue
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        ServiceCard(
            title = "WELCOME TO SMART LIBRARY",
            subtitle = "Study • Practice • Test • Achieve",
            buttonText = "OPEN STUDENT AREA",
            color = SmartRed,
            onClick = onStudent
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            FeatureCard(
                modifier = Modifier.weight(1f),
                title = "STUDENT",
                subtitle = "Study & Tests",
                color = SmartBlue,
                onClick = onStudent
            )

            FeatureCard(
                modifier = Modifier.weight(1f),
                title = "ADMIN",
                subtitle = "Library Management",
                color = SmartRed,
                onClick = onAdmin
            )
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Button(
            onClick = onStudentLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(14.dp),
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

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        OutlinedButton(
            onClick = onAdminLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(14.dp)
        ) {

            Text(
                text = "🔐  ADMIN LOGIN",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = SmartRed
            )
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        ServiceCard(
            title = "📝 ONLINE TEST",
            subtitle = "Bihar Police • Daroga • BPSC • SSC • Railway • SSC GD",
            buttonText = "START TEST",
            color = SmartBlue,
            onClick = onTest
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        ServiceCard(
            title = "📰 DAILY CURRENT AFFAIRS",
            subtitle = "Daily MCQ • Important Questions • Answers • Explanation",
            buttonText = "VIEW CURRENT AFFAIRS",
            color = SmartRed,
            onClick = onCurrentAffairs
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        ServiceCard(
            title = "📚 STUDY MATERIAL",
            subtitle = "Notes • PDF • Syllabus • Previous Year Questions",
            buttonText = "OPEN STUDY MATERIAL",
            color = SmartBlue,
            onClick = onStudyMaterial
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = SmartWhite
            ),
            shape = RoundedCornerShape(20.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "SMART LIBRARY FACILITIES",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = SmartRed
                )

                FacilityText("✓ Silent & Educational Environment")
                FacilityText("✓ Free Wi-Fi")
                FacilityText("✓ Locker Facility")
                FacilityText("✓ R.O. Water")
                FacilityText("✓ Separate Sitting Available")
                FacilityText("✓ News Paper & Magazines")
                FacilityText("✓ 24×7 Open")
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "DISCIPLINE TODAY • SUCCESS TOMORROW",
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold,
            color = SmartRed,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Smart Library Maner • Patna, Bihar",
            fontSize = 13.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )
    }
}

/* ================= STUDENT DASHBOARD ================= */

@Composable
fun StudentDashboard(
    onBack: () -> Unit,
    onTest: () -> Unit,
    onCurrent: () -> Unit,
    onStudy: () -> Unit
) {

    AppPage(
        title = "👨‍🎓 STUDENT DASHBOARD",
        onBack = onBack
    ) {

        DashboardCard(
            "📝 ONLINE TEST",
            "Practice Bihar Police, Daroga, BPSC, SSC, Railway & SSC GD",
            SmartBlue,
            onTest
        )

        DashboardCard(
            "📰 CURRENT AFFAIRS",
            "Daily MCQ, important questions and explanations",
            SmartRed,
            onCurrent
        )

        DashboardCard(
            "📚 STUDY MATERIAL",
            "Notes, syllabus and previous year questions",
            SmartBlue,
            onStudy
        )

        DashboardCard(
            "📊 MY PROGRESS",
            "Test result and preparation progress",
            SmartRed
        )
    }
}

/* ================= ADMIN DASHBOARD ================= */

@Composable
fun AdminDashboard(
    onBack: () -> Unit
) {

    AppPage(
        title = "🛠️ ADMIN • LIBRARY MANAGEMENT",
        onBack = onBack
    ) {

        DashboardCard(
            "👨‍🎓 STUDENT MANAGEMENT",
            "Students admission, student records and profile",
            SmartBlue
        )

        DashboardCard(
            "📝 ADMISSION",
            "New admission and admission records",
            SmartRed
        )

        DashboardCard(
            "💳 PAYMENT MANAGEMENT",
            "Paid, due, payment date and payment records",
            SmartBlue
        )

        DashboardCard(
            "🔐 LOGIN MANAGEMENT",
            "Student ID, Admin ID and account management",
            SmartRed
        )

        DashboardCard(
            "📝 TEST MANAGEMENT",
            "Create tests, questions, answers and results",
            SmartBlue
        )

        DashboardCard(
            "📚 LIBRARY MANAGEMENT",
            "Library facilities and student management",
            SmartRed
        )

        DashboardCard(
            "📊 REPORTS",
            "Admission, payment and test reports",
            SmartBlue
        )
    }
}

/* ================= TEST ================= */

@Composable
fun TestScreen(
    onBack: () -> Unit
) {

    AppPage(
        title = "📝 ONLINE TEST",
        onBack = onBack
    ) {

        DashboardCard(
            "BIHAR POLICE",
            "Practice questions",
            SmartBlue
        )

        DashboardCard(
            "BIHAR DAROGA",
            "Practice questions",
            SmartRed
        )

        DashboardCard(
            "BPSC",
            "Practice questions",
            SmartBlue
        )

        DashboardCard(
            "SSC / SSC GD",
            "Practice questions",
            SmartRed
        )

        DashboardCard(
            "RAILWAY",
            "Practice questions",
            SmartBlue
        )
    }
}

/* ================= CURRENT AFFAIRS ================= */

@Composable
fun CurrentAffairsScreen(
    onBack: () -> Unit
) {

    AppPage(
        title = "📰 DAILY CURRENT 
