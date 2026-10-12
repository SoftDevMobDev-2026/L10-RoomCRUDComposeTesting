package com.example.week10
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
class CityScreenTest {
    @get:Rule val composeRule = createComposeRule()
    @Test fun addCity_isAvailable() {
        var received: Pair<String, String>? = null
        composeRule.setContent {
            CityScreen(cities = emptyList(), onAdd = { name, state -> received = name to state }, onUpdate = {}, onDelete = {})
        }
        // Wait 2 seconds
        Thread.sleep(2000)
        composeRule
            .onNodeWithText("City name")
            .performTextInput("Melbourne")

        // Wait 2 seconds
        Thread.sleep(2000)

        composeRule
            .onNodeWithText("State")
            .performTextInput("VIC")

        // Wait 2 seconds
        Thread.sleep(2000)

        composeRule
            .onNodeWithText("Add City")
            .performClick()

        // Wait 2 seconds
        Thread.sleep(2000)
        composeRule
            .runOnIdle { assertEquals("Melbourne" to "VIC", received) }
    }

    @Test fun editAndDelete_areAvailable() {
        val city = CityEntity(id = 1, name = "Melbourne", state = "VIC")
        var updated: CityEntity? = null
        var deleted: CityEntity? = null
        composeRule.setContent {
            CityScreen(listOf(city), onAdd = { _, _ -> }, onUpdate = { updated = it }, onDelete = { deleted = it })
        }
        // Wait 2 seconds
        Thread.sleep(2000)

        composeRule
            .onNodeWithText("Edit Melbourne")
            .performClick()
        // Wait 2 seconds
        Thread.sleep(2000)

        composeRule
            .onNodeWithText("Save Changes")
            .assertIsDisplayed()

        // Wait 2 seconds
        Thread.sleep(2000)
        composeRule
            .onNodeWithText("Save Changes")
            .performClick()

        // Wait 2 seconds
        Thread.sleep(2000)

        composeRule.runOnIdle { assertEquals(city, updated) }

        // Wait 2 seconds
        Thread.sleep(2000)

        composeRule
            .onNodeWithText("Delete Melbourne")
            .performClick()
        composeRule.runOnIdle { assertEquals(city, deleted) }
    }

    @Test fun blankInput_disablesAddButton() {
        composeRule.setContent { CityScreen(emptyList(), onAdd = { _, _ -> }, onUpdate = {}, onDelete = {}) }
        composeRule.onNodeWithText("Add City").assertIsNotEnabled()
    }
}
