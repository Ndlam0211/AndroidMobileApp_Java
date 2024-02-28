package com.example.onlineshopapp.Model;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class Product implements Serializable{
    @Json(name = "id")
    private int id;
    @Json(name = "title")
    private String title;
    @Json(name = "price")
    private float price;
    @Json(name = "category")
    private String category;
    @Json(name = "description")
    private String description;
    @Json(name = "image")
    private String image;
    @Json(name = "rating")
    private Rating rating;
    private int numberInCart;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    // toString() method

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", rating=" + rating +
                '}';
    }

    public class Rating implements Serializable {
        @Json(name = "rate")
        private float rate;
        @Json(name = "count")
        private int count;

        // Getters and Setters

        public float getRate() {
            return rate;
        }

        public void setRate(float rate) {
            this.rate = rate;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        // toString() method

        @Override
        public String toString() {
            return "Rating{" +
                    "rate=" + rate +
                    ", count=" + count +
                    '}';
        }
    }
}