package com.smartlibrary.maner

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private val SmartRed = Color(0xFFD71920)
private val SmartBlue = Color(0xFF123A8C)
private val SmartGreen = Color(0xFF16A34A)
private val SmartPurple = Color(0xFF7C3AED)
private val SmartOrange = Color(0xFFF97316)
private val SmartPink = Color(0xFFDB2777)
private val SmartGold = Color(0xFFFFC107)
private val SmartBackground = Color(0xFFF7F9FC)
private val SmartText = Color(0xFF172033)

private val auth by lazy { FirebaseAuth.getInstance() }
private val db by lazy { FirebaseFirestore.getInstance() }

data class Student(
    val uid: String = "",
    val name: String = "",
    val admissionNo: String = "",
    val father: String = "",
    val mother: String = "",
    val mobile: String = "",
    val address: String = "",
    val shift: String = "",
    val locker: String = "Not Assigned",
    val paid: String = "0",
    val due: String = "0"
)

enum class Screen {
    HOME,
    LOGIN,
    REGISTER,
    DASHBOARD,
    ADMIN_LOGIN,
    ADMIN,
    TEST,
    CURRENT,
    MATERIAL
}

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

fun authEmail(admission: String): String {
    return "student." +
            admission.trim()
                .lowercase()
                .replace(Regex("[^a-z0-9._-]"), "_") +
            "@smartlibrarymaner.app"
}

@Composable
fun SmartLibraryApp() {

    var screen by remember {
        mutableStateOf(Screen.HOME)
    }

    var student by remember {
        mutableStateOf<Student?>(null)
    }

    val context = LocalContext.current

    fun showMessage(message: String) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_LONG
        ).show()
    }

    fun loadStudent(uid: String) {

        if (uid.isBlank()) {
            showMessage("Student account नहीं मिला।")
            return
        }

        db.collection("students")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->

                if (!document.exists()) {
                    auth.signOut()
                    showMessage("Student profile नहीं मिला।")
                    return@addOnSuccessListener
                }

                student = Student(
                    uid = uid,
                    name = document.getString("name").orEmpty(),
                    admissionNo = document.getString("admissionNo").orEmpty(),
                    father = document.getString("fatherName").orEmpty(),
                    mother = document.getString("motherName").orEmpty(),
                    mobile = document.getString("mobile").orEmpty(),
                    address = document.getString("address").orEmpty(),
                    shift = document.getString("shift").orEmpty(),
                    locker = document.getString("locker")
                        .orEmpty()
                        .ifBlank { "Not Assigned" },
                    paid = document.getString("feesPaid")
                        .orEmpty()
                        .ifBlank { "0" },
                    due = document.getString("feesDue")
                        .orEmpty()
                        .ifBlank { "0" }
                )

                screen = Screen.DASHBOARD
            }
            .addOnFailureListener {
                auth.signOut()
                showMessage("Profile load नहीं हो सका।")
            }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = SmartBackground
    ) {

        when (screen) {

            Screen.HOME -> {
                HomeScreen(
                    onStudentLogin = {
                        screen = Screen.LOGIN
                    },
                    onAdminLogin = {
                        screen = Screen.ADMIN_LOGIN
                    },
                    onTest = {
                        screen = Screen.TEST
                    },
                    onCurrentAffairs = {
                        screen = Screen.CURRENT
                    },
                    onStudyMaterial = {
                        screen = Screen.MATERIAL
                    }
                )
            }

            Screen.LOGIN -> {
                StudentLoginScreen(
                    onBack = {
                        screen = Screen.HOME
                    },
                    onRegister = {
                        screen = Screen.REGISTER
                    },
                    onLogin = { id, password ->

                        if (id.isBlank() || password.isBlank()) {

                            showMessage(
                                "Admission/Mobile और Password भरें।"
                            )

                        } else {

                            findStudentEmail(
                                id = id,
                                onSuccess = { email ->

                                    auth.signInWithEmailAndPassword(
                                        email,
                                        password
                                    )
                                        .addOnSuccessListener { result ->

                                            loadStudent(
                                                result.user?.uid.orEmpty()
                                            )
                                        }
                                        .addOnFailureListener {

                                            showMessage(
                                                "Login failed. Details check करें।"
                                            )
                                        }
                                },
                                onNotFound = {

                                    showMessage(
                                        "Admission/Mobile नहीं मिला।"
                                    )
                                },
                                onError = {

                                    showMessage(
                                        "Internet connection check करें।"
                                    )
                                }
                            )
                        }
                    }
                )
            }

            Screen.REGISTER -> {
                StudentRegistrationScreen(
                    onBack = {
                        screen = Screen.LOGIN
                    },
                    onCreated = { uid ->
                        loadStudent(uid)
                    },
                    onMessage = {
                        showMessage(it)
                    }
                )
            }

            Screen.DASHBOARD -> {
                StudentDashboardScreen(
                    student = student,
                    onLogout = {
                        auth.signOut()
                        student = null
                        screen = Screen.HOME
                    },
                    onTest = {
                        screen = Screen.TEST
                    },
                    onCurrentAffairs = {
                        screen = Screen.CURRENT
                    },
                    onStudyMaterial = {
                        screen = Screen.MATERIAL
                    }
                )
            }

            Screen.ADMIN_LOGIN -> {
                AdminLoginScreen(
                    onBack = {
                        screen = Screen.HOME
                    },
                    onLogin = {
                        screen = Screen.ADMIN
                    }
                )
            }

            Screen.ADMIN -> {
                AdminDashboardScreen(
                    onBack = {
                        screen = Screen.HOME
                    }
                )
            }

            Screen.TEST -> {
                OnlineTestScreen {
                    screen = Screen.HOME
                }
            }

            Screen.CURRENT -> {
                CurrentAffairsScreen {
                    screen = Screen.HOME
                }
            }

            Screen.MATERIAL -> {
                StudyMaterialScreen {
                    screen = Screen.HOME
                }
            }
        }
    }
}

