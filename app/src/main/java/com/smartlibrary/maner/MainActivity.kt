package com.smartlibrary.maner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
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

private val SmartRed = Color(0xFFD71920)
private val SmartBlue = Color(0xFF123A8C)
private val SmartGreen = Color(0xFF16A34A)
private val SmartPurple = Color(0xFF7C3AED)
private val SmartOrange = Color(0xFFF97316)
private val SmartPink = Color(0xFFDB2777)
private val SmartGold = Color(0xFFFFC107)
private val SmartWhite = Color.White
private val SmartBackground = Color(0xFFF7F9FC)
private val SmartText = Color(0xFF172033)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                SmartLibraryApp()
            }
        }
    }
}

enum class Screen {
    HOME,
    STUDENT_LOGIN,
    STUDENT_DASHBOARD,
    ADMIN_LOGIN,
    ADMIN_DASHBOARD,
    ONLINE_TEST,
    CURRENT_AFFAIRS,
    STUDY_MATERIAL
}

@Composable
fun SmartLibraryApp() {

    var screen by remember {
        mutableStateOf(Screen.HOME)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = SmartBackground
    ) {

        when (screen) {

            Screen.HOME -> HomeScreen(
                onStudentLogin = {
                    screen = Screen.STUDENT_LOGIN
                },
                onAdminLogin = {
                    screen = Screen.ADMIN_LOGIN
                },
                onTest = {
                    screen = Screen.ONLINE_TEST
                },
                onCurrentAffairs = {
                    screen = Screen.CURRENT_AFFAIRS
                },
                onStudyMaterial = {
                    screen = Screen.STUDY_MATERIAL
                }
            )

            Screen.STUDENT_LOGIN -> StudentLoginScreen(
                onBack = {
                    screen = Screen.HOME
                },
                onLogin = {
                    screen = Screen.STUDENT_DASHBOARD
                }
            )

            Screen.STUDENT_DASHBOARD -> StudentDashboardScreen(
                onBack = {
                    screen = Screen.HOME
                },
                onTest = {
                    screen = Screen.ONLINE_TEST
                },
                onCurrentAffairs = {
                    screen = Screen.CURRENT_AFFAIRS
                },
                onStudyMaterial = {
                    screen = Screen.STUDY_MATERIAL
                }
            )

            Screen.ADMIN_LOGIN -> AdminLoginScreen(
                onBack = {
                    screen = Screen.HOME
                },
                onLogin = {
                    screen = Screen.ADMIN_DASHBOARD
                }
            )

            Screen.ADMIN_DASHBOARD -> AdminDashboardScreen(
                onBack = {
                    screen = Screen.HOME
                }
            )

            Screen.ONLINE_TEST -> OnlineTestScreen(
                onBack = {
                    screen = Screen.HOME
                }
            )

            Screen.CURRENT_AFFAIRS -> CurrentAffairsScreen(
                onBack = {
                    screen = Screen.HOME
                }
            )

            Screen.STUDY_MATERIAL -> StudyMaterialScreen(
                onBack = {
                    screen = Screen.HOME
                }
            )
        }
    }
}

@Composable
fun PageColumn(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}

