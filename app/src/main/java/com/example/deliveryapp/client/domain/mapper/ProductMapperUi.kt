package com.example.deliveryapp.client.domain.mapper

import com.example.deliveryapp.client.domain.model.Coupon
import com.example.deliveryapp.client.domain.model.CouponSerializable
import com.example.deliveryapp.client.domain.model.Product
import com.example.deliveryapp.client.domain.model.ProductSerializable

fun ProductSerializable.toProduct(): Product{
    return Product(
        id = id,
        name = name,
        description = description,
        price = price,
        image = image,
        image2 = image2,
        image3 = image3,
        idCategory = idCategory,
        ranting = ranting,
        coupons = coupons.map {
            it.toCoupon()
        },
        quantity = quantity
    )
}

fun Product.toProductSerializable(): ProductSerializable {
    return ProductSerializable(
        id = id,
        name = name,
        description = description,
        price = price,
        image = image,
        image2 = image2,
        image3 = image3,
        idCategory = idCategory,
        ranting = ranting,
        coupons = coupons.map {
            it.toCouponUiModel()
        },
        quantity = quantity
    )
}

fun CouponSerializable.toCoupon(): Coupon{
    return Coupon(
        id = id,
        code = code,
        discountPercentage = discountPercentage,
        expirationDate = expirationDate
    )
}

fun Coupon.toCouponUiModel(): CouponSerializable {
    return CouponSerializable(
        id = id,
        code = code,
        discountPercentage = discountPercentage,
        expirationDate = expirationDate
    )
}
