package com.smartlibrary.maner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Red = Color(0xFFD71920)
private val Blue = Color(0xFF123A8C)
private val Green = Color(0xFF16A34A)
private val Purple = Color(0xFF7C3AED)
private val Orange = Color(0xFFF97316)
private val Pink = Color(0xFFDB2777)
private val Gold = Color(0xFFFFC107)
private val White = Color.White
private val TextDark = Color(0xFF172033)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartLibraryApp()
        }
    }
}

enum class Page {
    HOME,
    STUDENT_LOGIN,
    STUDENT_DASHBOARD,
    ADMIN_LOGIN,
    ADMIN_DASHBOARD,
    TEST,
    CURRENT_AFFAIRS,
    STUDY
}

@Composable
fun SmartLibraryApp() {
    var page by remember { mutableStateOf(Page.HOME) }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF7F9FC)
        ) {
            when (page) {
                Page.HOME -> Home(
                    student = { page = Page.STUDENT_LOGIN },
                    admin = { page = Page.ADMIN_LOGIN },
                    test = { page = Page.TEST },
                    current = { page = Page.CURRENT_AFFAIRS },
                    study = { page = Page.STUDY }
                )

                Page.STUDENT_LOGIN -> StudentLogin(
                    back = { page = Page.HOME },
                    login = { page = Page.STUDENT_DASHBOARD }
                )

                Page.STUDENT_DASHBOARD -> StudentDashboard(
                    back = { page = Page.HOME },
                    test = { page = Page.TEST },
                    current = { page = Page.CURRENT_AFFAIRS },
                    study = { page = Page.STUDY }
                )

                Page.ADMIN_LOGIN -> AdminLogin(
                    back = { page = Page.HOME },
                    login = { page = Page.ADMIN_DASHBOARD }
                )

                Page.ADMIN_DASHBOARD -> AdminDashboard(
                    back = { page = Page.HOME }
                )

                Page.TEST -> TestScreen(
                    back = { page = Page.HOME }
                )

                Page.CURRENT_AFFAIRS -> CurrentAffairs(
                    back = { page = Page.HOME }
                )

                Page.STUDY -> StudyMaterial(
                    back = { page = Page.HOME }
                )
            }
        }
    }
}

