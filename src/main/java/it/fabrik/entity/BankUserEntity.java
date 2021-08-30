package it.fabrik.entity;

import it.fabrik.component.AttributeEncryptor;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "BANKUSER")
public class BankUserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bankuser_seq")
    @SequenceGenerator(name = "bankuser_seq", sequenceName = "bankuser_seq", allocationSize = 1)
    private Integer id;

    @Column(unique = true)
    private String accountId;

    @Convert(converter = AttributeEncryptor.class)
    private String password;
}
