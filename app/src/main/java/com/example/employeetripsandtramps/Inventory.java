package com.example.employeetripsandtramps;

public class Inventory {

        String purchaseDate;
        String inventory_type;
        String brand;
        String amout;

        public String getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(String purchaseID) {
            this.purchaseDate = purchaseID;
        }

        public String getInventory_type() {
            return inventory_type;
        }

        public void setInventory_type(String inventory_type) {
            this.inventory_type = inventory_type;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getAmout() {
            return amout;
        }

        public void setAmout(String amout) {
            this.amout = amout;
        }
}

