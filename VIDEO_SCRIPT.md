# 5–7 Minute Demonstration Script

## 0:00–1:00 — GitHub walkthrough
Show the public repository, README, project structure, and at least 10 meaningful commits. Explain the development order briefly.

## 1:00–3:30 — Live app
1. Launch the app and show the empty pantry message.
2. Add ingredients with the form; deliberately leave a required field blank to show validation.
3. Show the pantry list (Read), edit an ingredient (Update), and delete one (Delete).
4. Add all ingredients for one recipe and open Suggested Recipes.
5. Remove one ingredient and refresh Suggested Recipes to prove the recipe disappears. Explain that partial matches are excluded.
6. Close and reopen the app to prove SQLite persistence.
7. Open recipe details and Settings.

## 3:30–5:45 — Code explanation
Explain three topics in your own words: `SQLiteOpenHelper` creates and queries the local tables; the RecyclerView adapter binds dynamic pantry/recipe data; Intents move between Activities and pass IDs; and `canMake()` normalises names, compares units, and checks quantities.

## 5:45–6:30 — Database justification
Explain that SQLite was selected because it is local, offline, simple to demonstrate, and satisfies persistence and CRUD requirements without a server.

Keep the final recording between 5 and 7 minutes, use clear narration, and point to actual code lines.
