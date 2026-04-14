*** Settings ***
Resource          ../resources/app_keywords.resource
Test Setup        Open Baking App
Test Teardown     Close Baking App

*** Test Cases ***
Launch Application Smoke Test
    [Documentation]    Verify the application launches and shows the intro screen.
    [Tags]             smoke
    App Should Launch Successfully
    Wait Until Page Contains Element    accessibility_id=Get Started    timeout=5s
