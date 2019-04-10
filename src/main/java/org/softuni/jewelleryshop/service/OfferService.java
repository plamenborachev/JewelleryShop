package org.softuni.jewelleryshop.service;

import org.softuni.jewelleryshop.domain.models.service.OfferServiceModel;

import java.util.List;

public interface OfferService {

    List<OfferServiceModel> findAllOffers();

    List<OfferServiceModel> findAllByCategory(String category);
}
