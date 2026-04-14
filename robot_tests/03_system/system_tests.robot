*** Settings ***
Resource          ../resources/app_keywords.resource
Test Setup        Open Baking App
Test Teardown     Close Baking App

*** Test Cases ***
Complete User Journey E2E
    [Documentation]    End-to-End test journey: Intro -> List -> Selection -> Details -> Back to List.
    [Tags]             system
    App Should Launch Successfully
    Navigate To Recipe List
    Select Recipe    Chocolate Cake
    Verify Recipe Content    Ingredients
    Verify Recipe Content    All-purpose flour
    Go Back
    Wait Until Page Contains    Chocolate Cake    timeout=5s
