*** Settings ***
Library           AppiumLibrary

*** Variables ***
${REMOTE_URL}     http://localhost:4723
${PLATFORM_NAME}  Android
${DEVICE_NAME}    Android Emulator
${APP_PACKAGE}    com.example.android.bakingapp
${APP_ACTIVITY}   .MainActivity

*** Keywords ***
Open Baking App
    Open Application    ${REMOTE_URL}    platformName=${PLATFORM_NAME}    deviceName=${DEVICE_NAME}    appPackage=${APP_PACKAGE}    appActivity=${APP_ACTIVITY}    automationName=UiAutomator2

Close Baking App
    Close Application

App Should Launch Successfully
    Wait Until Page Contains    Welcome to Baking App!    timeout=10s

Navigate To Recipe List
    Click Text    Get Started
    Wait Until Page Contains    Nutella Pie    timeout=10s

Select Recipe
    [Arguments]    ${recipe_name}
    ${locator}=    Set Variable    xpath=//*[contains(@text,"${recipe_name}")]
    Wait Until Element Is Visible    ${locator}    timeout=10s
    Click Element    ${locator}
    Wait Until Page Contains    Ingredients    timeout=10s

Scroll To Text
    [Arguments]    ${text}
    ${status}    Run Keyword And Return Status    Page Should Contain Text    ${text}
    IF    '${status}' == 'False'
        Execute Script    mobile: scroll    {"strategy": "accessibility id", "selector": "${text}"}
    END

Verify Recipe Content
    [Arguments]    ${content}
    Page Should Contain Text    ${content}

Go Back
    Click Element    accessibility_id=Back
