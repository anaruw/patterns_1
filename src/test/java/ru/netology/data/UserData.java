package ru.netology.data;

import lombok.NonNull;
import lombok.Value;
import lombok.With;

@Value
public class UserData {
    @With
    @NonNull
    String city;
    @With
    @NonNull
    String date;
    @With
    @NonNull
    String name;
    @With
    @NonNull
    String phone;
}