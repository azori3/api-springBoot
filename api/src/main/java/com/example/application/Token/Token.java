package com.example.application.Token;

import com.example.application.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {




    @Id
    @GeneratedValue
    private Integer id;


    @Column(unique = true)
    private String token;

    private  TokenType tokenType;

    private boolean expired;


    private boolean revoked;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

}




