package com.example.moudle2_handson_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moudle2_handson_assignment.ui.theme.Moudle2HandsonAssignmentTheme
import kotlinx.coroutines.delay


//MainActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Moudle2HandsonAssignmentTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "splash",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        //Different Screens
                        composable("splash") { SplashScreen(navController) }
                        composable("login") { LoginScreen(navController) }
                        composable("register") { RegisterScreen(navController) }
                        composable("welcome") { WelcomeScreen() }
                    }
                }
            }
        }
    }
}

//Function for Splash Screen
@Composable
fun SplashScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Adding the image of the splash screen from drawable
            Image(
                painter = painterResource(id = R.drawable.another),
                contentDescription = "App Logo",
                modifier = Modifier.size(200.dp)
            )
            //Code for the name of the Splash Screeen
            Text(text = "Cool Place", color = Color.Black)
        }

        //Adding Time for the Splash Screen
        LaunchedEffect(true) {
            delay(3000)
            navController.navigate("login")
        }
    }
}

//Code for the Login Screen
@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(8.dp))
        }

        Button(onClick = {
            errorMessage = validateLogin(email, password)
            if (errorMessage.isEmpty()) {
                navController.navigate("welcome")
            }
        }) {
            Text("Login")
        }
        TextButton(onClick = { navController.navigate("register") }) {
            Text("Register")
        }
    }
}

//Welcome Scren Code after login
@Composable
fun WelcomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Welcome to the App!",
            color = Color.Black,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}


//Code to make sure the login info fields are properlly filled
fun validateLogin(email: String, password: String): String {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    if (!email.matches(emailPattern.toRegex())) {
        return "Invalid email format."
    }
    if (password.isEmpty()) {
        return "Password cannot be empty."
    }
    return ""
}


//Code for the Registration Page
@Composable
fun RegisterScreen(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var familyName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = firstName, onValueChange = { firstName = it }, label = { Text("First Name") })
        TextField(value = familyName, onValueChange = { familyName = it }, label = { Text("Family Name") })
        TextField(value = dob, onValueChange = { dob = it }, label = { Text("Date of Birth") })
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(8.dp))
        }

        Button(onClick = {
            errorMessage = validateForm(firstName, familyName, email)
            if (errorMessage.isEmpty()) {
                navController.navigate("login")
            }
        }) {
            Text("Register")
        }
    }
}

//Code to make sure the Register filed are properly filled
fun validateForm(firstName: String, familyName: String, email: String): String {
    if (firstName.length < 3 || firstName.length > 30) {
        return "First name must be between 3 and 30 characters."
    }
    if (familyName.isEmpty()) {
        return "Family name cannot be empty."
    }
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    if (!email.matches(emailPattern.toRegex())) {
        return "Invalid email format."
    }
    return ""
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Moudle2HandsonAssignmentTheme {
        SplashScreen(navController = rememberNavController())
    }
}
