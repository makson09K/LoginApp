package com.example.loginapp

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FormularzUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    // Zadanie 3: Happy Path
    @Test
    fun test_poprawneLogowanie_sukces() {
        composeTestRule.onNodeWithTag("email_input").performTextInput("student@uczelnia.pl")
        composeTestRule.onNodeWithTag("password_input").performTextInput("Tajnie123!")
        composeTestRule.onNodeWithTag("login_button").performClick()

        composeTestRule.onNodeWithTag("success_message").assertIsDisplayed()
    }

    // Zadanie 4: Negative Testing
    @Test
    fun test_bledneDane_blokujePrzycisk() {
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("email_input").performTextInput("zly_email")
        composeTestRule.onNodeWithTag("password_input").performTextInput("123")

        composeTestRule.onNodeWithTag("login_button").assertIsNotEnabled()
        composeTestRule.onNodeWithTag("error_message").assertIsDisplayed()
    }

    // Zadanie 5: Rotacja ekranu
    @Test
    fun test_rotacjaEkranu_zachowujeStan() {
        val email = "test@rotacja.pl"
        composeTestRule.onNodeWithTag("email_input").performTextInput(email)

        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        device.setOrientationLeft()
        // Zwiększamy czas oczekiwania
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onNodeWithTag("email_input").fetchSemanticsNode().config[SemanticsProperties.EditableText].text == email
        }

        device.setOrientationNatural()
    }
}