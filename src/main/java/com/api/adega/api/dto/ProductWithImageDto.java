package com.api.adega.api.dto;

import com.api.adega.api.entities.Product;
import lombok.Data;
import org.apache.tomcat.util.codec.binary.Base64;

@Data
public class ProductWithImageDto {

    private Product product;

    //@JsonIgnore
    private byte[] imageBytes;

    public String getImageBytesAsBase64() {
        if (imageBytes != null) {
            return Base64.encodeBase64String(imageBytes);
        }
        return null;
    }
}
