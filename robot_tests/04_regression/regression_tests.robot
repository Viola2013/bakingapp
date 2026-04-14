*** Settings ***
Resource          ../resources/app_keywords.resource
Test Setup        Open Baking App
Test Teardown     Close Baking App

*** Test Cases ***
Regression On New Recipe Content
    [Documentation]    Verify newly added recipes from the update are present and accessible.
    [Tags]             regression
    Navigate To Recipe List
    Wait Until Page Contains    Chocolate Cake    timeout=5s
    Wait Until Page Contains    Apple Pie    timeout=5s
    Wait Until Page Contains    Blueberry Muffins    timeout=5s
    Select Recipe               Blueberry Muffins
    Verify Recipe Content       Fresh blueberries
