package com.brandyodhiambo.mynote.feature_auth.presentation.screens.signup

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
import com.brandyodhiambo.mynote.destinations.LoginScreenDestination
import com.brandyodhiambo.mynote.feature_auth.presentation.screens.login.LoginScreenEvents
import com.brandyodhiambo.mynote.ui.theme.Teal200
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Destination
fun SignUpScreen(
    navigator: DestinationsNavigator,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    var keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val state = viewModel.signUpState


    LaunchedEffect(key1 = context){
        viewModel.signUpValidationChannel.collect{ event->
            when(event){
                is SignUpViewModel.SignUpValidationEvent.SuccessfulValidation->{
                    viewModel.signUpUser()
                }
                is SignUpViewModel.SignUpValidationEvent.SuccessfulRegistration->{
                    Toast.makeText(context, "Please check your email for verification", Toast.LENGTH_SHORT).show()
                    navigator.popBackStack()
                    navigator.navigate(LoginScreenDestination)
                }
                is SignUpViewModel.SignUpValidationEvent.FailedRegistration->{
                    val error = event.errorMessage
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Column(
                Modifier.padding(16.dp), verticalArrangement = Arrangement.Top
            ) {
                Text(text = "Welcome", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "Create an Account Here",
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
                    onValueChange = { viewModel.onEvent(SignUpScreenEvent.EmailFieldChangeEvent(it))
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
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.DarkGray,
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
                    onValueChange = { viewModel.onEvent(SignUpScreenEvent.PasswordFieldChangeEvent(it))},
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
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.DarkGray,
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
                            viewModel.onEvent(SignUpScreenEvent.SubmitButtonClickEvent)
                        },
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
                                .padding(8.dp), text = "Sign Up", textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(
                onClick = {
                    navigator.popBackStack()
                    navigator.navigate(LoginScreenDestination)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Already have an account?")
                        append(" ")
                        withStyle(
                            style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)
                        ) {
                            append("Sign In")
                        }
                    },
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
            }

        }

    }

}