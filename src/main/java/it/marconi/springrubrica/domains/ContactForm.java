package it.marconi.springrubrica.domains;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactForm {
    
    @NotEmpty
    @Size(max = 30)
    private String name;

    @NotEmpty
    @Size(max = 50)
    private String surname;

    @NotEmpty
    @Pattern(regexp = "([0-9]{10})") //fa in modo che si possano inserire solo 10 caratteri numerici
    private String phone;

    //@Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") pattern scritto con regexp
    @Email //pattern più semplice che fa già tutto
    private String email;

    
}
