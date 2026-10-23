# Smart Pantry Manager

An Android application built with Java and XML for managing household
pantry ingredients and suggesting recipes based on available stock. This
project was developed for the Richfield Mobile App Development 700
assignment.

## Student and Assignment Details

Complete or confirm the details below before submitting the project documentation.

- **Name:** Katlego 
- **Surname:** Sambo
- **Student Number:** 402306874
- **Qualification:** Articulation to BSC
- **Year of Study:** 2026
- **Module:** Mobile App Development 700

## Table of Contents

-   [Overview](#overview)
-   [Objectives](#objectives)
-   [Features](#features)
-   [Technology Stack](#technology-stack)
-   [Application Workflow](#application-workflow)
-   [Database Design](#database-design)
-   [Project Structure](#project-structure)
-   [Requirements](#requirements)
-   [Setup and Run on Windows](#setup-and-run-on-windows)
-   [Build the Debug APK](#build-the-debug-apk)
-   [Using the Application](#using-the-application)
-   [Recipe Matching](#recipe-matching)
-   [Testing Checklist](#testing-checklist)
-   [Known Limitations](#known-limitations)
-   [Troubleshooting](#troubleshooting)
-   [Future Improvements](#future-improvements)
-   [Assignment Evidence](#assignment-evidence)

## Overview

Smart Pantry Manager helps users record ingredients kept at home,
maintain quantities, and identify recipes that can be prepared using
current pantry stock. Users can add, view, edit, and delete pantry
items, open recipe details, and change an expiry-alert preference in
Settings.

The app stores data locally using SQLite and does not require a remote
server for its core pantry functionality.

## Objectives

-   Maintain an inventory of pantry ingredients and quantities.
-   Support create, read, update, and delete (CRUD) operations.
-   Persist pantry data locally in SQLite.
-   Provide a preloaded recipe collection.
-   Suggest recipes only when all required ingredients and quantities
    are available.
-   Normalize selected ingredient-name and unit variations.
-   Provide a Settings screen with an expiry-alert preference.

## Features

### Pantry management

-   Display ingredient names, quantities, and units.
-   Add new pantry items.
-   Edit existing items.
-   Delete items.
-   Store an optional expiry date.
-   Save pantry data in the local database.

### Recipe suggestions and details

The database seeds 15 recipes: 1. Tomato Pasta 2. Vegetable Omelette 3.
Cheese Toast 4. Chicken Rice 5. Tuna Sandwich 6. Bean Salad 7. Pancakes
8. Garlic Potatoes 9. Fruit Smoothie 10. Avocado Toast 11. Lentil Soup
12. Peanut Noodles 13. Apple Oats 14. Corn Fritters 15. Mushroom Pasta

The recipe screen displays recipes that pass the ingredient-matching
check. Selecting a recipe opens its ingredients and preparation
instructions.

### Settings

Settings includes a preference for showing expiring-soon alerts. The
`settings` table stores the `expiry_alerts` value, initially set to
`true`. A preference toggle alone does not prove that automatic expiry
checking or scheduled notifications are implemented; that behaviour must
be tested separately.

## Technology Stack

-   **Language:** Java
-   **Platform:** Android
-   **UI:** Android Activities and XML layouts
-   **Storage:** SQLite using `SQLiteOpenHelper`
-   **Build:** Gradle
-   **Tools:** Android Studio or Visual Studio Code, Android SDK,
    Android Emulator, and ADB

## Application Workflow

1.  The app loads pantry entries from SQLite.
2.  The user adds, edits, or deletes pantry items.
3.  Recipe definitions and ingredient requirements are stored in
    `recipes` and `recipe_ingredients`.
4.  The database helper compares each recipe's requirements with the
    current pantry.
5.  Recipes are suggested only if every required ingredient is present
    in a compatible unit and sufficient quantity.
6.  The user can open a suggested recipe to view its details.

## Database Design

The database file is `smart_pantry.db`. The `DatabaseHelper` class
manages four tables.

### `pantry_items`

  Column          Purpose
  --------------- -------------------------------------------
  `id`            Auto-incrementing primary key
  `name`          Ingredient name
  `quantity`      Available numeric quantity
  `unit`          Unit such as `g`, `ml`, `piece`, or `can`
  `expiry_date`   Optional expiry date

### `recipes`

  Column    Purpose
  --------- -------------------------------
  `id`      Auto-incrementing primary key
  `name`    Recipe name
  `steps`   Preparation instructions

### `recipe_ingredients`

  Column              Purpose
  ------------------- -------------------------------
  `id`                Auto-incrementing primary key
  `recipe_id`         ID of the related recipe
  `ingredient_name`   Required ingredient
  `quantity`          Required quantity
  `unit`              Required unit

### `settings`

Stores application preferences as key/value pairs. The current schema
inserts `expiry_alerts` with an initial value of `true`.

## Project Structure

-   `app/src/main/java/com/example/smartpantry/` --- application Java
    classes and Activities.
-   `app/src/main/java/com/example/smartpantry/data/DatabaseHelper.java`
    --- database schema, recipe seeding, pantry CRUD operations, and
    recipe matching.
-   `app/src/main/java/com/example/smartpantry/model/` --- model classes
    such as `PantryItem` and `Recipe`.
-   `app/src/main/res/` --- layouts and Android resources.
-   `app/src/main/AndroidManifest.xml` --- application and Activity
    declarations.
-   `app/build.gradle` --- app module build configuration.
-   `build.gradle` and `settings.gradle` --- project-level Gradle
    configuration.
-   `gradlew` and `gradlew.bat` --- Gradle Wrapper launchers.

Exact filenames may change as the project is updated.

## Requirements

-   Windows, macOS, or Linux.
-   Android Studio with a compatible Java runtime.
-   Android SDK and Platform Tools.
-   Android Emulator and an Android Virtual Device (AVD), or a physical
    Android device with USB debugging enabled.
-   The Gradle Wrapper files included with the project.

## Setup and Run on Windows

### 1. Open the project

Open the `SmartPantryManager` project root in Android Studio or Visual
Studio Code. Android Studio provides the most complete built-in support
for Android SDKs, emulators, and Gradle.

### 2. Configure Java in PowerShell

For the development setup used for this project, set `JAVA_HOME` to
Android Studio's bundled Java runtime:

``` powershell
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
```

Verify the installation:

``` powershell
& "$env:JAVA_HOME\bin\java.exe" -version
Test-Path "$env:JAVA_HOME\bin\java.exe"
```

The last command should return `True`. If Android Studio is installed
elsewhere, use the actual JBR path on your computer.

### 3. Change to the project folder

``` powershell
cd "C:\path\to\SmartPantryManager"
```

Replace the example path with the folder where the project is saved.

### 4. Build the app

``` powershell
.\gradlew.bat assembleDebug
```

Wait for `BUILD SUCCESSFUL`. The first build may take longer while
Gradle downloads dependencies.

### 5. Start an emulator

Start an AVD through Android Studio's Device Manager. If an AVD named
`Medium_Phone_API_37.0` exists, you can also use:

``` powershell
& "$env:LOCALAPPDATA\Android\Sdk\emulator\emulator.exe" -avd Medium_Phone_API_37.0 -no-snapshot-load -gpu swiftshader_indirect
```

Wait for Android to finish booting before continuing. Your SDK path or
AVD name may differ.

### 6. Check ADB

``` powershell
& "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe" devices
```

A running emulator should appear with status `device`, for example:

``` text
emulator-5554    device
```

### 7. Install and launch

Run these commands from the project root:

``` powershell
$APK = Join-Path (Get-Location) "app\build\outputs\apk\debug\app-debug.apk"
& "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe" install -r $APK
& "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe" shell am start -n com.example.smartpantry/.MainActivity
```

The `-r` option reinstalls the APK and normally retains app data. Avoid
clearing app data or uninstalling the app if you need to preserve the
current pantry database.

## Build the Debug APK

From the project root on Windows:

``` powershell
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
.\gradlew.bat assembleDebug
```

The expected output is:

``` text
app/build/outputs/apk/debug/app-debug.apk
```

To inspect the file:

``` powershell
$APK = Join-Path (Get-Location) "app\build\outputs\apk\debug\app-debug.apk"
Get-Item $APK | Format-List FullName, Length, LastWriteTime
```

A successful build confirms that Gradle compiled the project; it does
not by itself prove every feature works correctly.

## Using the Application

1.  **Pantry:** review saved ingredients and quantities.
2.  **Add:** tap the add button and enter the ingredient name, quantity,
    unit, and optional expiry date.
3.  **Edit:** use an ingredient's edit control, change its details, and
    save.
4.  **Delete:** use the delete control and confirm if prompted.
5.  **Recipes:** open the Recipes tab to see recipes that pass the
    matching rules.
6.  **Recipe details:** select a recipe to view its ingredients and
    instructions.
7.  **Settings:** view or change the expiry-alert preference.

## Recipe Matching

The `DatabaseHelper` matching logic normalizes selected ingredient names
and converts supported units to base units before comparing quantities.
Examples in the current code include plural-to-singular mappings such as
`tomatoes` to `tomato` and `eggs` to `egg`, as well as mapping several
cooking-oil names to a common oil name. It also supports conversions
such as kilograms to grams and litres to millilitres.

A recipe should pass only when every required ingredient exists, the
units are compatible, and the available quantity is at least the
required quantity.

Example for a recipe requiring 200 g of pasta: - 250 g available:
quantity requirement passes. - 150 g available: recipe should be
excluded. - No pasta available: recipe should be excluded.

The supported synonyms and conversions are limited to those implemented
in the source code. Do not assume every unit or ingredient variation is
recognized.

## Testing Checklist

Record the actual result of each test before claiming it passes.

-   [ ] Gradle build completes successfully.
-   [ ] App installs and launches.
-   [ ] Pantry items show correct names, quantities, and units.
-   [ ] A pantry item can be added.
-   [ ] An existing item can be edited.
-   [ ] An item can be deleted.
-   [ ] Pantry data remains after closing and reopening the app.
-   [ ] Recipe details open successfully.
-   [ ] A recipe appears when all required ingredients and quantities
    are available.
-   [ ] A recipe is excluded when an ingredient is missing.
-   [ ] A recipe is excluded when a quantity is insufficient.
-   [ ] A recipe becomes eligible again when stock is restored.
-   [ ] Supported ingredient-name normalization works.
-   [ ] Supported unit conversions work.
-   [ ] The expiry-alert preference retains its value after reopening
    Settings.
-   [ ] Automatic expiry notifications are separately tested if claimed
    as implemented.

## Known Limitations

-   The expiry-alert preference exists, but the toggle alone does not
    establish that automatic expiry checks and scheduled notifications
    are implemented.
-   The development emulator displayed intermittent Android System UI,
    launcher, and graphics errors. Test on a stable emulator or physical
    device before demonstrating the app.
-   Strict recipe matching needs explicit tests for missing ingredients,
    insufficient quantities, naming variations, and unit conversions.
-   Unit conversion and ingredient-name normalization are limited to
    rules in the source code.
-   Barcode scanning, nutritional information, cloud synchronization,
    and dietary personalization are not documented as current features.

## Troubleshooting

### `JAVA_HOME` is invalid

``` powershell
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
& "$env:JAVA_HOME\bin\java.exe" -version
```

If that folder does not exist, locate the actual Android Studio JBR
directory and use its path.

### PowerShell cannot find `gradlew.bat`

Change to the project root and use `.\`:

``` powershell
Set-Location "C:\path\to\SmartPantryManager"
.\gradlew.bat assembleDebug
```

### APK path is null or missing

``` powershell
$Project = "C:\path\to\SmartPantryManager"
$APK = Join-Path $Project "app\build\outputs\apk\debug\app-debug.apk"
Test-Path $APK
```

If the result is `False`, build the app and inspect the Gradle output.

### ADB shows no devices

Start the emulator, wait for Android to finish booting, and run:

``` powershell
& "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe" devices
```

Adjust the SDK path if your SDK is installed elsewhere.

### Emulator freezes or displays system warnings

Try a cold boot from Android Studio's Device Manager or restart the AVD.
You can test software rendering with `-gpu swiftshader_indirect`.
Emulator instability may be related to the virtual device or host
computer and should not automatically be attributed to the app.

## Future Improvements

-   Implement and test scheduled expiry notifications.
-   Improve quantity and unit validation.
-   Support additional unit conversions and ingredient aliases.
-   Explain which ingredients are missing from each unavailable recipe.
-   Add barcode-based ingredient entry.
-   Add nutritional information and dietary preferences.
-   Add backup/export or optional cloud synchronization.
-   Expand automated and manual testing across Android versions and
    screen sizes.

## Assignment Evidence

Keep genuine screenshots of: - The invalid `JAVA_HOME` error and
corrected Java version check. - Successful Gradle build and debug APK
details. - Emulator installation/reinstallation evidence where
available. - ADB showing the emulator with status `device`. - Pantry
list, add/edit/delete forms, Settings, recipe suggestions, and recipe
details. - Strict-matching tests for a missing ingredient and
insufficient quantity. - Other evidence required by the assignment
brief.


------------------------------------------------------------------------

**Project:** Smart Pantry Manager\
**Package:** `com.example.smartpantry`\
**Database:** `smart_pantry.db`\
**Debug APK:** `app/build/outputs/apk/debug/app-debug.apk`
