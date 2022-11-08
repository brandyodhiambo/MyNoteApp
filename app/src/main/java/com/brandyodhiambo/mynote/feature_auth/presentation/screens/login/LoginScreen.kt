package com.brandyodhiambo.mynote.feature_auth.presentation.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.brandyodhiambo.mynote.R
import com.brandyodhiambo.mynote.destinations.NotesScreenDestination
import com.brandyodhiambo.mynote.destinations.SignUpScreenDestination
import com.brandyodhiambo.mynote.feature_auth.presentation.screens.forgotpassword.ForgotPasswordScreenEvents
import com.brandyodhiambo.mynote.feature_auth.presentation.screens.forgotpassword.ForgotPasswordViewModel
import com.brandyodhiambo.mynote.ui.theme.Teal200
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Destination
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel(),
    forgotPasswordViewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    var keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val openDialog = remember{ mutableStateOf(false) }
    val state = viewModel.loginState
    val passwordstate = forgotPasswordViewModel.forgotState

    LaunchedEffect(key1 = context){
        viewModel.loginValidationChannel.collect{ event->
            when(event){
                is LoginViewModel.LoginScreenVaidationEvents.Success->{
                    //Toast.makeText(context, "Validation Okay", Toast.LENGTH_SHORT).show()
                    viewModel.loginUser()
                }
                is LoginViewModel.LoginScreenVaidationEvents.ValidationSuccess->{
                    Toast.makeText(context, "Logging is Successfull", Toast.LENGTH_SHORT).show()
                    navigator.popBackStack()
                   navigator.navigate(NotesScreenDestination)
                }
                is LoginViewModel.LoginScreenVaidationEvents.ValidationError->{
                    Toast.makeText(context, "${ event.errorMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(key1 = context){
        forgotPasswordViewModel.forgotValidationChannel.collect{ forgotEvent->
            when(forgotEvent){
                is ForgotPasswordViewModel.ForgotScreenVaidationEvents.Success->{
                   // Toast.makeText(context, "Validation is ok", Toast.LENGTH_SHORT).show()
                    forgotPasswordViewModel.forgotPassword()
                }
                is ForgotPasswordViewModel.ForgotScreenVaidationEvents.ValidationSuccess->{
                    Toast.makeText(context, "Check email for password reset link", Toast.LENGTH_SHORT).show()
                }
                is ForgotPasswordViewModel.ForgotScreenVaidationEvents.ValidationError->{
                    val errorMessage = forgotEvent.errorMessage
                    Toast.makeText(context, "$errorMessage", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    Scaffold(
        topBar = {
            Column(
                Modifier.padding(16.dp), verticalArrangement = Arrangement.Top
            ) {
                Text(text = "Welcome Back", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "Login to Continue",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
            }
        },
        scaffoldState = scaffoldState
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(64.dp))

            Column {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    value = state.email,
                    onValueChange = { viewModel.onEvent(LoginScreenEvents.EmailFieldChangeEvent(it))
                    },
                    isError = state.emailError != null,
                    label = {
                        Text(
                            "Enter Email",
                            color = Color.Black
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = "email icon",
                            tint = Color.Black
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Teal200,
                        unfocusedBorderColor = DarkGray,
                        textColor = Color.Black
                    )
                )
                    if(state.emailError != null){
                        Text(
                            text = state.emailError ,
                            color = MaterialTheme.colors.error ,
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(end = 8.dp)

                        )
                    }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Column {
                var passwordVisible by rememberSaveable { mutableStateOf(false) }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    value = state.password,
                    onValueChange = { viewModel.onEvent(LoginScreenEvents.PasswordFieldChangeEvent(it))},
                   isError = state.passwordError != null,
                    label = {
                        Text(
                            text = "Enter password",
                            color = Color.Black
                        )
                    },
                    leadingIcon = {
                        Icon(painter = painterResource(id =  R.drawable.ic_key ), contentDescription = "password key" , tint = Color.Black)
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                       val description = if (passwordVisible) "Hide password" else "Show password"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = if (passwordVisible)  painterResource(id = R.drawable.ic_visibility) else painterResource(id = R.drawable.ic_visibility_off),
                                contentDescription = description,
                                tint = Color.Black
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Teal200,
                        unfocusedBorderColor = DarkGray,
                        textColor = Color.Black
                    )
                )
                if(state.passwordError != null){
                    Text(
                        text = state.passwordError ,
                        color = MaterialTheme.colors.error ,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 8.dp)

                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = {
                    openDialog.value = true
                }) {
                    Text(text = "Forgot password?")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if(state.showProgressBar){
                    CircularProgressIndicator(
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else{
                    Button(
                        onClick = {
                            keyboardController?.hide()
                            viewModel.onEvent(LoginScreenEvents.SubmitButtonClickEvent)},
                        colors = ButtonDefaults.buttonColors(
                           backgroundColor = Teal200
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        shape = RoundedCornerShape(8)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp), text = "Sign In", textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(
                onClick = {
                   navigator.popBackStack()
                    navigator.navigate(SignUpScreenDestination)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Don't have an account?")
                        append(" ")
                        withStyle(
                            style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)
                        ) {
                            append("Sign Up")
                        }
                    },
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
            }


        }

        if (openDialog.value) {
            AlertDialog(
                modifier = Modifier
                    .height(300.dp)
                    .width(420.dp),
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = "Forgot Password", color = Color.Black)
                },
                text = {
                    Column {
                        Text(text = "Enter your email to reset password", color = Teal200,)
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 2.dp, end = 2.dp),
                            value = passwordstate.email,
                            onValueChange = { forgotPasswordViewModel.onEvent(ForgotPasswordScreenEvents.EmailFieldChangeEvent(it))
                            },
                            isError = state.emailError != null,
                            label = {
                                Text(
                                    "Enter Email",
                                    color = Color.Black
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Email,
                                    contentDescription = "email icon",
                                    tint = Color.Black
                                )
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Teal200,
                                unfocusedBorderColor = DarkGray,
                                textColor = Color.Black
                            )
                        )
                        if(passwordstate.emailError != null){
                            Text(
                                text = passwordstate.emailError ,
                                color = MaterialTheme.colors.error ,
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .padding(end = 8.dp)

                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                        },
                    ) {
                        Text("Cancel")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                            keyboardController?.hide()
                            forgotPasswordViewModel.onEvent(ForgotPasswordScreenEvents.SubmitButtonClickEvent)
                        },
                    ) {
                        Text("Ok")
                    }
                },
            )
        }

    }

}