@Composable
fun ScreenColumn(content: @Composable ColumnScope.() -> Unit) {
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
fun Logo() {
    Card(
        modifier = Modifier.size(100.dp),
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(containerColor = Blue),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "SL",
                color = White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                "2021",
                color = Gold,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun Header(
    title: String,
    back: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (back != null) {
            TextButton(onClick = back) {
                Text(
                    "‹",
                    fontSize = 36.sp,
                    color = Blue,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                title,
                fontSize = 23.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Red
            )
            Text(
                "SMART LIBRARY MANER",
                fontSize = 11.sp,
                color = Blue,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun Home(
    student: () -> Unit,
    admin: () -> Unit,
    test: () -> Unit,
    current: () -> Unit,
    study: () -> Unit
) {
    ScreenColumn {

        Logo()

        Spacer(Modifier.height(12.dp))

        Text(
            "SMART LIBRARY",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Red
        )

        Text(
            "MANER • PATNA",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Blue
        )

        Spacer(Modifier.height(5.dp))

        Text(
            "पढ़ो आज, संवारो कल",
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            color = Blue
        )

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Red)
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "WELCOME TO SMART LIBRARY",
                    color = White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "Study • Practice • Test • Achieve",
                    color = White,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SmallCard(
                modifier = Modifier.weight(1f),
                title = "🎓 STUDENT",
                subtitle = "Study & Tests",
                color = Blue,
                click = student
            )

            SmallCard(
                modifier = Modifier.weight(1f),
                title = "🔐 ADMIN",
                subtitle = "Library Management",
                color = Red,
                click = admin
            )
        }

        Spacer(Modifier.height(12.dp))

        MainButton(
            "👤  STUDENT LOGIN",
            Blue,
            student
        )

        Spacer(Modifier.height(10.dp))

        OutlineButton(
            "🔐  ADMIN LOGIN",
            Red,
            admin
        )

        Spacer(Modifier.height(16.dp))

        Service(
            title = "📝  ONLINE TEST",
            subtitle = "Bihar Police • Daroga • BPSC • SSC • Railway • SSC GD",
            button = "START TEST",
            color = Blue,
            click = test
        )

        Spacer(Modifier.height(12.dp))

        Service(
            title = "📰  DAILY CURRENT AFFAIRS",
            subtitle = "Daily MCQ • Important Questions • Answers • Explanation",
            button = "VIEW CURRENT AFFAIRS",
            color = Red,
            click = current
        )

        Spacer(Modifier.height(12.dp))

        Service(
            title = "📚  STUDY MATERIAL",
            subtitle = "Notes • PDF • Syllabus • Previous Year Questions",
            button = "OPEN STUDY MATERIAL",
            color = Blue,
            click = study
        )

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = White),
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    "SMART LIBRARY FACILITIES",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Red
                )

                Facility("✓ Silent & Educational Environment")
                Facility("✓ Free Wi-Fi")
                Facility("✓ Locker Facility")
                Facility("✓ R.O. Water")
                Facility("✓ Separate Sitting Available")
                Facility("✓ News Paper & Magazines")
                Facility("✓ 24×7 Open")
            }
        }

        Spacer(Modifier.height(18.dp))

        Text(
            "DISCIPLINE TODAY • SUCCESS TOMORROW",
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Red,
            textAlign = TextAlign.Center
        )

        Text(
            "Smart Library Maner • Patna, Bihar",
            fontSize = 13.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun SmallCard(
    modifier: Modifier,
    title: String,
    subtitle: String,
    color: Color,
    click: () -> Unit
) {
    Card(
        modifier = modifier.clickable { click() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                title,
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                subtitle,
                color = White,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun MainButton(
    text: String,
    color: Color,
    click: () -> Unit
) {
    Button(
        onClick = click,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        )
    ) {
        Text(
            text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun OutlineButton(
    text: String,
    color: Color,
    click: () -> Unit
) {
    OutlinedButton(
        onClick = click,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(2.dp, color)
    ) {
        Text(
            text,
            color = color,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Service(
    title: String,
    subtitle: String,
    button: String,
    color: Color,
    click: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        border = BorderStroke(2.dp, color),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(Modifier.padding(16.dp)) {

            Text(
                title,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = color
            )

            Spacer(Modifier.height(8.dp))

            Text(
                subtitle,
                fontSize = 14.sp,
                color = TextDark,
                lineHeight = 22.sp
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = click,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = color
                )
            ) {
                Text(
                    button,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun Facility(text: String) {
    Text(
        text,
        modifier = Modifier.padding(vertical = 5.dp),
        fontSize = 15.sp,
        color = TextDark
    )
}

@Composable
fun StudentLogin(
    back: () -> Unit,
    login: () -> Unit
) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    ScreenColumn {

        Header("Student Login", back)

        Logo()

        Spacer(Modifier.height(12.dp))

        Text(
            "Student Login",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Blue
        )

        Text("अपने Account से Login करें")

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = id,
            onValueChange = { id = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Admission No. / Mobile No.")
            },
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Password")
            },
            singleLine = true
        )

        Spacer(Modifier.height(18.dp))

        MainButton(
            "LOGIN",
            Blue,
            login
        )

        Spacer(Modifier.height(8.dp))

        TextButton(onClick = {}) {
            Text(
                "Forgot Password?",
                color = Blue
            )
        }

        OutlineButton(
            "👤  NEW STUDENT REGISTRATION",
            Blue
        ) {}
    }
}

@Composable
fun AdminLogin(
    back: () -> Unit,
    login: () -> Unit
) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    ScreenColumn {

        Header("Admin Login", back)

        Spacer(Modifier.height(20.dp))

        Text(
            "🔐  ADMIN LOGIN",
            fontSize = 27.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Red
        )

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = id,
            onValueChange = { id = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Admin ID")
            },
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Password")
            },
            singleLine = true
        )

        Spacer(Modifier.height(18.dp))

        MainButton(
            "OPEN ADMIN MANAGEMENT",
            Red,
            login
        )

        Spacer(Modifier.height(20.dp))

        Text(
            "Students, Admission, Payment, Tests और Library Management यहाँ से किया जा सकेगा।",
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun StudentDashboard(
    back: () -> Unit,
    test: () -> Unit,
    current: () -> Unit,
    study: () -> Unit
) {
    ScreenColumn {

        Header("Student Dashboard", back)

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = White),
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            Column(Modifier.padding(16.dp)) {

                Text(
                    "🎓  STUDENT PROFILE",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Blue
                )

                Spacer(Modifier.height(8.dp))

                Text("Name: Student")
                Text("Admission No.: SL001")
                Text("Shift: Morning")
                Text("Locker: L-12")
            }
        }

        Spacer(Modifier.height(12.dp))

        Tile("📝", "Online Test", Blue, test)
        Tile("📰", "Current Affairs", Red, current)
        Tile("📚", "Study Material", Green, study)
        Tile("📊", "My Result", Orange) {}
        Tile("🔔", "Library Notice", Purple) {}
        Tile("💰", "Fees & Payment", Pink) {}
        Tile("🔒", "Locker Info", Orange) {}
        Tile("👤", "Profile", Blue) {}
    }
}

@Composable
fun AdminDashboard(back: () -> Unit) {
    ScreenColumn {

        Header("Admin • Library Management", back)

        Text(
            "🛠️  ADMIN • LIBRARY MANAGEMENT",
            fontSize = 23.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Blue,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        Tile("👨‍🎓", "Students Management", Blue) {}
        Tile("📝", "Admission Management", Red) {}
        Tile("💳", "Payment & Due Management", Green) {}
        Tile("🧪", "Test Management", Purple) {}
        Tile("📚", "Study Material Management", Orange) {}
        Tile("📰", "Current Affairs Management", Pink) {}
        Tile("🔐", "Locker Management", Blue) {}
        Tile("📢", "Library Notice", Red) {}
    }
}

@Composable
fun Tile(
    icon: String,
    title: String,
    color: Color,
    click: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { click() },
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
                icon,
                fontSize = 28.sp
            )

            Spacer(Modifier.width(14.dp))

            Text(
                title,
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TestScreen(back: () -> Unit) {
    ScreenColumn {

        Header("Online Test", back)

        Text(
            "ONLINE TEST",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Red
        )

        Text("अपनी परीक्षा चुनें")

        Spacer(Modifier.height(16.dp))

        val tests = listOf(
            "👮 Bihar Police Practice Test",
            "🛡️ Bihar Daroga Test",
            "📋 BPSC Test",
            "🟢 SSC GD Test",
            "🚆 Railway Test",
            "🌐 General Awareness Test",
            "📚 Current Affairs Quiz"
        )

        tests.forEach { test ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .clickable {},
                colors = CardDefaults.cardColors(
                    containerColor = White
                ),
                border = BorderStroke(1.dp, Blue)
            ) {
                Row(
                    modifier = Modifier.padding(17.dp),
                 
