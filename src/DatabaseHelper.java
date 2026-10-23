
package com.example.smartpantry.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.smartpantry.model.PantryItem;
import com.example.smartpantry.model.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "smart_pantry.db";
    private static final int DB_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
            "CREATE TABLE pantry_items (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "quantity REAL NOT NULL," +
            "unit TEXT NOT NULL," +
            "expiry_date TEXT)"
        );

        db.execSQL(
            "CREATE TABLE recipes (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "steps TEXT NOT NULL)"
        );

        db.execSQL(
            "CREATE TABLE recipe_ingredients (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "recipe_id INTEGER," +
            "ingredient_name TEXT," +
            "quantity REAL," +
            "unit TEXT)"
        );

        db.execSQL(
            "CREATE TABLE settings (" +
            "key TEXT PRIMARY KEY," +
            "value TEXT)"
        );

        db.execSQL(
            "INSERT INTO settings (key, value) " +
            "VALUES ('expiry_alerts', 'true')"
        );

        seedRecipes(db);
    }

    private void seedRecipes(SQLiteDatabase db) {
        String[][] data = {
            {"Tomato Pasta",
             "Boil pasta. Saute tomatoes and garlic. Combine and season.",
             "pasta|200|g;tomato|2|piece;garlic|1|piece;olive oil|10|ml"},

            {"Vegetable Omelette",
             "Beat eggs, add vegetables, and cook in a pan until set.",
             "egg|2|piece;onion|1|piece;tomato|1|piece;cheese|30|g"},

            {"Cheese Toast",
             "Toast bread, add cheese, and grill until melted.",
             "bread|2|piece;cheese|40|g"},

            {"Chicken Rice",
             "Cook chicken and rice with onion and seasonings.",
             "chicken|200|g;rice|200|g;onion|1|piece;garlic|1|piece"},

            {"Tuna Sandwich",
             "Mix tuna with mayonnaise and serve in bread.",
             "tuna|1|can;bread|2|piece;mayonnaise|20|g"},

            {"Bean Salad",
             "Combine beans, tomato, onion, and dressing.",
             "beans|1|can;tomato|1|piece;onion|1|piece;olive oil|10|ml"},

            {"Pancakes",
             "Mix flour, milk, and egg. Fry spoonfuls until golden.",
             "flour|200|g;milk|250|ml;egg|1|piece;sugar|20|g"},

            {"Garlic Potatoes",
             "Roast potatoes with garlic and oil until tender.",
             "potato|3|piece;garlic|1|piece;olive oil|15|ml"},

            {"Fruit Smoothie",
             "Blend banana, milk, and honey until smooth.",
             "banana|1|piece;milk|200|ml;honey|10|g"},

            {"Avocado Toast",
             "Mash avocado and spread on toasted bread.",
             "avocado|1|piece;bread|2|piece"},

            {"Lentil Soup",
             "Simmer lentils with carrot, onion, and stock.",
             "lentils|200|g;carrot|1|piece;onion|1|piece;stock|500|ml"},

            {"Peanut Noodles",
             "Cook noodles and toss with peanut butter and soy sauce.",
             "noodles|200|g;peanut butter|30|g;soy sauce|15|ml"},

            {"Apple Oats",
             "Cook oats with milk and top with sliced apple.",
             "oats|80|g;milk|200|ml;apple|1|piece"},

            {"Corn Fritters",
             "Mix corn, flour, and egg, then fry until crisp.",
             "corn|1|can;flour|100|g;egg|1|piece"},

            {"Mushroom Pasta",
             "Saute mushrooms and garlic, then combine with pasta.",
             "pasta|200|g;mushroom|150|g;garlic|1|piece;cream|100|ml"}
        };

        for (String[] recipe : data) {
            ContentValues recipeValues = new ContentValues();
            recipeValues.put("name", recipe[0]);
            recipeValues.put("steps", recipe[1]);

            long recipeId = db.insert(
                "recipes", null, recipeValues
            );

            if (recipeId == -1) {
                continue;
            }

            String[] ingredients = recipe[2].split(";");

            for (String ingredient : ingredients) {
                // Correctly escape the vertical bar in Java regex.
                String[] parts = ingredient.split("\\|", -1);

                if (parts.length != 3) {
                    continue;
                }

                try {
                    ContentValues ingredientValues = new ContentValues();
                    ingredientValues.put("recipe_id", recipeId);
                    ingredientValues.put("ingredient_name", parts[0]);
                    ingredientValues.put(
                        "quantity",
                        Double.parseDouble(parts[1])
                    );
                    ingredientValues.put("unit", parts[2]);

                    db.insert(
                        "recipe_ingredients",
                        null,
                        ingredientValues
                    );
                } catch (NumberFormatException e) {
                    android.util.Log.e(
                        "DatabaseHelper",
                        "Invalid ingredient quantity: " + ingredient,
                        e
                    );
                }
            }
        }
    }

    @Override
    public void onUpgrade(
        SQLiteDatabase db,
        int oldVersion,
        int newVersion
    ) {
        // Keep pantry items and settings. Refresh only recipe data.
        db.execSQL("DROP TABLE IF EXISTS recipe_ingredients");
        db.execSQL("DROP TABLE IF EXISTS recipes");

        db.execSQL(
            "CREATE TABLE recipes (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "steps TEXT NOT NULL)"
        );

        db.execSQL(
            "CREATE TABLE recipe_ingredients (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "recipe_id INTEGER," +
            "ingredient_name TEXT," +
            "quantity REAL," +
            "unit TEXT)"
        );

        seedRecipes(db);
    }

    public List<PantryItem> getPantryItems() {
        List<PantryItem> items = new ArrayList<>();

        Cursor cursor = getReadableDatabase().query(
            "pantry_items",
            null,
            null,
            null,
            null,
            null,
            "name COLLATE NOCASE"
        );

        try {
            while (cursor.moveToNext()) {
                items.add(new PantryItem(
                    cursor.getLong(
                        cursor.getColumnIndexOrThrow("id")
                    ),
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("name")
                    ),
                    cursor.getDouble(
                        cursor.getColumnIndexOrThrow("quantity")
                    ),
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("unit")
                    ),
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("expiry_date")
                    )
                ));
            }
        } finally {
            cursor.close();
        }

        return items;
    }

    public long insertPantry(PantryItem item) {
        return getWritableDatabase().insert(
            "pantry_items",
            null,
            values(item)
        );
    }

    public int updatePantry(PantryItem item) {
        return getWritableDatabase().update(
            "pantry_items",
            values(item),
            "id=?",
            new String[]{String.valueOf(item.id)}
        );
    }

    public int deletePantry(long id) {
        return getWritableDatabase().delete(
            "pantry_items",
            "id=?",
            new String[]{String.valueOf(id)}
        );
    }

    private ContentValues values(PantryItem item) {
        ContentValues values = new ContentValues();
        values.put("name", item.name);
        values.put("quantity", item.quantity);
        values.put("unit", item.unit);
        values.put("expiry_date", item.expiryDate);
        return values;
    }

    public List<Recipe> getSuggestedRecipes() {
        List<Recipe> result = new ArrayList<>();

        Cursor cursor = getReadableDatabase().query(
            "recipes",
            null,
            null,
            null,
            null,
            null,
            "name"
        );

        try {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(
                    cursor.getColumnIndexOrThrow("id")
                );

                if (canMake(id)) {
                    result.add(new Recipe(
                        id,
                        cursor.getString(
                            cursor.getColumnIndexOrThrow("name")
                        ),
                        cursor.getString(
                            cursor.getColumnIndexOrThrow("steps")
                        )
                    ));
                }
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    private boolean canMake(long recipeId) {
        Map<String, Double> pantry = new HashMap<>();
        Map<String, String> units = new HashMap<>();

        for (PantryItem item : getPantryItems()) {
            String name = normalise(item.name);
            double quantity = toBase(item.quantity, item.unit);
            String unit = baseUnit(item.unit);

            pantry.put(
                name,
                pantry.getOrDefault(name, 0.0) + quantity
            );
            units.put(name, unit);
        }

        Cursor cursor = getReadableDatabase().query(
            "recipe_ingredients",
            null,
            "recipe_id=?",
            new String[]{String.valueOf(recipeId)},
            null,
            null,
            null
        );

        boolean canMake = true;

        try {
            while (cursor.moveToNext()) {
                String name = normalise(
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("ingredient_name")
                    )
                );

                double required = toBase(
                    cursor.getDouble(
                        cursor.getColumnIndexOrThrow("quantity")
                    ),
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("unit")
                    )
                );

                String requiredUnit = baseUnit(
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("unit")
                    )
                );

                if (!pantry.containsKey(name)
                    || !requiredUnit.equals(units.get(name))
                    || pantry.get(name) < required) {
                    canMake = false;
                    break;
                }
            }
        } finally {
            cursor.close();
        }

        return canMake;
    }

    public List<String> getIngredients(long recipeId) {
        List<String> ingredients = new ArrayList<>();

        Cursor cursor = getReadableDatabase().query(
            "recipe_ingredients",
            null,
            "recipe_id=?",
            new String[]{String.valueOf(recipeId)},
            null,
            null,
            null
        );

        try {
            while (cursor.moveToNext()) {
                ingredients.add(
                    cursor.getDouble(
                        cursor.getColumnIndexOrThrow("quantity")
                    ) + " " +
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("unit")
                    ) + " " +
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("ingredient_name")
                    )
                );
            }
        } finally {
            cursor.close();
        }

        return ingredients;
    }

