package com.weather.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table()
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private int degrees;

    @Column()
    private String cloudiness;

    @Column()
    private String pressure;

    @Column()
    private Date date;
}
