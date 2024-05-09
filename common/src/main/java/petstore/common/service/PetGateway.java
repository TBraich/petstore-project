package petstore.common.service;

import petstore.common.dto.gw.PetInfo;

public interface PetGateway {
    PetInfo find(String petId);
}
