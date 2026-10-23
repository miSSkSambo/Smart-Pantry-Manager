# Smart Pantry Manager — Written Report

> Replace all square-bracket placeholders. Add your own screenshots from the running application. Do not submit this template without completing it.

## Cover Page
- Student name: [Your name]
- Student number: [Your ITS number]
- Module: Mobile App Development 700
- Application: Smart Pantry Manager
- Date: [Date]
- GitHub repository: [working public GitHub URL]

## Table of Contents
[Generate automatically in Word after applying heading styles.]

## 1. Introduction
Explain food waste, the target user, and how the app solves the problem by tracking available ingredients and suggesting recipes without requiring a shopping trip.

## 2. System Design
### 2.1 Screen flow
Pantry List → Add/Edit Ingredient → Pantry List; Pantry List → Suggested Recipes → Recipe Detail; Pantry List → Settings.

### 2.2 Database model
Describe `pantry_items`, `recipes`, `recipe_ingredients`, and `settings`. Include an ER diagram and explain the relationships.

## 3. Screenshots and Outputs
Insert labelled screenshots of: empty pantry, add validation error, populated pantry, edit item, delete confirmation/result, suggested recipes, zero-match message, recipe detail, settings, and persistence after reopening.

## 4. Key Code Snippets
Include 3–5 short snippets with explanations: SQLite schema/CRUD, RecyclerView adapter, Intent navigation, strict matching algorithm, and form validation.

## 5. Challenges and Solutions
Describe 2–3 genuine problems you encountered and how you fixed them. Example topics: normalising plural ingredient names, handling unit conversions, refreshing data in `onResume()`, or preventing blank results.

## 6. Conclusion and Reflection
Explain what you learned about Java Android development, Activities, Intents, SQLite, adapters, validation, and testing. State realistic future improvements.

## 7. References
Use Harvard style consistently. Suggested sources to verify and cite after consulting them:
- Android Developers (n.d.) *Save data in a local database using SQLite*. Available at: https://developer.android.com/training/data-storage/sqlite (Accessed: [date]).
- Android Developers (n.d.) *Create dynamic lists with RecyclerView*. Available at: https://developer.android.com/develop/ui/views/layout/recyclerview (Accessed: [date]).
- Android Developers (n.d.) *Activities*. Available at: https://developer.android.com/guide/components/activities/intro-activities (Accessed: [date]).
