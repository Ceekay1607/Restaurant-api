package com.tma.restaurantapi.model.converter;

import com.tma.restaurantapi.model.dto.response.BillDetailResponse;
import com.tma.restaurantapi.model.BillDetail;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

/**
 * Converter class to convert a {@link BillDetail} entity to a {@link BillDetailResponse} DTO.
 * Implements the {@link Converter} interface for mapping purposes.
 */
public class BillDetailsConverter implements Converter<BillDetail, BillDetailResponse> {

    @Override
    public BillDetailResponse convert(MappingContext<BillDetail, BillDetailResponse> mappingContext) {
        BillDetailResponse desObject = new BillDetailResponse();
        BillDetail srcObject= mappingContext.getSource();

        desObject.setName(srcObject.getMenu().getName());
        desObject.setQuantity(srcObject.getQuantity());
        desObject.setPrice(srcObject.getMenu().getPrice());

        return desObject;
    }

}