public static String normalise(String value) {
    if (value == null) {
        return "";
    }

    String name = value.toLowerCase(Locale.US)
            .trim()
            .replaceAll("\\s+", " ");

    switch (name) {
        case "tomatoes": return "tomato";
        case "potatoes": return "potato";
        case "eggs": return "egg";
        case "onions": return "onion";
        case "beans": return "bean";
        case "carrots": return "carrot";
        case "mushrooms": return "mushroom";
        case "lentils": return "lentil";
        case "noodles": return "noodle";
        case "olives": return "olive";
        case "chillies":
        case "chilis": return "chilli";
        case "cooking oil":
        case "vegetable oil":
        case "sunflower oil":
        case "canola oil":
        case "olive oil": return "oil";
        case "loaf":
        case "loaves": return "bread";
        case "sugars": return "sugar";
        case "flours": return "flour";
        default: return name;
    }
}

    
private String baseUnit(String unit) {
    unit = unit.toLowerCase(Locale.US).trim();

    if (unit.equals("kg") || unit.equals("kilogram")
            || unit.equals("kilograms") || unit.equals("g")
            || unit.equals("gram") || unit.equals("grams")) {
        return "g";
    }

    if (unit.equals("l") || unit.equals("litre")
            || unit.equals("litres") || unit.equals("liter")
            || unit.equals("liters") || unit.equals("ml")
            || unit.equals("millilitre") || unit.equals("millilitres")
            || unit.equals("milliliter") || unit.equals("milliliters")) {
        return "ml";
    }

    if (unit.equals("pcs") || unit.equals("pieces")
            || unit.equals("piece") || unit.equals("loaf")
            || unit.equals("loaves")) {
        return "piece";
    }

    if (unit.equals("can") || unit.equals("cans")) {
        return "can";
    }

    return unit;
}


   
private double toBase(double quantity, String unit) {
    unit = unit.toLowerCase(Locale.US).trim();

    if (unit.equals("kg") || unit.equals("kilogram")
            || unit.equals("kilograms") || unit.equals("l")
            || unit.equals("litre") || unit.equals("litres")
            || unit.equals("liter") || unit.equals("liters")) {
        return quantity * 1000;
    }

    // Treat one loaf as approximately 12 bread slices for recipe matching.
    if (unit.equals("loaf") || unit.equals("loaves")) {
        return quantity * 12;
    }

    return quantity;
}

}
