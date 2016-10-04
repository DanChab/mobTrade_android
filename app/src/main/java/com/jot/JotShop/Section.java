package com.jot.JotShop;

/**
 * Created by D4n on 10/1/2016.
 */

public class Section {
        private String name;
        private String description;



        public Section(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
}
