package br.com.ozelo.enums;

public enum Combustivel {

    FLEX("Flex Eta/Gas"),ETANOL("Etanol"),GASOLINA("Gasolina"),DIESEL("Diesel"),HIBRIDO("Hibrido"),FLEX_GNV("Flex/GNV"),GAS_GNV("Gas/GNV"),ETA_GNV("Eta/GNV");
    
    private final String description;
    
    Combustivel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
        
}