@Composable
fun SmartLogo() {

    Card(
        modifier = Modifier.size(105.dp),
        shape = RoundedCornerShape(60.dp),
        colors = CardDefaults.cardColors(
            containerColor = SmartBlue
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "SL",
                color = SmartWhite,
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = "2021",
                color = SmartGold,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TopHeader(
    title: String,
    onBack: (() -> Unit)? = null
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (onBack != null) {

            TextButton(
                onClick = onBack
            ) {

                Text(
                    text = "‹",
                    color = SmartBlue,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = title,
                color = SmartRed,
                fontSize = 23.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = "SMART LIBRARY • MANER",
                color = SmartBlue,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun HomeScreen(
    onStudentLogin: () -> Unit,
    onAdminLogin: () -> Unit,
    onTest: () -> Unit,
    onCurrentAffairs: () -> Unit,
    onStudyMaterial: () -> Unit
) {

    PageColumn {

        SmartLogo()

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "SMART LIBRARY",
            color = SmartRed,
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = "MANER • PATNA",
            color = SmartBlue,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Text(
            text = "पढ़ो आज, संवारो कल",
            color = SmartBlue,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        WelcomeCard()

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            HomeMiniCard(
                modifier = Modifier.weight(1f),
                title = "🎓 STUDENT",
                subtitle = "Study & Test",
                color = SmartBlue,
                onClick = onStudentLogin
            )

            HomeMiniCard(
                modifier = Modifier.weight(1f),
                title = "🔐 ADMIN",
                subtitle = "Management",
                color = SmartRed,
                onClick = onAdminLogin
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        PrimaryButton(
            text = "👤  STUDENT LOGIN",
            color = SmartBlue,
            onClick = onStudentLogin
        )

        Spacer(
            modifier = Modifier.height(9.dp)
        )

        OutlineActionButton(
            text = "🔐  ADMIN LOGIN",
            color = SmartRed,
            onClick = onAdminLogin
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        ServiceCard(
            title = "📝  ONLINE TEST",
            subtitle = "Bihar Police • Daroga • BPSC • SSC • Railway • SSC GD",
            buttonText = "START TEST",
            color = SmartBlue,
            onClick = onTest
        )

        Spacer(
            modifier = Modifier.height(11.dp)
        )

        ServiceCard(
            title = "📰  DAILY CURRENT AFFAIRS",
            subtitle = "Daily MCQ • Important Questions • Answers • Explanation",
            buttonText = "VIEW CURRENT AFFAIRS",
            color = SmartRed,
            onClick = onCurrentAffairs
        )

        Spacer(
            modifier = Modifier.height(11.dp)
        )

        ServiceCard(
            title = "📚  STUDY MATERIAL",
            subtitle = "Notes • Syllabus • Previous Year Questions • Study Content",
            buttonText = "OPEN STUDY MATERIAL",
            color = SmartGreen,
            onClick = onStudyMaterial
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        FacilitiesCard()

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "DISCIPLINE TODAY • SUCCESS TOMORROW",
            color = SmartRed,
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Text(
            text = "Smart Library Maner • Patna, Bihar",
            color = Color.Gray,
            fontSize = 13.sp
        )
    }
}

@Composable
fun WelcomeCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
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
                color = SmartWhite,
                fontSize = 21.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(7.dp)
            )

            Text(
                text = "Study • Practice • Test • Achieve",
                color = SmartWhite,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun HomeMiniCard(
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
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {

        Column(
            modifier = Modifier.padding(15.dp)
        ) {

            Text(
                text = title,
                color = SmartWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = subtitle,
                color = SmartWhite,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun PrimaryButton(
    text: String,
    color: Color,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        )
    ) {

        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun OutlineActionButton(
    text: String,
    color: Color,
    onClick: () -> Unit
) {

    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(
            width = 2.dp,
            color = color
        )
    ) {

        Text(
            text = text,
            color = color,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
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
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = SmartWhite
        ),
        border = BorderStroke(
            width = 2.dp,
            color = color
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                color = color,
                fontSize = 19.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(
                modifier = Modifier.height(7.dp)
            )

            Text(
                text = subtitle,
                color = SmartText,
                fontSize = 14.sp
            )

            Spacer(
                modifier = Modifier.height(11.dp)
            )

            Button(
                onClick = onClick,
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
fun FacilitiesCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = SmartWhite
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "SMART LIBRARY FACILITIES",
                color = SmartRed,
                fontSize = 19.sp,
                fontWeight = FontWeight.ExtraBold
            )

            FacilityItem("✓ Silent & Educational Environment")
            FacilityItem("✓ Free Wi-Fi")
            FacilityItem("✓ Locker Facility")
            FacilityItem("✓ R.O. Water")
            FacilityItem("✓ Separate Sitting Available")
            FacilityItem("✓ News Paper & Magazines")
            FacilityItem("✓ 24×7 Open")
        }
    }
}

@Composable
fun FacilityItem(
    text: String
) {

    Text(
        text = text,
        modifier = Modifier.padding(
            vertical = 4.dp
        ),
        color = SmartText,
        fontSize = 15.sp
    )
}

@Composable
fun StudentLoginScreen(
    onBack: () -> Unit,
    onLogin: () -> Unit
) {

    var admission by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    PageColumn {

        TopHeader(
            title = "Student Login",
            onBack = onBack
        )

        SmartLogo()

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "🎓 STUDENT LOGIN",
            color = SmartBlue,
            fontSize = 25.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = "अपने Account से Login करें",
            color = SmartText
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        OutlinedTextField(
            value = admission,
            onValueChange = {
                admission = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Admission No. / Mobile No.")
            },
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

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

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        PrimaryButton(
            text = "LOGIN",
            color = SmartBlue,
            onClick = onLogin
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        TextButton(
            onClick = {}
        ) {

            Text(
                text = "Forgot Password?",
                color = SmartBlue
            )
        }

        OutlineActionButton(
            text = "👤  NEW STUDENT REGISTRATION",
            color = SmartBlue,
            onClick = {}
        )
    }
}

@Composable
fun AdminLoginScreen(
    onBack: () -> Unit,
    onLogin: () -> Unit
) {

    var adminId by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    PageColumn {

        TopHeader(
            title = "Admin Login",
            onBack = onBack
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        Text(
            text = "🔐 ADMIN LOGIN",
            color = SmartRed,
            fontSize = 27.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

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

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        PrimaryButton(
            text = "OPEN ADMIN MANAGEMENT",
            color = SmartRed,
            onClick = onLogin
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Students • Admission • Payment • Tests • Library Management",
            color = SmartText,
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )
    }
}

@Composable
fun StudentDashboardScreen(
    onBack: () -> Unit,
    onTest: () -> Unit,
    onCurrentAffairs: () -> Unit,
    onStudyMaterial: () -> Unit
) {

    PageColumn {

        TopHeader(
            title = "Student Dashboard",
            onBack = onBack
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = SmartWhite
            ),
            border = BorderStroke(
                1.dp,
                Color.LightGray
            )
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "🎓 STUDENT PROFILE",
                    color = SmartBlue,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text("Name: Student")
                Text("Admission No.: SL001")
                Text("Shift: Morning")
                Text("Locker: L-12")
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        DashboardTile(
            icon = "📝",
            title = "Online Test",
            color = SmartBlue,
            onClick = onTest
        )

        DashboardTile(
            icon = "📰",
            title = "Current Affairs",
            color = SmartRed,
            onClick = onCurrentAffairs
        )

        DashboardTile(
            icon = "📚",
            title = "Study Material",
            color = SmartGreen,
            onClick = onStudyMaterial
        )

        DashboardTile(
            icon = "📊",
            title = "My Result",
            color = SmartOrange,
            onClick = {}
        )

        DashboardTile(
            icon = "🔔",
            title = "Library Notice",
            color = SmartPurple,
            onClick = {}
        )

        DashboardTile(
            icon = "💰",
            title = "Fees & Payment",
            color = SmartPink,
            onClick = {}
        )

        DashboardTile(
            icon = "🔒",
            title = "Locker Info",
            color = SmartOrange,
            onClick = {}
        )

        DashboardTile(
            icon = "👤",
            title = "Profile",
            color = SmartBlue,
            onClick = {}
        )
    }
}

@Composable
fun AdminDashboardScreen(
    onBack: () -> Unit
) {

    PageColumn {

        TopHeader(
            title = "Admin Management",
            onBack = onBack
        )

        Text(
            text = "🛠️ ADMIN • LIBRARY MANAGEMENT",
            color = SmartBlue,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        DashboardTile(
            "👨‍🎓",
            "Students Management",
            SmartBlue
        ) {}

        DashboardTile(
            "📝",
            "Admission Management",
            SmartRed
        ) {}

        DashboardTile(
            "💳",
            "Payment & Due Management",
            SmartGreen
        ) {}

        DashboardTile(
            "🧪",
            "Test Management",
            SmartPurple
        ) {}

        DashboardTile(
            "📚",
            "Study Material Management",
            SmartOrange
        ) {}

        DashboardTile(
            "📰",
            "Current Affairs Management",
            SmartPink
        ) {}

        DashboardTile(
            "🔐",
            "Locker Management",
            SmartBlue
        ) {}

        DashboardTile(
            "📢",
            "Library Notice",
            SmartRed
        ) {}
    }
}

@Composable
fun DashboardTile(
    icon: String,
    title: String,
    color: Color,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {

        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = icon,
                fontSize = 28.sp
            )

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Text(
                text = title,
                color = SmartWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun OnlineTestScreen(
    onBack: () -> Unit
) {

    PageColumn {

        TopHeader(
            title = "Online Test",
            onBack = onBack
        )

        Text(
            text = "📝 ONLINE TEST",
            color = SmartRed,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = "अपनी परीक्षा चुनें"
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        TestItem("👮 Bihar Police Practice Test")
        TestItem("🛡️ Bihar Daroga Test")
        TestItem("📋 BPSC Practice Test")
        TestItem("🟢 SSC GD Practice Test")
        TestItem("🚆 Railway Practice Test")
        TestItem("🌐 General Awareness Test")
        TestItem("📚 Current Affairs Quiz")

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Text(
            text = "Test Questions और Result System आगे जोड़ा जा सकता है।",
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TestItem(
    title: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable {},
        colors = CardDefaults.cardColors(
            containerColor = SmartWhite
        ),
        border = BorderStroke(
            1.dp,
            SmartBlue
        )
    ) {

        Row(
            modifier = Modifier.padding(17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = title,
                color = SmartText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CurrentAffairsScreen(
    onBack: () -> Unit
) {

    PageColumn {

        TopHeader(
            title = "Current Affairs",
            onBack = onBack
        )

        Text(
            text = "📰 DAILY CURRENT AFFAIRS",
            color = SmartRed,
            fontSize = 25.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        InfoCard(
            title = "आज के महत्वपूर्ण प्रश्न",
            text = "यहाँ Daily Current Affairs के महत्वपूर्ण MCQ, Answer और Explanation दिखाए जा सकते हैं।",
            color = SmartRed
        )

        InfoCard(
            title = "Bihar Current Affairs",
            text = "Bihar Police, Bihar Daroga और BPSC की तैयारी के लिए महत्वपूर्ण विषय।",
            color = SmartBlue
        )

        InfoCard(
            title = "National Current Affairs",
            text = "देश से जुड़े महत्वपूर्ण समाचार और परीक्षा उपयोगी प्रश्न।",
            color = SmartGreen
        )

        InfoCard(
            title = "International Current Affairs",
            text = "विश्व से जुड़े महत्वपूर्ण घटनाक्रम और परीक्षा प्रश्न।",
            color = SmartPurple
        )
    }
}

@Composable
fun StudyMaterialScreen(
    onBack: () -> Unit
) {

    PageColumn {

        TopHeader(
            title = "Study Material",
            onBack = onBack
        )

        Text(
            text = "📚 STUDY MATERIAL",
            color = SmartBlue,
            fontSize = 27.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        InfoCard(
            title = "📖 Notes",
            text = "Exam preparation के लिए subject-wise notes यहाँ उपलब्ध किए जा सकते हैं।",
            color = SmartBlue
        )

        InfoCard(
            title = "📋 Syllabus",
            text = "Bihar Police, Daroga, BPSC, SSC, Railway और अन्य exams का syllabus।",
            color = SmartRed
        )

        InfoCard(
            title = "📝 Previous Year Questions",
            text = "Previous Year Questions और Practice Questions का section।",
            color = SmartGreen
        )

        InfoCard(
            title = "📄 PDF Study Material",
            text = "Study PDF और educational material यहाँ बाद में जोड़े जा सकते हैं।",
            color = SmartOrange
        )
    }
}

@Composable
fun InfoCard(
    title: String,
    text: String,
    color: Color
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = SmartWhite
        ),
        border = BorderStroke(
            2.dp,
            color
        )
    ) {

        Column(
            modifier = Modifier.padding(17.dp)
        ) {

            Text(
                text = title,
                color = color,
                fontSize = 19.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(
                modifier = Modifier.height(7.dp)
            )

            Text(
                text = text,
                color = SmartText,
                fontSize = 14.sp
            )
        }
    }
}
