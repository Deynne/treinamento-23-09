package com.minsait.treinamento.model.entities.enumerators.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

public enum CargosSistema implements GrantedAuthority{
    ADMINISTRADOR("ADMIN"),
    CLIENTE("CLIENT");
    
    private String cargo;

    private final String PREFIX = "ROLE_";
    
    private CargosSistema(String cargo) {
        this.cargo = PREFIX.concat(cargo);
    }

    @Override
    public String getAuthority() {
        return this.cargo;
    }

    public static List<CargosSistema> getCargoSistema(Collection<? extends GrantedAuthority> collection) {
        return collection.stream().map(CargosSistema::getCargoSistema).filter(cargo -> cargo != null).collect(Collectors.toList());
    }
    
    public static CargosSistema getCargoSistema(GrantedAuthority authority) {
        CargosSistema[] cargos = CargosSistema.values();
        for(CargosSistema cargo : cargos) {
            if(authority.getAuthority().equals(cargo.getAuthority())) {
                return cargo;
            }
        }
        return null;
    }

}
