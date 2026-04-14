*** Settings ***
Resource          ../resources/app_keywords.resource
Test Setup        Open Baking App
Test Teardown     Close Baking App

*** Test Cases ***
End To End User Journey
    [Documentation]    End-to-end user journey: Launch -> Get Started -> Select Recipe -> View Ingredients -> Back -> Verify presence of other recipes
    [Tags]             e2e
    App Should Launch Successfully
    Navigate To Recipe List
    Select Recipe               Nutella Pie
    Verify Recipe Content       Ingredients
    Verify Recipe Content       Graham Cracker crumbs
    Go Back
    Wait Until Page Contains    Nutella Pie    timeout=10s
    Wait Until Page Contains    Brownies    timeout=10s
    Wait Until Page Contains    Chocolate Cake    timeout=10s
