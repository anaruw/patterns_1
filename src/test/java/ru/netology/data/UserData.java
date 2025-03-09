package ru.netology.data;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@Value
@Builder
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