*** Settings ***
Resource          ../resources/app_keywords.resource
Test Setup        Open Baking App
Test Teardown     Close Baking App

*** Test Cases ***
Integration Between Intro And List
    [Documentation]    Verify the "Get Started" button correctly loads the recipe list from the repository.
    [Tags]             integration
    Navigate To Recipe List
    Wait Until Page Contains    Nutella Pie    timeout=10s
    Wait Until Page Contains    Chocolate Cake    timeout=10s
    Page Should Contain Text    Brownies
