package ru.lab1.tg.domain;

//public class Product {
//
//    private final String name;
//    private final Double price;
//
//    public Product(String name, Double price){
//        this.name = name;
//        this.price = price;
//    }
//
//    public Double getPrice() {
//        return price;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//
//}

public record Product(String name, Double price) {

}