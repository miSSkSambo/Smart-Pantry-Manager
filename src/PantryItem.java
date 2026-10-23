package com.example.smartpantry.model;
public class PantryItem { public long id; public String name, unit, expiryDate; public double quantity; public PantryItem(long id,String name,double quantity,String unit,String expiryDate){this.id=id;this.name=name;this.quantity=quantity;this.unit=unit;this.expiryDate=expiryDate;} }
