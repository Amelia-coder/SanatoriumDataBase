package ru.vsu.cs.bordyugova_l_n.database.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private String email;
    private Integer room; // Only accept room ID as an integer
    private String resortCard;
}