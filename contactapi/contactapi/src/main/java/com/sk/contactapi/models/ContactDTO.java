package com.sk.contactapi.models;

public record ContactDTO(
    String name,
    String email,
    String phone,
    String address,
    int age,
    boolean active
) {
}
