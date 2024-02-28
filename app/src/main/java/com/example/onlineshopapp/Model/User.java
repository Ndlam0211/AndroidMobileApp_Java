package com.example.onlineshopapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    private Address address;
    private int id;
    private String email;
    private String username;
    private String password;
    private Name name;
    private String phone;
    private int __v;

    public User(Address address, int id, String email, String username, String password, Name name, String phone, int __v) {
        this.address = address;
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.__v = __v;
    }

    public User(){}

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    @Override
    public String toString() {
        return "User{" +
                "address=" + address +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name=" + name +
                ", phone='" + phone + '\'' +
                ", __v=" + __v +
                '}';
    }

    public class Address implements Serializable {
        private Geolocation geolocation;
        private String city;
        private String street;
        private int number;
        private String zipcode;

        public Address(Geolocation geolocation, String city, String street, int number, String zipcode) {
            this.geolocation = geolocation;
            this.city = city;
            this.street = street;
            this.number = number;
            this.zipcode = zipcode;
        }

        public Address(){}

        public Geolocation getGeolocation() {
            return geolocation;
        }

        public void setGeolocation(Geolocation geolocation) {
            this.geolocation = geolocation;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "geolocation=" + geolocation +
                    ", city='" + city + '\'' +
                    ", street='" + street + '\'' +
                    ", number=" + number +
                    ", zipcode='" + zipcode + '\'' +
                    '}';
        }

        public class Geolocation implements Serializable{
            private String lat;
            @SerializedName("long")
            private String Long;

            public Geolocation(String lat, String aLong) {
                this.lat = lat;
                Long = aLong;
            }

            public Geolocation(){}

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLong() {
                return Long;
            }

            public void setLong(String aLong) {
                Long = aLong;
            }

            @Override
            public String toString() {
                return "Geolocation{" +
                        "lat='" + lat + '\'' +
                        ", Long='" + Long + '\'' +
                        '}';
            }
        }
    }

    public class Name implements Serializable{
        private String firstname;
        private String lastname;

        public Name(String firstname, String lastname) {
            this.firstname = firstname;
            this.lastname = lastname;
        }

        public Name() {

        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        @Override
        public String toString() {
            return "Name{" +
                    "firstname='" + firstname + '\'' +
                    ", lastname='" + lastname + '\'' +
                    '}';
        }
    }
}