fun findStudentEmail(
    id: String,
    onSuccess: (String) -> Unit,
    onNotFound: () -> Unit,
    onError: () -> Unit
) {

    val value = id.trim().lowercase()

    db.collection("students")
        .whereEqualTo("admissionNo", value)
        .limit(1)
        .get()
        .addOnSuccessListener { result ->

            if (!result.isEmpty) {

                val email = result.documents[0]
                    .getString("authEmail")

                if (!email.isNullOrBlank()) {
                    onSuccess(email)
                } else {
                    onNotFound()
                }

            } else {

                db.collection("students")
                    .whereEqualTo("mobile", value)
                    .limit(1)
                    .get()
                    .addOnSuccessListener { mobileResult ->

                        if (!mobileResult.isEmpty) {

                            val email = mobileResult
                                .documents[0]
                                .getString("authEmail")

                            if (!email.isNullOrBlank()) {
                                onSuccess(email)
                            } else {
                                onNotFound()
                            }

                        } else {
                            onNotFound()
                        }
                    }
                    .addOnFailureListener {
                        onError()
                    }
            }
        }
        .addOnFailureListener {
            onError()
        }
}

@Composable
fun PageColumn(
    title: String,
    onBack: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (onBack != null) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

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

                Text(
                    text = title,
                    color = SmartRed,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

        } else if (title.isNotBlank()) {

            Text(
                text = title,
                color = SmartRed,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        content()
    }
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
                color = Color.White,
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
fun PrimaryButton(
    text: String,
    color: Color,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        )
    ) {

        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun OutlineButton(
    text: String,
    color: Color,
    onClick: () -> Unit
) {

    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(
            2.dp,
            color
        )
    ) {

        Text(
            text = text,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        label = {
            Text(label)
        },
        singleLine = true
    )
}

@Composable
fun HomeScreen(
    onStudentLogin: () -> Unit,
    onAdminLogin: () -> Unit,
    onTest: () -> Unit,
    onCurrentAffairs: () -> Unit,
    onStudyMaterial: () -> Unit
) {

    PageColumn(
        title = ""
    ) {

        SmartLogo()

        Spacer(
            modifier = Modifier.height(10.dp)
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

        Text(
            text = "पढ़ो आज, संवारो कल",
            color = SmartBlue,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

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
                    color = Color.White,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Study • Practice • Test • Achieve",
                    color = Color.White
                )
            }
        }

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            HomeMiniCard(
                title = "🎓 STUDENT",
                subtitle = "Study & Test",
                color = SmartBlue,
                modifier = Modifier.weight(1f),
                onClick = onStudentLogin
            )

            HomeMiniCard(
                title = "🔐 ADMIN",
                subtitle = "Management",
                color = SmartRed,
                modifier = Modifier.weight(1f),
                onClick = onAdminLogin
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        PrimaryButton(
            text = "👤 STUDENT LOGIN",
            color = SmartBlue,
            onClick = onStudentLogin
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlineButton(
            text = "🔐 ADMIN LOGIN",
            color = SmartRed,
            onClick = onAdminLogin
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        ServiceCard(
            title = "📝 ONLINE TEST",
            subtitle = "Bihar Police • Daroga • BPSC • SSC • Railway • SSC GD",
            buttonText = "START TEST",
            color = SmartBlue,
            onClick = onTest
        )

        ServiceCard(
            title = "📰 DAILY CURRENT AFFAIRS",
            subtitle = "Daily MCQ • Important Questions • Answers",
            buttonText = "VIEW CURRENT AFFAIRS",
            color = SmartRed,
            onClick = onCurrentAffairs
        )

        ServiceCard(
            title = "📚 STUDY MATERIAL",
            subtitle = "Notes • Syllabus • Previous Year Questions",
            buttonText = "OPEN STUDY MATERIAL",
            color = SmartGreen,
            onClick = onStudyMaterial
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "DISCIPLINE TODAY • SUCCESS TOMORROW",
            color = SmartRed,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = "Smart Library Maner • Patna, Bihar",
            color = Color.Gray,
            fontSize = 13.sp
        )
    }
}

@Composable
fun HomeMiniCard(
    title: String,
    subtitle: String,
    color: Color,
    modifier: Modifier,
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
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp
            )

            Text(
                text = subtitle,
                color = Color.White,
                fontSize = 13.sp
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
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(
            2.dp,
            color
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                color = color,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 19.sp
            )

            Text(
                text = subtitle,
                color = SmartText
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Button(
                onClick = onClick,
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
fun StudentLoginScreen(
    onBack: () -> Unit,
    onRegister: () -> Unit,
    onLogin: (String, String) -> Unit
) {

    var id by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    PageColumn(
        title = "Student Login",
        onBack = onBack
    ) {

        SmartLogo()

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "🎓 STUDENT LOGIN",
            color = SmartBlue,
            fontSize = 25.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        InputField(
            label = "Admission No. / Mobile No.",
            value = id,
            onValueChange = {
                id = it
            }
        )

        InputField(
            label = "Password",
            value = password,
            onValueChange = {
                password = it
            }
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        PrimaryButton(
            text = "LOGIN",
            color = SmartBlue
        ) {
            onLogin(
                id,
                password
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlineButton(
            text = "👤 NEW STUDENT REGISTRATION",
            color = SmartBlue,
            onClick = onRegister
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Firebase real login enabled",
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

@Composable
fun StudentRegistrationScreen(
    onBack: () -> Unit,
    onCreated: (String) -> Unit,
    onMessage: (String) -> Unit
) {

    var name by remember { mutableStateOf("") }
    var admission by remember { mutableStateOf("") }
    var father by remember { mutableStateOf("") }
    var mother by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var shift by remember { mutableStateOf("Morning") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var saving by remember { mutableStateOf(false) }

    PageColumn(
        title = "Student Registration",
        onBack = onBack
    ) {

        Text(
            text = "👤 NEW STUDENT ADMISSION",
            color = SmartBlue,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        InputField(
            "Student Name *",
            name
        ) {
            name = it
        }

        InputField(
            "Admission No. *",
            admission
        ) {
            admission = it
        }

        InputField(
            "Father Name",
            father
        ) {
            father = it
        }

        InputField(
            "Mother Name",
            mother
        ) {
            mother = it
        }

        InputField(
            "Mobile No. *",
            mobile
        ) {
            mobile = it
        }

        InputField(
            "Address",
            address
        ) {
            address = it
        }

        InputField(
            "Shift",
            shift
        ) {
            shift = it
        }

        InputField(
            "Password *",
            password
        ) {
            password = it
        }

        InputField(
            "Confirm Password *",
            confirmPassword
        ) {
            confirmPassword = it
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        PrimaryButton(
            text = if (saving) {
                "CREATING..."
            } else {
                "CREATE STUDENT ACCOUNT"
            },
            color = SmartBlue
        ) {

            if (!saving) {

                val admissionValue =
                    admission.trim().lowercase()

                val mobileValue =
                    mobile.trim()

                when {

                    name.isBlank() ||
                            admissionValue.isBlank() ||
                            mobileValue.isBlank() -> {

                        onMessage(
                            "Name, Admission No. और Mobile भरें।"
                        )
                    }

                    password.length < 6 -> {

                        onMessage(
                            "Password कम से कम 6 characters का रखें।"
                        )
                    }

                    password != confirmPassword -> {

                        onMessage(
                            "Password और Confirm Password अलग हैं।"
                        )
                    }

                    else -> {

                        saving = true

                        db.collection("students")
                            .whereEqualTo(
                                "admissionNo",
                                admissionValue
                            )
                            .limit(1)
                            .get()
                            .addOnSuccessListener { result ->

                                if (!result.isEmpty) {

                                    saving = false

                                    onMessage(
                                        "Admission No. पहले से registered है।"
                                    )

                                } else {

                                    db.collection("students")
                                        .whereEqualTo(
                                            "mobile",
                                            mobileValue
                                        )
                                        .limit(1)
                                        .get()
                                        .addOnSuccessListener { mobileResult ->

                                            if (!mobileResult.isEmpty) {

                                                saving = false

                                                onMessage(
                                                    "Mobile No. पहले से registered है।"
                                                )

                                            } else {

                                                val email =
                                                    authEmail(
                                                        admissionValue
                                                    )

                                                auth.createUserWithEmailAndPassword(
                                                    email,
                                                    password
                                                )
                                                    .addOnSuccessListener { result ->

                                                        val uid =
                                                            result.user
                                                                ?.uid
                                                                .orEmpty()

                                                        val data =
                                                            hashMapOf(
                                                                "uid" to uid,
                                                                "name" to name.trim(),
                                                                "admissionNo" to admissionValue,
                                                                "fatherName" to father.trim(),
                                                                "motherName" to mother.trim(),
                                                                "mobile" to mobileValue,
                                                                "address" to address.trim(),
                                                                "shift" to shift.trim(),
                                                                "locker" to "Not Assigned",
                                                                "feesPaid" to "0",
                                                                "feesDue" to "0",
                                                                "authEmail" to email,
                                                                "createdAt" to System.currentTimeMillis()
                                                            )

                                                        db.collection("students")
                                                            .document(uid)
                                                            .set(data)
                                                            .addOnSuccessListener {

                                                                saving = false

                                                                onCreated(uid)
                                                            }
                                                            .addOnFailureListener {

                                                                auth.currentUser
                                                                    ?.delete()

                                                                auth.signOut()

                                                                saving = false

                                                                onMessage(
                                                                    "Student profile save नहीं हो सका।"
                                                                )
                                                            }
                                                    }
                                                    .addOnFailureListener {

                                                        saving = false

                                                        onMessage(
                                                            "Account create नहीं हो सका। Admission No. या Password check करें।"
                                                        )
                                                    }
                                            }
                                        }
                                        .addOnFailureListener {

                                            saving = false

                                            onMessage(
                                                "Mobile check नहीं हो सका।"
                                            )
                                        }
                                }
                            }
                            .addOnFailureListener {

                                saving = false

                                onMessage(
                                    "Admission check नहीं हो सका।"
                                )
                            }
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "Password कम से कम 6 characters का रखें।",
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

@Composable
fun StudentDashboardScreen(
    student: Student?,
    onLogout: () -> Unit,
    onTest: () -> Unit,
    onCurrentAffairs: () -> Unit,
    onStudyMaterial: () -> Unit
) {

    val context = LocalContext.current

    PageColumn(
        title = "Student Dashboard",
        onBack = onLogout
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
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

                Text(
                    "Name: ${student?.name ?: "Loading..."}"
                )

                Text(
                    "Admission No.: ${student?.admissionNo ?: "-"}"
                )

                Text(
                    "Father Name: ${student?.father ?: "-"}"
                )

                Text(
                    "Mobile: ${student?.mobile ?: "-"}"
                )

                Text(
                    "Shift: ${student?.shift ?: "-"}"
                )

                Text(
                    "Locker: ${student?.locker ?: "Not Assigned"}"
                )
            }
        }

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
            onClick = {

                Toast.makeText(
                    context,
                    "Paid: ₹${student?.paid ?: "0"} • Due: ₹${student?.due ?: "0"}",
                    Toast.LENGTH_LONG
                ).show()
            }
        )

        DashboardTile(
            icon = "🔒",
            title = "Locker Info",
            color = SmartOrange,
            onClick = {

                Toast.makeText(
                    context,
                    "Locker: ${student?.locker ?: "Not Assigned"}",
                    Toast.LENGTH_LONG
                ).show()
            }
        )

        DashboardTile(
            icon = "👤",
            title = "Profile",
            color = SmartBlue,
            onClick = {}
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlineButton(
            text = "LOGOUT",
            color = SmartRed,
            onClick = onLogout
        )
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
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
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

    PageColumn(
        title = "Admin Login",
        onBack = onBack
    ) {

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "🔐 ADMIN LOGIN",
            color = SmartRed,
            fontSize = 27.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        InputField(
            "Admin ID",
            adminId
        ) {
            adminId = it
        }

        InputField(
            "Password",
            password
        ) {
            password = it
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        PrimaryButton(
            text = "OPEN ADMIN MANAGEMENT",
            color = SmartRed,
            onClick = onLogin
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Text(
            text = "Admin authentication अगली stage में Firebase से जोड़ी जाएगी।",
            color = Color.Gray,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AdminDashboardScreen(
    onBack: () -> Unit
) {

    PageColumn(
        title = "Admin Management",
        onBack = onBack
    ) {

        Text(
            text = "🛠️ ADMIN • LIBRARY MANAGEMENT",
            color = SmartBlue,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
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
fun OnlineTestScreen(
    onBack: () -> Unit
) {

    PageColumn(
        title = "Online Test",
        onBack = onBack
    ) {

        Text(
            text = "📝 ONLINE TEST",
            color = SmartRed,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold
        )

        val tests = listOf(
            "👮 Bihar Police Practice Test",
            "🛡️ Bihar Daroga Test",
            "📋 BPSC Practice Test",
            "🟢 SSC GD Practice Test",
            "🚆 Railway Practice Test",
            "🌐 General Awareness Test",
            "📚 Current Affairs Quiz"
        )

        tests.forEach { test ->

            DashboardTile(
                icon = "",
                title = test,
                color = SmartBlue,
                onClick = {}
            )
        }

        Text(
            text = "Questions और automatic result अगली stage में जोड़े जाएंगे।",
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CurrentAffairsScreen(
    onBack: () -> Unit
) {

    PageColumn(
        title = "Current Affairs",
        onBack = onBack
    ) {

        Text(
            text = "📰 DAILY CURRENT AFFAIRS",
            color = SmartRed,
            fontSize = 25.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        InfoCard(
            "आज के महत्वपूर्ण प्रश्न",
            "Daily Current Affairs के महत्वपूर्ण MCQ, Answer और Explanation यहाँ जोड़े जाएंगे।",
            SmartRed
        )

        InfoCard(
            "Bihar Current Affairs",
            "Bihar Police, Daroga और BPSC की तैयारी के लिए महत्वपूर्ण विषय।",
            SmartBlue
        )

        InfoCard(
            "National Current Affairs",
            "देश से जुड़े परीक्षा उपयोगी प्रश्न।",
            SmartGreen
        )

        InfoCard(
            "International Current Affairs",
            "विश्व से जुड़े महत्वपूर्ण घटनाक्रम।",
            SmartPurple
        )
    }
}

@Composable
fun StudyMaterialScreen(
    onBack: () -> Unit
) {

    PageColumn(
        title = "Study Material",
        onBack = onBack
    ) {

        Text(
            text = "📚 STUDY MATERIAL",
            color = SmartBlue,
            fontSize = 27.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        InfoCard(
            "📖 Notes",
            "Subject-wise notes यहाँ उपलब्ध किए जाएंगे।",
            SmartBlue
        )

        InfoCard(
            "📋 Syllabus",
            "Bihar Police, Daroga, BPSC, SSC, Railway आदि का syllabus।",
            SmartRed
        )

        InfoCard(
            "📝 Previous Year Questions",
            "Previous Year Questions और practice section।",
            SmartGreen
        )

        InfoCard(
            "📄 PDF Study Material",
            "Educational PDF यहाँ बाद में जोड़े जाएंगे।",
            SmartOrange
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
            containerColor = Color.White
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
                color = SmartText
            )
        }
    }
}
